package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.Formation;
import cz.terrmith.randomverse.core.ai.movement.formation.SimpleSpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.pattern.TrackingMovement;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.enemy.Mine;

import java.util.List;

/**
 * Testing level
 *
 * todo refactor formation movement creation - abstract method
 */
public class LevelMinefield extends World {
    public static final String ACTIVATION_KEY = "notImportant";
    private final ArtificialIntelligence ai;
    private final Player player;

    public LevelMinefield(final SpriteCollection spriteCollection, Player player, ArtificialIntelligence ai) {
        super(spriteCollection, 1, 3);
        this.ai = ai;
        this.player = player;
        player.getSprite().setPosition(500,400);
    }

    @Override
    protected void updateWorld() {
        deployMines();
    }

    private void deployMines() {
        waitForInactivation(ACTIVATION_KEY);

        Formation formation = Formation.rectangle(3, 3, new Position(random.nextInt(700) - 100, 0), Mine.SIZE * 5, Mine.SIZE * 5);
        final Mine.EnemyType enemyType;
        switch (random.nextInt(3)) {
            case 1:
                enemyType = Mine.EnemyType.SMALL_EXPLODING;
                break;
            case 2:
                enemyType = Mine.EnemyType.IMPACT;
                break;
            default:
                enemyType = Mine.EnemyType.BIG_EXPLODING;
                break;
        }
        List<Sprite> sprites = formation.createAndPositionSprites(getSpriteCollection(), new SpriteFactory() {
            @Override
            public Sprite newSprite(int x, int y) {
                return new Mine(x, y, enemyType, getSpriteCollection());
            }
        });

        SpriteContainer scn = new SimpleSpriteContainer(sprites, new TrackingMovement(player.getSprite()));
        scn.registerObserver(this, ACTIVATION_KEY);
        ai.registerSpriteContainer(scn);
    }
}
