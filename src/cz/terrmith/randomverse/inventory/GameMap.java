package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.Dialog;
import cz.terrmith.randomverse.core.dialog.DialogCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.geometry.RelativePosition;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.util.StringUtils;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.world.LevelDebrisField;
import cz.terrmith.randomverse.world.LevelEmpty;
import cz.terrmith.randomverse.world.LevelInvaders;
import cz.terrmith.randomverse.world.LevelMinefield;
import cz.terrmith.randomverse.world.LevelOne;
import cz.terrmith.randomverse.world.LevelSpaceHighWay;
import cz.terrmith.randomverse.world.events.EventResult;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Grid that represent global game map
 */
public class GameMap extends GridMenu {

//    private final Ship playerSprite;
    private final ArtificialIntelligence ai;
    private final SpriteCollection spriteCollection;
    private final Map<EventResult, NavigableTextCallback<Mission>> callbacks;
    private final Randomverse stateMachine;
    private Set<GridLocation> explored = new HashSet<GridLocation>();
    private GridLocation currentLocation;
    private World[][] worlds;
    private static Random random = new Random();
    private List<Mission> missions = new ArrayList<Mission>();

    //todo ugly dependency on MapState to init callbacks
    public GameMap(int rows, int columns, int cellSize, Position position, Randomverse stateMachine, ArtificialIntelligence ai,
                   SpriteCollection spc, Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        super(rows, columns, cellSize, position);
        this.callbacks = callbacks;
        this.spriteCollection = spc;
        this.ai = ai;
        this.stateMachine = stateMachine;

        reset();
    }

    private World[][] generateGameMap(int rows, int columns) {
        World[][] worldArray = new World[columns][rows];
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                switch (random.nextInt(10)) {
                    case 0:
                        worldArray[c][r] = new LevelSpaceHighWay(spriteCollection, ai, callbacks);
                        break;
                    case 1:
                        worldArray[c][r] = new LevelDebrisField(spriteCollection, this.stateMachine.getPlayer().getSprite(), ai, callbacks);
                        break;
                    case 2:
                        worldArray[c][r] = new LevelMinefield(spriteCollection, this.stateMachine.getPlayer(), ai, callbacks);
                        break;
                    case 3:
                        worldArray[c][r] = new LevelOne(spriteCollection, ai, callbacks);
                        break;
                    case 4:
                        worldArray[c][r] = new LevelInvaders(spriteCollection, ai, callbacks);
                        break;
                    default:
                        worldArray[c][r] = new LevelEmpty(spriteCollection, callbacks);
                        break;
                }
            }
        }
        worldArray[getX()][getY()] = new LevelOne(spriteCollection,ai, callbacks);

        return worldArray;
    }

    /**
     * Marks current position as explored
     */
    public void markExplored() {
        currentLocation = new GridLocation(getX(), getY());
        completeMisssions();
        explored.add(currentLocation);
    }

    private void completeMisssions() {
        for (Mission mission : this.missions) {
            if (currentLocation.equals(mission.getTargetLocation())) {
               if (!mission.isCompleted()) {
                   mission.setCompleted(true);
                   stateMachine.getPlayer().setMoney(stateMachine.getPlayer().getMoney() + mission.getRewardMoney());
                   System.out.println("Mission '" + mission.getName() + "' completed! You gain " + mission.getRewardMoney() + " credits!");
                   DialogCallback callback = new DialogCallback() {
                       @Override
                       public void onClose(Dialog d) {
                           System.out.println("Compleete mission dialog closed");
                       }
                   };
                   stateMachine.showDialog(new Dialog("Mission '" + mission.getName() + "' completed! You gain " + mission.getRewardMoney() + " credits!",200,200,400,200, callback));

               }
            }
        }
    }

    /**
     * returns true, if it is possible to select given location
     * @param loc location
     * @return true if it is possible to move to given location
     */
    private boolean canMove(GridLocation loc) {
        RelativePosition relativePosition = GridLocation.getRelativePositionTo(loc, explored);
        return (relativePosition.equals(RelativePosition.CONTAINS)
                || relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4));
    }

    @Override
    public void selectRight() {
        GridLocation newPosition = new GridLocation(getX() + 1, getY());
        if (canMove(newPosition)) {
            super.selectRight();
        }
    }

    @Override
    public void selectLeft() {
        GridLocation newPosition = new GridLocation(getX() - 1, getY());
        if (canMove(newPosition)) {
            super.selectLeft();
        }
    }

    @Override
    public void selectBelow() {
        GridLocation newPosition = new GridLocation(getX(), getY() + 1);
        if (canMove(newPosition)) {
            super.selectBelow();
        }
    }

    @Override
    public void selectAbove() {
        GridLocation newPosition = new GridLocation(getX(), getY() - 1);
        if (canMove(newPosition)) {
            super.selectAbove();
        }
    }

	/**
	 * Creates a level based on current position
	 * @return level to be played
	 * @param spriteCollection sprite collection
	 */
	public World getCurrentWorld() {
        return worlds[getX()][getY()];
	}

    public void addMission(Mission m) {
        if (m != null) {
            System.out.println("mission added " + m.getTargetLocation());
            this.missions.add(m);
        }
    }

    @Override
    public void drawMenu(Graphics g) {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {

                final GridLocation location = new GridLocation(i, j);
                RelativePosition relativePosition = GridLocation.getRelativePositionTo(location, explored);

                if (relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4)
                    || relativePosition.equals(RelativePosition.CONTAINS)) {

                    Position position = Position.sum (getPosition(),
                                                      new Position(i * getCellSize() + 1, j * getCellSize() + 1));
                    worlds[i][j].drawMapIcon(g, position, getCellSize() - 1);

	                if (relativePosition.equals(RelativePosition.CONTAINS)) {

	                } else if (relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4)) {
		                //g.setColor(Color.DARK_GRAY);
                        g.setColor(new Color(100, 135, 177, 100));
                        g.fillRect((int)getPosition().getX() + i * getCellSize() + 1,
                                (int)getPosition().getY() + j * getCellSize() + 1,
                                getCellSize() - 1, getCellSize() - 1);
	                }
                    if (currentLocation.equals(location)) {
                        g.setColor(Color.WHITE);
                        g.drawRect((int)getPosition().getX() + i * getCellSize() + 1,
                               (int)getPosition().getY() + j * getCellSize() + 1,
                                getCellSize() - 1, getCellSize() - 1);
                    }
                }

                for (Mission m : this.missions) {
                    if (!m.isCompleted() && m.getTargetLocation().equals(location)) {
                        //mark mission areas
                        g.setColor(new Color(239, 255, 0,100));
                        g.fillRect((int)getPosition().getX() + i * getCellSize() + 1,
                                (int)getPosition().getY() + j * getCellSize() + 1,
                                getCellSize() - 1, getCellSize() - 1);

                        //draw mission description if selected
                        if (new GridLocation(getX(), getY()).equals(m.getTargetLocation())) {
                            g.setColor(Color.YELLOW);
                            StringUtils.drawString(g, m.getDescription(), 450, GameWindow.SCREEN_H - 100, 300);
                        }
                        break;
                    }
                }
            }
        }
        g.setColor(Color.green);
        g.drawRect((int) getPosition().getX() + getX() * getCellSize() + 1,
                   (int) getPosition().getY() + getY() * getCellSize() + 1,
                    getCellSize() - 1, getCellSize() - 1);

        getCurrentWorld().drawScannerInfo(g, new Position(50, GameWindow.SCREEN_H - 100), this.stateMachine.getPlayer().getSprite().getScannerStrength());
    }

    /**
     * TODO remove reset or turn to singleton
     */
    public void reset() {
        explored = new HashSet<GridLocation>();
        missions.clear();
        setX(getColumns() / 2);
        setY(getRows() / 2);
        worlds = generateGameMap(getRows(), getColumns());
        markExplored();
    }
}
