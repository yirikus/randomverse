package cz.terrmith.randomverse.sprite.factory;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.sprite.projectile.Projectile;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 24.10.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class ProjectileFactory implements SpriteFactory {

    private Damage damage;

    public ProjectileFactory(Damage damage) {
        this.damage = damage;
    }

    @Override
    public Sprite newSprite(int x, int y) {
        return new Projectile(x, y, damage);
    }
}
