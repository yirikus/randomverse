package cz.terrmith.randomverse.core.sprite.properties;

/**
 * @author jiri.kus
 */
public class Loot {
    private int amount;
    private String type;
    private boolean powerup;

    public Loot(int amount, String type, boolean powerup) {
        this.amount = amount;
        this.type = type;
        this.powerup = powerup;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isPowerup() {
        return powerup;
    }

    public String getType() {
        return type;
    }
}
