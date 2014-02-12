package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.ai.movement.pattern.chain.MovementChainLink;
import cz.terrmith.randomverse.core.geometry.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Stateful (!) class that represents a movement pattern
 */
public class MovementChain implements MovementPattern {

    private List<MovementChainLink> links = new ArrayList<MovementChainLink>();
    private int index = 0;

    private Position startPosition;
    private long startTime;

    public MovementChain(MovementChainLink mp) {
        if (mp == null) {
            throw new IllegalArgumentException("mp must not be null");
        }
        links.add(mp);

    }

    /**
     * Creates MovementChain that
     * @return
     */
    public void addChainLink(MovementChainLink mp) {
       this.links.add(mp);
    }

    @Override
    public Position nextPosition(Position currentPosition, int speed) {
        if (startPosition == null) {
            startPosition = currentPosition;
            startTime = System.currentTimeMillis();
        }

        MovementChainLink currentChainLink = links.get(index);
        long timeTraveled = System.currentTimeMillis() - startTime;
        double distanceTraveled = startPosition.distanceFrom(currentPosition);

        if (links.size() > index + 1
                && currentChainLink.targetMet(currentPosition, timeTraveled, distanceTraveled)) {
            index++;
            currentChainLink = links.get(index);

            //set new starting points
            startPosition = currentPosition;
            startTime = System.currentTimeMillis();
        }

        return currentChainLink.getMovementPattern().nextPosition(currentPosition, speed);
    }

    /**
     * Returns true if movement chain has reached the last chain link
     */
    public boolean lastChainLinkReached() {
        return this.links.size() == index + 1;

    }
}
