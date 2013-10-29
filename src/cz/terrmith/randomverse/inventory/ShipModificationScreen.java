package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.Plane;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.sprite.ExtensionPoint;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.ShipPart;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 17.10.13
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
public class ShipModificationScreen {

	private static final int PARTS_PER_ROW = 4;
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
	            if (partX < (PARTS_PER_ROW - 1)) {
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
                if (partY < ((parts.size() - 1)/ PARTS_PER_ROW)) {
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
               mode = Mode.SHIP;
	           // change/add ship part to local copy
	           ship.removeTile(shipX, shipY);
	           SimpleSprite sprite = factory.create(partX + partY * PARTS_PER_ROW);
	           Tile newTile = new Tile(shipX, shipY, sprite);
	           ship.addTile(newTile);

               // change/add ship part to player reference
               playerRef.getSprite().removeTile(shipX, shipY);
               SimpleSprite sprite2 = factory.create(partX + partY * PARTS_PER_ROW);
               playerRef.getSprite().addTile(shipX, shipY, sprite2);

	           addExtensionPoints(newTile);

	           partX = 0;
	           partY = 0;
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
            t.getSprite().drawSprite(g,iml);
        }
        g.setColor(Color.GREEN);
        g.drawRect( (int) ship.getXPosn() + this.shipX * Tile.DEFAULT_SIZE,
                    (int) ship.getXPosn() + this.shipY * Tile.DEFAULT_SIZE,
                    Tile.DEFAULT_SIZE,
                    Tile.DEFAULT_SIZE);

	    // draw parts to choose from
	    int initX = 100;
	    int initY = 300;
	    int column = 0;
	    int row = 0;

        for (Sprite s : parts) {
            s.setPosition(initX + column * s.getWidth(), initY + row * s.getHeight());
            s.drawSprite(g,iml);
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
            }

	        g.drawString("total ship speed: " + playerRef.getSprite().getSpeed(), 400, 100);

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
