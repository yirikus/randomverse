package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.dialog.Dialog;
import cz.terrmith.randomverse.core.dialog.DialogCallback;
import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.geometry.Plane;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.menu.Menu;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CollisionTester;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.core.sprite.abilitiy.Loot;
import cz.terrmith.randomverse.core.sprite.abilitiy.LootSprite;
import cz.terrmith.randomverse.core.sprite.abilitiy.Lootable;
import cz.terrmith.randomverse.core.sprite.abilitiy.Solid;
import cz.terrmith.randomverse.core.sprite.collision.Collision;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.inventory.GameMap;
import cz.terrmith.randomverse.inventory.GridMenu;
import cz.terrmith.randomverse.inventory.ShipModificationScreen;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

/**
 * Contains global constants
 */
public class Randomverse extends GameEngine {

    public static final String WINDOW_NAME = "Randomverse";

    private final Command command;
	private final CollisionTester collisionTester;
	private final Player player;
	private ShipModificationScreen inventory = null;
    private int screenWidth;
    private int screenHeight;
    private World world;
    private SpriteCollection spriteCollection;
	private GameMap map;

	private enum GameMode {MAIN_MENU, GAME, MAP,
		SHOP,
		INVENTORY}
    private GameMode gameMode = GameMode.MAIN_MENU;
    private Menu menu;

    public Randomverse(Command cmd, int screenWidth, int screenHeight) {
        this.command = cmd;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Boundary screenBoundary = new Boundary(0, screenWidth, 0, screenHeight);
        Boundary extendedBoundary = new Boundary(-screenWidth, 2 * screenWidth, -screenHeight, 2 * screenHeight);
        this.spriteCollection = new SpriteCollection(screenBoundary, extendedBoundary);
	    this.player = new Player(cmd, spriteCollection);
	    this.collisionTester = new CollisionTester(this.spriteCollection);
        this.menu = new Menu(new Position(100,100));
        menu.addItem("start");
        menu.addItem("options");
        menu.addItem("exit");
	    map = new GameMap(10, 16, Tile.DEFAULT_SIZE, new Position(100,100));
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
	        case SHOP:
		        updateInventory();
		        if (Command.State.PRESSED.equals(command.getInventory())
		          || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {

			        gameMode = GameMode.MAP;
			        command.setInventory(false);
		        }
		        break;
            case MAP:
	            updateMap();
	            break;
            case GAME:

                if (Command.State.PRESSED.equals(command.getPrevious())
                  || Command.State.RELEASED_PRESSED.equals(command.getPrevious())) {
                    gameMode = GameMode.MAIN_MENU;
                    command.clear();
                } else if (!player.getSprite().isActive()) {
	               // add modal game over dialog
	                DialogCallback callback = new DialogCallback() {
		                @Override
		                public void onClose() {
			                gameMode = GameMode.MAIN_MENU;
		                }
	                };
	                Dialog dialog = new Dialog("GAME OVER",200,200,400,200,callback);
	                showDialog(dialog);
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
	                updateItems();
                    player.update();
                    updateWorld();
                }
                break;
        }
    }

	private void updateMap() {
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

			gameMode = GameMode.GAME;
			spriteCollection.clear();
			command.clear();
			spriteCollection.put(SpriteLayer.PLAYER, player.getSprite());
			this.world = new LevelOne(this.spriteCollection);

			DialogCallback callback = new DialogCallback() {
				@Override
				public void onClose() {
				}
			};
			Dialog dialog = new Dialog("GAME START",200,200,400,200,callback);
			showDialog(dialog);

		} else if (Command.State.PRESSED_RELEASED.equals(command.getAction2())) {
		} else if (Command.State.PRESSED_RELEASED.equals(command.getAction3())) {
		} else if (Command.State.PRESSED_RELEASED.equals(command.getAction4())) {
		} else  if (Command.State.PRESSED.equals(command.getInventory())
		  || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {
			gameMode = GameMode.SHOP;
			inventory = new ShipModificationScreen(player, spriteCollection);
			command.setInventory(false);
		} else if (Command.State.PRESSED.equals(command.getPrevious())
		  || Command.State.RELEASED_PRESSED.equals(command.getPrevious())) {
			gameMode = GameMode.MAIN_MENU;
			command.clear();
		}
	}

	private void updateItems() {
		Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.ITEM).iterator();
		while (iterator.hasNext()) {
			Sprite s = iterator.next();
			s.updateSprite();
			if (!s.collidesWith(player.getSprite()).isEmpty()) {
				Loot loot = ((LootSprite) s).pickUp();
				player.addLoot(loot);
			}

			if (!s.isActive()) {
				iterator.remove();
			}

		}
	}

	@Override
	public void pause() {
		super.pause();
		world.setPaused(true);
	}

	@Override
	public void unpause() {
		super.unpause();
		world.setPaused(false);
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
	    if (world.completed()) {
		    DialogCallback callback = new DialogCallback() {
			    @Override
			    public void onClose() {
                    map.markExplored();
				    gameMode = GameMode.MAP;
			    }
		    };
		    Dialog dialog = new Dialog("YOU WIN, BITCH! SCORE: " + player.getMoney(), 200, 200, 400, 200, callback);
		    showDialog(dialog);
	    }
    }

    private void updateNpcs() {
        Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.NPC).iterator();
        Boundary b = getSpriteCollection().getBoundary(SpriteLayer.NPC);
        while (iterator.hasNext()) {
            Sprite npcSprite = iterator.next();

            // set sprites that are not within as inactive
            Position spritePos = new Position(npcSprite.getXPosn(), npcSprite.getYPosn());
            if (!b.withinBoundary(spritePos)) {
                npcSprite.setActive(false);
            }

	        // test collision with player
	        List<Collision> collisions = player.getSprite().findCollisionCollections(npcSprite);
	        //deal damage
	        for (Collision c : collisions) {

		        if (c.getSprite() instanceof Solid) {
			        Solid solidPlayerPart = (Solid) c.getSprite();
			        for (Sprite s : c.getCollidingSprites()) {
				        if (s instanceof Solid) {
					        //deal damage
					        if (s instanceof Destructible) {
						        ((Destructible) s).reduceHealth(solidPlayerPart.getImpactDamage());
					        }

					        if (c.getSprite() instanceof Destructible) {
						        ((Destructible) c.getSprite()).reduceHealth(((Solid) s).getImpactDamage());
					        }
					        //obtain damage
				        }
			        }
		        }
	        }

            // delete inactive sprites
            if (npcSprite.isActive()) {
                npcSprite.updateSprite();
            } else {
	            // if sprite was killed add loot to sprite collection
	            if (SpriteStatus.DEAD.equals(npcSprite.getStatus()) && npcSprite instanceof Lootable) {
		            getSpriteCollection().put(SpriteLayer.ITEM, ((Lootable) npcSprite).getLootSprite());
	            }
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
            menu.selectPrevious();
            command.setUp(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getDown())) {
            menu.selectNext();
            command.setDown(false);
        } else if (Command.State.PRESSED_RELEASED.equals(command.getAction1())) {
            if (menu.getSelected().equals("start")) {
                gameMode = GameMode.GAME;
	            spriteCollection.clear();
                command.clear();
                player.reset();
                spriteCollection.put(SpriteLayer.PLAYER, player.getSprite());
                this.world = new LevelOne(this.spriteCollection);

	            DialogCallback callback = new DialogCallback() {
		            @Override
		            public void onClose() {
		            }
	            };
	            Dialog dialog = new Dialog("GAME START",200,200,400,200,callback);
	            showDialog(dialog);

            } else if (menu.getSelected().equals("options")) {

            } if (menu.getSelected().equals("exit")) {
                command.setTerminated(true);
            }
        }
    }



    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }

    @Override
    public void drawHUD(Graphics2D g2, ImageLoader iml) {
        switch(gameMode) {
            case GAME:
                Font font = new Font("system",Font.BOLD,20);
                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString(player.getCurrentHealth() + "/" + player.getTotalHealth(), 10, screenHeight -50);
	            g2.drawString("$" + player.getMoney(), 10, screenHeight -20);
                break;
            case MAIN_MENU:
                // clear the background
	            clearScreen(g2, Color.darkGray);
                menu.drawMenu(g2);
                break;
            case INVENTORY:
                // clear the background
                clearScreen(g2, Color.darkGray);
                inventory.drawScreen(g2, iml);
                break;
	        case MAP:
		        clearScreen(g2, Color.BLACK);
		        map.drawMenu(g2);
		        break;
        }
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

}
