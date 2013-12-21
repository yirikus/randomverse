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
    private GameMap map;

    public MapState(Randomverse stateMachine) {
        this.command = stateMachine.getCommand();
        this.stateMachine = stateMachine;
        map = new GameMap(10, 16, Tile.DEFAULT_SIZE, new Position(100,100));
    }

    @Override
    public String getName() {
        return StateName.MAP.name();
    }

    @Override
    public void update() {
        if (Command.State.PRESSED_RELEASED.equals(command.getUp())) {
            map.selectAbove();
            command.setUp(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
            map.selectBelow();
            command.setDown(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getLeft())) {
            map.selectLeft();
            command.setLeft(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getRight())) {
            map.selectRight();
            command.setRight(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            int mapx = map.getX();
            int mapy = map.getY();

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
        map.drawMenu(g2);
    }

    @Override
    public void activate(State prevState) {
        if (StateName.GAME.name().equals(prevState.getName())) {
            map.markExplored();
        }
    }

    @Override
    public void deactivate(State nextState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
