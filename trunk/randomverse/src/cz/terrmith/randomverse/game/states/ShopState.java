package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.geometry.Plane;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.inventory.ShipModificationScreen;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * TODO identical to inventory state, just with different name and return state
 * User: Dell
 * Date: 12/21/13
 * Time: 4:17 PM
 *
 */
public class ShopState implements State {

    // reference to command
    private final Command command;
    private final Randomverse stateMachine;
    private ShipModificationScreen inventory = null;

    public ShopState(Randomverse stateMachine) {
        this.command = stateMachine.getCommand();
        this.stateMachine = stateMachine;
    }


    @Override
    public String getName() {
        return StateName.SHOP.name();
    }

    @Override
    public void update() {
        updateInventory();
        if (Command.State.PRESSED.equals(command.getInventory())
                || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {

            stateMachine.setCurrentState(StateName.MAP.name());
            command.setInventory(false);
        }
    }

    private void updateInventory() {
        if (Command.State.PRESSED_RELEASED.equals(command.getUp())) {
            inventory.moveUp();
            command.setUp(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
            inventory.moveDown();
            command.setDown(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getLeft())) {
            inventory.moveLeft();
            command.setLeft(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getRight())) {
            inventory.moveRight();
            command.setRight(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            inventory.select();
            command.setAction1(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction2())) {
            inventory.clear();
            command.setAction2(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction3())) {
            inventory.flip(Plane.HORIZONTAL);
            command.setAction3(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction4())) {
            inventory.flip(Plane.VERTICAL);
            command.setAction4(false);
        }
    }

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {
        // clear the background
        stateMachine.clearScreen(g2, Color.darkGray);
        inventory.drawScreen(g2, iml);
    }

    @Override
    public void activate(State prevState) {
        inventory = new ShipModificationScreen(stateMachine.getPlayer(), stateMachine.getSpriteCollection());
        command.setInventory(false);
    }

    @Override
    public void deactivate(State nextState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
