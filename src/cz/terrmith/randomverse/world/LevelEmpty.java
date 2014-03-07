package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.core.world.WorldEvent;
import cz.terrmith.randomverse.graphics.SpaceBackground;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.LevelEmptyEvents;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

/**
 * Level just to hold an event. No gameplay whatsoever
 *
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:04
 */
public class LevelEmpty extends World {

    private final SpaceBackground background;

    public LevelEmpty(final SpriteCollection spriteCollection, Map<EventResult, NavigableTextCallback> callbacks) {
        super(spriteCollection, 7, 0);

        this.background = new SpaceBackground(10);

        setWorldEvent(randomEvent(callbacks));
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback > callbacks) {
        switch (random.nextInt(5)) {
            default: return LevelEmptyEvents.shop(callbacks);
        }
    }

    @Override
    protected void createSprites() {
        //nothing
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
