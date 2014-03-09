package cz.terrmith.randomverse.core.world;

import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainerObserver;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.util.StringUtils;
import cz.terrmith.randomverse.sprite.enemy.SimpleEnemy;
import cz.terrmith.randomverse.world.events.WorldEvent;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Endless world that creates sprites just activate active screen based on time and number of updates
 */
public abstract class World implements SpriteContainerObserver {

    protected final static Random random = new Random();
    private boolean started;
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
    private WorldEvent worldEvent;

    /**
     *
     * @param spriteCollection
     * @param period time to wait between updates in seconds
     */
    public World(SpriteCollection spriteCollection, long period, long wavesToDefeat) {
        System.out.println("new world, waves to defeat: " + wavesToDefeat);
        this.spriteCollection = spriteCollection;
        this.period = TimeUnit.SECONDS.toMillis(period);
	    this.wavesToDefeat = wavesToDefeat;
    }

    /**
     * updates sprite collection if enough time was waited
     */
    public void update() {
        if (!started) {
            this.startTime = System.currentTimeMillis();
            this.started = true;
        }
        if (!paused && !waitForNotification && (period * updateCount) < (System.currentTimeMillis() - startTime)) {
//            System.out.println("beforeUpdate " + !paused + " | " + !waitForNotification +" | " + (period * updateCount) + " | " + (System.currentTimeMillis() - startTime));
            updateCount++;
            System.out.println("updateCount: " + updateCount);
            updateWorld();
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
    protected abstract void updateWorld();

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
        if (this.waitForNotification && this.activationKey.equals(activationKey)) {
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

    public final WorldEvent getWorldEvent() {
        return worldEvent;
    }

    public final void setWorldEvent(WorldEvent worldEvent) {
        this.worldEvent = worldEvent;
    }

    //todo scannerStrength in draw method is probably ugly
    public void drawScannerInfo(Graphics g, Position position, int scannerStrenght) {
        String scannerInfo = worldEvent.getScannerInfo(scannerStrenght);
        StringUtils.drawString(g, "SCANNER[" + scannerStrenght + "]: " + scannerInfo, (int) position.getX(), (int) position.getY(), 300);
    }

    /**
     *
     * @param formationSize
     * @return
     */
    protected List<Sprite> createSprites(int formationSize, SpriteFactory sf) {
        List<Sprite> enemies = new ArrayList<Sprite>(formationSize);
        for(int i = 0; i < formationSize; i++) {
            // position has no meaning, it will be repositioned upon FormationMovement creation
            Sprite sprite = sf.newSprite(0, 0);
            enemies.add(sprite);
            getSpriteCollection().put(SpriteLayer.NPC, sprite);
        }
        return enemies;
    }

    private SimpleEnemy.EnemyType randomEnemyType() {
        int enemyRandomizer= random.nextInt(3);
        final SimpleEnemy.EnemyType enemyType;
        switch (enemyRandomizer) {
            case 0:
                enemyType = SimpleEnemy.EnemyType.SINGLE;
                break;
            case 1:
                enemyType = SimpleEnemy.EnemyType.DOUBLE;
                break;
            default:
                enemyType = SimpleEnemy.EnemyType.KAMIKAZE;
                break;

        }
        return enemyType;
    }
}
