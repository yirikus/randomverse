package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.geometry.Plane;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.menu.Menu;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CollisionTester;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.inventory.ShipModificationScreen;
import cz.terrmith.randomverse.inventory.ShipPartFactory;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Contains global constants
 */
public class Randomverse implements GameEngine {

    public static final String WINDOW_NAME = "Randomverse";
    public static final int STEP = 6;

    private final Command command;
	private final CollisionTester collisionTester;
	private ShipModificationScreen inventory = null;
    private int screenWidth;
    private int screenHeight;
    private World world;
    private SpriteCollection spriteCollection;
    private Ship player;
    private enum GameMode {MAIN_MENU, GAME, INVENTORY}
    private GameMode gameMode = GameMode.MAIN_MENU;
    private Menu menu;

    public Randomverse(Command cmd, int screenWidth, int screenHeight) {
        this.command = cmd;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Boundary screenBoundary = new Boundary(0, screenWidth, 0, screenHeight);
        Boundary extendedBoundary = new Boundary(-screenWidth, 2 * screenWidth, -screenHeight, 2 * screenHeight);
        this.spriteCollection = new SpriteCollection(screenBoundary, extendedBoundary);
	    this.collisionTester = new CollisionTester(this.spriteCollection);
        this.menu = new Menu(new Position(100,100));
        menu.addItem("start");
        menu.addItem("options");
        menu.addItem("exit");
    }

    private void createPlayer() {
        this.player = new Ship(300, 300);
	    ShipPartFactory factory = new ShipPartFactory(getSpriteCollection(), Damage.DamageType.NPC);

	    player.addTile(-1, 1, factory.create(ShipPartFactory.Item.GUN_1.ordinal()));
	    SimpleSprite gun2 = factory.create(ShipPartFactory.Item.GUN_1.ordinal());
	    gun2.flipHorizontal();
	    player.addTile(1, 1, gun2);

	    player.addTile(0, 0, factory.create(ShipPartFactory.Item.COCKPIT_1.ordinal()));
	    player.addTile(0, 1, factory.create(ShipPartFactory.Item.MID_1.ordinal()));
	    player.addTile(0, 2, factory.create(ShipPartFactory.Item.ENGINE_1.ordinal()));

    }

    @Override
    public void update() {
        switch(gameMode) {
            case MAIN_MENU:
                updateMenu();
                break;
            case INVENTORY:
                updateInventory();
                if (Command.State.PRESSED.equals(command.getInventory())
                    || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {

                    gameMode = GameMode.GAME;
                    command.setInventory(false);
                    world.setPaused(false);
                }
            break;
            case GAME:
                if (Command.State.PRESSED.equals(command.getPrevious())
                  || Command.State.RELEASED_PRESSED.equals(command.getPrevious())) {
                    spriteCollection.clear();
                    gameMode = GameMode.MAIN_MENU;
                    command.clear();
                } else  if (Command.State.PRESSED.equals(command.getInventory())
                        || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {
                    gameMode = GameMode.INVENTORY;
                    inventory = new ShipModificationScreen(player, spriteCollection);
                    command.setInventory(false);
                    world.setPaused(true);
                } else {
                    updateProjectiles();
                    updateNpcs();
                    updatePlayer();
                    updateWorld();
                }
                break;
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

    private void updateWorld() {
        world.update();
    }

    private void updateNpcs() {
        Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.NPC).iterator();
        Boundary b = getSpriteCollection().getBoundary(SpriteLayer.NPC);
        while (iterator.hasNext()) {
            Sprite s = iterator.next();

            // set sprites that are not within as inactive
            Position spritePos = new Position(s.getXPosn(), s.getYPosn());
            if (!b.withinBoundary(spritePos)) {
                s.setActive(false);
            }

            // delete inactive sprites
            if (s.isActive()) {
                s.updateSprite();
            } else {
                iterator.remove();
            }
        }
    }


    private void updateProjectiles() {
        Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.PROJECTILE).iterator();
        Boundary b = getSpriteCollection().getBoundary(SpriteLayer.PROJECTILE);
        while (iterator.hasNext()) {
            Sprite s = iterator.next();

            // set sprites that are not within as inactive
            Position spritePos = new Position(s.getXPosn(), s.getYPosn());
            if (!b.withinBoundary(spritePos)) {
                s.setActive(false);
            }

            if (s.isActive()) {
                s.updateSprite();
                if (s instanceof DamageDealer) {
                    DamageDealer d = (DamageDealer) s;
                    dealDamage(d);
                }
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * Checks collisions for given damage dealer and deals damage
     *
     * @param dmgDealer damage dealer
     */
    private void dealDamage(DamageDealer dmgDealer) {
        List<Destructible> collidingSprites;
        // non player collision
        if (Damage.DamageType.NPC.equals(dmgDealer.getDamage().getType())
                || Damage.DamageType.BOTH.equals(dmgDealer.getDamage().getType())) {

            collidingSprites = collisionTester.findCollisions(dmgDealer, SpriteLayer.NPC);
            // player collision
        } else {
            collidingSprites = collisionTester.findCollisions(dmgDealer, SpriteLayer.PLAYER);
        }
        dmgDealer.dealDamage(collidingSprites);
    }

    public void updateMenu(){
        if (Command.State.PRESSED_RELEASED.equals(command.getUp())) {
            System.out.println("select prev: "+ command.getUp());
            menu.selectPrevious();
            command.setUp(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
            System.out.println("select next:" + command.getDown());
            menu.selectNext();
            command.setDown(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            if (menu.getSelected().equals("start")) {
                gameMode = GameMode.GAME;
                command.clear();
                createPlayer();
                spriteCollection.put(SpriteLayer.PLAYER, player);
                this.world = new LevelOne(this.spriteCollection);
            } else if (menu.getSelected().equals("options")) {

            } if (menu.getSelected().equals("exit")) {
                command.setTerminated(true);
            }
        }
    }

    private void updatePlayer() {
        int dx = 0;
        int dy = 0;
        if (Command.State.RELEASED_PRESSED.equals(command.getUp())
            || Command.State.PRESSED.equals(command.getUp())) {
            dy -= STEP;
        }
        if (Command.State.RELEASED_PRESSED.equals(command.getDown())
                || Command.State.PRESSED.equals(command.getDown())) {
            dy += STEP;
        }
        if (Command.State.RELEASED_PRESSED.equals(command.getLeft())
            || Command.State.PRESSED.equals(command.getLeft())) {
            dx -= STEP;
        }
        if (Command.State.RELEASED_PRESSED.equals(command.getRight())
            || Command.State.PRESSED.equals(command.getRight())) {
            dx += STEP;
        }

        if (Command.State.RELEASED_PRESSED.equals(command.getAction1())
            || Command.State.PRESSED.equals(command.getAction1())) {
            player.attack();
        }

        player.setStep(dx, dy);
        player.updateSprite();

    }

    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }

    @Override
    public void drawGUI(Graphics2D g2, ImageLoader iml) {
        switch(gameMode) {
            case GAME:
                Font font = new Font("system",Font.BOLD,12);
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString(player.getCurrentHealth() + "/" + player.getTotalHealth(), 10, screenHeight -50);
                break;
            case MAIN_MENU:
                // clear the background
                g2.setColor(Color.darkGray);
                g2.fillRect(0, 0, 800, 600);

                menu.drawMenu(g2);
                break;
            case INVENTORY:
                // clear the background
                g2.setColor(Color.darkGray);
                g2.fillRect(0, 0, 800, 600);

                inventory.drawScreen(g2, iml);
                break;
        }
    }


}
