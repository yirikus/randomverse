package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.sprite.abilitiy.Loot;

/**
 * @author jiri.kus
 */
public class MoneyLoot implements Loot {

	private int amount;

	public MoneyLoot(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
}
