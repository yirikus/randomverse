package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.SimpleSpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.InvisibleArea;
import cz.terrmith.randomverse.sprite.enemy.SimpleEnemy;

import java.util.Random;

/**
 * Player has to reach designated area (see method addTargetArea)
 */
public class LevelSpaceHighWay extends World {
    private final ArtificialIntelligence ai;
    private Random random = new Random();
    private boolean created;
    private int lanes;

    public LevelSpaceHighWay(final SpriteCollection spriteCollection, ArtificialIntelligence ai) {
        super(spriteCollection, 1, 1);
        this.ai = ai;
    }

    private void addTargetArea() {
        Sprite sprite = new InvisibleArea(0,0,GameWindow.SCREEN_W, 100);
        getSpriteCollection().put(SpriteLayer.NPC, sprite);

        SpriteContainer scn = new SimpleSpriteContainer(sprite);
        ai.registerSpriteContainer(scn);
        scn.registerObserver(this, String.valueOf(getUpdateCount()));
    }

    @Override
    protected void updateWorld() {
        if (!this.created) {
            this.created = true;
            addTargetArea();
        }

        addSpaceship(0,164);
        addSpaceship(800,236);

        if (lanes == 4) {
            addSpaceship(0,100);
            addSpaceship(800,300);
        }

    }

    private void addSpaceship(int x, int y) {
        Sprite enemy = new SimpleEnemy(x, y, SimpleEnemy.EnemyType.KAMIKAZE, getSpriteCollection());
        enemy.setSpeed(5);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        SpriteContainer scn = new SimpleSpriteContainer(enemy);
        if (x <=0) {
            enemy.setStep(enemy.getSpeed(),0);
        } else {
            enemy.setStep(-enemy.getSpeed(),0);
        }
    }
}
