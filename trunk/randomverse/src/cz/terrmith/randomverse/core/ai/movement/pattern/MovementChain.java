package cz.terrmith.randomverse.core.ai.movement.pattern;

import cz.terrmith.randomverse.core.ai.movement.pattern.chain.MovementChainLink;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stateful (!) class that represents a movement pattern
 */
public class MovementChain extends MovementPattern {

    private List<MovementChainLink> links = new ArrayList<MovementChainLink>();
    private int index = 0;

    private Position startPosition;
    private long startTime;

    public MovementChain (List<MovementChainLink> links) {
        this.links = links;
    }

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
    public Position nextPosition(Position currentPosition, double speed) {
        if (startPosition == null) {
            startPosition = currentPosition;
            startTime = System.currentTimeMillis();
        }

        MovementChainLink currentChainLink = links.get(index);
        long timeTraveled = System.currentTimeMillis() - startTime;
        double distanceTraveled = startPosition.distanceFrom(currentPosition);

        if (links.size() > index + 1
                && currentChainLink.targetMet(currentPosition, timeTraveled, distanceTraveled, speed)) {
            index++;
            currentChainLink = links.get(index);

            //set new starting points
            startPosition = currentPosition;
            startTime = System.currentTimeMillis();
        }

        return currentChainLink.getMovementPattern().nextPosition(currentPosition, speed);
    }

    @Override
    public MovementPattern copy() {
        return new MovementChain(new ArrayList<MovementChainLink>(getLinks()));
    }

    /**
     * Returns true if movement chain has reached the last chain link
     */
    public boolean lastChainLinkReached() {
        return this.links.size() == index + 1;
    }

    public List<MovementChainLink> getLinks() {
        return Collections.unmodifiableList(links);
    }
}
