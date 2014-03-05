package cz.terrmith.randomverse.core.ai.movement.pattern;


import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * Immutable class that represents movement pattern in which  enemy moves
 */
public abstract class MovementPattern {

    /**
     * Returns next position
     * @param position current position
     * @param speed speed of movement
     * @return
     */
    public abstract Position nextPosition(Position position, double speed);

    /**
     * Returns next position based on position and speed of given sprite
     * @param sprite sprite
     * @return
     */
    public Position nextPosition(Sprite s) {
        return nextPosition(new Position(s.getXPosn(), s.getYPosn()), s.getSpeed());
    }

    /**
     * returns copy of MovementPattern (new != this, but this.equals(new) )
     * @return
     */
    public abstract MovementPattern copy();
}
