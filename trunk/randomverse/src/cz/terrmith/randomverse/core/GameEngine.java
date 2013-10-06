package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;

/**
 * Game engine interface
 */
public interface GameEngine {

    /**
     * Updates games status
     */
    void update();

    SpriteCollection getSpriteCollection();
}
