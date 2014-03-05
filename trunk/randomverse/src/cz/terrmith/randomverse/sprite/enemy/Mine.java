package cz.terrmith.randomverse.sprite.enemy;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.sprite.properties.Lootable;
import cz.terrmith.randomverse.core.sprite.properties.Solid;
import cz.terrmith.randomverse.core.sprite.properties.SpritePropertyHelper;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.projectile.Explosion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 10.10.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class Mine extends SimpleSprite implements Destructible, Lootable, Solid {
    public static final int IMPACT_DAMAGE = 1;
    public static final int SPEED = 2;
    private static final int TOTAL_HEALTH = 2;
    public static final int SIZE = Tile.DEFAULT_SIZE;
    private final SpriteCollection spc;
    private int currentHealth = TOTAL_HEALTH;
    private LootSprite lootSprite;
    private Damage explosionDamage;
    private final int explosionSize;

    @Override
    public int getImpactDamage() {
        return IMPACT_DAMAGE;
    }

    public enum EnemyType { SMALL_EXPLODING, IMPACT, BIG_EXPLODING }

    /**
     * Creates empty multisprites, tiles are expected to be added by calling provided methods
     */
    public Mine(int x, int y, EnemyType enemyType, SpriteCollection spc) {
        super(x, y, SIZE, SIZE, null);
        setSpeed(SPEED);
        //image
        Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
        imageForStatus.put(DefaultSpriteStatus.DEFAULT.name(), new ImageLocation("mine", enemyType.ordinal()));
        setImageForStatus(imageForStatus);

        switch (enemyType) {
            case IMPACT:
                explosionDamage = null;
                explosionSize = 0;
                setSpeed(SPEED + 0.5);
                break;
            case SMALL_EXPLODING:
                explosionDamage = new Damage(2, Damage.DamageType.BOTH);
                explosionSize = Tile.DEFAULT_SIZE * 2;
                break;
            case BIG_EXPLODING:
                explosionDamage = new Damage(2, Damage.DamageType.BOTH);
                explosionSize = Tile.DEFAULT_SIZE * 3;
                break;
            default:
                throw new IllegalArgumentException("Unknown or null enum: " + enemyType);
        }

        //Lootable
        this.setLootSprite(new LootSprite(0,0,10,10,null, LootFactory.randomLoot(1)));

        //explosion creation
        this.spc = spc;
    }

    @Override
    public void updateSprite() {
        super.updateSprite();
    }

    @Override
    public int getTotalHealth() {
        return this.TOTAL_HEALTH;
    }

    @Override
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public void reduceHealth(int amount) {
        this.currentHealth -= amount;
        if (this.currentHealth < 1) {
            if (explosionDamage != null && explosionSize > 0) {
            System.out.println("creating projectile: " + getXPosn() + ", " + getYPosn());
            spc.put(SpriteLayer.PROJECTILE, new Explosion(getXPosn() + SIZE / 2,
                                                          getYPosn() + SIZE / 2,
                                                          this.explosionDamage,
                                                          this.explosionSize));
            }
            this.setActive(false);
            setStatus(DefaultSpriteStatus.DEAD.name());
        }
    }

    @Override
    public LootSprite getLootSprite() {
        lootSprite.setPosition(getXPosn() + getWidth() / 2, getYPosn() + getHeight() / 2);
        return lootSprite;
    }

    @Override
    public void setLootSprite(LootSprite lootSprite) {
        this.lootSprite = lootSprite;
    }

    @Override
    public void collide(Sprite s) {
        super.collide(s);

        SpritePropertyHelper.dealDamage(this,s);
        SpritePropertyHelper.dealImpactDamage(this, s);
    }
}
