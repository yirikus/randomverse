package cz.terrmith.randomverse.core.input;

/**
 * Set of commands to controll the game
 *
 * TODO singleton
 */
public class Command {

    private String keyTyped;

    public boolean isAnyKey() {
		return anyKey;
	}

	public void setAnyKey(boolean anyKey) {
		this.anyKey = anyKey;
	}

    public void setKeyTyped(String keyTyped) {
        this.keyTyped = keyTyped;
    }

    public String getKeyTyped() {
        return keyTyped;
    }

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
    private State action1 = State.RELEASED;
    private State action2 = State.RELEASED;
    private State action3 = State.RELEASED;
    private State action4 = State.RELEASED;
    private boolean screenshot;
	private boolean anyKey;

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

    public State getAction1() {
        return action1;
    }

    public void setAction1(boolean action1) {
        this.action1 = changeState(this.action1, action1);
    }

    public State getAction2() {
        return action2;
    }

    public void setAction2(boolean action2) {
        this.action2 = changeState(this.action2, action2);
	    System.out.println("action2:" + this.action2.name());
    }

    public State getAction3() {
        return action3;
    }

    public void setAction3(boolean action3) {
        this.action3 = changeState(this.action3, action3);
    }

    public State getAction4() {
        return action4;
    }

    public void setAction4(boolean action4) {
        this.action4 = changeState(this.action4, action4);
    }

    public boolean isScreenshot() {
        return screenshot;
    }

    public void setScreenshot(boolean screenshot) {
        this.screenshot = screenshot;
    }

    public void clear() {
        this.previous = State.RELEASED;
        this.inventory = State.RELEASED;
        this.up = State.RELEASED;
        this.down = State.RELEASED;
        this.left = State.RELEASED;
        this.right = State.RELEASED;
        this.action1 = State.RELEASED;
        this.action2 = State.RELEASED;
        this.action3 = State.RELEASED;
        this.action4 = State.RELEASED;
        this.anyKey = false;
    }

    private Command.State changeState(Command.State currentState, boolean pressed){

        switch(currentState){
            case PRESSED:
                if (pressed) {
//                    System.out.println("change state " + currentState + " -> PRESSED (" + pressed + ")");
                    return Command.State.PRESSED;
                } else {
//                    System.out.println("change state " + currentState + " -> PRESSED_RELEASED (" + pressed + ")");
                    return State.PRESSED_RELEASED;
                }
            case PRESSED_RELEASED:
                if (pressed) {
//                    System.out.println("change state " + currentState + " -> PRESSED (" + pressed + ")");
                    return State.PRESSED;
                } else {
//                    System.out.println("change state " + currentState + " -> RELEASED (" + pressed + ")");
                    return State.RELEASED;
                }
            case RELEASED:
                if (pressed) {
//                    System.out.println("change state " + currentState + " -> RELEASED_PRESSED (" + pressed + ")");
                    return Command.State.RELEASED_PRESSED;
                } else {
//                    System.out.println("change state " + currentState + " -> RELEASED (" + pressed + ")");
                    return State.RELEASED;
                }
            case RELEASED_PRESSED:
                if (pressed) {
//                    System.out.println("change state " + currentState + " -> PRESSED (" + pressed + ")");
                    return Command.State.PRESSED;
                } else {
//                    System.out.println("change state " + currentState + " -> PRESSED_RELEASED (" + pressed + ")");
                    return State.PRESSED_RELEASED;
                }
        }

        throw new IllegalStateException("currentState: " + currentState);
    }
}
