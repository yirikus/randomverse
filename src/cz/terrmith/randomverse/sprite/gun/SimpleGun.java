package cz.terrmith.randomverse.sprite.gun;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.creator.ProjectileCreator;
import cz.terrmith.randomverse.core.sprite.creator.SpriteCreator;
import cz.terrmith.randomverse.sprite.factory.ProjectileFactory;

/**
 * @author jiri.kus
 */
public class SimpleGun extends SimpleSprite implements CanAttack {
	private static final int DEFAULT_SHOOT_TIMER = 8;
	private int shootTimer = DEFAULT_SHOOT_TIMER;
	private int canShootIn = shootTimer;
    private SpriteCreator spriteCreator;

    /**
	 * Creates sprite wtih initial position x, y and size w, h
	 *
	 * @param x x position
	 * @param y y position
	 * @param w width
	 * @param h height
	 */
	public SimpleGun(int x, int y, int w, int h, SpriteCollection spriteCollection, Damage.DamageType damageType) {
		super(x, y, w, h, null);
        this.spriteCreator = new ProjectileCreator(spriteCollection, new ProjectileFactory(new Damage(1,damageType)));
        this.getImageForStatus().put(SpriteStatus.DEFAULT, new ImageLocation("sideGun",0));
    }

    /**
     * Convenience contructor with x = 0, y = 0, w,h = Tile.DEFAULT_SIZE
     * with default projectile
     *
     */
    public SimpleGun(SpriteCollection spriteCollection,Damage.DamageType damageType) {
        super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null);
        this.spriteCreator = new ProjectileCreator(spriteCollection, new ProjectileFactory(new Damage(1,damageType)));
        this.getImageForStatus().put(SpriteStatus.DEFAULT, new ImageLocation("sideGun",0));
    }

    /**
     * Convenience contructor with x = 0, y = 0, w,h = Tile.DEFAULT_SIZE
     * @param rateOfFire how long to wait before next attack
     */
    public SimpleGun(SpriteCollection spriteCollection, int rateOfFire, Damage damage, ImageLocation imageLocation) {
        super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null);
        this.shootTimer = rateOfFire;
        this.spriteCreator = new ProjectileCreator(spriteCollection, new ProjectileFactory(damage));
        this.getImageForStatus().put(SpriteStatus.DEFAULT, imageLocation);
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
		if(canShootIn <= 0) {
			spriteCreator.createSprites(getXPosn() + getWidth() / 2,
			                            getYPosn() + getHeight() / 2, 0, 1, -12, -getHeight());
			canShootIn = DEFAULT_SHOOT_TIMER;
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
}
