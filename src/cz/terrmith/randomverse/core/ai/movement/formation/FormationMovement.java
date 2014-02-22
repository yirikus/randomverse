package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementChain;
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
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 13.2.14
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public class FormationMovement {
    private List<Formation> formations;
    /**
     * Each entry is a list of rder number for a formation with corresponding index
     */
    private List<Integer[]> orders;
    private List<Sprite> sprites;
    /**
     * Movement patterns revelant for current formation index.
     * When moving to next formation is done, these patterns will be replaced
     */
    private List<MovementChain> movementChains = new ArrayList<MovementChain>();
    private int targetFormationIndex;
    private int currentOrderNo;
    private Integer[] order;
    /**
     * True if all movements for CURRENT FORMATION were finished
     */
    private boolean formationMovementFinished;


    /**
     *
     * @param sprites
     * @param formations
     * @param orders orders in which sprites will move (if all numbers are the same, they will move simultaneously)
     */
    public FormationMovement(List<Sprite> sprites, List<Formation> formations, List<Integer[]> orders) {
        if (orders.size() != (formations.size() - 1)) {
            throw new IllegalArgumentException("Formation movement orders must be (number of formation - 1), formations: " + formations.size() + ", orders: " + orders.size());
        }

        if (sprites == null || formations == null) {
            throw new IllegalArgumentException("sprites and formations must not be null");
        }
        if (sprites.size() > formations.get(0).getPositions().size()) {
            throw new IllegalArgumentException("Number of sprites must be <= than number of positions in a formation");
        }

        this.sprites = Collections.unmodifiableList(sprites);
        this.formations = Collections.unmodifiableList(formations);
        this.orders = Collections.unmodifiableList(orders);
        this.order = orders.get(0);
        this.targetFormationIndex = 1;

        //reposition sprites to match first formation
        List<Position> positions = formations.get(0).getPositions();
        for (int i = 0; i < sprites.size(); i ++) {
            Sprite sprite = sprites.get(i);
            Position p = positions.get(i);
            sprite.setPosition(p.getX(), p.getY());
        }
        createFormationMovementChain(formations.get(0), formations.get(1));
    }

    /**
     * Creates movements necessary to reach next formation
     * @param first starting formation
     * @param second formation to be reached
     */
    private void createFormationMovementChain(Formation first, Formation second) {
        List<Position> positionsFirst = first.getPositions();
        List<Position> positionsSecond = second.getPositions();
        movementChains.clear();
        for (int i = 0; i < sprites.size(); i++) {
            MovementChainLink firstLink = MovementChainLink.positionedLink(new VectorMovement(positionsFirst.get(i), positionsSecond.get(i)), positionsSecond.get(i));
            final MovementChain movementChain = new MovementChain(firstLink);
            movementChain.addChainLink(MovementChainLink.timedLink(new StopMovement(), 0));

            movementChains.add(movementChain);
        }
    }

    /**
     * Move sprites to next formation
     */
    public void updateSprites() {
        if (formationMovementFinished && targetFormationIndex >= (formations.size())) {
            //all movements finished
            return;
        }
        System.out.println("formation movement: " + targetFormationIndex);

        boolean orderMovementFinished = true;

        //update movement
        for (int i = 0; i < sprites.size(); i++) {
            int ithOrderNo = order[i];
            if (ithOrderNo == currentOrderNo) {
                boolean  singleMovementFinished = updateMovement(i);
                orderMovementFinished = orderMovementFinished && singleMovementFinished;
            }
        }

        // find next order no (minimum of following order numbers)
        int newOrderNo = order[order.length - 1];
        if (orderMovementFinished) {
            for (int i = 0; i < sprites.size(); i++) {
                int ithOrderNo = order[i];
                if (ithOrderNo > currentOrderNo && ithOrderNo < newOrderNo) {
                    newOrderNo = ithOrderNo;
                }
            }
            formationMovementFinished = currentOrderNo == Collections.max(Arrays.asList(order));
            currentOrderNo = newOrderNo;

            if (formationMovementFinished) {
                System.out.println("formation movement finished: " + targetFormationIndex);
                // move to next formation
                targetFormationIndex += 1;
                if (targetFormationIndex < (formations.size())) {
                    //update orders
                    order = orders.get(targetFormationIndex -1);
                    currentOrderNo = Collections.min(Arrays.asList(order));
                    // replace movement patterns
                    createFormationMovementChain(formations.get(targetFormationIndex - 1), formations.get(targetFormationIndex));
                    formationMovementFinished = false;
                }

            }
        }
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
