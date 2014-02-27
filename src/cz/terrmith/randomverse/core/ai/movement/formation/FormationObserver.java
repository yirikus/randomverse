package cz.terrmith.randomverse.core.ai.movement.formation;

/**
 * Obervers formation for any updates
 */
public interface FormationObserver {

    /**
     * Notifies observer that all sprites in formation were destroyed
     */
    void waveDestroyedNotification();
}
