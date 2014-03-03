package cz.terrmith.randomverse.core.ai;

import cz.terrmith.randomverse.core.ai.attack.AttackPattern;
import cz.terrmith.randomverse.core.ai.movement.formation.FormationMovement;
import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.CanAttack;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable class that contains all behavioral patterns of an npc
 */
public class ArtificialIntelligence {

    private MovementPattern movementPattern;
    private AttackPattern attackPattern;
    private List<FormationMovement> formations = new ArrayList<FormationMovement>();

    /**
     * Creates new AI with given patterns and reference to a player
     * @param attackPattern
     */
    public ArtificialIntelligence(AttackPattern attackPattern) {
        this.attackPattern = attackPattern;
    }

//
//    private boolean shouldAttack() {
//        return attackPattern.shouldAttack();
//    }
//
//    private Position nextPosition(Position position, int speed) {
//        return movementPattern.nextPosition(position,speed);
//    }

    public void updateSprites(){
        for (FormationMovement f : formations) {
            f.updateSprites();
            List<Sprite> sprites = f.getSprites();
            for (Sprite sprite : sprites) {
                if (sprite instanceof CanAttack) {
                    updateAttack((CanAttack)sprite);
                }
            }
        }
    }

    private void updateAttack(CanAttack sprite) {
        if (attackPattern.shouldAttack()) {
            sprite.attack();
        }
    }

    /**
     * Registers formation with this movement pattern,
     * AI will update sprites of registered formations
     */
    public void registerFormation(FormationMovement formation) {
        this.formations.add(formation);
    }
}
