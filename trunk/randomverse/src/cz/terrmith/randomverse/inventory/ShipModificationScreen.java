package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.sprite.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 17.10.13
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
public class ShipModificationScreen {

	private static final int PARTS_PER_ROW = 4;
	private Ship player;
    private int shipX = 0;
    private int shipY = 0;

    private int partX = 0;
    private int partY = 0;

    private enum Mode{SHIP, PART}
    private Mode mode = Mode.SHIP;

    private List<SimpleSprite> parts;

    public ShipModificationScreen(Ship player) {
        this.player = new Ship(player);
        this.parts = new ArrayList<SimpleSprite>();
        fillParts();

    }

    private void fillParts() {
        Map<SpriteStatus, ImageLocation> cockpit0 = new HashMap<SpriteStatus, ImageLocation>();
        cockpit0.put(SpriteStatus.DEFAULT, new ImageLocation("cockpit", 0));
        Map<SpriteStatus, ImageLocation> cockpit1 = new HashMap<SpriteStatus, ImageLocation>();
        cockpit1.put(SpriteStatus.DEFAULT, new ImageLocation("cockpit", 1));
        Map<SpriteStatus, ImageLocation> cockpit2 = new HashMap<SpriteStatus, ImageLocation>();
        cockpit2.put(SpriteStatus.DEFAULT, new ImageLocation("cockpit", 2));
        Map<SpriteStatus, ImageLocation> cockpit3 = new HashMap<SpriteStatus, ImageLocation>();
        cockpit3.put(SpriteStatus.DEFAULT, new ImageLocation("cockpit", 3));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, cockpit0));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, cockpit1));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, cockpit2));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, cockpit3));

        Map<SpriteStatus, ImageLocation> midParts0 = new HashMap<SpriteStatus, ImageLocation>();
        midParts0.put(SpriteStatus.DEFAULT, new ImageLocation("midParts", 0));
        Map<SpriteStatus, ImageLocation> midParts1 = new HashMap<SpriteStatus, ImageLocation>();
        midParts1.put(SpriteStatus.DEFAULT, new ImageLocation("midParts", 1));
        Map<SpriteStatus, ImageLocation> midParts2 = new HashMap<SpriteStatus, ImageLocation>();
        midParts2.put(SpriteStatus.DEFAULT, new ImageLocation("midParts", 2));
        Map<SpriteStatus, ImageLocation> midParts3 = new HashMap<SpriteStatus, ImageLocation>();
        midParts3.put(SpriteStatus.DEFAULT, new ImageLocation("midParts", 3));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, midParts0));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, midParts1));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, midParts2));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, midParts3));

        Map<SpriteStatus, ImageLocation> bottomEngines0 = new HashMap<SpriteStatus, ImageLocation>();
        bottomEngines0.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", 0));
        Map<SpriteStatus, ImageLocation> bottomEngines1 = new HashMap<SpriteStatus, ImageLocation>();
        bottomEngines1.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", 1));
        Map<SpriteStatus, ImageLocation> bottomEngines2 = new HashMap<SpriteStatus, ImageLocation>();
        bottomEngines2.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", 2));
        Map<SpriteStatus, ImageLocation> bottomEngines3 = new HashMap<SpriteStatus, ImageLocation>();
        bottomEngines3.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", 3));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, bottomEngines0));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, bottomEngines1));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, bottomEngines2));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, bottomEngines3));

        Map<SpriteStatus, ImageLocation> sideGun0 = new HashMap<SpriteStatus, ImageLocation>();
        sideGun0.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", 0));
        Map<SpriteStatus, ImageLocation> sideGun1 = new HashMap<SpriteStatus, ImageLocation>();
        sideGun1.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", 1));
        Map<SpriteStatus, ImageLocation> sideGun2 = new HashMap<SpriteStatus, ImageLocation>();
        sideGun2.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", 2));
        Map<SpriteStatus, ImageLocation> sideGun3 = new HashMap<SpriteStatus, ImageLocation>();
        sideGun3.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", 3));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, sideGun0));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, sideGun1));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, sideGun2));
        parts.add(new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, sideGun3));
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
	           // change/add ship part
	           player.removeTile(shipX, shipY);
	           SimpleSprite sprite = new SimpleSprite(parts.get(partX + partY * PARTS_PER_ROW));
	           player.addTile(shipX, shipY, sprite);

	           partX = 0;
	           partY = 0;
               break;
       }
    }

    public void drawScreen(Graphics g, ImageLoader iml) {
        //translate player
        player.setPosition(100, 100);
        for (Tile t : player.getTiles()) {
            t.getSprite().drawSprite(g,iml);
        }
        g.setColor(Color.GREEN);
        g.drawRect( (int)player.getXPosn() + this.shipX * Tile.DEFAULT_SIZE,
                    (int)player.getXPosn() + this.shipY * Tile.DEFAULT_SIZE,
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
