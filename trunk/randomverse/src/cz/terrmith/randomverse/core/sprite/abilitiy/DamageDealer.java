package cz.terrmith.randomverse.core.sprite.abilitiy;

import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 8.10.13
 * Time: 0:08
 * To change this template use File | Settings | File Templates.
 */
public interface DamageDealer extends Sprite {

    /**
     * Damage on collision
     * @return
     */
    Damage getDamage();

	/**
	 * Deals damage to given destructible sprite
	 * @param targets target sprites
	 */
	void dealDamage(List<Destructible> targets);
}
