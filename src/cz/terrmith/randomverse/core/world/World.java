package cz.terrmith.randomverse.core.world;

import cz.terrmith.randomverse.core.ai.movement.formation.FormationObserver;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;

import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

/**
 * Endless world that creates sprites just activate active screen based on time and number of updates
 */
public abstract class World implements FormationObserver{

    private long startTime;
    private long period;
    private long updateCount = 0;
    private SpriteCollection spriteCollection;
    private boolean paused;
    private long pausedTime;
	private long wavesToDefeat = 0;
    private long wavesDefeated = 0;
    private boolean waitForNotification = false;
    private String activationKey;

    /**
     *
     * @param spriteCollection
     * @param period time to wait between updates in seconds
     */
    public World(SpriteCollection spriteCollection, long period, long wavesToDefeat) {
        this.spriteCollection = spriteCollection;
        this.startTime = System.currentTimeMillis();
        this.period = TimeUnit.SECONDS.toMillis(period);
	    this.wavesToDefeat = wavesToDefeat;
    }

    /**
     * updates sprite collection if enough time was waited
     */
    public void update() {
        if (!paused && !waitForNotification && (period * updateCount) < (System.currentTimeMillis() - startTime)) {
//            System.out.println("beforeUpdate " + !paused + " | " + !waitForNotification +" | " + (period * updateCount) + " | " + (System.currentTimeMillis() - startTime));
            updateCount++;
            System.out.println("updateCount: " + updateCount);
            createSprites();
        }
    }

    public boolean completed() {
        return getWavesDefeated() >= getWavesToDefeat();
    }

    public long getUpdateCount() {
        return updateCount;
    }

    /**
     * Expected to contain logic that adds sprites to sprite collection based on current updateCount
     */
    protected abstract void createSprites();

    public SpriteCollection getSpriteCollection() {
        return spriteCollection;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (paused) {
            this.pausedTime = System.currentTimeMillis();
        } else {
            this.startTime += (System.currentTimeMillis() - pausedTime);
        }
    }

    public long getWavesToDefeat() {
        return wavesToDefeat;
    }

    public long getWavesDefeated() {
        return wavesDefeated;
    }

    public void incrementWavesDefeated() {
        wavesDefeated++;
    }

    @Override
    public void waveDestroyedNotification(String activationKey) {
        incrementWavesDefeated();
        System.out.println("booyah " + wavesDefeated + "/" + wavesToDefeat);
        if (this.activationKey.equals(activationKey)) {
            this.waitForNotification = false;
        }
    }

    /**
     * World will not be updated until waveDestroyedNotification is received
     */
    protected void waitForInactivation(String activationKey){
        this.waitForNotification = true;
        this.activationKey = activationKey;
    }

    public abstract void drawMapIcon(Graphics g, Position position, int size);
}
