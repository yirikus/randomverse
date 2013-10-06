package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SimpleSprite that consists of tiles (multiple sprites arranged in a grid)
 */
public class MultiSprite implements Sprite{

    private List<Tile> tiles;
    private boolean active;

    /**
     * X Position of tile on a [0,0] position
     */
    private int locX;
    /**
     * Y Position of tile on a [0,0] position
     */
    private int locY;

    /**
     * Creates empty multisprites, tiles are expected to be added by calling provided methods
     */
    public MultiSprite(int x, int y){
        this.locX = x;
        this.locY = y;
        this.tiles = new ArrayList<Tile>();
    }

    /**
     * Adds sprite to a grid,
     * throws exception if on desired position already is a sprite
     *
     * @param x
     * @param y
     * @param sprite
     */
    public void addTile(int x, int y, Sprite sprite){
        Tile newTile = new Tile(x, y, sprite);
        for (Tile t : tiles) {
            if (t.samePositionAs(newTile)) {
                throw new IllegalArgumentException("Trying to put a tile on a postion that is already taken: [" + x + ", " + y + "]");
            }
        }

        tiles.add(newTile);
    }

    @Override
    public int getWidth() {
        throw new UnsupportedOperationException("not implemented yet"); //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getHeight() {
        throw new UnsupportedOperationException("not implemented yet"); //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean a) {
        this.active = a;
    }

    @Override
    public void setPosition(int x, int y) {
        for (Tile t : tiles) {
            int newX = (t.getX() * Tile.DEFAULT_SIZE) + x;
            int newY = (t.getY() * Tile.DEFAULT_SIZE) + y;
            t.getSprite().setPosition(newX,newY);
        }
    }

    @Override
    public void translate(int xDist, int yDist) {
        throw new UnsupportedOperationException("not implemented yet"); //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getXPosn() {
        if (!tiles.isEmpty()) {
            return tiles.get(0).getSprite().getXPosn();
        } else {
            return 0;
        }
    }

    @Override
    public int getYPosn() {
        if (!tiles.isEmpty()) {
            return tiles.get(0).getSprite().getYPosn();
        } else {
            return 0;
        }
    }

    @Override
    public void setStep(int dx, int dy) {
        for (Tile t : tiles) {
            t.getSprite().setStep(dx, dy);
        }
    }

    @Override
    public int getXStep() {
        if (!tiles.isEmpty()) {
            return tiles.get(0).getSprite().getXStep();
        } else {
            return 0;
        }
    }

    @Override
    public int getYStep() {
        if (!tiles.isEmpty()) {
            return tiles.get(0).getSprite().getYStep();
        } else {
            return 0;
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        throw new UnsupportedOperationException("not implemented yet"); //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateSprite() {
        for (Tile t : tiles) {
            t.getSprite().updateSprite();
        }
    }

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        for (Tile t : tiles) {
            t.getSprite().drawSprite(g, ims);
        }
    }
}
