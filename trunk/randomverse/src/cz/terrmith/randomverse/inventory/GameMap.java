package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.geometry.RelativePosition;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.world.LevelDebrisField;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Grid that represent global game map
 */
public class GameMap extends GridMenu {

    private final Sprite player;
    private Set<GridLocation> explored = new HashSet<GridLocation>();


    public GameMap(int rows, int columns, int cellSize, Position position, Sprite player) {
        super(rows, columns, cellSize, position);
        setX(rows / 2);
        setY(columns / 2);
        markExplored();
        this.player = player;
    }

    /**
     * Marks current position as explored
     */
    public void markExplored() {
        explored.add(new GridLocation(getX(), getY()));
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
	public World createLevel(SpriteCollection spriteCollection) {
		if (getX() % 2 == 1) {
			return new LevelOne(spriteCollection);
		} else {
			return new LevelDebrisField(spriteCollection, this.player);
		}
	}

    @Override
    public void drawMenu(Graphics g) {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {

                RelativePosition relativePosition = GridLocation.getRelativePositionTo(new GridLocation(i, j), explored);

                if (relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4)
                    || relativePosition.equals(RelativePosition.CONTAINS)) {

	                if (i % 2 == 0) {
		                g.setColor(new Color(112, 146, 190));
	                }  else {
		                g.setColor(new Color(185, 122, 87));
	                }
	                g.fillRect((int)getPosition().getX() + i * getCellSize() + 1,
	                           (int)getPosition().getY() + j * getCellSize() + 1,
	                           getCellSize() - 1, getCellSize() - 1);

	                if (relativePosition.equals(RelativePosition.CONTAINS)) {
		                g.setColor(Color.WHITE);
	                } else if (relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4)) {
		                g.setColor(Color.DARK_GRAY);
	                }
                    g.drawRect((int)getPosition().getX() + i * getCellSize() + 1,
                               (int)getPosition().getY() + j * getCellSize() + 1,
                                getCellSize() - 1, getCellSize() - 1);

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
        markExplored();
    }
}
