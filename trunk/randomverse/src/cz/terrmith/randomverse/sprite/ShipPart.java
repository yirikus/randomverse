package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 7.10.13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class ShipPart extends SimpleSprite implements Destructible{

	private int totalHealth;
	private int currentHealth;

	public ShipPart(ShipPart sprite) {
		super(sprite);
		this.totalHealth = sprite.getTotalHealth();
		this.currentHealth = sprite.getTotalHealth();
	}

	public ShipPart(int totalHealth, Map<SpriteStatus, ImageLocation> imageForStatus) {
		super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, imageForStatus);
		this.totalHealth = totalHealth;
		this.currentHealth = totalHealth;
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
			this.setStatus(SpriteStatus.DEAD);
		}
	}
}
