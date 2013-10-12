package cz.terrmith.randomverse.core.sprite;


import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Data objects that holds a sprite and its position a tile grid
 */
public class Tile {

    /**
     * Default tile size
     */
    public static final int DEFAULT_SIZE = 32;

    private int tileX;
    private int tileY;
    private final Sprite sprite;

    /**
     *
     * @param tileX tile position in a grid (tileset)
     * @param tileY tile position in a grid (tileset)
     * @param sprite sprite on given tile
     */
    public Tile(int tileX, int tileY, Sprite sprite) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int x) {
        this.tileX = x;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int y) {
        this.tileY = y;
    }

    public void setSpritePosition(Position p){
        double newX = (getTileX() * Tile.DEFAULT_SIZE) + p.getX();
        double newY = (getTileY() * Tile.DEFAULT_SIZE) + p.getY();
        getSprite().setPosition(newX, newY);
    }

    /**
     * Returns position of sprite on [0,0] from reference tile
     * @param t tile from which [0,0] sprite position will be computed
     */
    public static Position getZeroSpritePosition(Tile t){
        Position p = new Position(t.getSprite().getXPosn(),
                                  t.getSprite().getYPosn());

        return new Position(p.getX() - (t.getTileX() * Tile.DEFAULT_SIZE),
                            p.getY() - (t.getTileY() * Tile.DEFAULT_SIZE));
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
        return this.getTileX() == tile.getTileX()
            && this.getTileY() == tile.getTileY();
    }

    @Override
    public String toString() {
        return "[" + tileX + "," + tileY + "]" + "[" + getSprite().getXPosn() + "," + getSprite().getYPosn() + "]";
    }
}
