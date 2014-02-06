package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.geometry.Position;
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
     * @return width in pixels
     */
    int getWidth();

    /**
     * Height of the sprite's image
     * @return height in pixels
     */
    int getHeight();

    boolean isActive();

    void setActive(boolean a);

    void setPosition(double x, double y);

    void translate(double xDist, double yDist);

    /**
     * Returns X position
     *
     * @return position on screen
     */
    double getXPosn();

    /**
     * Returns Y position
     * @return position on screen
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
     * @return sprite collection contains nothing, this or sprites that are parts of this
     *                sprite and collide with given sprite
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
	 * @return status of sprite
	 */
	String getStatus();

	/**
	 * Sets sprite status
	 * @param status sprite status
	 */
	void setStatus(String status);

	/**
	 * Return copy of this sprite such as
	 * this.copy() != this
	 * this.copy().equals(this)
	 *
	 * @return copy of this sprite
	 */
	Sprite copy();

	/**
	 * Sets parent of this sprite (like multisprite)
	 * @param parent
	 */
	void setParent(Sprite parent);

	/**
	 * Returns parent or null
	 * @return
	 */
	Sprite getParent();

    /**
     * Returns normalized unit vector of movement
     */
    Position getMovementVector();

    /**
     * Applies all effects resulting from collision with given sprite
     * note: This method should not apply collision effects on given sprite, that sprite should handle it himself
     * @param s
     */
    void collide(Sprite s);
}
