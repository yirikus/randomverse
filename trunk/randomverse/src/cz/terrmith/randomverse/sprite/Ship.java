package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.sprite.abilitiy.SpriteCreator;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * SimpleSprite of a ship
 */
public class Ship extends MultiSprite implements CanAttack, Destructible {

	private int currentHealth;

    private static final Map<SpriteStatus, String> imageForStatus;
    static {
        Map<SpriteStatus, String> aMap = new HashMap<SpriteStatus, String>();
        aMap.put(SpriteStatus.DEFAULT, "midParts");
        imageForStatus = Collections.unmodifiableMap(aMap);
    }

    public Ship(int x, int y) {
        super(x, y);

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

        Map<SpriteStatus, ImageLocation> gun = new HashMap<SpriteStatus, ImageLocation>();
        gun.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(-1, 1, new SimpleGun(-1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, gun));

        setPosition(x, y);
    }

    @Override
    public void updateSprite() {
        super.updateSprite();
    }

    @Override
    public void attack(SpriteCreator spriteCreator) {
        for (Tile t : this.getTiles()){
	        System.out.println(t.getSprite());
	        if (t.getSprite() instanceof CanAttack) {
		        ((CanAttack) t.getSprite()).attack(spriteCreator);
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
