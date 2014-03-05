package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.Formation;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationMovement;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationOrder;
import cz.terrmith.randomverse.core.ai.movement.formation.SimpleSpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.pattern.TrackingMovement;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.core.world.WorldEvent;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.enemy.Mine;
import cz.terrmith.randomverse.sprite.enemy.SimpleEnemy;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ship.Ship;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;
import cz.terrmith.randomverse.sprite.ship.part.gun.SimpleGun;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.LevelMineFieldEvents;
import cz.terrmith.randomverse.world.events.LevelOneEvents;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Testing level
 *
 * todo refactor formation movement creation - abstract method
 */
public class LevelMinefield extends World {
    public static final String ACTIVATION_KEY = "notImportant";
    private final ArtificialIntelligence ai;
    private final Color[] starColours;
    private final Position[] starCoordinates;
    private final Sprite playerSprite;

    public LevelMinefield(final SpriteCollection spriteCollection, Sprite playerSprite, ArtificialIntelligence ai, Map<EventResult, NavigableTextCallback> callbacks) {
        super(spriteCollection, 1, 3);
        this.ai = ai;
        this.playerSprite = playerSprite;

        int stars = 2 + random.nextInt(8);
        this.starColours = new Color[stars];
        this.starCoordinates = new Position[stars];
        for (int i = 0; i < starColours.length; i++) {
            starColours[i] = new Color(255, 155 + random.nextInt(100), 0);
            starCoordinates[i] = new Position(random.nextDouble(), random.nextDouble());
        }

        setWorldEvent(randomEvent(callbacks));
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback > callbacks) {
        switch (random.nextInt(5)) {
            case 1: return LevelMineFieldEvents.minefield(callbacks);
            default: return LevelMineFieldEvents.minefieldAvoidable(callbacks);
        }

    }

    @Override
    protected void createSprites() {
        deployMines();
    }

    private void deployMines() {
        waitForInactivation(ACTIVATION_KEY);

        Formation formation = Formation.boxFormation(3, 3, new Position(random.nextInt(800), 0), Mine.SIZE * 5, Mine.SIZE * 5);
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

        SpriteContainer scn = new SimpleSpriteContainer(sprites, new TrackingMovement(playerSprite));
        scn.registerObserver(this, ACTIVATION_KEY);
        ai.registerSpriteContainer(scn);
    }


    @Override
    public void drawMapIcon(Graphics g, Position position, int size) {

        //background
        g.setColor(Color.BLACK);
        g.fillRect((int) position.getX(),
                (int) position.getY(),
                size, size);
        //stars
        for (int i =0; i < starColours.length; i++) {
            g.setColor(starColours[i]);
            int x = (int)position.getX() + (int)(starCoordinates[i].getX() * size);
            int y = (int)position.getY() + (int)(starCoordinates[i].getY() * size);
            g.drawLine(x, y, x, y);
        }


    }
}
