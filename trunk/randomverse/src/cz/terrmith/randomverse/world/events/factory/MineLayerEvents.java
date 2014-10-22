package cz.terrmith.randomverse.world.events.factory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.world.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Player encounters mine layer ship and has to fight it
 */
public class MineLayerEvents extends WorldEventFactory {
    public MineLayerEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent minelayer() {
//        WorldEventResult result = new WorldEventResult(null, new LevelInvaders(getSpc(), getAi(), LevelInvaders.Variant.AQUABELLE));
        World world;
        final NavigableTextLeaf navigableText = new NavigableTextLeaf(
                "You encounter strange ship flying in a pattern. It leaves small machines along the path..",
                getCallbacks().get(EventCallbackResult.EMBARK), new WorldEventResult(null, world);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, ". . . O"));
        scannerInfo.add(new ScannerInfo(5, ". . . O [special class ship]"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
