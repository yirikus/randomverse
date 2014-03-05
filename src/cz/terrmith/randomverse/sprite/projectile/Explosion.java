package cz.terrmith.randomverse.sprite.projectile;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.DamageDealer;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;

/**
 * Projectile - sprite that is fired by a gun
 */
public class Explosion extends SimpleSprite implements DamageDealer {

    private final int size;
    private Damage damage;
    private boolean hit;
    private int health = 10;

    /**
     * Creates sprite wihh initial position x, y and size w, h
     *
     * @param x              x position
     * @param y              y position
     */
    public Explosion(double x, double y, Damage damage) {
        this(x, y, damage, Tile.DEFAULT_SIZE * 2);
    }

    /**
     * Creates sprite wihh initial position x, y and size w, h
     *
     * @param x              x position
     * @param y              y position
     */
    public Explosion(double x, double y, Damage damage, int size) {
        super(x, y, 11, 22, new HashMap<String, ImageLocation>());
        this.damage = damage;
        this.size = size;
    }

    @Override
    public Damage getDamage() {
        return this.damage;
    }

	@Override
	public void dealDamage(List<Destructible> targets) {
		if(targets != null && !targets.isEmpty() && !hit) {
			for (Destructible d : targets) {
                  d.collide(this);
//                d.reduceHealth(getDamage().getAmount());
            }
			this.hit = true;
		}
	}

    @Override
    public void updateSprite() {
        super.updateSprite();
        if (health > 0) {
            this.health--;
        } else if (health < 1) {
            setActive(false);
        }
    }

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        g.setColor(new Color(200, 117, 0, 0 + (20 * health)));
        g.fillRect((int)getXPosn() - size/2,
                    (int)getYPosn() - size/2,
                    size, size);
    }

    @Override
	public Rectangle getBoundingBox() {
		return new Rectangle((int)getXPosn() - size / 2,
							  (int)getYPosn() - size / 2,
							  size, size);
    }
}
