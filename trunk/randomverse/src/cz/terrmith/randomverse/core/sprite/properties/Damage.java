package cz.terrmith.randomverse.core.sprite.properties;

/**
 * Represents damage
 */
public class Damage {
    /**
     * To which entity can be damage dealt
     */
    public enum DamageType {NPC, PLAYER,BOTH}

    private int amount;
    private DamageType type;

    public Damage(int amount, DamageType type) {
        this.amount = amount;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public DamageType getType() {
        return type;
    }
}
