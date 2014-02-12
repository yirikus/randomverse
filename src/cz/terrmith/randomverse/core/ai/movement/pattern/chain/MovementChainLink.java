package cz.terrmith.randomverse.core.ai.movement.pattern.chain;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementChain;
import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.geometry.Position;

/**
 * Movement pattern that is limited by time, space or target position
 */
public class MovementChainLink {
    private MovementPattern movementPattern;

    private enum TargetType {POSITION, TIME, DISTANCE}

    private Position targetPosition;
    private long targetTime;
    private double targetDistance;
    private TargetType targetType;

    /**
     * Use static factory methods
     */
    private MovementChainLink() {
    }

    /**
     * Creates new movement chain link that will end when target position is reached
     *
     * @param mp             movement
     * @param targetPosition position to reach
     * @return
     */
    public static MovementChainLink positionedLink(MovementPattern mp, Position targetPosition) {
        if (mp instanceof MovementChain) {
            throw new IllegalArgumentException("It is not possible to put MovementChain into MovementChain");
        }

        if (targetPosition == null) {
            throw  new IllegalArgumentException("Position must not be null, it would be unreachable");
        }

        if (mp == null) {
            throw  new IllegalArgumentException("MovementPattern must not be null, target would be unreachable");
        }

        MovementChainLink newInstance = new MovementChainLink();
        newInstance.movementPattern = mp;
        newInstance.targetPosition = targetPosition;
        newInstance.targetType = TargetType.POSITION;

        return newInstance;
    }

    /**
     * Creates new movement chain link that will end when traveled for given time
     *
     * @param mp   movement
     * @param time time to travel
     * @return
     */
    public static MovementChainLink timedLink(MovementPattern mp, long time) {
        if (mp instanceof MovementChain) {
            throw new IllegalArgumentException("It is not possible to put MovementChain into MovementChain");
        }

        MovementChainLink newInstance = new MovementChainLink();
        newInstance.movementPattern = mp;
        newInstance.targetTime = time;
        newInstance.targetType = TargetType.TIME;

        return newInstance;
    }

    /**
     * Creates new movement chain link that will end when traveled given distance
     *
     * @param mp       movement
     * @param distance distance to travel
     * @return
     */
    public static MovementChainLink distanceddLink(MovementPattern mp, double distance) {
        if (mp instanceof MovementChain) {
            throw new IllegalArgumentException("It is not possible to put MovementChain into MovementChain");
        }

        if (mp == null) {
            throw  new IllegalArgumentException("MovementPattern must not be null, target would be unreachable");
        }

        MovementChainLink newInstance = new MovementChainLink();
        newInstance.movementPattern = mp;
        newInstance.targetDistance = distance;
        newInstance.targetType = TargetType.DISTANCE;

        return newInstance;
    }

    /**
     * Returns true if movement has reached its target
     *
     * @param p        current position
     * @param time     time traveled in this movement pattern
     * @param distance distance traveled in this movement pattern
     * @return
     */
    public boolean targetMet(Position p, long time, double distance) {
        switch (targetType) {
            case DISTANCE:
                return distance >= targetDistance;
            case POSITION:
                return targetPosition.equals(p);
            case TIME:
                return time >= targetTime;
        }

        throw new IllegalStateException("");
    }

    public MovementPattern getMovementPattern() {
        return movementPattern;
    }

}
