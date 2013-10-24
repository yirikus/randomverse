package cz.terrmith.randomverse.sprite.projectile;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 8.10.13
 * Time: 0:06
 * To change this template use File | Settings | File Templates.
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

        Map<SpriteStatus, ImageLocation> aMap = new HashMap<SpriteStatus, ImageLocation>();
        aMap.put(SpriteStatus.DEFAULT, new ImageLocation("shots",damage.getAmount()%4));
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
