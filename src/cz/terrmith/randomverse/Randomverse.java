package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.CollisionTester;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.game.states.GameState;
import cz.terrmith.randomverse.game.states.InventoryState;
import cz.terrmith.randomverse.game.states.MapState;
import cz.terrmith.randomverse.game.states.MenuState;
import cz.terrmith.randomverse.inventory.GameMap;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/21/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Randomverse extends GameEngine {

    private final Command command;
    private int screenWidth;
    private int screenHeight;
    private final CollisionTester collisionTester;
    private final Player player;
    private SpriteCollection spriteCollection;
	private GameMap map;

    public Randomverse (Command cmd, int screenWidth, int screenHeight) {
        this.command = cmd;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Boundary screenBoundary = new Boundary(0, screenWidth, 0, screenHeight);
        Boundary extendedBoundary = new Boundary(-screenWidth, 2 * screenWidth, -screenHeight, 2 * screenHeight);
        this.spriteCollection = new SpriteCollection(screenBoundary, extendedBoundary);
        this.player = new Player(cmd, spriteCollection);
        this.collisionTester = new CollisionTester(this.spriteCollection);
	    map = new GameMap(10, 16, Tile.DEFAULT_SIZE, new Position(100,100));

        addState(new GameState(this));
        addState(new MenuState(this));
        addState(new MapState(this));
        addState(new InventoryState(this));


        setCurrentState(StateName.MAIN_MENU.name());
    }

    @Override
    public void pause() {
        super.pause();

    }

    @Override
    public void unpause() {
        super.unpause();
    }

    @Override
    public void update() {
	    if (!isPaused()) {
            getCurrentState().update();
        }
    }

    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }

    @Override
    public void drawHUD(Graphics2D g2, ImageLoader iml) {
        getCurrentState().draw(g2, iml);
    }

    @Override
    public void waitForUnpause() {
        if(command.isAnyKey()) {
            boolean closed = getDialog() == null || closeDialog();
            if (closed) {
                unpause();
                command.clear();
            }
        }
    }

    /**
     *  Paints black over whole screen.
     * @param g graphics
     */
    public void clearScreen(Graphics g) {
        clearScreen(g, Color.BLACK);
    }

    /**
     * Paints black with given color over whole screen.
     * @param g graphics
     */
    public void clearScreen(Graphics g, Color color) {
        // clear the background
        g.setColor(color);
        g.fillRect(0, 0, screenWidth, screenHeight);
    }

    public Player getPlayer() {
        return player;
    }

    public CollisionTester getCollisionTester() {
        return collisionTester;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public Command getCommand() {
        return command;
    }

	public GameMap getMap() {
		return map;
	}
}
