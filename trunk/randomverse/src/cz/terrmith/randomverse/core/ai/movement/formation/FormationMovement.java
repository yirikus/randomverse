package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.geometry.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 13.2.14
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public class FormationMovement {
    List<MovementPattern> movementPatterns = new ArrayList<MovementPattern>();

    /**
     * Creates new formation movement
     * @param f formation
     * @param mp movement pattern that will be cloned to each formation position
     */
    public FormationMovement(Formation f, MovementPattern mp) {
        for (Position p : f.getPositions()) {
            movementPatterns.add(mp.copy());
        }

    }
}
