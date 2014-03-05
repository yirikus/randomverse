package cz.terrmith.randomverse.sprite.enemy;

import cz.terrmith.randomverse.core.AnimationEngine;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.creator.ProjectileCreator;
import cz.terrmith.randomverse.core.sprite.creator.SpriteCreator;
import cz.terrmith.randomverse.core.sprite.properties.CanAttack;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.sprite.properties.Lootable;
import cz.terrmith.randomverse.core.sprite.properties.Solid;
import cz.terrmith.randomverse.core.sprite.properties.SpritePropertyHelper;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.factory.ProjectileFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 10.10.13
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
public class SimpleEnemy extends SimpleSprite implements CanAttack, Destructible, Lootable, Solid {
    public static final int IMPACT_DAMAGE = 1;
    public static final int SPEED = 2;
    private final int totalHealth = 4;
    private int currentHealth = totalHealth;
    private LootSprite lootSprite;
    private final SpriteCreator spriteCreator;
    private int shootTimer;
    private int canShootIn = 0;

    private int maxShots;
    private int currentShots;

    @Override
    public int getImpactDamage() {
        return IMPACT_DAMAGE;
    }

    public enum EnemyType {
        KAMIKAZE, SINGLE, DOUBLE
    }

    /**
     * Creates empty multisprites, tiles are expected to be added by calling provided methods
     */
    public SimpleEnemy(int x, int y, EnemyType enemyType, SpriteCollection spc) {
        super(x, y, Tile.DEFAULT_SIZE * 2, Tile.DEFAULT_SIZE * 2, null);
        setSpeed(2);
        //image
        Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
        imageForStatus.put(DefaultSpriteStatus.DEFAULT.name(), new ImageLocation("enemies", enemyType.ordinal()));
        imageForStatus.put(DefaultSpriteStatus.DAMAGED.name(), new ImageLocation("enemies_damaged", enemyType.ordinal()));
        setImageForStatus(imageForStatus);
        //CanAttack
        this.spriteCreator = new ProjectileCreator(spc, new ProjectileFactory(new Damage(1, Damage.DamageType.PLAYER)));
        this.spriteCreator.flipVertical();
        //5 seconds
        shootTimer = AnimationEngine.DEFAULT_FPS * 5;
        switch (enemyType) {
            case SINGLE:
                maxShots = 1;
                break;
            case DOUBLE:
                maxShots = 2;
                break;
            default:
                maxShots = 0;
        }
        //Lootable
        this.setLootSprite(new LootSprite(0,0,10,10,null, LootFactory.randomLoot(1)));
        //Destructible
    }

    @Override
    public void updateSprite() {
        super.updateSprite();
        if(canShootIn > 0) {
            canShootIn--;
        }
    }

    @Override
    public void attack() {
        if (maxShots <= 0) {
            //this one does not shoot
            return;
        }
        if(canShootIn <= 0 && !DefaultSpriteStatus.DEAD.name().equals(getStatus())) {
            canShootIn = shootTimer;
            currentShots++;
        }

        if (currentShots > 0) {
            currentShots--;
            spriteCreator.createSprites(getXPosn() + getWidth() / 2,
                                        getYPosn() + getHeight() / 2,
                                        0, 1, -12, -getHeight());
        }
    }

    @Override
    public void setAttackTimer(int value) {
        this.shootTimer = value;
    }

    @Override
    public int getAttackTimer() {
        return shootTimer;
    }

    @Override
    public void flipHorizontal() {
        super.flipHorizontal();
        spriteCreator.flipHorizontal();
    }

    @Override
    public void flipVertical() {
        super.flipVertical();
        spriteCreator.flipVertical();
    }

    @Override
    public int getTotalHealth() {
        return this.totalHealth;
    }

    @Override
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public void reduceHealth(int amount) {
        this.currentHealth -= amount;
        if (this.currentHealth < 1) {
            this.setStatus(DefaultSpriteStatus.DEAD.name());
            setActive(false);
        } else if (this.currentHealth <= (this.totalHealth / 2)) {
            this.setStatus(DefaultSpriteStatus.DAMAGED.name());
            setActive(true);
        } else {
            this.setStatus(DefaultSpriteStatus.DEFAULT.name());
            setActive(true);
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
