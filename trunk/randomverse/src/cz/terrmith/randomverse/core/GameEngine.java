package cz.terrmith.randomverse.core;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;

import java.awt.Graphics2D;

/**
 * Game engine interface
 */
public interface GameEngine {

    /**
     * Updates games status
     */
    void update();

    SpriteCollection getSpriteCollection();

    void drawGUI(Graphics2D g2, ImageLoader iml);
}
