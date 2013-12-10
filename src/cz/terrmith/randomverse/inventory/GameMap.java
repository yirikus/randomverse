package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.geometry.RelativePosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/10/13
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameMap extends GridMenu {

    private List<GridLocation> explored = new ArrayList<GridLocation>();


    public GameMap(int rows, int columns, int cellSize, Position position) {
        super(rows, columns, cellSize, position);
        setX(rows / 2);
        setY(columns / 2);
        markExplored();
    }

    /**
     * Marks current position as explored
     */
    public void markExplored() {
        explored.add(new GridLocation(getX(), getY()));
    }

    /**
     * returns true, if it is possible to select given location
     * @param loc
     * @return
     */
    private boolean canMove(GridLocation loc) {
        RelativePosition relativePosition = GridLocation.getRelativePositionTo(loc, explored);
        return (loc.equals(RelativePosition.CONTAINS)
                || loc.equals(RelativePosition.NEIGHBOURHOOD_4));
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

    @Override
    public void drawMenu(Graphics g) {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                RelativePosition relativePosition = GridLocation.getRelativePositionTo(new GridLocation(i, j), explored);
                if (relativePosition.equals(RelativePosition.CONTAINS)) {
                    g.setColor(Color.WHITE);
                } else if (relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4)) {
                    g.setColor(Color.DARK_GRAY);
                }

                if (relativePosition.equals(RelativePosition.NEIGHBOURHOOD_4)
                    || relativePosition.equals(RelativePosition.CONTAINS)) {
                    g.drawRect((int)getPosition().getX() + i * getCellSize(),
                               (int)getPosition().getY() + j * getCellSize(),
                                getCellSize(), getCellSize());
                }
            }
        }
        g.setColor(Color.green);
        g.drawRect((int) getPosition().getX() + getX() * getCellSize(),
                   (int) getPosition().getY() + getY() * getCellSize(),
                    getCellSize(), getCellSize());
    }
}
