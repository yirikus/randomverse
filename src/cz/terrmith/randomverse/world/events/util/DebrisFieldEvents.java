package cz.terrmith.randomverse.world.events.util;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextBranch;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.inventory.Mission;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.ScannerInfo;
import cz.terrmith.randomverse.world.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class DebrisFieldEvents {

    private DebrisFieldEvents(){}

    public static WorldEvent shipwreck(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {

        NavigableTextBranch navigableText = new NavigableTextBranch("You found a shipwreck", "");
        navigableText.addOption(new NavigableTextLeaf("Investigate the shipwreck", callbacks.get(EventResult.EMBARK),null));
        navigableText.addOption(new NavigableTextLeaf("It seems too dangerous, so you leave it be..", callbacks.get(EventResult.MOVE),null));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity; huge mass"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; many particles; low electronics"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo, "1");
        return ret;
    }
}
