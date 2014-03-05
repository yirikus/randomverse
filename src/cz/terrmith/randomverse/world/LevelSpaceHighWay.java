package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.GameWindow;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.SimpleSpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.core.world.WorldEvent;
import cz.terrmith.randomverse.sprite.InvisibleArea;
import cz.terrmith.randomverse.sprite.enemy.SimpleEnemy;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.SpaceHighwayEvents;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Random;

/**
 * Player has to reach designated area (see method addTargetArea)
 */
public class LevelSpaceHighWay extends World {
    private final ArtificialIntelligence ai;
    private final Color lineColor;
    private Random random = new Random();
    private boolean created;
    private int lanes;

    public LevelSpaceHighWay(final SpriteCollection spriteCollection, ArtificialIntelligence ai, Map<EventResult, NavigableTextCallback> callbacks) {
        super(spriteCollection, 1, 1);
        this.ai = ai;
        this.lineColor = new Color(167, 161, 255);

        setWorldEvent(randomEvent(callbacks));
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback > callbacks) {
        switch (random.nextInt(5)) {
            case 1:
                this.lanes = 4;
                return SpaceHighwayEvents.highway(callbacks);
            default:
                this.lanes = 2;
                return SpaceHighwayEvents.highway(callbacks);
        }

    }


    private void addTargetArea() {
        Sprite sprite = new InvisibleArea(0,0,GameWindow.SCREEN_W, 100);
        getSpriteCollection().put(SpriteLayer.NPC, sprite);

        SpriteContainer scn = new SimpleSpriteContainer(sprite);
        ai.registerSpriteContainer(scn);
        scn.registerObserver(this, String.valueOf(getUpdateCount()));
    }

    @Override
    protected void createSprites() {
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


    @Override
    public void drawMapIcon(Graphics g, Position position, int size) {

        //background
        g.setColor(Color.BLACK);
        g.fillRect((int) position.getX(),
                (int) position.getY(),
                size, size);

        g.setColor(lineColor);
        g.drawLine((int)position.getX(), (int)position.getY() + size/2,
                   (int)position.getX() + size, (int)position.getY() + size/2);


    }


}
