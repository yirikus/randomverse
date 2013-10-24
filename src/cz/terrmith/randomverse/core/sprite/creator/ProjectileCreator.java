package cz.terrmith.randomverse.core.sprite.creator;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;

/**
 * Sprite creator that creates projectiles
 * created projectiles move in a specified direction and speed
 *
 */
public class ProjectileCreator implements SpriteCreator {

    private final SpriteCollection spriteCollection;
    private SpriteFactory projectileFactory;
    private int horizontalFlip = 1;
    private int verticalFlip = 1;


    public ProjectileCreator(SpriteCollection spriteCollection, SpriteFactory projectileFactory) {
        this.spriteCollection = spriteCollection;
        this.projectileFactory = projectileFactory;
    }

    @Override
    public void createSprites(double x, double y, int dx, int dy, int speed, int distanceFromOrigin) {
        if(dx > 1 || dx < -1 || dy > 1 || dy < -1) {
            throw new IllegalArgumentException("distance x,y must be in interval [-1,1], but was: [" + dx + ", " + dy +"]");
        }
        Sprite projectile = projectileFactory.newSprite((int)x, (int)y); //new Projectile(x, y, this.damageType);
        projectile.translate(horizontalFlip * dx * distanceFromOrigin, verticalFlip * dy * distanceFromOrigin);
        projectile.setStep(horizontalFlip * dx * (speed), verticalFlip * dy * (speed));
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
