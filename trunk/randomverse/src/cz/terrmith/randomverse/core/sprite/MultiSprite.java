package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SimpleSprite that consists of tiles (multiple sprites arranged in a grid)
 */
public class MultiSprite implements Sprite{

    private List<Tile> tiles;
    private boolean active = true;

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

    public void addTile(Tile newTile) {
        for (Tile t : tiles) {
            if (t.samePositionAs(newTile)) {
                throw new IllegalArgumentException("Trying to put a tile on a postion that is already taken: [" + newTile.getTileX() + ", " + newTile.getTileY() + "]");
            }
        }

        tiles.add(newTile);
        setPosition(this.locX, this.locY);
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
        addTile(newTile);
    }

	public List<Tile> getTiles(){
		return Collections.unmodifiableList(this.tiles);
	}

    @Override
    public int getHeight() {
        int maxHeight = 0;
        int minHeight = 0;

        // find boundaries
        for (Tile t : tiles) {
            maxHeight = Math.max(maxHeight, t.getTileY());
            minHeight = Math.min(minHeight, t.getTileY());
        }
        int widthInTiles = Math.abs(maxHeight) + Math.abs(minHeight) + (maxHeight  >= 0 && minHeight <= 0 ? 1 : 0);

        return widthInTiles * Tile.DEFAULT_SIZE;
    }

    @Override
    public int getWidth() {
        int maxWidth = 0;
        int minWidth = 0;

        // find boundaries
        for (Tile t : tiles) {
            maxWidth = Math.max(maxWidth,t.getTileX());
            minWidth = Math.min(minWidth, t.getTileX());

        }
        int widthInTiles = Math.abs(maxWidth) + Math.abs(minWidth) + (maxWidth  >= 0 && minWidth <= 0 ? 1 : 0);

        return widthInTiles * Tile.DEFAULT_SIZE;
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
    public void setPosition(double x, double y) {
        for (Tile t : tiles) {
            t.setSpritePosition(new Position(x,y));
        }
    }

    @Override
    public void translate(double xDist, double yDist) {
        for (Tile t : tiles) {
            t.getSprite().translate(xDist, yDist);
        }
    }

    @Override
    public double getXPosn() {
        if (!tiles.isEmpty()) {
            return Tile.getZeroSpritePosition(tiles.get(0)).getX();
        } else {
            return 0;
        }
    }

    @Override
    public double getYPosn() {
        if (!tiles.isEmpty()) {
            return Tile.getZeroSpritePosition(tiles.get(0)).getY();
        } else {
            return 0;
        }
    }

    @Override
    public void setStep(double dx, double dy) {
        for (Tile t : tiles) {
            t.getSprite().setStep(dx, dy);
        }
    }

    @Override
    public double getXStep() {
        if (!tiles.isEmpty()) {
            return tiles.get(0).getSprite().getXStep();
        } else {
            return 0;
        }
    }

    @Override
    public double getYStep() {
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

    @Override
    public boolean collidesWith(Sprite sprite) {
	    for (Tile t: tiles) {
			if (t.getSprite().collidesWith(sprite)) {
				return true;
			}
        }
	    return false;
    }

    @Override
    public void flipHorizontal() {
        int maxWidth = 0;
        int minWidth = 0;

        // find boundaries
        for (Tile t : tiles) {
            maxWidth = Math.max(maxWidth,t.getTileX());
            minWidth = Math.min(minWidth, t.getTileX());

        }
        //flip
        for (Tile t : tiles) {
            //normalize
            int nX = t.getTileX() - minWidth;
            //compute flipped value
            t.setTileX(maxWidth - nX);
            //assign
            t.getSprite().flipHorizontal();
        }
        setPosition(getXPosn(), getYPosn());
    }

    @Override
    public void flipVertical() {
        int maxHeight = 0;
        int minHeight = 0;

        for (Tile t : tiles) {
            maxHeight = Math.max(maxHeight,t.getTileY());
            minHeight = Math.min(minHeight,t.getTileY());
        }
        for (Tile t : tiles) {
            //normalize
            int nY = t.getTileY() - minHeight;
            //compute flipped value
            t.setTileY(maxHeight - nY);
            //assign
            t.getSprite().flipVertical();
        }
       System.out.println("flip V");
        setPosition(getXPosn(), getYPosn());
    }
}
