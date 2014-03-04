package cz.terrmith.randomverse.core.ai;

import cz.terrmith.randomverse.core.ai.attack.AttackPattern;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.CanAttack;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO singleton
 *
 * Immutable class that contains all behavioral patterns of an npc
 */
public class ArtificialIntelligence {

    private AttackPattern attackPattern;
    private List<SpriteContainer> spriteContainers = new ArrayList<SpriteContainer>();

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
        for (SpriteContainer scn : spriteContainers) {
            scn.updateSprites();
            List<Sprite> sprites = scn.getSprites();
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
     * Registers sprite container with this ai,
     * AI will update sprites of registered spriteContainers
     */
    public void registerSpriteContainer(SpriteContainer scn) {
        this.spriteContainers.add(scn);
    }
}
