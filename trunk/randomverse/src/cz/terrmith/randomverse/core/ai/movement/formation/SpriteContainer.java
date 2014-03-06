package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.List;

/**
 * Defines classes that hold sprites like FormationMovement
 *
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 9:48
 */
public interface SpriteContainer {
    /**
     * Registers observer of this container
     * @param obs observer
     * @param activationKey observer is registered under a key
     * TODO one activation key per observable
     */
    void registerObserver(SpriteContainerObserver obs, String activationKey);

    /**
     * Updates sprites
     */
    void updateSprites();

    /**
     * Returns all sprites in this container
     * @return
     */
    List<Sprite> getSprites();

    /**
     * Returns true if all sprites are active
     */
    public boolean isActive();

}
