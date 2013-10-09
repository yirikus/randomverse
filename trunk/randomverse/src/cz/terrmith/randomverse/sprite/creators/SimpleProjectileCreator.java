package cz.terrmith.randomverse.sprite.creators;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.abilitiy.SpriteCreator;
import cz.terrmith.randomverse.sprite.projectile.SimpleProjectile;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 8.10.13
 * Time: 0:02
 * To change this template use File | Settings | File Templates.
 */
public class SimpleProjectileCreator implements SpriteCreator {

    private final SpriteCollection spriteCollection;

    public SimpleProjectileCreator(SpriteCollection spriteCollection) {
        this.spriteCollection = spriteCollection;
    }

    @Override
    public void createSprites(double x, double y, int dx, int dy) {

        Sprite projectile = new SimpleProjectile(x, y);
        projectile.setStep(dx * (-10), dy * (-10));
        System.out.println("adding particle to sprite collection:" + spriteCollection);
        spriteCollection.put(SpriteLayer.PROJECTILE,projectile);
    }
}
