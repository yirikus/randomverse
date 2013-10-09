package cz.terrmith.randomverse.sprite.gun;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.SpriteCreator;

import java.util.Map;

/**
 * @author jiri.kus
 */
public class SimpleGun extends SimpleSprite implements CanAttack {
	private static final int DEFAULT_SHOOT_TIMER = 8;
	private int shootTimer = DEFAULT_SHOOT_TIMER;
	private int canShootIn = shootTimer;

	/**
	 * Creates sprite wtih initial position x, y and size w, h
	 *
	 * @param x x position
	 * @param y y position
	 * @param w width
	 * @param h height
	 * @param imageForStatus Map of sprite statuses and images
	 */
	public SimpleGun(int x, int y, int w, int h,
	                 Map<SpriteStatus, ImageLocation> imageForStatus) {
		super(x, y, w, h, imageForStatus);
	}

	@Override
	public void updateSprite() {
		super.updateSprite();
		if(canShootIn > 0) {
			canShootIn--;
		}
	}
	@Override
	public void attack(SpriteCreator spriteCreator) {
		if(canShootIn <= 0) {
			spriteCreator.createSprites(getXPosn() + getWidth() / 2,
			                            getYPosn(), 0, 1);
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
}
