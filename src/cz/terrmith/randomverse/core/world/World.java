package cz.terrmith.randomverse.core.world;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;

import java.util.concurrent.TimeUnit;

/**
 * Endless world that creates sprites just before active screen based on time and number of updates
 */
public abstract class World {

    private long startTime;
    private long period;
    private long updateCount = 0;
    private SpriteCollection spriteCollection;
    private boolean paused;
    private long pausedTime;

    /**
     *
     * @param spriteCollection
     * @param period time to wait between updates in seconds
     */
    public World(SpriteCollection spriteCollection, long period) {
        this.spriteCollection = spriteCollection;
        this.startTime = System.currentTimeMillis();
        this.period = TimeUnit.SECONDS.toMillis(period);
    }

    /**
     * updates sprite collection if enough time was waited
     */
    public void update() {
        if (!paused && (period * updateCount) < (System.currentTimeMillis() - startTime)) {
            updateCount++;
            createSprites();
        }
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
}
