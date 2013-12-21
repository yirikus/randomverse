package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.dialog.Dialog;
import cz.terrmith.randomverse.core.dialog.DialogCallback;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.menu.Menu;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/21/13
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MenuState implements State {

    // reference to command
    private final Command command;
    private final Randomverse stateMachine;
    private Menu menu;

    public MenuState(Randomverse stateMachine) {
        this.command = stateMachine.getCommand();
        this.stateMachine = stateMachine;

        this.menu = new Menu(new Position(100,100));
        menu.addItem("start");
        menu.addItem("options");
        menu.addItem("exit");
    }

    @Override
    public String getName() {
        return StateName.MAIN_MENU.name();
    }

    @Override
    public void update() {
        if (Command.State.PRESSED_RELEASED.equals(command.getUp())) {
            menu.selectPrevious();
            command.setUp(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
            menu.selectNext();
            command.setDown(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            if (menu.getSelected().equals("start")) {
                stateMachine.setCurrentState(StateName.GAME.name());

            } else if (menu.getSelected().equals("options")) {

            } if (menu.getSelected().equals("exit")) {
                command.setTerminated(true);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {
        // clear the background
        stateMachine.clearScreen(g2, Color.darkGray);
        menu.drawMenu(g2);
    }

    @Override
    public void activate(State prevState) {
        command.clear();
    }

    @Override
    public void deactivate(State nextState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
