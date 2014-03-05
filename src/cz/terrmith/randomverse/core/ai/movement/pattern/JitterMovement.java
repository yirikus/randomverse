package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.Random;

/**
 * MovementPattern wrapper
 * Adds jitter to given movement
 *
 * WARNING: this is kind of random
 *                      - Captain Obvious
 */
public class JitterMovement extends MovementPattern {

    private final long seed;
    private Random random;
    private MovementPattern mp;

    /**
     * Creates new movement with given seed based on given movement
     * @param baseMovement base movement
     * @param seed seed for random generator
     */
    public JitterMovement(MovementPattern baseMovement, long seed) {
        if (baseMovement == null) {
            throw new IllegalArgumentException("Base movement must not be null");
        }

        if (baseMovement instanceof JitterMovement) {
            throw new IllegalArgumentException("Cannot base JitterMovement on JitterMovement");
        }
        random = new Random(seed);
        this.seed = seed;
        this.mp = baseMovement;

    }


    /**
     * Creates new movement with random seed and based on given movement
     * @param baseMovement base movement
     */
    public JitterMovement(MovementPattern baseMovement) {
        this(baseMovement, System.currentTimeMillis());
    }

    @Override
    public Position nextPosition(Position start, double speed) {
        final Position next = mp.nextPosition(start,speed);

        Position diff = Position.difference(next, start);

        double dx = Math.abs(diff.getX());
        double dy = Math.abs(diff.getY());

        if (dx < dy) {
            return new Position(next.getX() + (2 * random.nextDouble() - 1) * speed, next.getY());
        } else {
            return new Position(next.getX(), next.getY() + (2 * random.nextDouble() - 1) * speed);
        }
    }

    @Override
    public MovementPattern copy() {
        return new JitterMovement(this.mp, this.seed);
    }
}
