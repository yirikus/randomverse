package cz.terrmith.randomverse.world;

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
import cz.terrmith.randomverse.graphics.SpaceBackground;
import cz.terrmith.randomverse.sprite.enemy.debris.Debris;
import cz.terrmith.randomverse.world.events.DebrisFieldEvents;
import cz.terrmith.randomverse.world.events.EventResult;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Random;

/**
 * Debris level
 */
public class LevelDebrisField extends World {

    private static Random random = new Random();
    private final Sprite player;
    private final ArtificialIntelligence ai;
    private final SpaceBackground background;

    /**
     * @param spriteCollection sprite collection
     */
    public LevelDebrisField(SpriteCollection spriteCollection, Sprite player, ArtificialIntelligence ai, Map<EventResult, NavigableTextCallback > callbacks) {
        super(spriteCollection, 5, 5);
        this.player = player;
        this.ai = ai;

        this.background = new SpaceBackground(5);

        setWorldEvent(randomEvent(callbacks));
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback > callbacks) {
        switch (random.nextInt(5)) {
            default: return DebrisFieldEvents.shipwreck(callbacks);
        }

    }

    @Override
    protected void createSprites() {
        Debris enemy = new Debris(random.nextInt() % 600 + 100, -100, getSpriteCollection(), player);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        SpriteContainer scn = new SimpleSpriteContainer(enemy);
        ai.registerSpriteContainer(scn);
        scn.registerObserver(this, String.valueOf(getUpdateCount()));
        enemy.setStep(0, enemy.getSpeed());
    }

    @Override
    public void drawMapIcon(Graphics g, Position position, int size) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect((int) position.getX(),
                (int) position.getY(),
                size, size);
        //stars
        background.drawBackground(g, position, size);

    }
}
