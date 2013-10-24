package cz.terrmith.randomverse.core.sprite.factory;

import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * Describes an object that creates sprite instances
 */
public interface SpriteFactory {

   Sprite newSprite(int x, int y);
}
