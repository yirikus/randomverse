package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Moving graphic object
 * has image representation, position, soundbank, bounding box
 */
public class Sprite {
    // default step sizes (how far to move in each update)
    private static final int XSTEP = 5;
    private static final int YSTEP = 5;

    // default dimensions when there is no image
    private static final int SIZE = 12;
    private final Map<SpriteStatus, String> imageForStatus;

    // image-related
    private String imageName;
    private int width, height;     // image dimensions

    private boolean isLooping;

    private int pWidth, pHeight;   // panel dimensions

    private boolean isActive = true;
    // a sprite is updated and drawn only when it is active

    // protected vars
    protected int locx, locy;        // location of sprite
    protected int dx, dy;            // amount to move for each update
    // sprite status
    private SpriteStatus status;


    /**
     * Creates sprite wtih initial position x, y and size w, h
     * @param x x position
     * @param y y position
     * @param w width
     * @param h height
     * @param imageForStatus Map of sprite statuses and images
     */
    public Sprite(int x, int y, int w, int h, Map<SpriteStatus,String> imageForStatus) {
        locx = x; locy = y;
        pWidth = w; pHeight = h;
        dx = XSTEP; dy = YSTEP;

        this.imageForStatus = imageForStatus;
        this.status = SpriteStatus.DEFAULT;
    }

    public int getWidth()    // of the sprite's image
    {  return width;  }

    public int getHeight()   // of the sprite's image
    {  return height;  }

    public int getPWidth()   // of the enclosing panel
    {  return pWidth;  }

    public int getPHeight()  // of the enclosing panel
    {  return pHeight;  }


    public boolean isActive()
    {  return isActive;  }

    public void setActive(boolean a)
    {  isActive = a;  }

    public void setPosition(int x, int y)
    {  locx = x; locy = y;  }

    public void translate(int xDist, int yDist)
    {  locx += xDist;  locy += yDist;  }

    public int getXPosn()
    {  return locx;  }

    public int getYPosn()
    {  return locy;  }


    public void setStep(int dx, int dy)
    {  this.dx = dx; this.dy = dy; }

    public int getXStep()
    {  return dx;  }

    public int getYStep()
    {  return dy;  }


    public Rectangle getBoundingBox() {
        return  new Rectangle(locx, locy, width, height);
    }

    /**
     * Updates sprite status
     */
    public void updateSprite() {
        if (isActive()) {
            locx += dx;
            locy += dy;
        }
    }



    public void drawSprite(Graphics g, ImageLoader ims) {
        if (isActive()) {
            BufferedImage image = ims.getImage(imageForStatus.get(this.status));
            g.drawImage(image, locx, locy, null);
        }
    }
}
