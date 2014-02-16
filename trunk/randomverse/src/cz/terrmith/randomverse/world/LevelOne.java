package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.Formation;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationMovement;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteFormationFactory;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.*;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ship.Ship;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;
import cz.terrmith.randomverse.sprite.ship.part.gun.SimpleGun;

import java.util.*;

/**
 * Testing level
 */
public class LevelOne extends World {
    private final ArtificialIntelligence ai;
    private Random random = new Random();
    private SpriteFormationFactory formationFactory;

    public LevelOne(final SpriteCollection spriteCollection, ArtificialIntelligence ai) {
        super(spriteCollection,3,100);
        this.ai = ai;
    }

    @Override
    protected void createSprites() {
        if (getUpdateCount() < 2 ) {
            Formation formation1 = Formation.BoxFormation(3, 3, new Position(200, 200), Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE);
            Formation formation2 = Formation.BoxFormation(2, 5, new Position(200, 400), Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE);
            Formation formation3 = Formation.BoxFormation(1, 9, new Position(200, 800), Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE);

            List<Formation> formations = new ArrayList<Formation>(3);
            formations.add(formation1);
            formations.add(formation2);
            formations.add(formation3);

            List<Sprite> enemies = new ArrayList<Sprite>(9);
            for(int i = 0; i < 9; i++) {
                // position has no meaning, it will be repositioned upon FormationMovement creation
                Sprite sprite = createEnemy(0, 0);
                enemies.add(sprite);
                getSpriteCollection().put(SpriteLayer.NPC, sprite);
            }

            List<Integer[]> orders = new ArrayList<Integer[]>();
            orders.add(new Integer[]{1, 1, 1, 2, 2, 2, 3, 3, 3});
            orders.add(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

            FormationMovement formationMovement = new FormationMovement(enemies, formations,orders);
            ai.registerFormation(formationMovement);
        }
    }

    private Sprite createEnemy(int x, int y) {
        //ArtificialIntelligence ai = new ArtificialIntelligence(mp, new RandomAttackPattern(64));
        Ship enemy = new Ship(x,y,null);
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

}
