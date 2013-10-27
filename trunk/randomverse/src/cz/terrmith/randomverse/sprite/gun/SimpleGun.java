package cz.terrmith.randomverse.sprite.gun;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.sprite.creator.ProjectileCreator;
import cz.terrmith.randomverse.core.sprite.creator.SpriteCreator;
import cz.terrmith.randomverse.sprite.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ShipPart;
import cz.terrmith.randomverse.sprite.factory.ProjectileFactory;

import java.util.HashSet;

/**
 * @author jiri.kus
 */
public class SimpleGun extends ShipPart implements CanAttack, Destructible {
	private static final int DEFAULT_SHOOT_TIMER = 8;
	private int shootTimer = DEFAULT_SHOOT_TIMER;
	private int canShootIn = shootTimer;
    private SpriteCreator spriteCreator;

	/**
	 * Creates sprite wtih initial position x, y and size w, h
	 *
	 * @param totalHealth total health
	 */
	public SimpleGun(int totalHealth, SpriteCollection spriteCollection, Damage.DamageType damageType) {
		super(totalHealth, null, new HashSet<ExtensionPoint>());
		this.getExtensions().add(ExtensionPoint.RIGHT);
        this.spriteCreator = new ProjectileCreator(spriteCollection, new ProjectileFactory(new Damage(1,damageType)));
        this.getImageForStatus().put(SpriteStatus.DEFAULT, new ImageLocation("sideGun",0));
    }

    /**
     * Convenience contructor with x = 0, y = 0, w,h = Tile.DEFAULT_SIZE
     * with default projectile
     *
     */
    public SimpleGun(SpriteCollection spriteCollection,Damage.DamageType damageType, int totalHealth) {
        super(totalHealth, null, new HashSet<ExtensionPoint>());
	    this.getExtensions().add(ExtensionPoint.RIGHT);
	    this.spriteCreator = new ProjectileCreator(spriteCollection, new ProjectileFactory(new Damage(1,damageType)));
        this.getImageForStatus().put(SpriteStatus.DEFAULT, new ImageLocation("sideGun",0));
    }

    /**
     * Convenience contructor with x = 0, y = 0, w,h = Tile.DEFAULT_SIZE
     * @param rateOfFire how long to wait before next attack
     */
    public SimpleGun(SpriteCollection spriteCollection, int rateOfFire, Damage damage, ImageLocation imageLocation, int totalHealth) {
        super(totalHealth, null, new HashSet<ExtensionPoint>());
	    this.getExtensions().add(ExtensionPoint.RIGHT);
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
		if(canShootIn <= 0 && !SpriteStatus.DEAD.equals(getStatus())) {
			spriteCreator.createSprites(getXPosn() + getWidth() / 2,
			                            getYPosn() + getHeight() / 2, 0, 1, -12, -getHeight());
			canShootIn = shootTimer;
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
