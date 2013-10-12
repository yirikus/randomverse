package cz.terrmith.randomverse.core.input;

/**
 * Set of commands to controll the game
 */
public class UserCommand {
    /**
     * User commands
     */
    private boolean previousPressed;
    private boolean inventoryShown;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean shoot;
    private boolean bomb;
    private boolean shield;
    private boolean special;

    public void setPreviousScreen(boolean previousPressed) {
        this.previousPressed = previousPressed;
    }

    public boolean isPreviousPressed() {
        return previousPressed;
    }

    public void setPreviousPressed(boolean previousPressed) {
        this.previousPressed = previousPressed;
    }

    public boolean isInventoryShown() {
        return inventoryShown;
    }

    public void setInventoryShown(boolean inventoryShown) {
        this.inventoryShown = inventoryShown;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }
}
