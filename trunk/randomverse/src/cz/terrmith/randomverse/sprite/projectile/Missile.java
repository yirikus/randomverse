package cz.terrmith.randomverse.sprite.projectile;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;

import java.util.HashMap;
import java.util.Map;

/**
 *Sprite that creates explosion on destruction
 *
 * @author jiri.kus
 */
public class Missile extends SimpleSprite implements Destructible {
	private static final int MAX_HEALTH = 1;
	private final SpriteCollection spc;
	private int health = MAX_HEALTH;
	private Damage damage;

	public Missile(Missile m) {
		super(m);
		this.spc = m.spc;
		this.damage = m.damage;
	}

	public Missile(double x, double y, Damage damage, SpriteCollection spc) {
		super(x, y, 11, 22, null);

		Map<String, ImageLocation> aMap = new HashMap<String, ImageLocation>();
		aMap.put(DefaultSpriteStatus.DEFAULT.name(), new ImageLocation("shots",damage.getAmount()%4));
		setImageForStatus(aMap);

		this.spc = spc;
		this.damage = damage;
	}


	@Override
	public int getTotalHealth() {
		return MAX_HEALTH;
	}

	@Override
	public int getCurrentHealth() {
		return this.health;
	}

	@Override
	public void reduceHealth(int amount) {
		this.health -= amount;
		if (this.health < 1) {
			System.out.println("creating projectile: " + getXPosn() + ", " + getYPosn());
			spc.put(SpriteLayer.PROJECTILE, new Explosion(getXPosn(), getYPosn(), this.damage));
			this.setActive(false);
			setStatus(DefaultSpriteStatus.DEAD.name());
		}
	}
}
