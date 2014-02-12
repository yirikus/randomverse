package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.attack.RandomAttackPattern;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteFormationFactory;
import cz.terrmith.randomverse.core.ai.movement.pattern.MovementChain;
import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.ai.movement.pattern.StopMovement;
import cz.terrmith.randomverse.core.ai.movement.pattern.TopDownMovement;
import cz.terrmith.randomverse.core.ai.movement.pattern.chain.MovementChainLink;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ship.Ship;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;
import cz.terrmith.randomverse.sprite.ship.part.gun.SimpleGun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Testing level
 */
public class LevelOne extends World {
    private Random random = new Random();
    private SpriteFormationFactory formationFactory;

    public LevelOne(final SpriteCollection spriteCollection) {
        super(spriteCollection,3,100);
        this.formationFactory = new SpriteFormationFactory(spriteCollection) {
            @Override
            protected MovementPattern createMovementPattern() {
//                int amplitude = 100 + random.nextInt(300);
//                int frequency = 100 + random.nextInt(300);
//                WaveMovement wave = new WaveMovement(amplitude, frequency);

//                MovementChain movementChain = new MovementChain(MovementChainLink.timedLink(wave,1000));
//                movementChain.addChainLink(MovementChainLink.timedLink(new TopDownMovement(),1000));
                final MovementChainLink firstLink;
                if(getPreviousMovementChain() != null) {
                    firstLink = MovementChainLink.continuedLink(new StopMovement(), getPreviousMovementChain());
                } else {
                    firstLink = MovementChainLink.timedLink(new StopMovement(), 0);
                }
                final MovementChain movementChain = new MovementChain(firstLink);
                movementChain.addChainLink(MovementChainLink.distancedLink(new TopDownMovement(),300));
                movementChain.addChainLink(MovementChainLink.timedLink(new StopMovement(),0));

                return movementChain;
            }

            @Override
            protected Sprite createEnemy(int x, int y, MovementPattern mp) {
                ArtificialIntelligence ai = new ArtificialIntelligence(mp, new RandomAttackPattern(64));
                Ship enemy = new Ship(x,y,null,ai);
	            //ShipPartFactory factory = new ShipPartFactory(spriteCollection, Damage.DamageType.PLAYER);
                if (random.nextBoolean()) {
                    enemy.addTile(-1, 1, new SimpleGun(getSpriteCollection(), Damage.DamageType.PLAYER,1,0));
                }
                if (random.nextBoolean()) {
                    SimpleGun flippedGun = new SimpleGun(getSpriteCollection(), Damage.DamageType.PLAYER,1,0);
                    flippedGun.flipHorizontal();
                    enemy.addTile(1, 1, flippedGun);
                }

	            Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
	            imageForStatus.put(DefaultSpriteStatus.DEFAULT.name(), new ImageLocation("midParts",random.nextInt(4)));
	            Set<ExtensionPoint> extensions = new HashSet<ExtensionPoint>();
	            extensions.add(ExtensionPoint.LEFT);
	            extensions.add(ExtensionPoint.RIGHT);
	            extensions.add(ExtensionPoint.TOP);
	            extensions.add(ExtensionPoint.BOTTOM);
	            ShipPart body = new ShipPart(1, imageForStatus, extensions, 0);
	            enemy.addTile(0, 1, body);

                enemy.flipVertical();
	            enemy.setLootSprite(new LootSprite(0,0,10,10,null, LootFactory.randomLoot(1)));
                return enemy;
            }
        };

    }

    @Override
    protected void createSprites() {
        if (getUpdateCount() < 2 ) {
            formationFactory.createBoxFormation(random.nextInt(400), 3, 3, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        }

//        if (getUpdateCount() < 2 || (getUpdateCount() > 6)) {
//            formationFactory.createBoxFormation(random.nextInt(600), random.nextInt(3) + 1, random.nextInt(3) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
//        } else if (getUpdateCount() < 4) {
//            formationFactory.createBoxFormation(random.nextInt(400), 6, 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
//        } else if (getUpdateCount() < 6) {
//            formationFactory.createBoxFormation(random.nextInt(200), 1, 6, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
//        }
    }
}
