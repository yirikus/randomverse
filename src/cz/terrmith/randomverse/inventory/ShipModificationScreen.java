package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.geometry.Plane;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.sprite.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 17.10.13
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
public class ShipModificationScreen {

	private static final int PARTS_PER_ROW = 4;
    private final Ship playerRef;
    private final Ship ship;
    private final ShipPartFactory factory;
    private int shipX = 0;
    private int shipY = 0;

    private int partX = 0;
    private int partY = 0;

    private enum Mode{SHIP, PART}
    private Mode mode = Mode.SHIP;

    private List<SimpleSprite> parts;

    public ShipModificationScreen(Ship player, SpriteCollection spriteCollection) {
        this.ship = new Ship(player);
        this.playerRef = player;
        this.parts = new ArrayList<SimpleSprite>();
        factory = new ShipPartFactory(spriteCollection, Damage.DamageType.NPC);
        parts.addAll(factory.createAll());
    }



    /**
     * Move selector left
     */
    public void moveLeft() {
        switch (mode) {
            case SHIP:
               this.shipX--;
                break;
            case PART:
                this.partX--;
                break;
        }
    }

    /**
     * Move selector right
     */
    public void moveRight() {
        switch (mode) {
            case SHIP:
                this.shipX++;
                break;
            case PART:
                this.partX++;
                break;
        }
    }

    /**
     * Move selector up
     */
    public void moveUp() {
        switch (mode) {
            case SHIP:
                this.shipY--;
                break;
            case PART:
                this.partY--;
                break;
        }
    }

    /**
     * Move selector down
     */
    public void moveDown() {
        switch (mode) {
            case SHIP:
                this.shipY++;
                break;
            case PART:
                this.partY++;
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
               ship.addTile(shipX, shipY, sprite);

               // change/add ship part to player reference
               playerRef.removeTile(shipX, shipY);
               SimpleSprite sprite2 = factory.create(partX + partY * PARTS_PER_ROW);
               playerRef.addTile(shipX, shipY, sprite2);

	           partX = 0;
	           partY = 0;
               break;
       }
    }

    public void clear() {
        switch (mode) {
            case SHIP:
                // change/add ship part to local copy
                ship.removeTile(shipX, shipY);
                // change/add ship part to player reference
                playerRef.removeTile(shipX, shipY);
                break;
            case PART:
                mode = Mode.SHIP;
                partX = 0;
                partY = 0;
                break;
        }
    }

    public void flip(Plane plane) {
        switch (mode) {
            case SHIP:
                flip(ship, plane);
                flip(playerRef, plane);
                break;
            case PART:
                break;
        }

    }


    private void flip(Ship ship, Plane plane) {
        Tile tile = Tile.findTile(ship.getTiles(), shipX, shipY);
        if (tile != null) {
            switch (plane) {
                case HORIZONTAL:
                    tile.getSprite().flipHorizontal();
                    break;
                case VERTICAL:
                    tile.getSprite().flipVertical();
                    break;
            }
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
            }

            column++;
             if(column == PARTS_PER_ROW) {
                column = 0;
                row++;
            }
        }

    }

}
