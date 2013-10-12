package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.Debug;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Moving graphic object
 * has image representation, position, soundbank, bounding box
 */
public class SimpleSprite implements Sprite {
    // default dimensions when there is no image
    private Map<SpriteStatus, ImageLocation> imageForStatus;

    // image-related
    private int width, height;     // image dimensions

	// a sprite is updated and drawn only when it is active
    private boolean isActive = true;

    // protected vars
    private double locx, locy;        // location of sprite
    private double dx, dy;            // amount to move for each update
    // sprite status
    private SpriteStatus status = SpriteStatus.DEFAULT;;
    private boolean yFlip = false;
    private boolean xFlip = false;


    /**
     * Creates sprite wtih initial position x, y and size w, h
     * @param x x position
     * @param y y position
     * @param w width
     * @param h height
     * @param imageForStatus Map of sprite statuses and images
     */
    public SimpleSprite(double x, double y, int w, int h, Map<SpriteStatus, ImageLocation> imageForStatus) {
        locx = x; locy = y;
        dx = 0; dy = 0;
	    this.width = w;
	    this.height = h;
        if (imageForStatus != null) {
            this.imageForStatus = imageForStatus;
        } else {
            this.imageForStatus = new HashMap<SpriteStatus, ImageLocation>();
        }
    }

    public Map<SpriteStatus, ImageLocation> getImageForStatus() {
        return imageForStatus;
    }

    public void setImageForStatus(Map<SpriteStatus, ImageLocation> imageForStatus) {
        this.imageForStatus = imageForStatus;
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
    public void setPosition(double x, double y){
        locx = x;
        locy = y;
    }

    @Override
    public void translate(double xDist, double yDist){
        locx += xDist;
        locy += yDist;
    }

    @Override
    public double getXPosn(){
        return locx;
    }

    @Override
    public double getYPosn(){
        return locy;
    }


    @Override
    public void setStep(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public double getXStep(){
        return dx;
    }

    @Override
    public double getYStep(){
        return dy;
    }


    @Override
    public Rectangle getBoundingBox() {
        return  new Rectangle((int)Math.round(locx), (int)Math.round(locy), width, height);
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
	    if(Debug.DEBUG_COLLISIONS) {
            g.setColor(new Color(0,0,255,150));
            g.fillRect(getBoundingBox().x, getBoundingBox().y, (int)getBoundingBox().getWidth(), (int)getBoundingBox().getHeight());
        }
	    if (isActive() && imageForStatus != null) {
            ImageLocation imageLocation = imageForStatus.get(this.status);
            BufferedImage image = ims.getImage(imageLocation.getName(), imageLocation.getNumber());
            int imageX = (int)Math.round(locx);
            int imageY = (int)Math.round(locy);
            if (this.yFlip && this.xFlip) {
                /*
                    2 - -
                    - - -
                    - - 1
                 */
                g.drawImage(image, imageX + width, imageY + height,  imageX, imageY, 0, 0, width, height, null);
            } else if (this.yFlip) {
                /*
                    - - 2
                    - - -
                    1 - -
                 */
                g.drawImage(image, imageX, imageY + height,  imageX + width, imageY, 0, 0, width, height, null);
            } else if (this.xFlip) {
                /*
                    - - 1
                    - - -
                    2 - -
                 */
                g.drawImage(image, imageX + width,  imageY, imageX, imageY + height, 0, 0, width, height, null);

            } else {
                //image is not flipped
                g.drawImage(image, imageX, imageY, null);
            }
        }
    }

    @Override
    public boolean collidesWith(Sprite sprite) {
	    if (sprite instanceof MultiSprite) {
		    MultiSprite multiSprite = (MultiSprite) sprite;
		    return multiSprite.collidesWith(this);
	    } else {
            return this.getBoundingBox().intersects(sprite.getBoundingBox());
	    }
    }

    @Override
    public void flipHorizontal() {
        this.xFlip = !xFlip;
    }

    @Override
    public void flipVertical() {
        this.yFlip = !yFlip;
    }
}
