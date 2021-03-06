package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.attack.RandomAttackPattern;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.CollisionTester;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.game.states.*;
import cz.terrmith.randomverse.inventory.GameMap;
import cz.terrmith.randomverse.world.LevelEmpty;
import cz.terrmith.randomverse.world.events.EventCallbackResult;
import cz.terrmith.randomverse.world.events.WorldEventResult;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/21/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Randomverse extends GameEngine {

    private final Command command;
    private final ArtificialIntelligence ai;
    private final HashMap<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks;
    private int screenWidth;
    private int screenHeight;
    private final CollisionTester collisionTester;
    private final Player player;
    private final SpriteCollection spriteCollection;
	private final GameMap map;
    private World currentWorld;

    public Randomverse (Command cmd, int screenWidth, int screenHeight) {
        this.command = cmd;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Boundary screenBoundary = new Boundary(0, screenWidth, 0, screenHeight);
        Boundary extendedBoundary = new Boundary(-screenWidth, 2 * screenWidth, -screenHeight, 2 * screenHeight);
        this.spriteCollection = new SpriteCollection(screenBoundary, extendedBoundary);
        this.player = new Player(cmd, spriteCollection);
        this.collisionTester = new CollisionTester(this.spriteCollection);
        this.ai = new ArtificialIntelligence(new RandomAttackPattern(1000));
        this.callbacks = new HashMap<EventCallbackResult, NavigableTextCallback<WorldEventResult>>();

        addState(new GameState(this));
        addState(new LadderState(this));
        addState(new CutSceneState(this));
        addState(new MenuState(this));
        addState(new MapState(this));
        addState(new InventoryState(this));
        addState(new ShopState(this));

        setCurrentState(StateName.MAIN_MENU.name());

        //TODO replace whith surrounded
        this.currentWorld = new LevelEmpty(spriteCollection);
        //this.currentWorld = new LevelSurrounded(spriteCollection, ai);
        map = new GameMap(10, 16, Tile.DEFAULT_SIZE, new Position(100,100), this, ai, spriteCollection, this.callbacks);
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
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }

    @Override
    public void drawHUD(Graphics2D g2, ImageLoader iml) {
        getCurrentState().draw(g2, iml);
    }

    @Override
    public void waitForUnpause() {
        System.out.println("paused");
        if((command.isAnyKey() && !getDialog().isInput()) || command.getAction2() == Command.State.PRESSED || command.getAction2() == Command.State.RELEASED_PRESSED) {
            boolean closed = getDialog() == null || closeDialog();
            if (closed) {
                unpause();
                command.clear();
            }
        } else if (getDialog() != null && getDialog().isInput() && command.getKeyTyped() != null) {
            getDialog().setInputText(getDialog().getInputText() + command.getKeyTyped());
            command.setKeyTyped(null);
        }
    }

    @Override
    public void resetGame() {
        player.reset();
        //TODO replace whith surrounded
        this.currentWorld = new LevelEmpty(spriteCollection);
        //this.currentWorld = new LevelSurrounded(spriteCollection, ai);
        map.reset();
        spriteCollection.clear();
        command.clear();
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

    public ArtificialIntelligence getAi() {
        return ai;
    }

    public Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> getCallbacks() {
        return Collections.unmodifiableMap(callbacks);
    }

    public void addCallback(EventCallbackResult key, NavigableTextCallback callback) {
        callbacks.put(key, callback);
    }

    public World getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(World currentWorld) {
        this.currentWorld = currentWorld;
    }
}
