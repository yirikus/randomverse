package cz.terrmith.randomverse.sprite.factory;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.factory.DamageDealerFactory;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.sprite.projectile.Missile;
import cz.terrmith.randomverse.sprite.projectile.Projectile;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 24.10.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class MissileFactory implements DamageDealerFactory {

	private final SpriteCollection spc;
	private Damage damage;

    public MissileFactory(Damage damage, SpriteCollection spc) {
        this.damage = damage;
	    this.spc = spc;
    }

    @Override
    public Sprite newSprite(int x, int y) {
        return new Missile(x, y, damage, spc);
    }
	@Override
	public Damage getDamage() {
		return damage;
	}

	public void setDamage(Damage damage) {
		this.damage = damage;
	}
}
