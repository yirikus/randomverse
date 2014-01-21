package cz.terrmith.randomverse.loot;

import cz.terrmith.randomverse.core.sprite.properties.Loot;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/11/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class LootFactory {


    public static Loot randomLoot(int amount) {
        Random random = new Random();
        if (random.nextInt()%5 == 0) {
           return new Loot(amount, LootType.HEALTH.name(), true);
        } else {
            return new Loot(amount, LootType.MONEY.name(), false);
        }
    }
}
