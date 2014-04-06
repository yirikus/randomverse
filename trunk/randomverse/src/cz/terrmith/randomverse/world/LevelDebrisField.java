package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.SimpleSpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
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
    private final ArtificialIntelligence ai;

    /**
     * @param spriteCollection sprite collection
     */
    public LevelDebrisField(SpriteCollection spriteCollection, Sprite player, ArtificialIntelligence ai) {
        super(spriteCollection, 5, 5);
        this.player = player;
        this.ai = ai;
    }

    @Override
    protected void updateWorld() {
        Debris enemy = new Debris(random.nextInt() % 600 + 100, -100, getSpriteCollection(), player);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        SpriteContainer scn = new SimpleSpriteContainer(enemy);
        ai.registerSpriteContainer(scn);
        scn.registerObserver(this, String.valueOf(getUpdateCount()));
        enemy.setStep(0, enemy.getSpeed());
    }
}
