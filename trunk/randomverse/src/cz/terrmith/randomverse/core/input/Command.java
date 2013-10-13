package cz.terrmith.randomverse.core.input;

/**
 * Set of commands to controll the game
 */
public class Command {

    public enum State {PRESSED, RELEASED, PRESSED_RELEASED, RELEASED_PRESSED}
    /**
     * User commands
     */
    private State previous = State.RELEASED;
    private State inventory = State.RELEASED;
    private State up = State.RELEASED;
    private State down = State.RELEASED;
    private State left = State.RELEASED;
    private State right = State.RELEASED;
    private State shoot = State.RELEASED;
    private State bomb = State.RELEASED;
    private State shield = State.RELEASED;
    private State special = State.RELEASED;

    public Command() {
    }

    /**
     * Terminates application
     */
    private boolean terminated;
    private boolean pause;

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public State getPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = changeState(this.previous,previous);
    }

    public State getInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        this.inventory = changeState(this.inventory,inventory);
    }

    public State getUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = changeState(this.up,up);
    }

    public State getDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = changeState(this.down,down);
    }

    public State getLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = changeState(this.left,left);
    }

    public State getRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = changeState(this.right,right);
    }

    public State getShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        this.shoot = changeState(this.shoot,shoot);
    }

    public State getBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = changeState(this.bomb,bomb);
    }

    public State getShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = changeState(this.shield,shield);
    }

    public State getSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = changeState(this.special,special);
    }

    private Command.State changeState(Command.State currentState, boolean pressed){
        switch(currentState){
            case PRESSED:
                if (pressed) {
                    return Command.State.PRESSED;
                } else {
                    return State.PRESSED_RELEASED;
                }
            case PRESSED_RELEASED:
                if (pressed) {
                    return State.PRESSED;
                } else {
                    return State.RELEASED;
                }
            case RELEASED:
                if (pressed) {
                    return Command.State.RELEASED_PRESSED;
                } else {
                    return State.RELEASED;
                }
            case RELEASED_PRESSED:
                if (pressed) {
                    return Command.State.PRESSED;
                } else {
                    return State.PRESSED_RELEASED;
                }
        }

        throw new IllegalStateException("currentState: " + currentState);
    }
}
