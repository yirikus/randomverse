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
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.3.14
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class SpaceHighwayEvents {
    public static WorldEvent highway(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        final String EMBARK = "11";
        final String LEAVE = "12";
        map.put(ENTRY, new NavigableText("A space highway is blocking your way! Since this is 2D space you can not fly over it, you will have to shoot your way through or find some other way..",
                new NavigableTextOption[]{new NavigableTextOption("Attack the highway!", EMBARK),
                        new NavigableTextOption("Find another way", LEAVE)}));
        map.put(EMBARK, new NavigableText("Fuck police, you are going this way!", callbacks.get(EventResult.EMBARK)));
        map.put(LEAVE, new NavigableText("You wisely avoid shooting at civilians.", callbacks.get(EventResult.NONE)));
        DynamicText dynamicText = new DynamicText(map, ENTRY);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Galactic channel: space highway, federal property, no scans allowed"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo);
        return ret;
    }
}
