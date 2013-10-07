package cz.terrmith.randomverse.core.sprite;

import java.awt.*;

/**
 * Data objects that holds a sprite and its position a tile grid
 */
public class Tile {

    /**
     * Default tile size
     */
    public static int DEFAULT_SIZE = 32;

    private int x;
    private int y;
    private final Sprite sprite;

    public Tile(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
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

    /**
     * Returns true if this tile has same position as given tile
     * @param tile
     * @return true if tiles has same position, false otherwise
     */
    public boolean samePositionAs(Tile tile) {
        if(tile == null) {
            return false;
        }
        return this.getX() == tile.getX()
            && this.getY() == tile.getY();
    }
}
