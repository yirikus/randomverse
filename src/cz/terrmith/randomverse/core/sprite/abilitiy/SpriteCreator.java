package cz.terrmith.randomverse.core.sprite.abilitiy;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;

/**
 * SpriteCreator can create another sprite (e.g. projectile)
 */
public interface SpriteCreator {

    /**
     * Creates sprites. Can make use of direction which caller sprite is heading
     *
	 * @param x x position of new sprite
	 * @param y y position of new sprite
	 * @param dx x direction of new sprite
	 * @param dy y direction of new sprite
	 */
    public void createSprites(double x, double y, int dx, int dy);

}
