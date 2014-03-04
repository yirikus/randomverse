package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.geometry.RelativePosition;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.world.LevelDebrisField;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Grid that represent global game map
 */
public class GameMap extends GridMenu {

    private final Sprite player;
    private final ArtificialIntelligence ai;
    private final SpriteCollection spriteCollection;
    private Set<GridLocation> explored = new HashSet<GridLocation>();
    private GridLocation currentLocation;
    private World[][] worlds;
    private static Random random = new Random();


    public GameMap(int rows, int columns, int cellSize, Position position, Sprite player, ArtificialIntelligence ai, SpriteCollection spc) {
        super(rows, columns, cellSize, position);
        this.spriteCollection = spc;
        this.worlds = generateGameMap(rows, columns);
        this.ai = ai;
        setX(rows / 2);
        setY(columns / 2);
        markExplored();
        this.player = player;
    }

    private World[][] generateGameMap(int rows, int columns) {
        World[][] worldArray = new World[columns][rows];
        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                if (random.nextInt(2) == 1) {
                    worldArray[c][r] = new LevelOne(spriteCollection, ai);
                } else {
                    worldArray[c][r] = new LevelDebrisField(spriteCollection, this.player, ai);
                }
            }
        }
        worldArray[getX()][getY()] = new LevelOne(spriteCollection,ai);

        return worldArray;
    }

    /**
     * Marks current position as explored
     */
    public void markExplored() {
        currentLocation = new GridLocation(getX(), getY());
        explored.add(currentLocation);
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
	public World createLevel() {
        return worlds[getX()][getY()];
	}

    @Override
    public void drawMenu(Graphics g) {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {

                RelativePosition relativePosition = GridLocation.getRelativePositionTo(new GridLocation(i, j), explored);

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
                    if (currentLocation.equals(new GridLocation(i,j))) {
                        g.setColor(Color.WHITE);
                        g.drawRect((int)getPosition().getX() + i * getCellSize() + 1,
                               (int)getPosition().getY() + j * getCellSize() + 1,
                                getCellSize() - 1, getCellSize() - 1);
                    }
                }
            }
        }
        g.setColor(Color.green);
        g.drawRect((int) getPosition().getX() + getX() * getCellSize() + 1,
                   (int) getPosition().getY() + getY() * getCellSize() + 1,
                    getCellSize() - 1, getCellSize() - 1);
    }

    public void reset() {
        explored = new HashSet<GridLocation>();
        setX(getRows() / 2);
        setY(getColumns() / 2);
        worlds = generateGameMap(getRows(), getColumns());
        markExplored();
    }
}