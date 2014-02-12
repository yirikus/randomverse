package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementChain;
import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;

/**
 * Formation factory template
 */
public abstract class SpriteFormationFactory {

    private SpriteCollection spriteCollection;
    private MovementChain previousMovementChain;

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
                MovementPattern mp = createMovementPattern();
                Sprite enemy = createEnemy(xPos, -100, mp);
                enemy.translate(x * maxWidth, -y * maxHeight);
                this.spriteCollection.put(SpriteLayer.NPC, enemy);
                if (mp instanceof MovementChain) {
                    setPreviousMovementChain((MovementChain)mp);
                }
            }
        }
        //reset
        setPreviousMovementChain(null);
    }

    /**
     * This method should provide movement pattern that will be set to created enemies
     * @return
     */
    protected abstract MovementPattern createMovementPattern();

    /**
     * This method will be called to create an enemy
     * y is above the screen
     *
     * @param xPos position of an enemy
     * @param yPos position of an enemy
     * @param mp movement pattern
     * @return
     */
    protected abstract Sprite createEnemy(int xPos, int yPos, MovementPattern mp);

	public SpriteCollection getSpriteCollection() {
		return spriteCollection;
	}

	public void setSpriteCollection(SpriteCollection spriteCollection) {
		this.spriteCollection = spriteCollection;
	}

    public MovementChain getPreviousMovementChain() {
        return previousMovementChain;
    }

    private void setPreviousMovementChain(MovementChain previousMovementChain) {
        this.previousMovementChain = previousMovementChain;
    }
}
