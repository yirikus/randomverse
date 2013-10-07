package cz.terrmith.randomverse.core.sprite.abilitiy;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;

/**
 * SpriteCreator can create another sprite (e.g. projectile)
 */
public interface SpriteCreator {

    /**
     * Creates sprites. Can make use of direction which caller sprite is heading
     * @param parent owner of this creator
     */
    public void createSprites(Sprite parent);

}
