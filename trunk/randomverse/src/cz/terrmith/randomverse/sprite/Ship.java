package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.sprite.abilitiy.SpriteCreator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * SimpleSprite of a ship
 */
public class Ship extends MultiSprite implements CanAttack, Destructible {

    private static final int SHOOT_TIMER = 8;
    private int canShootIn = SHOOT_TIMER;
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
        addTile(0, 0, new SimpleSprite(0, 0, 8, 8, cockpit));

        Map<SpriteStatus, ImageLocation> engine = new HashMap<SpriteStatus, ImageLocation>();
        engine.put(SpriteStatus.DEFAULT, new ImageLocation("midParts",(int)(random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 1, new SimpleSprite(0, 1, 8, 8, engine));

        Map<SpriteStatus, ImageLocation> thruster = new HashMap<SpriteStatus, ImageLocation>();
        thruster.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 2, new SimpleSprite(0, 2, 8, 8, thruster));

        Map<SpriteStatus, ImageLocation> gun = new HashMap<SpriteStatus, ImageLocation>();
        gun.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(-1, 1, new SimpleSprite(-1, 1, 8, 8, gun));

        setPosition(x, y);
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
        if(canShootIn == 0) {
            System.out.println("attacking: " + spriteCreator.toString());
            spriteCreator.createSprites(this);
            canShootIn = SHOOT_TIMER;
        }
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
