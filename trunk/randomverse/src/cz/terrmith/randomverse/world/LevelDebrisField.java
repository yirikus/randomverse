package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.enemy.debris.Debris;

import java.util.Random;

/**
 * Debris level
 */
public class LevelDebrisField extends World {

    private static Random random = new Random();
    private final Sprite player;

    /**
     * @param spriteCollection sprite collection
     */
    public LevelDebrisField(SpriteCollection spriteCollection, Sprite player) {
        super(spriteCollection, 1, 100);
        this.player = player;
    }

    @Override
    protected void createSprites() {
        Debris enemy = new Debris(random.nextInt() % 600 + 100, -100, getSpriteCollection(), player);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        enemy.setStep(0, enemy.getSpeed());
    }
}
