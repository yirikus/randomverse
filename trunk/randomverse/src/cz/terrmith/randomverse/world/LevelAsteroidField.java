package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.enemy.Asteroid;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/27/13
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class LevelAsteroidField extends World {

    private static Random random = new Random();

    /**
     * @param spriteCollection
     */
    public LevelAsteroidField(SpriteCollection spriteCollection) {
        super(spriteCollection, 1, 20);
    }

    @Override
    protected void createSprites() {
        Asteroid enemy = new Asteroid(random.nextInt() % 600 + 100, -100);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        enemy.setStep(0, 1);
    }
}
