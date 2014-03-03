package cz.terrmith.randomverse.sprite.ship;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Ability;
import cz.terrmith.randomverse.core.sprite.properties.CanAttack;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.sprite.properties.Lootable;
import cz.terrmith.randomverse.core.sprite.properties.ProvidesAbility;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;

import java.util.List;
import java.util.Map;

/**
 * Ship is a sprite that moves and uses it's parts to use abilities like an attack
 * can be player or npc controlled
 */
public class Ship extends MultiSprite implements CanAttack, Destructible, Lootable, ProvidesAbility{

    private ArtificialIntelligence ai;
	private LootSprite lootSprite;

	public Ship(int x, int y) {
        this(x, y, null);
    }


    /**
     * Copy constructor
     * NOTE: AI is not Copied
     * @param ship instance that should be copied
     */
    public Ship(Ship ship) {
        super(ship);

    }

    /**
     * Constructor
     * @param x  intial x position
     * @param y initial y position
     */
    public Ship(int x, int y, List<Tile> tiles) {
        super(x, y, tiles);
        this.ai = ai;

        setPosition(x, y);
    }

	/**
	 * Sets sprite at given tile and all adjacent tiles
	 * as connected to core
	 *
	 * @param tile tile to be connected
	 */
	private void setConnectionToCore(Tile tile) {
		ShipPart part = (ShipPart) tile.getSprite();
		if (DefaultSpriteStatus.DEAD.name().equals(part.getStatus())) {
			return;
		}
		part.setConnectedToCore(true);
		for (Tile t : getTiles()) {
			boolean top = part.getExtensions().contains(ExtensionPoint.TOP) && t.getTileX() == tile.getTileX() && t.getTileY() == tile.getTileY() - 1;
			boolean bottom = part.getExtensions().contains(ExtensionPoint.BOTTOM) &&  t.getTileX() == tile.getTileX() && t.getTileY() == tile.getTileY() + 1;
			boolean left = part.getExtensions().contains(ExtensionPoint.LEFT) && t.getTileX() == tile.getTileX() - 1 && t.getTileY() == tile.getTileY();
			boolean right = part.getExtensions().contains(ExtensionPoint.RIGHT) && t.getTileX() == tile.getTileX() + 1&& t.getTileY() == tile.getTileY();
			//top
			if ( !(((ShipPart)t.getSprite()).isConnectedToCore()) && (top || bottom || left || right)) {
				setConnectionToCore(t);
			}
		}
	}

    @Override
    public void updateSprite() {
	    Tile core = Tile.findTile(getTiles(),0,0);

	    //set all as disconnected
	    for (Tile t : getTiles()) {
		    if (t.getSprite() instanceof ShipPart) {
			    ((ShipPart)t.getSprite()).setConnectedToCore(false);
		    }
	    }

	    // find connected
	    setConnectionToCore(core);

        super.updateSprite();

	    if(DefaultSpriteStatus.DEAD.name().equals(core.getSprite().getStatus())) {
		    this.setStatus(DefaultSpriteStatus.DEAD.name());
            System.out.println("Aaaah YE KILLED ME");
		    setActive(false);
	    }
    }

    @Override
    public void attack() {
        for (Tile t : this.getTiles()){
	        if (t.getSprite() instanceof CanAttack) {
		        ((CanAttack) t.getSprite()).attack();
	        }
        }
    }

	@Override
	public void setAttackTimer(int value) {
		for (Tile t : this.getTiles()){
			if (t.getSprite() instanceof CanAttack) {
				((CanAttack) t.getSprite()).setAttackTimer(value);
			}
		}
	}

	@Override
	public int getAttackTimer() {
		for (Tile t : this.getTiles()){
			if (t.getSprite() instanceof CanAttack) {
				return ((CanAttack) t.getSprite()).getAttackTimer();
			}
		}
		return 0;
	}

	@Override
	public int getTotalHealth() {
		return ((Destructible)Tile.findTile(getTiles(),0,0).getSprite()).getTotalHealth();
	}

    public ArtificialIntelligence getAi() {
        return ai;
    }

	@Override
	public int getCurrentHealth() {
		return ((Destructible)Tile.findTile(getTiles(),0,0).getSprite()).getCurrentHealth();
	}

	@Override
	public void reduceHealth(int amount) {
		throw new IllegalStateException("Can not reduce health directly");
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
	public double getSpeed() {
		double totalSpeed = 0;
		for (Tile t : getTiles()) {
			if (!t.getSprite().getStatus().equals(DefaultSpriteStatus.DEAD.name())
			    && t.getSprite() instanceof ShipPart) {

				totalSpeed += ((ShipPart) t.getSprite()).getSpeed();
			}
		}
		if (totalSpeed <= 0.25) {
			totalSpeed = 0.25;
		}
		return totalSpeed;
	}

    @Override
    public void useAbility(String group, Sprite parent) {
        for (Tile t : getTiles()) {
            if (t.getSprite() instanceof ProvidesAbility) {
                ((ProvidesAbility) t.getSprite()).useAbility(group, this);
            }
        }
    }

    @Override
    public Map<String, Ability> getAbilities() {
        throw new UnsupportedOperationException("not implemented yet"); //To change body of implemented methods use File | Settings | File Templates.
    }
}
