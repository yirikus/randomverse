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
        System.out.println("pause");
        this.pause = pause;
    }

    public State getPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        System.out.println("prev");
        this.previous = changeState(this.previous,previous);
    }

    public State getInventory() {
        return inventory;
    }

    public void setInventory(boolean inventory) {
        System.out.println("inv");
        this.inventory = changeState(this.inventory,inventory);
    }

    public State getUp() {
        return up;
    }

    public void setUp(boolean up) {
        System.out.println("up");
        this.up = changeState(this.up,up);
    }

    public State getDown() {
        return down;
    }

    public void setDown(boolean down) {
        System.out.println("down");
        this.down = changeState(this.down,down);
    }

    public State getLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        System.out.println("left");
        this.left = changeState(this.left,left);
    }

    public State getRight() {
        return right;
    }

    public void setRight(boolean right) {
        System.out.println("right");
        this.right = changeState(this.right,right);
    }

    public State getShoot() {
        return shoot;
    }

    public void setShoot(boolean shoot) {
        System.out.println("shoot");
        this.shoot = changeState(this.shoot,shoot);
    }

    public State getBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        System.out.println("bomb");
        this.bomb = changeState(this.bomb,bomb);
    }

    public State getShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        System.out.println("shield");
        this.shield = changeState(this.shield,shield);
    }

    public State getSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = changeState(this.special,special);
    }

    public void clear() {
        this.previous = State.RELEASED;
        this.inventory = State.RELEASED;
        this.up = State.RELEASED;
        this.down = State.RELEASED;
        this.left = State.RELEASED;
        this.right = State.RELEASED;
        this.shoot = State.RELEASED;
        this.bomb = State.RELEASED;
        this.shield = State.RELEASED;
        this.special = State.RELEASED;
    }

    private Command.State changeState(Command.State currentState, boolean pressed){

        switch(currentState){
            case PRESSED:
                if (pressed) {
                    System.out.println("change state " + currentState + " -> PRESSED (" + pressed + ")");
                    return Command.State.PRESSED;
                } else {
                    System.out.println("change state " + currentState + " -> PRESSED_RELEASED (" + pressed + ")");
                    return State.PRESSED_RELEASED;
                }
            case PRESSED_RELEASED:
                if (pressed) {
                    System.out.println("change state " + currentState + " -> PRESSED (" + pressed + ")");
                    return State.PRESSED;
                } else {
                    System.out.println("change state " + currentState + " -> RELEASED (" + pressed + ")");
                    return State.RELEASED;
                }
            case RELEASED:
                if (pressed) {
                    System.out.println("change state " + currentState + " -> RELEASED_PRESSED (" + pressed + ")");
                    return Command.State.RELEASED_PRESSED;
                } else {
                    System.out.println("change state " + currentState + " -> RELEASED (" + pressed + ")");
                    return State.RELEASED;
                }
            case RELEASED_PRESSED:
                if (pressed) {
                    System.out.println("change state " + currentState + " -> PRESSED (" + pressed + ")");
                    return Command.State.PRESSED;
                } else {
                    System.out.println("change state " + currentState + " -> PRESSED_RELEASED (" + pressed + ")");
                    return State.PRESSED_RELEASED;
                }
        }

        throw new IllegalStateException("currentState: " + currentState);
    }
}
