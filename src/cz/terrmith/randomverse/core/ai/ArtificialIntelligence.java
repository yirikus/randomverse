package cz.terrmith.randomverse.core.ai;

import cz.terrmith.randomverse.core.ai.attack.AttackPattern;
import cz.terrmith.randomverse.core.ai.movement.MovementPattern;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;

/**
 * Immutable class that contains all behavioral patterns of an npc
 */
public class ArtificialIntelligence implements MovementPattern, AttackPattern{

    private MovementPattern movementPattern;
    private AttackPattern attackPattern;

    /**
     * Creates new AI with given patterns and reference to a player
     * @param movementPattern
     * @param attackPattern
     */
    public ArtificialIntelligence(MovementPattern movementPattern, AttackPattern attackPattern) {
        this.movementPattern = movementPattern;
        this.attackPattern = attackPattern;
    }

    public MovementPattern getMovementPattern() {
        return movementPattern;
    }

    public AttackPattern getAttackPattern() {
        return attackPattern;
    }


    @Override
    public boolean shouldAttack() {
        return attackPattern.shouldAttack();
    }

    @Override
    public Position nextPosition(Position position, int speed) {
        return movementPattern.nextPosition(position,speed);
    }

    public void updateSprite(Sprite sprite){
        updateMovement(sprite);
        if (sprite instanceof CanAttack) {
            updateAttack((CanAttack)sprite);
        }
    }

    private void updateAttack(CanAttack sprite) {
        if (attackPattern.shouldAttack()) {
            sprite.attack();
        }
    }

    private void updateMovement(Sprite sprite) {
        Position currentPos = new Position(sprite.getXPosn(), sprite.getYPosn());
        Position nextPosition = this.nextPosition(currentPos, 3);
        sprite.setPosition(nextPosition.getX(), nextPosition.getY());
    }
}
