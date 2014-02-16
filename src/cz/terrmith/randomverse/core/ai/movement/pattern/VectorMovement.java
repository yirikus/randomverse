package cz.terrmith.randomverse.core.ai.movement.pattern;


import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 11.10.13
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */
public class VectorMovement implements MovementPattern{

    private final Position vector;

    /**
     * Creates top down movement
     */
    public VectorMovement() {
        this(new Position(0.0, 1.0));
    }

    /**
     * Cretes movement that is directed towards given point
     * @param vector
     */
    public VectorMovement(Position vector) {
        this.vector = Position.normalizedVector(new Position(0, 0), vector);
    }

    @Override
    public Position nextPosition(Position position, int speed) {
        return new Position(position.getX() + (vector.getX() * speed),
                            position.getY() + (vector.getY() * speed));
    }

    @Override
    public MovementPattern copy() {
        return new VectorMovement();
    }
}
