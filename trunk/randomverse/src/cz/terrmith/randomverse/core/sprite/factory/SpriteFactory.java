package cz.terrmith.randomverse.core.sprite.factory;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.Damage;

/**
 * Describes an object that creates sprite instances
 */
public interface SpriteFactory {

   Sprite newSprite(int x, int y);

    /* TODO GET D-A-M-A-G-E why the HELL is this method HERE ??? It does not belong to a fucking SPRITE FACTORY
        DAMAGE is not RELEVANT to a SPRITE gaaaaaAAAHHHH!*/
	Damage getDamage();
}
