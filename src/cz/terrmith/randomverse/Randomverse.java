package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.input.UserCommand;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.sprite.Ship;

/**
 * Contains global constants
 */
public class Randomverse implements GameEngine {

    public static final String WINDOW_NAME = "Randomverse";

    private final UserCommand command;
    private SpriteCollection spriteCollection;
    private Ship player;

    public Randomverse(UserCommand cmd){
        this.command = cmd;
        this.spriteCollection = new SpriteCollection();
        this.player = new Ship(300,300);
        spriteCollection.put(SpriteLayer.PLAYER, player);
    }


    @Override
    public void update() {
        updatePlayer();
    }

    private void updatePlayer() {
        int dx = 0;
        int dy = 0;
        if (command.isUp()) {
            dy -= 1;
        }
        if (command.isDown()) {
            dy += 1;
        }
        if (command.isLeft()) {
           dx -= 1;
        }
        if (command.isRight()) {
           dx += 1;
        }

        player.setStep(dx, dy);
        player.updateSprite();

    }

    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }


}
