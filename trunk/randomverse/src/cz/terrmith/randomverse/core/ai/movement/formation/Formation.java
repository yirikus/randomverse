package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * represents positions where should be put enemies to create desired Formation
 */
public class Formation {

    private List<Position> positions;
    private static Random random = new Random();

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
    public static Formation boxFormation(int rows, int columns, Position startPosition, int width, int height) {
        List<Position> formationPositions = new ArrayList<Position>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                formationPositions.add(new Position(x * width + startPosition.getX(),
                        -y * height + startPosition.getY()));
            }
        }
        return new Formation(formationPositions);
    }

    public static Formation randomBoxFormation(int rows, int columns, Position startPosition, int width, int height) {
        List<Position> formationPositions = new ArrayList<Position>();
        for (int y = 0; y < rows; y++) {
            int chosen = random.nextInt(columns);
            for (int x = 0; x < columns; x++) {
                if (chosen == x) {
                    formationPositions.add(new Position(x * width + startPosition.getX(),
                            -y * height + startPosition.getY()));
                }
            }
        }
        return new Formation(formationPositions);
    }

    /**
     * Returns translated formation
     * @return
     */
    public Formation translate(int dx, int dy) {
        List<Position> translated = new ArrayList<Position>();
        for (Position p : this.getPositions()) {
            translated.add(new Position(p.getX() + dx, p.getY() + dy));
        }
        return new Formation(translated);

    }

    /**
     * Simulates movement and computes end positions
     * @return
     */
    public static Formation movementSimulation(Formation startingFormation, MovementPattern mp, int distance) {
        List<Position> formationPositions = new ArrayList<Position>();
        for(Position p : startingFormation.getPositions()) {
            formationPositions.add(mp.nextPosition(p, distance));
        }
        return new Formation(formationPositions);
    }

    /**
     * Distributes given number of positions evenly on a circle
     * @param c center
     * @param radius radius
     * @param positionCount number of positions
     * @return new formation
     */
    public static Formation circle(Position c, int radius, int positionCount, int startingPosition){
        List<Position> formationPositions = new ArrayList<Position>();
        double delta = (2 * Math.PI) / positionCount;
        double angle = 0.0 + startingPosition * delta;
        for (int i = 0; i < positionCount; i++) {
            formationPositions.add(Position.pointOnCircle(c, (double)radius, angle, true));
            angle += delta;
        }
        return new Formation(formationPositions);
    }

    /**
     * Distributes given number of positions evenly on a circle
     * @param c center
     * @param radius radius
     * @param positionCount number of positions
     * @return new formation
     */
    public static Formation circle(Position c, int radius, int positionCount){
        return circle(c, radius, positionCount, 0);
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
