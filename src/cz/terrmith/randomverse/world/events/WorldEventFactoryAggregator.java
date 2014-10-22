package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.world.events.factory.*;

import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 6.4.14
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
public class WorldEventFactoryAggregator {

    private final DebrisFieldEvents debrisField;
    private final LevelEmptyEvents empty;
    private final LevelInvadersEvents invaders;
    private final LevelMineFieldEvents minefield;
    private final SpaceHighwayEvents highway;
    private final SurroundedEvents surrounded;
    private static final Random random = new Random();

    public WorldEventFactoryAggregator(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        debrisField = new DebrisFieldEvents(callbacks, ai, spc, player);
        empty = new LevelEmptyEvents(callbacks,ai,spc,player);
        invaders = new LevelInvadersEvents(callbacks,ai,spc,player);
        minefield = new LevelMineFieldEvents(callbacks, ai, spc, player);
        highway = new SpaceHighwayEvents(callbacks, ai, spc, player);
        surrounded = new SurroundedEvents(callbacks, ai, spc, player);
    }

    public WorldEvent randomEvent() {
        switch (random.nextInt(10)) {
            case 0:
                return highway.highway();
            case 1:
                return debrisField.shipwreck();
            case 2:
                return minefield.minefield();
            case 3:
                return surrounded.surrounded();
            case 4:
                return invaders.aquabelles();
            case 5:
                return empty.shop();
            case 6:
                return invaders.invaders();
            case 7:
                return invaders.invadersWithUfos();
            default:
                return empty.goSomewhere();
        }
    }

    public DebrisFieldEvents getDebrisField() {
        return debrisField;
    }

    public LevelEmptyEvents getEmpty() {
        return empty;
    }

    public LevelInvadersEvents getInvaders() {
        return invaders;
    }

    public LevelMineFieldEvents getMinefield() {
        return minefield;
    }

    public SpaceHighwayEvents getHighway() {
        return highway;
    }

    public SurroundedEvents getSurrounded() {
        return surrounded;
    }
}
