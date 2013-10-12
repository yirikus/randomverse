package cz.terrmith.randomverse.sprite.enemy;

import cz.terrmith.randomverse.core.ai.movement.MovementPattern;
import cz.terrmith.randomverse.core.sprite.MultiSprite;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 10.10.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class SimpleEnemy extends MultiSprite {
    /**
     * Creates empty multisprites, tiles are expected to be added by calling provided methods
     */
    public SimpleEnemy(int x, int y, MovementPattern ai) {
        super(x, y);
    }
}
