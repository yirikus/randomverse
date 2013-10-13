package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Ship is a sprite that moves and uses it's parts to use abilities like an attack
 * can be player or npc controlled
 */
public class Ship extends MultiSprite implements CanAttack, Destructible {

	private int currentHealth;
    private int totalHealth;
    private ArtificialIntelligence ai;

    public Ship(int x, int y) {
        this(x,y,null);
    }

    /**
     * Constructor
     * @param x  intial x position
     * @param y initial y position
     * @param ai null if player controlled, otherwise ai has to be provided
     */
    public Ship(int x, int y, ArtificialIntelligence ai) {
        super(x, y);

        this.totalHealth = 10;
        this.currentHealth = this.totalHealth;
        this.ai = ai;

        Random random = new Random();
        Map<SpriteStatus, ImageLocation> cockpit = new HashMap<SpriteStatus, ImageLocation>();
        cockpit.put(SpriteStatus.DEFAULT, new ImageLocation("cockpit", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 0, new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, cockpit));

        Map<SpriteStatus, ImageLocation> engine = new HashMap<SpriteStatus, ImageLocation>();
        engine.put(SpriteStatus.DEFAULT, new ImageLocation("midParts",(int)(random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 1, new SimpleSprite(0, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, engine));

        Map<SpriteStatus, ImageLocation> thruster = new HashMap<SpriteStatus, ImageLocation>();
        thruster.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 2, new SimpleSprite(0, 2, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, thruster));

        setPosition(x, y);
    }

    @Override
    public void updateSprite() {
        //if AI is present use it
        if (ai != null) {
            ai.update(this);
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
