package cz.terrmith.randomverse.core;

/**
 * System commands. Adds set of commands to
 * controll the application start
 */
public class SystemCommand extends UserCommand {

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
}
