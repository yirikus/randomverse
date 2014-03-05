package cz.terrmith.randomverse.sprite.ship.part;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
import cz.terrmith.randomverse.core.sprite.properties.Loot;
import cz.terrmith.randomverse.core.sprite.properties.Solid;
import cz.terrmith.randomverse.core.sprite.properties.SpritePropertyHelper;
import cz.terrmith.randomverse.loot.LootType;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Ship part
 * Can have extentions point - other ship parts can be connected to these extension points
 * Must be connected to core (ship tile [0,0]) or they are destroyed
 */
public class ShipPart extends SimpleSprite implements Destructible, Solid {

    protected static final int DRAW_ATTR_LINE_HEIGHT = 20;
    private final int scannerStrength;
    private int totalHealth;
	private int currentHealth;
	private boolean connectedToCore;
	private Set<ExtensionPoint> extensions;

	private double speed = 0.0;
	private int price;


	public ShipPart(ShipPart that) {
		super(that);
		this.totalHealth = that.getTotalHealth();
		this.currentHealth = that.getTotalHealth();
		this.price = that.getPrice();
		this.extensions = new HashSet<ExtensionPoint>(that.getExtensions());
        this.scannerStrength = that.scannerStrength;
	}

	public ShipPart(int totalHealth, Map<String, ImageLocation> imageForStatus, Set<ExtensionPoint> extensions,
	                int price, int scannerStrength) {
		super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, imageForStatus);
		this.totalHealth = totalHealth;
		this.currentHealth = totalHealth;
		this.extensions = extensions;
		this.price = price;
        this.scannerStrength = scannerStrength;
	}

    public ShipPart(int totalHealth, Map<String, ImageLocation> imageForStatus, Set<ExtensionPoint> extensions,
                    int price) {
        this(totalHealth, imageForStatus, extensions, price, 0);
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
		} else if (this.currentHealth < this.totalHealth) {
			this.setStatus(DefaultSpriteStatus.DAMAGED.name());
            setActive(true);
		} else {
            this.setStatus(DefaultSpriteStatus.DEFAULT.name());
            setActive(true);
        }
	}

	public Set<ExtensionPoint> getExtensions() {
		return extensions;
	}

	@Override
	public void updateSprite() {
		if(!connectedToCore){
			reduceHealth(getCurrentHealth());
		}
		super.updateSprite();
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

		if (removedLeft) {
			extensions.add(ExtensionPoint.RIGHT);
		}
		if (removedRight) {
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

    @Override
    public void collide(Sprite s) {
        super.collide(s);

        SpritePropertyHelper.dealDamage(this,s);
        SpritePropertyHelper.dealImpactDamage(this, s);

    }

    public int getScannerStrength() {
        return scannerStrength;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @return next y to draw on
     */
    public int drawAttributes(Graphics g, int x, int y) {
        g.setColor(Color.GREEN);
        g.drawString("Price: $" + getPrice(), x, y);
        g.drawString("speed: " + getSpeed(), x, y + DRAW_ATTR_LINE_HEIGHT);
        g.drawString("health: " + getTotalHealth(), x, y + 2 * DRAW_ATTR_LINE_HEIGHT);
        g.drawString("scanner strength: " + getScannerStrength(), x, y + 3 * DRAW_ATTR_LINE_HEIGHT);
        return y + 4 * DRAW_ATTR_LINE_HEIGHT;
    }
}
