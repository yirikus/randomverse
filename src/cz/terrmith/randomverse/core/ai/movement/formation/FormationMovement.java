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
    private int currentFormationIndex;
    private int currentOrderNo;
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

        //reposition sprites to match first formation
        List<Position> positions = formations.get(0).getPositions();
        for (int i = 0; i < sprites.size(); i ++) {
            Sprite sprite = sprites.get(i);
            Position p = positions.get(i);
            sprite.setPosition(p.getX(), p.getY());
        }
        createFormationMovementChain(formations.get(0));
    }

    /**
     * Creates movements necessary to reach next formation
     * @param formation formation to be reached
     */
    private void createFormationMovementChain(Formation formation) {
        List<Position> positions = formation.getPositions();
        movementChains.clear();
        for (int i = 0; i < positions.size(); i++) {
            MovementChainLink firstLink = MovementChainLink.timedLink(new StopMovement(), 0);
            final MovementChain movementChain = new MovementChain(firstLink);
            movementChain.addChainLink(MovementChainLink.positionedLink(new VectorMovement(positions.get(i)), positions.get(i)));
            movementChain.addChainLink(MovementChainLink.timedLink(new StopMovement(),0));

            movementChains.add(movementChain);
        }
    }

    /**
     * Move sprites to next formation
     */
    public void updateSprites() {
        if (formationMovementFinished && currentFormationIndex >= (formations.size() - 1)) {
            //all movements finished
            return;
        }
        int newOrderNo = currentOrderNo;
        Integer[] order = orders.get(currentFormationIndex);
        boolean orderMovementFinished = true;

        //update movement
        for (int i = 0; i < sprites.size(); i++) {
            int ithOrderNo = order[i];
            if (ithOrderNo == currentOrderNo) {
                orderMovementFinished = orderMovementFinished && updateMovement(i);
            }
        }

        if (orderMovementFinished) {

            // find next order no (minimum of following order numbers)
            for (int i = 0; i < sprites.size(); i++) {
                int ithOrderNo = order[i];
                if ((newOrderNo == currentOrderNo
                        || (ithOrderNo > currentOrderNo && ithOrderNo < newOrderNo))) {
                    newOrderNo = ithOrderNo;
                }
            }
            currentOrderNo = newOrderNo;
        }
        formationMovementFinished = orderMovementFinished && currentOrderNo == Collections.max(Arrays.asList(order));

        if (formationMovementFinished) {
            // move to next formation
            currentFormationIndex++;
            if (currentFormationIndex >= (formations.size() - 1)) {
                //movement finished
                return;
            }

            // replace movement patterns
            createFormationMovementChain(formations.get(currentFormationIndex + 1));
            orderMovementFinished = false;
        }
    }

    /**
     *
     * @param i
     * @return true if movements target was met
     */
    private boolean updateMovement(int i) {
        Sprite sprite = sprites.get(i);
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
