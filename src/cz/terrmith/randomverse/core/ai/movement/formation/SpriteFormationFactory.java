package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 13.10.13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class SpriteFormationFactory {

    private SpriteCollection spriteCollection;
	private MovementPattern movementPattern;

    protected SpriteFormationFactory(SpriteCollection spriteCollection) {
        this.spriteCollection = spriteCollection;
    }

    /**
     * Creates box formation
     * @param xPos position x
     * @param enemiesPerRow enemies per row
     * @param rows number of rows
     */
    public void createBoxFormation(int xPos, int enemiesPerRow, int rows, int maxWidth, int maxHeight) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < enemiesPerRow; x++) {
                Sprite enemy = createEnemy(xPos,-100);
                enemy.translate(x * maxWidth, -y * maxHeight);
                this.spriteCollection.put(SpriteLayer.NPC, enemy);
            }
        }
    }

    protected abstract Sprite createEnemy(int xPos, int i);

	public SpriteCollection getSpriteCollection() {
		return spriteCollection;
	}

	public void setSpriteCollection(SpriteCollection spriteCollection) {
		this.spriteCollection = spriteCollection;
	}

	public MovementPattern getMovementPattern() {
		return movementPattern;
	}

	public void setMovementPattern(MovementPattern movementPattern) {
		this.movementPattern = movementPattern;
	}
}