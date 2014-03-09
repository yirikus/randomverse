package cz.terrmith.randomverse.world.events.util;

import cz.terrmith.randomverse.core.dialog.DynamicText;
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
public final class LevelOneEvents {

    private LevelOneEvents(){}

    public static WorldEvent surrounded(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        NavigableTextLeaf navigableText = new NavigableTextLeaf("You, sir are surrounded!",callbacks.get(EventResult.EMBARK), null);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; Frigate class ships"));

        return new WorldEvent(dynamicText, scannerInfo, "1");
    }
}
