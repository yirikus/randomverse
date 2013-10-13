package cz.terrmith.randomverse.sprite.projectile;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.Collections;
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
public class SimpleProjectile extends SimpleSprite implements DamageDealer {

    private static final Map<SpriteStatus, ImageLocation> imageForStatus;
    static {
        Map<SpriteStatus, ImageLocation> aMap = new HashMap<SpriteStatus, ImageLocation>();
        aMap.put(SpriteStatus.DEFAULT, new ImageLocation("shots",0));
        imageForStatus = Collections.unmodifiableMap(aMap);
    }

    private Damage damage;

    /**
     * Creates sprite wihh initial position x, y and size w, h
     *
     * @param x              x position
     * @param y              y position
     */
    public SimpleProjectile(double x, double y, Damage.DamageType damageType) {
        super(x, y, 11, 22, null);
        setImageForStatus(this.imageForStatus);
        this.damage = new Damage(3,damageType);
    }

    @Override
    public Damage getDamage() {
        return this.damage;
    }

	@Override
	public void dealDamage(List<Destructible> targets) {
		if(targets != null && !targets.isEmpty()) {
			System.out.println("target hit!");
			targets.get(0).reduceHealth(getDamage().getAmount());
			setActive(false);
		}

	}
}
