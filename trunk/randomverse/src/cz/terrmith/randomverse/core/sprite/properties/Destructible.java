package cz.terrmith.randomverse.core.sprite.properties;

import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * @author jiri.kus
 */
public interface Destructible extends Sprite {
	int getTotalHealth();
	int getCurrentHealth();


	/**
	 * Reduces health by given amount
	 * @param amount
	 */
	void reduceHealth(int amount);
}
