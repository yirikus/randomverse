package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.sprite.abilitiy.Solid;

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

	public ShipPart(ShipPart sprite) {
		super(sprite);
		this.totalHealth = sprite.getTotalHealth();
		this.currentHealth = sprite.getTotalHealth();
		this.extensions = new HashSet<ExtensionPoint>(sprite.getExtensions());
	}

	public ShipPart(int totalHealth, Map<SpriteStatus, ImageLocation> imageForStatus, Set<ExtensionPoint> extensions) {
		super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, imageForStatus);
		this.totalHealth = totalHealth;
		this.currentHealth = totalHealth;
		this.extensions = extensions;
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
}
