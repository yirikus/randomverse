package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * Movement that is not actaully a movement... object will just hold position
 */
public class StopMovement extends MovementPattern {
    @Override
    public Position nextPosition(Position position, double speed) {
        return position;
    }

    @Override
    public MovementPattern copy() {
        return new StopMovement();
    }
}
