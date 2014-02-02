package cz.terrmith.randomverse.sprite.enemy.debris;

import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Debris
 */
public class Debris extends MultiSprite implements Destructible{

    private static Random r = new Random();
    private final SpriteCollection spriteCollection;
    private double speed;
    private Sprite player;

    /**
     * Creates random asteroid at given position
     * @param x x position
     * @param y y position
     */
    public Debris(int x, int y, SpriteCollection spriteCollection, Sprite player) {
        super(x, y);
        this.player = player;
        this.spriteCollection = spriteCollection;
        generateAsteroid(5);
    }

    /**
     * Generates tiles of asteroid\
     * @param maxSize maximum distance from center
     */
    private void generateAsteroid(int maxSize) {

        final List<GridLocation> locations = new ArrayList<GridLocation>();
        locations.add(new GridLocation(0,0));

        generateLocations(locations, locations, maxSize -1);
	    this.speed = Math.max(5 - locations.size()/4, 1);

        System.out.println("generated debris of size: " + locations.size() + ", speed:" + speed);

        for (GridLocation gl : locations) {
            DebrisPart.DebrisPartType type = DebrisPart.DebrisPartType.REGULAR;
            if (r.nextInt() % 10 == 0) {
                switch(r.nextInt(6)) {
                    case 0: type = DebrisPart.DebrisPartType.CARGO;
                        break;
                    case 1: type = DebrisPart.DebrisPartType.HARD;
                        break;
                    case 2: type = DebrisPart.DebrisPartType.EXPLODING;
                        break;
                    case 3: type = DebrisPart.DebrisPartType.CLUSTER;
                        break;
                    case 4: type = DebrisPart.DebrisPartType.GUN;
                        break;
                    case 5: type = DebrisPart.DebrisPartType.REFLECT;
                        break;
                }
            }

            addTile(gl.getX(), gl.getY(), new DebrisPart(0, 0, this, type, player));
        }

        updateSpriteStatus();

    }

    /**
     * Recursively adds locations
     * @param locations output parameter
     * @param steps how many times times will be this method repeated
     */
    private void generateLocations(List<GridLocation> locations, List<GridLocation>newLocations, int steps) {
        if (steps < 0) {
            return;
        }
        List<GridLocation>nextSeeds = new ArrayList<GridLocation>();
        for (GridLocation gl : new ArrayList<GridLocation>(newLocations)) {
            addLocation(locations, nextSeeds, new GridLocation(gl.getX() + 1, gl.getY()));
            addLocation(locations, nextSeeds, new GridLocation(gl.getX() - 1, gl.getY()));
            addLocation(locations, nextSeeds, new GridLocation(gl.getX(), gl.getY() + 1));
            addLocation(locations, nextSeeds, new GridLocation(gl.getX(), gl.getY() - 1));
        }
        generateLocations(locations, nextSeeds, steps - 1);
    }

    /**
     * Adds location with 50% probability
     * @param locations output parameter
     * @param newLocations new locations will be adjacent to these locations
     * @param newLoc location to be added
     */
    private void addLocation(List<GridLocation> locations, List<GridLocation> newLocations, GridLocation newLoc) {
        if (r.nextBoolean() && !locations.contains(newLoc)) {
            locations.add(newLoc);
            newLocations.add(newLoc);
        }
    }

    @Override
    public int getTotalHealth() {
        throw new IllegalStateException("Can not reduce health directly");
    }

    @Override
    public int getCurrentHealth() {
        throw new IllegalStateException("Can not reduce health directly");
    }

    @Override
    public void reduceHealth(int amount) {
        throw new IllegalStateException("Can not reduce health directly");
    }

	public double getSpeed() {
		return speed;
	}

    public void updateSpriteStatus(){
        System.out.println("#######################");
        for (Tile t: getTiles()) {
            DebrisPart sprite = (DebrisPart)t.getSprite();
            sprite.updateSpriteStatus(new GridLocation(t.getTileX(), t.getTileY()));
        }
    }

    public SpriteCollection getSpriteCollection() {
        return spriteCollection;
    }
}
