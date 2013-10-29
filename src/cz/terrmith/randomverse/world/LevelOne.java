package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.attack.RandomAttackPattern;
import cz.terrmith.randomverse.core.ai.movement.TopDownMovement;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteFormationFactory;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.LootSprite;
import cz.terrmith.randomverse.core.sprite.abilitiy.Lootable;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.ExtensionPoint;
import cz.terrmith.randomverse.sprite.MoneyLoot;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.ShipPart;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 12.10.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class LevelOne extends World {
    private Random random = new Random();
    private SpriteFormationFactory formationFactory;

    public LevelOne(SpriteCollection spriteCollection) {
        super(spriteCollection,3);
        this.formationFactory = new SpriteFormationFactory(spriteCollection) {
            @Override
            protected Sprite createEnemy(int x, int y) {
                ArtificialIntelligence ai = new ArtificialIntelligence(new TopDownMovement(),new RandomAttackPattern(64));
                Ship enemy = new Ship(x,y,null,ai);
                if (random.nextBoolean()) {
                    enemy.addTile(-1, 1, new SimpleGun(getSpriteCollection(), Damage.DamageType.PLAYER,1));
                }
                if (random.nextBoolean()) {
                    SimpleGun flippedGun = new SimpleGun(getSpriteCollection(), Damage.DamageType.PLAYER,1);
                    flippedGun.flipHorizontal();
                    enemy.addTile(1, 1, flippedGun);
                }

	            Map<SpriteStatus, ImageLocation> imageForStatus = new HashMap<SpriteStatus, ImageLocation>();
	            imageForStatus.put(SpriteStatus.DEFAULT, new ImageLocation("midParts",random.nextInt(4)));
	            Set<ExtensionPoint> extensions = new HashSet<ExtensionPoint>();
	            extensions.add(ExtensionPoint.LEFT);
	            extensions.add(ExtensionPoint.RIGHT);
	            extensions.add(ExtensionPoint.TOP);
	            extensions.add(ExtensionPoint.BOTTOM);
	            ShipPart body = new ShipPart(1, imageForStatus, extensions);
	            enemy.addTile(0, 1, body);

                enemy.flipVertical();
	            ((Lootable) enemy).setLootSprite(new LootSprite(0,0,10,10,null, new MoneyLoot(1)));
                return enemy;
            }
        };

    }

    @Override
    protected void createSprites() {
        if (getUpdateCount() < 5) {
            formationFactory.createBoxFormation(random.nextInt(600), random.nextInt(3) + 1, random.nextInt(3) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 10) {
            formationFactory.createBoxFormation(random.nextInt(500), random.nextInt(4) + 1, random.nextInt(4) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 15) {
            formationFactory.createBoxFormation(random.nextInt(400), random.nextInt(5) + 1, random.nextInt(5) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 15) {
            formationFactory.createBoxFormation(random.nextInt(300), random.nextInt(6) + 1, random.nextInt(6) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 15) {
            formationFactory.createBoxFormation(random.nextInt(200), random.nextInt(7) + 1, random.nextInt(7) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else if (getUpdateCount() < 15) {
            formationFactory.createBoxFormation(random.nextInt(200), random.nextInt(8) + 1, random.nextInt(8) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        } else {
            formationFactory.createBoxFormation(random.nextInt(200), random.nextInt(9) + 1, random.nextInt(9) + 1, Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        }
    }
}
