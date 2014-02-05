package cz.terrmith.randomverse.ability;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.Ability;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author jiri.kus
 */
public class Shield extends Ability implements Destructible {

    private static final int HEALTH_MAX = 10;
    private int health = HEALTH_MAX;
    private Sprite parent;
    private static final int THICKNESS = 10;
    private long lastUpdate = System.currentTimeMillis();

    /**
	 * Creates an instance
	 */
	public Shield() {
		super(AbilityGroup.ACTION_2.name());
	}

	/**
	 * Switches shield on or off
	 */
	@Override
	public void useAbility(Sprite parent) {
		setActive(!isActive());
		if (isActive()) {
			//update parent [x, y]
            this.parent = parent;
			setPosition(parent.getXPosn(), parent.getYPosn());
		}
		System.out.println("shield: " + isActive());
	}

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        Rectangle rect = parent.getBoundingBox();
        g.setColor(new Color(0,250 - (health * 10), 250 - (health * 10),0 + (10 * health)));
        g.fillRect((int)getXPosn() - THICKNESS,
                   (int)getYPosn() - THICKNESS,
                   (int)rect.getWidth() + THICKNESS,
                   (int)rect.getHeight() + THICKNESS);
    }

    public void updateSprite() {
		super.updateSprite();
        if (isActive() && parent != null) {
            Rectangle rectangle = parent.getBoundingBox();
              setPosition(rectangle.getX(), rectangle.getY());
        }
        if (health < HEALTH_MAX && !isActive()) {
            long now = System.currentTimeMillis();
            if ((now - lastUpdate) >= 1500 ) {
                lastUpdate = now;
                health++;
            } else {
                System.out.println("shield replenish : " + (now - lastUpdate));
            }
        }
	}

    @Override
    public int getTotalHealth() {
        return HEALTH_MAX;
    }

    @Override
    public int getCurrentHealth() {
        return this.health;
    }

    @Override
    public void reduceHealth(int amount) {
        this.health = Math.max(0, health - amount);
    }

    @Override
    public Rectangle getBoundingBox() {
        Rectangle rect = parent.getBoundingBox();
        return new Rectangle((int)rect.getX() - THICKNESS,
                (int)rect.getY() - THICKNESS,
                (int)rect.getWidth() + THICKNESS,
                (int)rect.getHeight() + THICKNESS);
    }
}
