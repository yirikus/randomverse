package cz.terrmith.randomverse.core.sprite.properties;

import cz.terrmith.randomverse.core.sprite.Sprite;

/**
 * Static class that provides methods with default property behavior
 */
public class SpritePropertyHelper {
    /**
     * This class is static
     */
    private SpritePropertyHelper() {
        //static
    }

    /**
     * Deals damage to given sprite if possible
     * @param destructible sprite that will obtain damage
     * @param damageDealer sprite that deals damage
     */
    public static void dealDamage(Destructible destructible, Sprite damageDealer) {
        if (damageDealer instanceof DamageDealer) {
            //TODO handle damage types ????
            Damage damage = (((DamageDealer) damageDealer).getDamage());
            if (damage != null) {
                destructible.reduceHealth(damage.getAmount());
            }
        }
    }

    /**
     * Deals impact damage to given sprite if possible
     * @param destructible sprite that will obtain damage
     * @param solid sprite that deals damage
     */
    public static void dealImpactDamage(Destructible destructible, Sprite solid) {
        if (solid instanceof Solid) {
            destructible.reduceHealth(((Solid)solid).getImpactDamage());
        }
    }


}
