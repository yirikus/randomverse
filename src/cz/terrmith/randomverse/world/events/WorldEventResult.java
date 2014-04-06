package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.inventory.Mission;

/**
 * Holds various objects that can be reault of a world event
 */
public class WorldEventResult {
    private Mission mission;
    private World world;

    public WorldEventResult(Mission mission, World world) {
        this.mission = mission;
        this.world = world;
    }

    public Mission getMission() {
        return mission;
    }

    public World getWorld() {
        return world;
    }

}
