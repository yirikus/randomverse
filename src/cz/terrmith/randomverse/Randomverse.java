package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.input.UserCommand;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.creators.SimpleProjectileCreator;

/**
 * Contains global constants
 */
public class Randomverse implements GameEngine {

    public static final String WINDOW_NAME = "Randomverse";
    public static final int STEP = 3;

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
        updateProjectiles();
        updatePlayer();
    }

    private void updateProjectiles() {
        for (Sprite s: getSpriteCollection().getSprites(SpriteLayer.PARTICLE)) {
            s.updateSprite();
        }
    }

    private void updatePlayer() {
        int dx = 0;
        int dy = 0;
        if (command.isUp()) {
            dy -= STEP;
        }
        if (command.isDown()) {
            dy += STEP;
        }
        if (command.isLeft()) {
           dx -= STEP;
        }
        if (command.isRight()) {
           dx += STEP;
        }

        if (command.isShoot()) {
            System.out.println("shoot");
            player.attack(new SimpleProjectileCreator(getSpriteCollection()));
        }

        player.setStep(dx, dy);
        player.updateSprite();

    }

    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }


}
