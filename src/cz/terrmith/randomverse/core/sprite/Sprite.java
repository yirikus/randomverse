package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;

/**
 * Sprite interface
 * Sprite is a moving graphic object that
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

    void setPosition(int x, int y);

    void translate(int xDist, int yDist);

    int getXPosn();

    int getYPosn();

    void setStep(int dx, int dy);

    int getXStep();

    int getYStep();

    Rectangle getBoundingBox();

    /**
     * Updates sprite status
     */
    void updateSprite();

    void drawSprite(Graphics g, ImageLoader ims);
}
