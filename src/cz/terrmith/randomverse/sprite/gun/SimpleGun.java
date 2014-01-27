package cz.terrmith.randomverse.sprite.gun;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.sprite.properties.CanAttack;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
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
	private final SpriteFactory spriteFactory;
	private int shootTimer = DEFAULT_SHOOT_TIMER;
	private int canShootIn = shootTimer;
    private SpriteCreator spriteCreator;

    /**
     * Convenience contructor with x = 0, y = 0, w,h = Tile.DEFAULT_SIZE
     * with default projectile
     *
     * TODO tenhle konstruktor je picovina
     */
    public SimpleGun(SpriteCollection spriteCollection,Damage.DamageType damageType, int totalHealth, int price) {
	    this(spriteCollection, DEFAULT_SHOOT_TIMER, new ImageLocation("sideGun", 0),
	         totalHealth, price, new ProjectileFactory(new Damage(1, damageType)));
    }

    /**
     * Convenience contructor with x = 0, y = 0, w,h = Tile.DEFAULT_SIZE
     * @param rateOfFire how long to wait activate next attack
     */
    public SimpleGun(SpriteCollection spriteCollection, int rateOfFire, ImageLocation imageLocation, int totalHealth, int price, SpriteFactory spriteFactory) {
        super(totalHealth, null, new HashSet<ExtensionPoint>(), price);
	    this.getExtensions().add(ExtensionPoint.RIGHT);
	    this.shootTimer = rateOfFire;
	    this.spriteFactory = spriteFactory;
	    System.out.println("gun created " + this.spriteFactory.getClass().getName());
        this.spriteCreator = new ProjectileCreator(spriteCollection, this.spriteFactory);
        this.getImageForStatus().put(DefaultSpriteStatus.DEFAULT.name(), imageLocation);
    }

	public SimpleGun(SimpleGun simpleGun) {
		super(simpleGun);
		this.shootTimer = simpleGun.getAttackTimer();
		this.spriteFactory = simpleGun.spriteFactory;
		this.spriteCreator = new ProjectileCreator((ProjectileCreator)simpleGun.getSpriteCreator());
		this.setImageForStatus(simpleGun.getImageForStatus());
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
		if(canShootIn <= 0 && DefaultSpriteStatus.DEFAULT.name().equals(getStatus())) {
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

	@Override
	public Sprite copy() {
		return new SimpleGun(this);
	}

	public SpriteCreator getSpriteCreator() {
		return spriteCreator;
	}

	public Damage getDamage() {
		return spriteFactory.getDamage();
	}
}
