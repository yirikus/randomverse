package cz.terrmith.randomverse.world;

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

    /**
     * @param spriteCollection sprite collection
     */
    public LevelDebrisField(SpriteCollection spriteCollection) {
        super(spriteCollection, 1, 1);
    }

    @Override
    protected void createSprites() {
        Debris enemy = new Debris(random.nextInt() % 600 + 100, -100);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        enemy.setStep(0, enemy.getSpeed());
    }
}
