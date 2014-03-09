package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.graphics.SpaceBackground;
import cz.terrmith.randomverse.inventory.Mission;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.WorldEvent;
import cz.terrmith.randomverse.world.events.util.LevelEmptyEvents;

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

    public LevelEmpty(final SpriteCollection spriteCollection, Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        super(spriteCollection, 7, 0);

        this.background = new SpaceBackground(10);

        setWorldEvent(randomEvent(callbacks));
    }

    private WorldEvent randomEvent(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        switch (random.nextInt(5)) {
            case 1: return LevelEmptyEvents.shop(callbacks);
            default: return LevelEmptyEvents.goSomewhere(callbacks);
        }
    }

    @Override
    protected void updateWorld() {
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
