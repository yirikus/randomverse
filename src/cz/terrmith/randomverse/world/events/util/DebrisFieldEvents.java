package cz.terrmith.randomverse.world.events.util;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextBranch;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.world.LevelDebrisField;
import cz.terrmith.randomverse.world.events.EventCallbackResult;
import cz.terrmith.randomverse.world.events.ScannerInfo;
import cz.terrmith.randomverse.world.events.WorldEvent;
import cz.terrmith.randomverse.world.events.WorldEventResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class DebrisFieldEvents extends WorldEventFactory {

    public DebrisFieldEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent shipwreck() {

        NavigableTextBranch navigableText = new NavigableTextBranch("You found a shipwreck", "");
        WorldEventResult result = new WorldEventResult(null, new LevelDebrisField(getSpc(), getPlayer().getSprite(), getAi()));
        navigableText.addOption(new NavigableTextLeaf("Investigate the shipwreck", this.getCallbacks().get(EventCallbackResult.EMBARK),result));
        navigableText.addOption(new NavigableTextLeaf("It seems too dangerous, so you leave it be..", this.getCallbacks().get(EventCallbackResult.MOVE),null));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity; huge mass"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; many particles; low electronics"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo, "1");
        return ret;
    }
}
