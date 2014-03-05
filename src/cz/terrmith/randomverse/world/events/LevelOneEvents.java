package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.world.ScannerInfo;
import cz.terrmith.randomverse.core.world.WorldEvent;

import java.util.ArrayList;
import java.util.HashMap;
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

    public static WorldEvent shop(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        map.put(ENTRY, new NavigableText("Lets shop!",callbacks.get(EventResult.SHOP)));
        DynamicText dynamicText = new DynamicText(map, ENTRY);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Ad network: 'Visit Atsep Icnu's Intergalactic Spaceparts shop! Best prices in known universe!'"));

        return new WorldEvent(dynamicText, scannerInfo);
    }

    public static WorldEvent surrounded(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        map.put(ENTRY, new NavigableText("You, sir are surrounded!",callbacks.get(EventResult.EMBARK)));
        DynamicText dynamicText = new DynamicText(map, ENTRY);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; Frigate class ships"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
