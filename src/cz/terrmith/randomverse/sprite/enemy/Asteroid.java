package cz.terrmith.randomverse.sprite.enemy;

import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;

import java.util.*;

/**
 * Asteroid
 */
public class Asteroid extends MultiSprite implements Destructible{

    private static Random r = new Random();

    /**
     * Creates random asteroid at given position
     * @param x x position
     * @param y y position
     */
    public Asteroid(int x, int y) {
        super(x, y);
        generateAsteroid(5);

    }

    /**
     * Generates tiles of asteroid\
     * @param maxSize maximum distance from center
     * @return
     */
    private void generateAsteroid(int maxSize) {

        final List<GridLocation> locations = new ArrayList<GridLocation>();
        locations.add(new GridLocation(0,0));

        generateLocations(locations, locations, maxSize -1);

        System.out.println("generated asteroid of size " + locations.size());

        for (GridLocation gl : locations) {
            addTile(gl.getX(), gl.getY(), new AsteroidPart(0, 0));
        }

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
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getCurrentHealth() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reduceHealth(int amount) {
        throw new IllegalStateException("Can not reduce health directly");
    }
}
