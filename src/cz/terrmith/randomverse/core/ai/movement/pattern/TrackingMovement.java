package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * Movement towards given sprite
 *
 * User: TERRMITh
 * Date: 5.3.14
 * Time: 20:25
 */
public class TrackingMovement extends MovementPattern{

    private Sprite target;

    /**
     *
     * @param target target of movement
     */
    public TrackingMovement(Sprite target) {
        this.target = target;
    }

    @Override
    public Position nextPosition(Position position, double speed) {
        Position vector = Position.normalizedVector(position, new Position(target.getXPosn(), target.getYPosn()));
        return Position.sum(position, new Position(speed * vector.getX(), speed * vector.getY()));
    }

    @Override
    public MovementPattern copy() {
        // this movement pattern is idempotent
        return this;
    }
}
