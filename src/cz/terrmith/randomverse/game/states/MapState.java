package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.dialog.*;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.inventory.GameMap;
import cz.terrmith.randomverse.inventory.ShipModificationScreen;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.*;

/**
 * State that represent main map on which player travels. Map does not reset and is persistent throughput one game
 */
public class MapState implements State {
    private final Command command;
    private final Randomverse stateMachine;

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
            int mapx = stateMachine.getMap().getX();
            int mapy = stateMachine.getMap().getY();

            stateMachine.setCurrentState(StateName.GAME.name());

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

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {
        stateMachine.clearScreen(g2, Color.BLACK);
	    stateMachine.getMap().drawMenu(g2);
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
