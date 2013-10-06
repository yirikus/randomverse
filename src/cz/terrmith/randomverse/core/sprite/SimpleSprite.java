package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Moving graphic object
 * has image representation, position, soundbank, bounding box
 */
public class SimpleSprite implements Sprite {
    // default step sizes (how far to move in each update)
    private static final int XSTEP = 5;
    private static final int YSTEP = 5;

    // default dimensions when there is no image
    private static final int SIZE = 12;
    private final Map<SpriteStatus, ImageLocation> imageForStatus;

    // image-related
    private int width, height;     // image dimensions

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
    public SimpleSprite(int x, int y, int w, int h, Map<SpriteStatus, ImageLocation> imageForStatus) {
        locx = x; locy = y;
        dx = XSTEP; dy = YSTEP;

        this.imageForStatus = imageForStatus;
        this.status = SpriteStatus.DEFAULT;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    @Override
    public boolean isActive(){
        return isActive;
    }

    @Override
    public void setActive(boolean a){
        isActive = a;
    }

    @Override
    public void setPosition(int x, int y){
        locx = x;
        locy = y;
    }

    @Override
    public void translate(int xDist, int yDist){
        locx += xDist;
        locy += yDist;
    }

    @Override
    public int getXPosn(){
        return locx;
    }

    @Override
    public int getYPosn(){
        return locy;
    }


    @Override
    public void setStep(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public int getXStep(){
        return dx;
    }

    @Override
    public int getYStep(){
        return dy;
    }


    @Override
    public Rectangle getBoundingBox() {
        return  new Rectangle(locx, locy, width, height);
    }

    @Override
    public void updateSprite() {
        if (isActive()) {
            locx += dx;
            locy += dy;
        }
    }

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        if (isActive()) {
            ImageLocation imageLocation = imageForStatus.get(this.status);
            BufferedImage image = ims.getImage(imageLocation.getName(), imageLocation.getNumber());
            g.drawImage(image, locx, locy, null);
        }
    }
}
