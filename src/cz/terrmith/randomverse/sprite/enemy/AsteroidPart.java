package cz.terrmith.randomverse.sprite.enemy;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.HashMap;
import java.util.Map;

/**
 * Part of an asteroid
 */
public class AsteroidPart extends SimpleSprite implements Destructible{

    private static final int MAX_HEALTH = 1;
    private int currentHealth = MAX_HEALTH;


    public AsteroidPart(double x, double y) {
        super(x, y, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null);
        Map<SpriteStatus, ImageLocation> imageForStatus = new HashMap<SpriteStatus, ImageLocation>();
        imageForStatus.put(SpriteStatus.DEFAULT, new ImageLocation("asteroid",0));
        setImageForStatus(imageForStatus);
    }

    @Override
    public int getTotalHealth() {
        return 1;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public void reduceHealth(int amount) {
        currentHealth -= amount;
        if (this.currentHealth < 1) {
            this.setStatus(SpriteStatus.DEAD);
        } else {
            this.setStatus(SpriteStatus.DEFAULT);
        }
    }
}
