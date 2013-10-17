package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.geometry.Position;

import java.awt.*;

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
    private Position position;
    private int x = 0;
   private int y = 0;

    public GridMenu(int rows, int columns, Position position) {
        this.rows = rows;
        this.columns = columns;
        this.position = position;
    }

    public void selectRight() {
        if(y < (columns - 1)) {
            y++;
        }
    }

    public void selectLeft() {
        if(y > 0) {
            y--;
        }
    }

    public void selectBelow() {
        if(x < (rows - 1)) {
            x++;
        }
    }

    public void selectAbove() {
        if(x > 0) {
            x--;
        }
    }

    public void drawMenu(Graphics g) {

    }
}
