package cz.terrmith.randomverse.core.ai.movement.pattern;


import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 11.10.13
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */
public class TopDownMovement implements MovementPattern{

    @Override
    public Position nextPosition(Position position, int speed) {
        return new Position(position.getX(), position.getY() + speed);
    }

    @Override
    public MovementPattern copy() {
        return new TopDownMovement();
    }
}
