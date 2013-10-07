package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;

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

    void translate(int xDist, int yDist);

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
     * Returns true if this sprite collides with given sprite
     * @param sprite sprite that is tested for collision
     * @return true if collision occurred
     */
    boolean collidesWith(Sprite sprite);
}
