package cz.terrmith.randomverse.core.sprite.creator;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.creator.SpriteCreator;
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
    int horizontalFlip = 1;
    int verticalFlip = 1;


    public SimpleProjectileCreator(SpriteCollection spriteCollection) {
        this.spriteCollection = spriteCollection;
    }

    @Override
    public void createSprites(double x, double y, int dx, int dy, int speed, int distanceFromOrigin) {
        if(dx > 1 || dx < -1 || dy > 1 || dy < -1) {
            throw new IllegalArgumentException("distance x,y must be in interval [-1,1], but was: [" + dx + ", " + dy +"]");
        }
        Sprite projectile = new SimpleProjectile(x, y);
        projectile.translate(horizontalFlip * dx * distanceFromOrigin, verticalFlip * dy * distanceFromOrigin);
        projectile.setStep(horizontalFlip * dx * (speed), verticalFlip * dy * (speed));
        System.out.println("adding particle to sprite collection:" + spriteCollection);
        spriteCollection.put(SpriteLayer.PROJECTILE,projectile);
    }

    @Override
    public void flipHorizontal() {
        this.horizontalFlip = - this.horizontalFlip;
    }

    @Override
    public void flipVertical() {
        this.verticalFlip = -this.verticalFlip;
    }
}
