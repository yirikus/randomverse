package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Movement that is not actaully a movement... object will just hold position
 */
public class StopMovement implements MovementPattern {
    @Override
    public Position nextPosition(Position position, int speed) {
        return position;
    }
}
