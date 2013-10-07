package cz.terrmith.randomverse.core.sprite.abilitiy;

import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * Special type of Sprite that can attack.
 * Has impact damage
 */
public interface CanAttack extends Sprite{

    /**
     * Primary purpose of an attack is to create sprites that deal damage on impact
     * @param spriteCreator
     */
    void attack(SpriteCreator spriteCreator);
}
