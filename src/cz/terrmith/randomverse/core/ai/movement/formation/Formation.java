package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * represents positions where should be put enemies to create desired Formation
 */
public class Formation {

    private List<Position> positions;

    /**
     * Creates precomputed formation
     *
     * @param positions
     */
    public Formation(List<Position> positions) {
        this.positions = positions;
    }

    /**
     * creates new box formation of size ROWS x COLUMNS
     *
     * @param rows          rows
     * @param columns       columns
     * @param startPosition bottom left corner
     * @param width         space between columns
     * @param height        space between rows
     * @return
     */
    public static Formation BoxFormation(int rows, int columns, Position startPosition, int width, int height) {
        List<Position> formationPositions = new ArrayList<Position>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                formationPositions.add(new Position(x * width + startPosition.getX(),
                        -y * height + startPosition.getY()));
            }
        }
        return new Formation(formationPositions);
    }

    /**
     * Simulates movement and computes end positions
     * @return
     */
    public static Formation MovementSimulation(Formation startingFormation, MovementPattern mp, int distance) {
        List<Position> formationPositions = new ArrayList<Position>();
        for(Position p : startingFormation.getPositions()) {
            formationPositions.add(mp.nextPosition(p, distance));
        }
        return new Formation(formationPositions);
    }


    /**
     * Returns all positions of this formation
     *
     * @return
     */
    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    /**
     * Creates new sprites in this formation using factory
     * TODO delete ? will this be useful ?
     * @param sf sprite factory
     * @return sprites in a formation
     */
    public List<Sprite> createSpriteFormation(SpriteFactory sf) {
        List<Sprite> ret = new ArrayList<Sprite>();
        for (Position p : positions) {
            sf.newSprite((int) p.getX(), (int) p.getY());
        }
        return ret;
    }
}
