package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextOption;
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
public final class DebrisFieldEvents {

    private DebrisFieldEvents(){}

    public static WorldEvent shipwreck(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        final String INVESTIGATE = "11";
        final String LEAVE = "12";
        map.put(ENTRY, new NavigableText("You found a shipwreck",
                new NavigableTextOption[]{new NavigableTextOption("Investigate the shipwreck", INVESTIGATE),
                        new NavigableTextOption("Leave", LEAVE)}));
        map.put(INVESTIGATE, new NavigableText("You move closer to investigate", callbacks.get(EventResult.EMBARK)));
        map.put(LEAVE, new NavigableText("It seems too dangerous, so you leave..", callbacks.get(EventResult.MOVE)));
        DynamicText dynamicText = new DynamicText(map, ENTRY);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity; huge mass"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; many particles; low electronics"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo);
        return ret;
    }
}
