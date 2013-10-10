package cz.terrmith.randomverse.core.sprite.movement;


import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Represents movement pattern in which  enemy moves
 */
public interface MovementPattern {

    /**
     * Returns next position
     * @param position current position
     * @param speed speed of movement
     * @return
     */
    Position nextPosition(Position position, int speed);

}
