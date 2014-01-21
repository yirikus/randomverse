package cz.terrmith.randomverse.sprite.projectile;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.DamageDealer;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Projectile - sprite that is fired by a gun
 */
public class Projectile extends SimpleSprite implements DamageDealer {

    private Damage damage;

    /**
     * Creates sprite wihh initial position x, y and size w, h
     *
     * @param x              x position
     * @param y              y position
     */
    public Projectile(double x, double y, Damage damage) {
        super(x, y, 11, 22, null);

        Map<String, ImageLocation> aMap = new HashMap<String, ImageLocation>();
        aMap.put(DefaultSpriteStatus.DEFAULT.name(), new ImageLocation("shots",damage.getAmount()%4));
        setImageForStatus(aMap);

        this.damage = damage;
    }

    @Override
    public Damage getDamage() {
        return this.damage;
    }

	@Override
	public void dealDamage(List<Destructible> targets) {
		if(targets != null && !targets.isEmpty()) {
			targets.get(0).reduceHealth(getDamage().getAmount());
			setActive(false);
		}

	}
}
