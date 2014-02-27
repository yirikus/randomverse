package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementChain;
import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.ai.movement.pattern.StopMovement;
import cz.terrmith.randomverse.core.ai.movement.pattern.VectorMovement;
import cz.terrmith.randomverse.core.ai.movement.pattern.chain.MovementChainLink;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents sequence of formations
 */
public class FormationMovement {
    private final MovementPattern[] customMovementPatterns;
    private final int repeatFrom;
    private List<Formation> formations;
    /**
     * Each entry is a list of rder number for a formation with corresponding index
     */
    private List<FormationOrder> orders;
    private List<Sprite> sprites;
    /**
     * Movement patterns revelant for current formation index.
     * When moving to next formation is done, these patterns will be replaced
     */
    private List<MovementChain> movementChains = new ArrayList<MovementChain>();
    private int targetFormationIndex;
    private int currentOrderNo;
    private FormationOrder order;
    private enum MovementStatus {FINISHED, NOT_STARTED, IN_PROGRESS}
    private Long movementStartTime;
    private MovementStatus[] movementStatus;

    /**
     * True if all movements for CURRENT FORMATION were finished
     */
    private boolean formationMovementFinished;

    /**
     *
     * @param sprites
     * @param formations
     * @param orders orders in which sprites will move (if all numbers are the same, they will move simultaneously)
     * @param customMovementPatterns custom movement patterns to be used instead of VectorMovemement
     */
    public FormationMovement(List<Sprite> sprites, List<Formation> formations, List<FormationOrder> orders, MovementPattern[] customMovementPatterns) {
        this(sprites, formations, orders, customMovementPatterns, -1);
    }

    /**
     *
     * @param sprites
     * @param formations formations
     * @param orders orders in which sprites will move (if all numbers are the same, they will move simultaneously)
     * @param customMovementPatterns custom movement patterns to be used instead of VectorMovemement
     * @param repeatFrom [0, formation.size] or negative if not desired
     *
     */
    public FormationMovement(List<Sprite> sprites, List<Formation> formations, List<FormationOrder> orders, MovementPattern[] customMovementPatterns, int repeatFrom) {
        if ((repeatFrom < 0 && orders.size() != (formations.size() - 1))
            || (repeatFrom >= 0 && orders.size() != formations.size())) {
            throw new IllegalArgumentException("Formation movement orders must be (number of formation - 1), or equal to formations size if repeatFrom >= 0, formations: " + formations.size() + ", orders: " + orders.size());
        }

        if (repeatFrom > (formations.size() - 1)) {
            throw new IllegalArgumentException("Wrong parameter, repeat from should be in interval [0,formation.size - 1] or negative if not desired, was: " + repeatFrom);
        }

        if (customMovementPatterns != null && customMovementPatterns.length != orders.size()) {
            throw new IllegalArgumentException("Custom movement size must be equal to formation size - 1, should be: " + orders.size() + ", was: " + customMovementPatterns.length);
        }

        if (sprites == null || formations == null) {
            throw new IllegalArgumentException("sprites and formations must not be null");
        }
        if (sprites.size() > formations.get(0).getPositions().size()) {
            throw new IllegalArgumentException("Number of sprites must be <= than number of positions in a formation");
        }

        this.sprites = Collections.unmodifiableList(sprites);
        this.movementStatus = new MovementStatus[sprites.size()];
        for (int i = 0 ;i < sprites.size(); i++) {
            movementStatus[i] = (MovementStatus.NOT_STARTED);
        }
        this.formations = Collections.unmodifiableList(formations);
        this.orders = Collections.unmodifiableList(orders);
        this.order = orders.get(0);
        this.targetFormationIndex = 1;
        this.customMovementPatterns = customMovementPatterns;
        this.repeatFrom = repeatFrom;

        //reposition sprites to match first formation
        List<Position> positions = formations.get(0).getPositions();
        for (int i = 0; i < sprites.size(); i ++) {
            Sprite sprite = sprites.get(i);
            Position p = positions.get(i);
            sprite.setPosition(p.getX(), p.getY());
        }
        createFormationMovementChain(formations.get(0), formations.get(1), customMovementPatterns != null ? customMovementPatterns[0] : null);
    }

    /**
     * Creates movements necessary to reach next formation
     * @param first starting formation
     * @param second formation to be reached
     */
    private void createFormationMovementChain(Formation first, Formation second, MovementPattern customMovement) {
        List<Position> positionsFirst = first.getPositions();
        List<Position> positionsSecond = second.getPositions();
        movementChains.clear();
        for (int i = 0; i < sprites.size(); i++) {
            final MovementPattern mp;
            if (customMovement != null) {
                mp = customMovement;
            } else {
                mp = new VectorMovement(positionsFirst.get(i), positionsSecond.get(i));
            }
            MovementChainLink firstLink = MovementChainLink.positionedLink(mp, positionsSecond.get(i));
            final MovementChain movementChain = new MovementChain(firstLink);
            movementChain.addChainLink(MovementChainLink.timedLink(new StopMovement(), 0));

            movementChains.add(movementChain);
        }
    }

    /**
     * Move sprites to next formation
     */
    public void updateSprites() {
        final long currentTime = System.currentTimeMillis();
        final Integer maxOrder = Collections.max(Arrays.asList(order.getOrders()));
        if (formationMovementFinished && targetFormationIndex >= (formations.size())) {
            //all movements finished
            return;
        }

        boolean orderMovementFinished = true;

        //update movement
        for (int i = 0; i < sprites.size(); i++) {
            int ithOrderNo = order.getOrders()[i];
            if (ithOrderNo == currentOrderNo || movementStatus[i] == MovementStatus.IN_PROGRESS) {
                if(order.getWaitTime() != null && movementStartTime == null) {
                    movementStartTime = currentTime;
                }
                boolean  singleMovementFinished = updateMovement(i);
                if (singleMovementFinished) {
                    movementStatus[i] = MovementStatus.FINISHED;
                } else {
                    movementStatus[i] = MovementStatus.IN_PROGRESS;
                }
                orderMovementFinished = orderMovementFinished && (singleMovementFinished || timePassed(currentTime, maxOrder));
            }
        }

        int newOrderNo = maxOrder;
        if (orderMovementFinished) {
            // find next order no (minimum of following order numbers)
            for (int i = 0; i < sprites.size(); i++) {
                int ithOrderNo = order.getOrders()[i];
                if (ithOrderNo > currentOrderNo && ithOrderNo < newOrderNo) {
                    newOrderNo = ithOrderNo;
                }
            }
            formationMovementFinished = currentOrderNo == maxOrder;
            currentOrderNo = newOrderNo;
            movementStartTime = System.currentTimeMillis();

            if (formationMovementFinished) {
                //reset movement status
                for (int i = 0; i < movementStatus.length; i++) {
                    movementStatus[i] = MovementStatus.NOT_STARTED;
                }
                // move to next formation
                targetFormationIndex += 1;
                if (targetFormationIndex < (formations.size())) {
                    //update orders
                    order = orders.get(targetFormationIndex -1);
                    currentOrderNo = Collections.min(Arrays.asList(order.getOrders()));
                    // replace movement patterns
                    createFormationMovementChain(formations.get(targetFormationIndex - 1),
                                                 formations.get(targetFormationIndex),
                                                 customMovementPatterns != null ? customMovementPatterns[targetFormationIndex - 1] : null);
                    formationMovementFinished = false;
                } else if (repeatFrom >= 0){
                    //formation finished, but repeat is desired
                    targetFormationIndex = repeatFrom;

                    order = orders.get(orders.size() - 1);
                    currentOrderNo = Collections.min(Arrays.asList(order.getOrders()));
                    // replace movement patterns
                    createFormationMovementChain(formations.get(formations.size() - 1),
                            formations.get(targetFormationIndex),
                            customMovementPatterns != null ? customMovementPatterns[formations.size() - 1] : null);
                    formationMovementFinished = false;
                }

            }
        }
    }

    /**
     * Returns true if enough time passed to move to next orderNo
     * Returns always false if maxOrder == currentOrderNo, or waitTime for current order is not set
     *
     * @param currentTime provide current time to prevent repeated computation
     * @param maxOrder provide max order of current order to prevent repeated computation
     * @return
     */
    private boolean timePassed(long currentTime, int maxOrder) {
        if (order.getWaitTime() == null) {
            return false;
        }
        long timePassed = currentTime - movementStartTime;
        return currentOrderNo != maxOrder && timePassed >= order.getWaitTime();
    }

    /**
     *
     * @param i
     * @return true if movements target was met
     */
    private boolean updateMovement(int i) {
        Sprite sprite = sprites.get(i);
        if (!sprite.isActive()) {
            return true;
        }
        MovementChain mp = movementChains.get(i);
        Position currentPos = new Position(sprite.getXPosn(), sprite.getYPosn());
        Position nextPosition = mp.nextPosition(currentPos, 3);
        sprite.setPosition(nextPosition.getX(), nextPosition.getY());

        return mp.lastChainLinkReached();
    }


    public List<Sprite> getSprites() {
        return sprites;
    }
}
