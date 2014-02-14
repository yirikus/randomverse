package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *  represents positions where should be put enemies to create desired Formation
 */
public class Formation {

    List<Position> positions;

    /**
     * Creates precomputed formation
     * @param positions
     */
    public Formation(List<Position> positions) {
        this.positions = positions;
    }

    /**
     * creates new box formation of size ROWS x COLUMNS
     * @param rows rows
     * @param columns columns
     * @param startPosition bottom left corner
     * @param width space between columns
     * @param height space between rows
     * @return
     */
    public static Formation BoxFormation(int rows, int columns, Position startPosition, int width, int height) {
        List<Position> formationPositions = new ArrayList<Position>();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
//                MovementPattern mp = createMovementPattern();
//                Sprite enemy = createEnemy(xPos, -100, mp);
//                enemy.translate(x * width, -y * height);
//                this.spriteCollection.put(SpriteLayer.NPC, enemy);
                formationPositions.add(new Position(x * width + startPosition.getX(),
                                                   -y * height + startPosition.getY()));

            }
        }
        return new Formation(formationPositions);
    }

    public List<Position> getPositions() {
        return positions;
    }

    /**
     * Creates new sprites in this formation
     * @param sf sprite factory
     * @return sprites in a formation
     */
    public List<Sprite> createSpriteFormation(SpriteFactory sf) {
        List<Sprite> ret = new ArrayList<Sprite>();
        for (Position p : positions) {
            sf.newSprite((int)p.getX(), (int)p.getY());
        }
        return ret;
    }

    /**
     * Translates given sprites to positions defined by this formation
     * @param sprites list of sprites, size must be <= size of formation positions
     */
    public void applyFormation(List<Sprite> sprites) {

    }
}
