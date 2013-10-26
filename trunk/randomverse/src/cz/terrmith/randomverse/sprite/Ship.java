package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.List;

/**
 * Ship is a sprite that moves and uses it's parts to use abilities like an attack
 * can be player or npc controlled
 */
public class Ship extends MultiSprite implements CanAttack, Destructible{

    private ArtificialIntelligence ai;

    public Ship(int x, int y) {
        this(x, y, null, null);
    }


    /**
     * Copy constructor
     * NOTE: AI is not Copied
     * @param ship
     */
    public Ship(Ship ship) {
       this((int)ship.getXPosn(), (int)ship.getYPosn(), Tile.cloneTiles(ship.getTiles()), null);
    }

    /**
     * Constructor
     * @param x  intial x position
     * @param y initial y position
     * @param ai null if player controlled, otherwise ai has to be provided
     */
    public Ship(int x, int y, List<Tile> tiles, ArtificialIntelligence ai) {
        super(x, y, tiles);
        this.ai = ai;

        setPosition(x, y);
    }

	/**
	 * Sets sprite at given tile and all adjacent tiles
	 * as connected to core
	 *
	 * @param tile
	 */
	private void setConnectionToCore(Tile tile) {
		ShipPart part = (ShipPart) tile.getSprite();
		if (SpriteStatus.DEAD.equals(part.getStatus())) {
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

        //if AI is present use it
        if (ai != null) {
            ai.updateSprite(this);
        }

        super.updateSprite();

	    if(SpriteStatus.DEAD.equals(core.getSprite().getStatus())) {
		    setActive(false);
		    return;
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
}
