package cz.terrmith.randomverse.core.sprite.creator;

import cz.terrmith.randomverse.core.geometry.Position;

/**
 * SpriteCreator can create another sprite (e.g. projectile) on given spot that will travel in a given direction
 */
public interface SpriteCreator {

    /**
     * Creates sprites. Can make use of direction which caller sprite is heading
     *
	 * @param x x position of new sprite
	 * @param y y position of new sprite
	 * @param dx x direction of new sprite [-1,1]
	 * @param dy y direction of new sprite [-1,1]
     * @param speed speed
     * @param distanceFromOrigin distance from origin
	 */
    void createSprites(double x, double y, double dx, double dy, int speed, int distanceFromOrigin);

    /**
     * Creates sprites. Can make use of direction which caller sprite is heading
     *
     * @param from position of new sprite
     * @param delta vector
     * @param speed speed
     * @param distanceFromOrigin distance from origin
     */
    void createSprites(Position from, Position delta, int speed, int distanceFromOrigin);

    /**
     * flips direction of an attack horizontally
     */
    void flipHorizontal();

    /**
     * flips direction of an attack horizontally
     */
    void flipVertical();
}
