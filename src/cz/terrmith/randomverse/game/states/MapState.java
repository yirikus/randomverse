package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.core.world.WorldEvent;
import cz.terrmith.randomverse.game.StateName;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * State that represent main map on which player travels. Map does not reset and is persistent throughput one game
 */
public class MapState implements State {
    private final Command command;
    private final Randomverse stateMachine;
    private WorldEvent currentEvent;

    public MapState(Randomverse stateMachine) {
        this.command = stateMachine.getCommand();
        this.stateMachine = stateMachine;
    }

    @Override
    public String getName() {
        return StateName.MAP.name();
    }

    @Override
    public void update() {
        if (currentEvent == null) {
            updateMap();
        } else {
            updateWorldEvent();
        }
    }

    private void updateWorldEvent() {
        currentEvent.updateEvent(command);
        if (WorldEvent.Progress.CONCLUDED == currentEvent.getProgress()) {
            stateMachine.setCurrentState(StateName.GAME.name());
            currentEvent = null;
        }
    }

    private void updateMap() {
        if (Command.State.PRESSED_RELEASED.equals(command.getUp())) {
            stateMachine.getMap().selectAbove();
            command.setUp(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
	        stateMachine.getMap().selectBelow();
            command.setDown(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getLeft())) {
	        stateMachine.getMap().selectLeft();
            command.setLeft(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getRight())) {
	        stateMachine.getMap().selectRight();
            command.setRight(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            handleSelection();
            command.setAction1(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction2())) {
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction3())) {
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction4())) {
        } else  if (Command.State.PRESSED.equals(command.getInventory())
                || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {
            stateMachine.setCurrentState(StateName.INVENTORY.name());
        } else if (Command.State.PRESSED.equals(command.getPrevious())
                || Command.State.RELEASED_PRESSED.equals(command.getPrevious())) {
            stateMachine.setCurrentState(StateName.MAIN_MENU.name());
        }
    }

    private void handleSelection() {
        int mapx = stateMachine.getMap().getX();
        int mapy = stateMachine.getMap().getY();

        World currentWorld = stateMachine.getMap().getCurrentWorld();
        this.currentEvent = currentWorld.getWorldEvent();
        if (currentEvent == null) {
            stateMachine.setCurrentState(StateName.GAME.name());
        }
    }

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {
        stateMachine.clearScreen(g2, Color.BLACK);
        stateMachine.getMap().drawMenu(g2);

        if (currentEvent != null) {
            currentEvent.drawEvent(g2);
        }
    }

    @Override
    public void activate(State prevState) {
        if (StateName.GAME.name().equals(prevState.getName())) {
	        stateMachine.getMap().markExplored();
        }
    }

    @Override
    public void deactivate(State nextState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
