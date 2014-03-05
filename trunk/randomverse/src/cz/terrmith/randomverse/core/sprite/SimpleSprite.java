package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.Debug;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Moving graphic object
 * has image representation, position, soundbank, bounding box
 */
public class SimpleSprite implements Sprite {
    // default dimensions when there is no image
    private Map<String, ImageLocation> imageForStatus;

    // image-related
    private int width, height;     // image dimensions

	// a sprite is updated and drawn only when it is active
    private boolean isActive = true;

    // protected vars
    private double locx, locy;        // location of sprite
    private double prevLocx, prevLocy; // previous location
    private double dx, dy;            // amount to move for each update
    // sprite status
    private String status = DefaultSpriteStatus.DEFAULT.name();
    private boolean yFlip = false;
    private boolean xFlip = false;
	private Sprite parent;
    private double speed;

    /**
     * Copy constructor
     * @param sprite instance that should be copied
     */
    public SimpleSprite(SimpleSprite sprite) {
        this(sprite.getXPosn(), sprite.getYPosn(), sprite.getWidth(), sprite.getHeight(), sprite.getImageForStatus());
        this.status = sprite.getStatus();
	    this.xFlip = sprite.isFlippedHorizontally();
	    this.yFlip = sprite.isFlippedVertically();
	    this.parent = sprite.getParent();
        this.speed = sprite.getSpeed();
    }

    /**
     * Creates sprite wtih initial position x, y and size w, h
     * @param x x position
     * @param y y position
     * @param w width
     * @param h height
     * @param imageForStatus Map of sprite statuses and images
     */
    public SimpleSprite(double x, double y, int w, int h, Map<String, ImageLocation> imageForStatus) {
        locx = x;
        locy = y;
        prevLocx = x;
        prevLocy = y;

        dx = 0; dy = 0;
	    this.width = w;
	    this.height = h;
        if (imageForStatus != null) {
            this.imageForStatus = imageForStatus;
        } else {
            this.imageForStatus = new HashMap<String, ImageLocation>();
        }
    }

    public Map<String, ImageLocation> getImageForStatus() {
        return imageForStatus;
    }

    public void setImageForStatus(Map<String, ImageLocation> imageForStatus) {
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
        // if new position is the same as last it is not considered as vector change
        if (x != locx && y != locy) {
            prevLocx = locx;
            prevLocy = locy;
        }
        locx = x;
        locy = y;
    }

    @Override
    public void setPosition(Position p) {
        setPosition(p.getX(), p.getY());
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
            setPosition(locx + dx, locy + dy);
        }
    }

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
	    if(Debug.DEBUG_COLLISIONS) {
            g.setColor(new Color(0,0,255,150));
            g.fillRect(getBoundingBox().x, getBoundingBox().y, (int)getBoundingBox().getWidth(), (int)getBoundingBox().getHeight());
        }
        if (imageForStatus == null) {
            throw new IllegalStateException("no imageForStatus collection");
        }
	    if (isActive() && imageForStatus != null) {
            ImageLocation imageLocation = imageForStatus.get(this.status);
		    if (imageLocation != null) {
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
    }

    @Override
    public List<Sprite> collidesWith(Sprite sprite) {
	    if (sprite instanceof MultiSprite) {
		    MultiSprite multiSprite = (MultiSprite) sprite;
		    return multiSprite.collidesWith(this);
	    } else if (!DefaultSpriteStatus.DEAD.name().equals(getStatus()) && this.getBoundingBox().intersects(sprite.getBoundingBox())){
		    List<Sprite> ret = new ArrayList<Sprite>();
		    ret.add(this);
            return ret;
	    }
	    return new ArrayList<Sprite>();
    }

    public void setXFlip(boolean flip) {
        this.xFlip = flip;
    }

    public void setYFlip(boolean flip) {
        this.yFlip = flip;
    }

    @Override
    public void flipHorizontal() {
        this.xFlip = !xFlip;
    }

    public boolean isFlippedHorizontally() {
        return this.xFlip;
    }

    @Override
    public void flipVertical() {
        this.yFlip = !yFlip;
    }

    public boolean isFlippedVertically() {
        return this.yFlip;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Sprite copy() {
		return new SimpleSprite(this);
	}

	@Override
	public void setParent(Sprite parent) {
		this.parent = parent;
	}

	@Override
	public Sprite getParent() {
		return this.parent;
	}

    @Override
    public Position getMovementVector() {
        return Position.normalizedVector(new Position(prevLocx,prevLocy), new Position(locx, locy));
    }

    @Override
    public void collide(Sprite s) {
        //does nothing
//        throw new UnsupportedOperationException("Method sprite.collide() for was not overriden for class " + this.getClass().getName());
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
