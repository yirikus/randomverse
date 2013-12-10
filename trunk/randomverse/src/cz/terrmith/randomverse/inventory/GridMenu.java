package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.geometry.Position;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 17.10.13
 * Time: 21:02
 * To change this template use File | Settings | File Templates.
 */
public class GridMenu {
   private int rows;
   private int columns;
    private int cellSize;
    private Position position;
    /**
     * Selected cell
     */
    private int x = 0;
   private int y = 0;

    public GridMenu(int rows, int columns, int cellSize, Position position) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        this.position = position;
    }

    public void selectRight() {
        if(x < (columns - 1)) {
            x++;
        }
    }

    public void selectLeft() {
        if(x > 0) {
            x--;
        }
    }

    public void selectBelow() {
        if(y < (rows - 1)) {
            y++;
        }
    }

    public void selectAbove() {
        if(y > 0) {
            y--;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Position getPosition() {
        return position;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void drawMenu(Graphics g) {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                g.setColor(Color.white);
                g.drawRect((int)position.getX() + i * cellSize + 1,
                           (int)position.getY() + j * cellSize + 1,
                           cellSize - 1 ,cellSize - 1);
            }
        }
        g.setColor(Color.green);
        g.drawRect((int)position.getX() + x * cellSize + 1,
                (int)position.getY() + y * cellSize + 1,
                cellSize - 1 ,cellSize - 1);
    }
}
