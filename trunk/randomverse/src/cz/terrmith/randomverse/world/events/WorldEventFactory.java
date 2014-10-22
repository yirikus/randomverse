package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 6.4.14
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public class WorldEventFactory {

    private final Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks;
    private final ArtificialIntelligence ai;
    private final SpriteCollection spc;
    private final Player player;

    public WorldEventFactory(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        this.callbacks = callbacks;
        this.ai = ai;
        this.spc = spc;
        this.player = player;
    }

    public Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> getCallbacks() {
        return callbacks;
    }

    public ArtificialIntelligence getAi() {
        return ai;
    }

    public SpriteCollection getSpc() {
        return spc;
    }

    public Player getPlayer() {
        return player;
    }
}
