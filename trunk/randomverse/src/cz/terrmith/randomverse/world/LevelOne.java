package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.attack.RandomAttackPattern;
import cz.terrmith.randomverse.core.ai.movement.TopDownMovement;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.*;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.LootSprite;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.ExtensionPoint;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.ShipPart;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.util.*;

/**
 * Testing level
 */
public class LevelOne extends World {
    private Random random = new Random();
    private SpriteFormationFactory formationFactory;

    public LevelOne(final SpriteCollection spriteCollection) {
        super(spriteCollection,3,3);
        this.formationFactory = new SpriteFormationFactory(spriteCollection) {
            @Override
            protected Sprite createEnemy(int x, int y) {
                ArtificialIntelligence ai = new ArtificialIntelligence(new TopDownMovement(),new RandomAttackPattern(64));
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
        if (getUpdateCount() < 2 || (getUpdateCount() > 6)) {
            formationFactory.createBoxFormation(random.nextInt(600), random.nextInt(3) + 1, random.nextInt(3) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 4) {
            formationFactory.createBoxFormation(random.nextInt(400), 6, 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 6) {
            formationFactory.createBoxFormation(random.nextInt(200), 1, 6, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        }
    }
}
