package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.List;

/**
 * Ship is a sprite that moves and uses it's parts to use abilities like an attack
 * can be player or npc controlled
 */
public class Ship extends MultiSprite implements CanAttack, Destructible {

	private int currentHealth;
    private int totalHealth;
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
        this.totalHealth = 10;
        this.currentHealth = this.totalHealth;
        this.ai = ai;

        setPosition(x, y);
    }

    @Override
    public void updateSprite() {
        //if AI is present use it
        if (ai != null) {
            ai.updateSprite(this);
        }

        super.updateSprite();
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
		return 10;
	}

    public ArtificialIntelligence getAi() {
        return ai;
    }

    @Override
	public int getCurrentHealth() {
		return currentHealth;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void reduceHealth(int amount) {
		this.currentHealth -= amount;
		if (this.currentHealth < 1) {
			setActive(false);
		}
	}
}
