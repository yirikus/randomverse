package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.Plane;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ship.Ship;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;
import cz.terrmith.randomverse.sprite.ship.part.gun.SimpleGun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Screen that allows modification of a ship = adding and removing ship parts
 */
public class ShipModificationScreen {

	private static final int PARTS_PER_ROW = 4;
    // shifts parts
    private static final int PART_SHIFT = 1;
    private final Player playerRef;
    private final Ship ship;
    private final ShipPartFactory factory;
	Map<GridLocation,Set<ExtensionPoint>> extensionPoints = new HashMap<GridLocation, Set<ExtensionPoint>>();

	private int shipX = 0;
    private int shipY = 0;

    private int partX = 0;
    private int partY = 0;

    private enum Mode{SHIP, PART}
    private Mode mode = Mode.SHIP;

    private List<ShipPart> parts;

    public ShipModificationScreen(Player player, SpriteCollection spriteCollection) {
        this.ship = new Ship(player.getSprite());
        this.playerRef = player;
        this.parts = new ArrayList<ShipPart>();
        factory = new ShipPartFactory(spriteCollection, Damage.DamageType.NPC);
	    for (Tile t : ship.getTiles()) {
		    addExtensionPoints(t);
	    }
        parts.addAll(factory.createAll());
    }



    /**
     * Move selector left
     */
    public void moveLeft() {
        switch (mode) {
            case SHIP:
	            if (extensionExists(shipX - 1, shipY)) {
                    this.shipX--;
	            }
                break;
            case PART:
	            if (partX > 0) {
                    this.partX--;
	            }
                break;
        }
    }

	private boolean extensionExists(int x, int y) {
		return extensionPoints.get(new GridLocation(x, y)) != null
		   && !extensionPoints.get(new GridLocation(x, y)).isEmpty();
	}

	/**
     * Move selector right
     */
    public void moveRight() {
        switch (mode) {
            case SHIP:
	            if (extensionExists(shipX + 1, shipY)) {
	                this.shipX++;
	            }
                break;
            case PART:
	            if (partX < (PARTS_PER_ROW - 1) && (partX + partY * PARTS_PER_ROW) < (parts.size() + PART_SHIFT - 1)) {
                    this.partX++;
	            }
                break;
        }
    }

    /**
     * Move selector up
     */
    public void moveUp() {
        switch (mode) {
            case SHIP:
	            if (extensionExists(shipX, shipY - 1)) {
                    this.shipY--;
	            }
                break;
            case PART:
                if (partY > 0) {
	                this.partY--;
                }
                break;
        }
    }

    /**
     * Move selector down
     */
    public void moveDown() {
        switch (mode) {
            case SHIP:
	            if (extensionExists(shipX, shipY + 1)) {
                    this.shipY++;
	            }
                break;
            case PART:
                if ((partX + (partY + 1) * PARTS_PER_ROW) < (parts.size() + PART_SHIFT)) {
	                this.partY++;
                }
                break;
        }
    }

    /**
     * Selectes tile which is highlighted by selector
     */
    public void select() {
       switch (mode) {
           case SHIP:
               mode = Mode.PART;
               break;
           case PART:
               int partSelection = partX + partY * PARTS_PER_ROW - PART_SHIFT;
               Tile tileBeingReplaced = Tile.findTile(playerRef.getSprite().getTiles(), shipX, shipY);
               int replacementPartPrice = 0;
               int replacementPartTotalPrice = 0;
               ShipPart partBeingReplaced = null;
               if (tileBeingReplaced != null) {
                   partBeingReplaced = (ShipPart)tileBeingReplaced.getSprite();
                   replacementPartPrice = + partBeingReplaced.getComputedPrice();
                   replacementPartTotalPrice = partBeingReplaced.getPrice();
               }


               // buy part
               if (partSelection >= 0) {
                   ShipPart shipPart = parts.get(partSelection);

                   if (shipPart.getPrice() <= (playerRef.getMoney() + replacementPartPrice)) {
                       playerRef.setMoney(playerRef.getMoney() + replacementPartPrice - shipPart.getPrice());
                       mode = Mode.SHIP;
                       // change/add ship part to local copy
                       ship.removeTile(shipX, shipY);
                       SimpleSprite sprite = factory.create(partSelection);
                       Tile newTile = new Tile(shipX, shipY, sprite);
                       ship.addTile(newTile);

                       // change/add ship part to player reference
                       playerRef.getSprite().removeTile(shipX, shipY);
                       SimpleSprite sprite2 = factory.create(partSelection);
                       playerRef.getSprite().addTile(shipX, shipY, sprite2);

                       addExtensionPoints(newTile);

                   }
                   partX = 0;
                   partY = 0;
               //repair part
               } else  if (partBeingReplaced != null && (replacementPartTotalPrice - replacementPartPrice) > 0) {
                   playerRef.setMoney(playerRef.getMoney() - (replacementPartTotalPrice - replacementPartPrice) );
                   partBeingReplaced.reduceHealth(partBeingReplaced.getCurrentHealth() - partBeingReplaced.getTotalHealth());
                   ShipPart mirrorPart = (ShipPart)Tile.findTile(ship.getTiles(), shipX, shipY).getSprite();
                   mirrorPart.reduceHealth(partBeingReplaced.getCurrentHealth() - partBeingReplaced.getTotalHealth());
                   partX = 0;
                   partY = 0;
               }

               break;
       }
    }

    public void clear() {
        switch (mode) {
            case SHIP:
                removeExtensionPoints(Tile.findTile(ship.getTiles(), shipX, shipY));
                // change/add ship part to local copy
                ship.removeTile(shipX, shipY);
                // change/add ship part to player reference
                playerRef.getSprite().removeTile(shipX, shipY);

                break;
            case PART:
                mode = Mode.SHIP;
                partX = 0;
                partY = 0;
                break;
        }
    }

	private void removeExtensionPoints(Tile tile) {
		Set<ExtensionPoint> tileExtensions = ((ShipPart) tile.getSprite()).getExtensions();
		for (ExtensionPoint ex : tileExtensions) {
			GridLocation extensionLocation = getExtensionPointLocation(tile, ex);
			extensionPoints.get(extensionLocation).remove(ExtensionPoint.flip(ex));
		}
	}

	public void flip(Plane plane) {
        switch (mode) {
            case SHIP:
                flip(ship, plane);
                flip(playerRef.getSprite(), plane);
                break;
            case PART:
                break;
        }

    }


    private void flip(Ship ship, Plane plane) {
        Tile tile = Tile.findTile(ship.getTiles(), shipX, shipY);
        if (tile != null) {
	        removeExtensionPoints(tile);
            switch (plane) {
                case HORIZONTAL:
                    tile.getSprite().flipHorizontal();
	                break;
                case VERTICAL:
                    tile.getSprite().flipVertical();
                    break;
            }
	        addExtensionPoints(tile);
        }
    }


    public void drawScreen(Graphics g, ImageLoader iml) {
        //translate ship
        ship.setPosition(100, 100);
        for (Tile t : ship.getTiles()) {
            String prevStatus = t.getSprite().getStatus();
	        if(t.getSprite().getStatus().equals(DefaultSpriteStatus.DEAD.name())) {
		        g.setColor(Color.RED);
		        g.fillRect( (int)t.getSprite().getXPosn(),
		                    (int)t.getSprite().getYPosn(),
		                    t.getSprite().getWidth(),
		                    t.getSprite().getHeight());
		        t.getSprite().setStatus(DefaultSpriteStatus.DEFAULT.name());
	        }
            t.getSprite().drawSprite(g,iml);
	        t.getSprite().setStatus(prevStatus);
        }
        g.setColor(Color.GREEN);
        g.drawRect( (int) ship.getXPosn() + this.shipX * Tile.DEFAULT_SIZE,
                    (int) ship.getXPosn() + this.shipY * Tile.DEFAULT_SIZE,
                    Tile.DEFAULT_SIZE,
                    Tile.DEFAULT_SIZE);

	    // draw parts to choose from
	    int initX = 100;
	    int initY = 300;
	    int column = PART_SHIFT;
	    int row = 0;

	    int replacementTilePrice = 0;
        int replacementTileTotalPrice = 0;
	    Tile tileBeignReplaced = Tile.findTile(playerRef.getSprite().getTiles(), shipX, shipY);
	    if (tileBeignReplaced != null) {
		    ShipPart partBeingReplaced = (ShipPart) tileBeignReplaced.getSprite();
		    replacementTilePrice = partBeingReplaced.getComputedPrice();
            replacementTileTotalPrice = partBeingReplaced.getPrice();
	    }


        //test if shift block is selected
        for (int i = 0; i < PART_SHIFT; i++) {
            if (i == 0) {
                //g.drawString("Repair",initX + i * column * Tile.DEFAULT_SIZE,
                //                      initY + i * row * Tile.DEFAULT_SIZE + 20);
                BufferedImage image = iml.getImage("repair");
                g.drawImage(image, initX + i * column,  initY + i * row, null);
                // darken if no reason to repair
                if (tileBeignReplaced != null && (replacementTileTotalPrice - replacementTilePrice) > 0) {
                    g.setColor(new Color(0,0,0,125));
                    g.fillRect(initX + i * column * Tile.DEFAULT_SIZE,
                                initY + i * row * Tile.DEFAULT_SIZE,
                                Tile.DEFAULT_SIZE,
                                Tile.DEFAULT_SIZE);
                }
            }
            if ((partX + partY * PARTS_PER_ROW) == i) {
                g.setColor(Color.GREEN);
                g.drawRect(initX + i * column * Tile.DEFAULT_SIZE,
                           initY + i * row * Tile.DEFAULT_SIZE,
                           Tile.DEFAULT_SIZE,
                           Tile.DEFAULT_SIZE);

                g.drawString("repair price: " + (replacementTileTotalPrice - replacementTilePrice), 400, 180);
            }
        }

	    for (Sprite s : parts) {
            s.setPosition(initX + column * s.getWidth(), initY + row * s.getHeight());
            s.drawSprite(g,iml);

		    // darken if not enough money
		    if (((ShipPart)s).getPrice() > (playerRef.getMoney() + replacementTilePrice)) {
			    g.setColor(new Color(0,0,0,125));
			    g.fillRect((int) s.getXPosn(),
			               (int) s.getYPosn(),
			               s.getWidth(),
			               s.getHeight());
		    }

		    // draw rectangle if selected
		    if (column == this.partX && row == this.partY) {
                g.setColor(Color.GREEN);
                g.drawRect( (int)s.getXPosn(),
                            (int)s.getYPosn(),
                            s.getWidth(),
                            s.getHeight());

	            // draw part info
	            g.drawString("speed: " + ((ShipPart)s).getSpeed(),400,300);
	            g.drawString("health: " + ((ShipPart)s).getTotalHealth(),400,320);
	            if (s instanceof SimpleGun) {
		            g.drawString("damage: " + ((SimpleGun)s).getDamage().getAmount(),400,340);
		            g.drawString("attack rate: " + ((SimpleGun)s).getAttackTimer(),400,360);
	            }
	            g.drawString("Price: $" + ((ShipPart)s).getPrice(),400,380);
            }

	        g.drawString("total ship speed: " + playerRef.getSprite().getSpeed(), 400, 100);
		    g.drawString("total money: " + playerRef.getMoney(), 400, 120);
		    g.drawString("part sell price: " + replacementTilePrice, 400, 160);

            column++;
             if(column == PARTS_PER_ROW) {
                column = 0;
                row++;
            }
        }


	    // draw extension points
	    for (Map.Entry<GridLocation, Set<ExtensionPoint>> entry: extensionPoints.entrySet()) {
			if (Tile.findTile(ship.getTiles(), entry.getKey().getX(), entry.getKey().getY()) == null) {
				drawExtensionPoint(g, 100, 100, entry.getKey(), entry.getValue());
			}
	    }

    }

	/**
	 * Draws rectangle that indicates possible extension points
	 * @param initX x of [0,0] cell
	 * @param initY y of [0,0] cell
	 * @param location location in a grid
	 * @param extensionPointSet extension points to draw
	 */
	private void drawExtensionPoint(Graphics g, int initX, int initY, GridLocation location, Set<ExtensionPoint> extensionPointSet) {
		for (ExtensionPoint ex : extensionPointSet) {
			int dx = 0;
			int dy = 0;
			switch (ex) {
				case TOP:
					dx += Tile.DEFAULT_SIZE/2;
					break;
				case BOTTOM:
					dx += Tile.DEFAULT_SIZE/2;
					dy += Tile.DEFAULT_SIZE;
					break;
				case LEFT:
					dy += Tile.DEFAULT_SIZE/2;
					break;
				case RIGHT:
					dx += Tile.DEFAULT_SIZE;
					dy += Tile.DEFAULT_SIZE/2;
					break;
			}
			g.setColor(Color.RED);
			g.drawRect(initX + location.getX() * Tile.DEFAULT_SIZE + dx,
			           initY + location.getY() * Tile.DEFAULT_SIZE + dy,
			           2,
			           2);
		}
	}

	private void addExtensionPoints(Tile t) {
		ShipPart part = (ShipPart)t.getSprite();
		for (ExtensionPoint ex : part.getExtensions()) {
			GridLocation gridLocation = getExtensionPointLocation(t, ex);

//			System.out.println(t + ": " + ex.name() + ": " + gridLocation);
			Set<ExtensionPoint> extensionSet = extensionPoints.get(gridLocation);
			if (extensionSet != null) {
				extensionSet.add(ExtensionPoint.flip(ex));
			} else {
				extensionSet = new HashSet<ExtensionPoint>();
				extensionSet.add(ExtensionPoint.flip(ex));
				extensionPoints.put(gridLocation, extensionSet);
			}
		}
	}

	private GridLocation getExtensionPointLocation(Tile t, ExtensionPoint ex) {
		switch (ex) {
			case TOP:
				return new GridLocation(t.getTileX(), t.getTileY() - 1);
			case BOTTOM:
				return new GridLocation(t.getTileX(), t.getTileY() + 1);
			case LEFT:
				return new GridLocation(t.getTileX() - 1, t.getTileY());
			case RIGHT:
				return new GridLocation(t.getTileX() + 1, t.getTileY());
		}
		return null;
	}

}
