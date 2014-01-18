package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.sprite.abilitiy.Loot;
import cz.terrmith.randomverse.core.sprite.abilitiy.Solid;
import cz.terrmith.randomverse.loot.LootType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Ship part
 * Can have extentions point - other ship parts can be connected to these extension points
 * Must be connected to core (ship tile [0,0]) or they are destroyed
 */
public class ShipPart extends SimpleSprite implements Destructible, Solid {

	private int totalHealth;
	private int currentHealth;
	private boolean connectedToCore;
	private Set<ExtensionPoint> extensions;

	private double speed = 0.0;
	private int price;


	public ShipPart(ShipPart sprite) {
		super(sprite);
		this.totalHealth = sprite.getTotalHealth();
		this.currentHealth = sprite.getTotalHealth();
		this.price = sprite.getPrice();
		this.extensions = new HashSet<ExtensionPoint>(sprite.getExtensions());
	}

	public ShipPart(int totalHealth, Map<String, ImageLocation> imageForStatus, Set<ExtensionPoint> extensions,
	                int price) {
		super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, imageForStatus);
		this.totalHealth = totalHealth;
		this.currentHealth = totalHealth;
		this.extensions = extensions;
		this.price = price;
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
		} else if (this.currentHealth < this.totalHealth) {
			this.setStatus(DefaultSpriteStatus.DAMAGED.name());
		} else {
            this.setStatus(DefaultSpriteStatus.DEFAULT.name());
        }
	}

	public Set<ExtensionPoint> getExtensions() {
		return extensions;
	}

	@Override
	public void updateSprite() {
		if(!connectedToCore){
			reduceHealth(getCurrentHealth());
		} else {
			super.updateSprite();
		}
	}

	public boolean isConnectedToCore() {
		return connectedToCore;
	}

	public void setConnectedToCore(boolean connectedToCore) {
		this.connectedToCore = connectedToCore;
	}

	@Override
	public void flipVertical() {
		super.flipVertical();
		flipExtensionPointsVertically();
	}

	private void flipExtensionPointsVertically() {
		boolean removedTop = extensions.remove(ExtensionPoint.TOP);
		boolean removedBottom = extensions.remove(ExtensionPoint.BOTTOM);
		if (removedBottom) {
			extensions.add(ExtensionPoint.TOP);
		}
		if (removedTop) {
			extensions.add(ExtensionPoint.BOTTOM);
		}
	}

	@Override
	public void flipHorizontal() {
		super.flipHorizontal();
		flipExtensionPointsHorizontally();
	}

	private void flipExtensionPointsHorizontally() {
		boolean removedLeft = extensions.remove(ExtensionPoint.LEFT);
		boolean removedRight = extensions.remove(ExtensionPoint.RIGHT);
		System.out.println("removedLeft: " + removedLeft + ", removedright: " + removedRight);

		if (removedLeft) {
			System.out.println("addRight");
			extensions.add(ExtensionPoint.RIGHT);
		}
		if (removedRight) {
			System.out.println("addLeft");
			extensions.add(ExtensionPoint.LEFT);
		}
	}

	@Override
	public Sprite copy() {
		return new ShipPart(this);
	}

	@Override
	public int getImpactDamage() {
		return 1;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Returns price that ignores health (price that was set on instantiation)
	 * @return  price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Price is computed from price and health
	 * @return  price
	 */
	public int getComputedPrice() {
		return (currentHealth/totalHealth) * price;
	}

    public void addPowerup(Loot loot) {
        if(LootType.HEALTH.name().equals(loot.getType())) {
            reduceHealth(-loot.getAmount());
        }
    }
}
