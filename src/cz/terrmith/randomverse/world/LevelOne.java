package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.Formation;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationMovement;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationOrder;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ship.Ship;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;
import cz.terrmith.randomverse.sprite.ship.part.gun.SimpleGun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Testing level
 */
public class LevelOne extends World {
    private final ArtificialIntelligence ai;
    private Random random = new Random();

    public LevelOne(final SpriteCollection spriteCollection, ArtificialIntelligence ai) {
        super(spriteCollection,1,2);
        this.ai = ai;
    }

    @Override
    protected void createSprites() {
        if (getUpdateCount() == 1 ) {
            System.out.println("create sprites 1");
            formation2();
        } else {
            System.out.println("create sprites 2");
            formation1();
        }
    }

    private void formation2() {
        waitForInactivation();
        int formationSize = 9;
        Formation formation1 = Formation.boxFormation(1, formationSize, new Position(0, 0), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        int amplitude = 100 + random.nextInt(300);
        int frequency = 200;// + random.nextInt(300);
//            MovementPattern waveMovement = new WaveMovement(amplitude, frequency);
//            MovementPattern waveMovement = new VectorMovement(new Position(0,1));

//            Formation formation2 = Formation.movementSimulation(formation1, waveMovement, 400);
        Formation formation2 = Formation.boxFormation(1, formationSize, new Position(0, 400), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        Formation formation3 = Formation.circle(new Position(400,100), 50, formationSize);
        Formation formation4 = Formation.circle(new Position(400, 100), 100, formationSize, 1);
        Formation formation5 = Formation.circle(new Position(400,200), 100, formationSize, 2);
        Formation formation6 = Formation.circle(new Position(400,200), 150, formationSize, 3);
        Formation formation7 = Formation.circle(new Position(400,200), 50, formationSize, 4);
        Formation formation8 = Formation.circle(new Position(400,200), 200, formationSize, 5);
        Formation formation9 = Formation.circle(new Position(400,200), 100, formationSize, 4);

        List<Formation> formations = new ArrayList<Formation>(3);
        formations.add(formation1);
        formations.add(formation2);
        formations.add(formation3);
        formations.add(formation4);
        formations.add(formation5);
        formations.add(formation6);
        formations.add(formation7);
        formations.add(formation8);
        formations.add(formation9);

        List<Sprite> enemies = new ArrayList<Sprite>(formationSize);
        for(int i = 0; i < formationSize; i++) {
            // position has no meaning, it will be repositioned upon FormationMovement creation
            Sprite sprite = createEnemy(0, 0);
            enemies.add(sprite);
            getSpriteCollection().put(SpriteLayer.NPC, sprite);
        }

        List<FormationOrder> orders = new ArrayList<FormationOrder>();
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1, 2, 3, 4, 5}, formationSize, 200L));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1, 2}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, formationSize, 100L));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));
        orders.add(FormationOrder.repeatedSequence(new Integer[]{1}, formationSize, null));

        FormationMovement formationMovement = new FormationMovement(enemies, formations, orders, null, 2);
        formationMovement.registerObserver(this);
        ai.registerFormation(formationMovement);
    }

    private void formation1() {
        waitForInactivation();
        Formation formation1 = Formation.boxFormation(1, 9, new Position(0, 0), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        Formation formation2 = Formation.boxFormation(1, 9, new Position(0, 150), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        Formation formation3 = Formation.boxFormation(3, 3, new Position(300, 300), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        Formation formation4 = Formation.boxFormation(3, 4, new Position(250, 400), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);
        Formation formation5 = Formation.boxFormation(1, 9, new Position(0, 0), Tile.DEFAULT_SIZE * 3, Tile.DEFAULT_SIZE * 3);


        List<Formation> formations = new ArrayList<Formation>(3);
        formations.add(formation1);
        formations.add(formation2);
        formations.add(formation3);
        formations.add(formation4);
        formations.add(formation5);

        List<Sprite> enemies = new ArrayList<Sprite>(9);
        for(int i = 0; i < 9; i++) {
            // position has no meaning, it will be repositioned upon FormationMovement creation
            Sprite sprite = createEnemy(0, 0);
            enemies.add(sprite);
            getSpriteCollection().put(SpriteLayer.NPC, sprite);
        }

        List<FormationOrder> orders = new ArrayList<FormationOrder>();
        orders.add(new FormationOrder(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        orders.add(new FormationOrder(new Integer[]{1, 1, 1, 2, 2, 2, 3, 3, 3}));
        orders.add(new FormationOrder(new Integer[]{1, 2, 1, 1, 2, 1, 2, 1, 2}));
        orders.add(new FormationOrder(new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1}));

        FormationMovement formationMovement = new FormationMovement(enemies, formations,orders, null);
        formationMovement.registerObserver(this);
        ai.registerFormation(formationMovement);
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
