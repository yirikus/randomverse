package cz.terrmith.randomverse.world.events.factory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.world.LevelSurrounded;
import cz.terrmith.randomverse.world.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class SurroundedEvents extends WorldEventFactory {

    public SurroundedEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent surrounded() {
        WorldEventResult result = new WorldEventResult(null, new LevelSurrounded(getSpc(), getAi()));
        NavigableTextLeaf navigableText = new NavigableTextLeaf("You, sir are surrounded!", getCallbacks().get(EventCallbackResult.EMBARK), result);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; Frigate class ships"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
