package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;

import java.awt.*;

/**
 * Game engine interface
 */
public interface GameEngine {

    /**
     * Updates games status
     */
    void update();

    SpriteCollection getSpriteCollection();

    void drawGUI(Graphics2D g2);
}
