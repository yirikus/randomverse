package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;

/**
 * Level just to hold an event. No gameplay whatsoever
 *
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:04
 */
public class LevelEmpty extends World {

    public LevelEmpty(final SpriteCollection spriteCollection) {
        super(spriteCollection, 7, 0);
    }

    @Override
    protected void updateWorld() {
        //nothing
    }
}
