package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.collision.Collision;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
	private String status = DefaultSpriteStatus.DEFAULT.name();

	/**
	 * Creates empty multisprites, tiles are expected to be added by calling provided methods
	 *
	 * @param x x position of [0,0] tile
	 * @param y y position of [0,0] tile
	 * @param tiles tile list
	 */
    public MultiSprite(int x, int y, List<Tile> tiles){
        this.locX = x;
        this.locY = y;
        if (tiles == null) {
            this.tiles = new ArrayList<Tile>();
        } else {
            this.tiles = tiles;
        }
    }

    /**
     * Creates empty multisprites, tiles are expected to be added by calling provided methods
     */
    public MultiSprite(int x, int y){
        this(x, y, null);
    }

    /**
     * Copy constructor
     * @param sprite instance that should be copied
     */
    public MultiSprite(MultiSprite sprite) {
	    this((int)sprite.getXPosn(), (int)sprite.getYPosn(), Tile.cloneTiles(sprite.getTiles()));
    }
	/**
	 * Adds sprite to a grid,
	 * throws exception if on desired position already is a sprite
	 */
    public void addTile(Tile newTile) {
        for (Tile t : tiles) {
            if (t.samePositionAs(newTile)) {
                throw new IllegalArgumentException("Trying to put a tile on a postion that is already taken: [" + newTile.getTileX() + ", " + newTile.getTileY() + "]");
            }
        }

        tiles.add(newTile);
	    newTile.getSprite().setParent(this);
        setPosition(this.locX, this.locY);
    }

    /**
     * Adds sprite to a grid,
     * throws exception if on desired position already is a sprite
     *
     * @param x tile x
     * @param y tile y
     * @param sprite tile sprite
     */
    public void addTile(int x, int y, SimpleSprite sprite){
        if (sprite == null)  {
            throw new IllegalArgumentException("Tile must have a sprite");
        }
        Tile newTile = new Tile(x, y, sprite);
	    sprite.setParent(this);
        addTile(newTile);
    }

	/**
	 * Removes tile on given grid coordinates
	 * @param x tile x
	 * @param y tile y
	 */
	public void removeTile(int x, int y) {
		Iterator<Tile> it = tiles.iterator();
		while (it.hasNext()) {
			Tile t = it.next();
			if (t.getTileY() == y && t.getTileX() == x) {
				it.remove();
				// there can not be two tiles on the same position
				break;
			}
		}
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

    /**
     * Computes bounding box from active tile sprites
     * @return
     */
    @Override
    public Rectangle getBoundingBox() {
        Tile top = null;
	    Tile left = null;
	    Tile right = null;
	    Tile bottom = null;

	    for (Tile t : getTiles()) {
			if (t.getSprite().isActive() && (left == null || t.getTileX() < left.getTileX())) {
				left = t;
			}

		    if (t.getSprite().isActive() && (right == null || t.getTileX() > right.getTileX())) {
				right = t;
		    }

		    if (t.getSprite().isActive() && (top == null || t.getTileY() < top.getTileY())) {
				top = t;
		    }

		    if (t.getSprite().isActive() && (bottom == null || t.getTileY() > bottom.getTileY())) {
				bottom = t;
		    }
	    }
	    return new Rectangle((int)left.getSprite().getXPosn(),
	                         (int)top.getSprite().getYPosn(),
	                         left.getSprite().getWidth() + (int)right.getSprite().getXPosn() - (int)left.getSprite().getXPosn(),
	                         top.getSprite().getHeight() + (int)bottom.getSprite().getYPosn() - (int)top.getSprite().getYPosn());
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

	/**
	 * Finds collisions between two multisprites and returns detailed collision information
	 * @param sprite multisprite
	 * @return list of collisions
	 */
	public List<Collision> findCollisionCollections(Sprite sprite) {
		List<Collision> collidingSprites = new ArrayList<Collision>();
		for (Tile t: tiles) {
			Sprite s = t.getSprite();
			if (!DefaultSpriteStatus.DEAD.name().equals(s.getStatus())) {

				List<Sprite> collisions = sprite.collidesWith(s);
				if (!collisions.isEmpty()) {
					collidingSprites.add(new Collision(s, collisions));
				}
			}
		}
		return collidingSprites;
	}

	@Override
	public List<Sprite> collidesWith(Sprite sprite) {
		List<Sprite> collidingSprites = new ArrayList<Sprite>();
		for (Tile t: tiles) {
			Sprite s = t.getSprite();
			if (!s.collidesWith(sprite).isEmpty() && !DefaultSpriteStatus.DEAD.name().equals(s.getStatus())) {
				collidingSprites.add(s);
			}
		}
		return collidingSprites;
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
        setPosition(getXPosn(), getYPosn());
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Sprite copy() {
		return new MultiSprite(this);
	}

	@Override
	public void setParent(Sprite parent) {
		throw new UnsupportedOperationException("Multisprite can not have parent");
	}

	@Override
	public Sprite getParent() {
		throw new UnsupportedOperationException("Multisprite can not have parent");
	}

    @Override
    public Position getMovementVector() {
        //movement vector is the same for all tiles
        Sprite sprite = tiles.get(0).getSprite();
        return sprite.getMovementVector();
    }

    @Override
    public void collide(Sprite s) {
        throw new UnsupportedOperationException("Collide should be called on tiles, not on parent multisprite");
    }

    @Override
    public double getSpeed() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void setSpeed(double speed) {
        throw new UnsupportedOperationException("not implemented yet"); //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Iterates over tiles and recomputes sprite positions
     */
    public void revalidatePosition() {
        Position p = Tile.getZeroSpritePosition(tiles.get(0));
        setPosition(p.getX(), p.getY());
    }
}
