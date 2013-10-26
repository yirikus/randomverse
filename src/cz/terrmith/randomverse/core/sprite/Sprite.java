package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;


/**
 * Sprite interface
 * Sprite is a graphic object that can move and collide with other objects
 * has image representation, position, soundbank, bounding box
 */
public interface Sprite {

    /**
     * Width of the sprite's image
     * @return
     */
    int getWidth();

    /**
     * Height of the sprite's image
     * @return
     */
    int getHeight();

    boolean isActive();

    void setActive(boolean a);

    void setPosition(double x, double y);

    void translate(double xDist, double yDist);

    /**
     * Returns X position
     *
     * @return
     */
    double getXPosn();

    /**
     * Returns Y position
     * @return
     */
    double getYPosn();

    void setStep(double dx, double dy);

    double getXStep();

    double getYStep();

    Rectangle getBoundingBox();

    /**
     * Updates sprite status
     */
    void updateSprite();

    /**
     * Draws sprite
     * @param g graphics
     * @param ims image loader
     */
    void drawSprite(Graphics g, ImageLoader ims);

    /**
     * Tests if this sprite collides with given sprite and
     * returns all sprites parts that collide with given sprite or itself if its single part
     *
     * @param sprite sprite that is tested for collision
     * @return sprite collection or empty collection if no colliding sprite was found
     */
    List<Sprite> collidesWith(Sprite sprite);

    /**
     * Flips sprite horizontally
     */
    void flipHorizontal();

    /**
     * Flips sprite vertically
     */
    void flipVertical();

	/**
	 * Returns sprite status
	 * @return
	 */
	SpriteStatus getStatus();

	/**
	 * Sets sprite status
	 * @param status
	 */
	void setStatus(SpriteStatus status);
}
