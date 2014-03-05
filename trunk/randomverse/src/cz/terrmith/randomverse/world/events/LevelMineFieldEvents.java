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
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class LevelMineFieldEvents {

    public static WorldEvent minefieldAvoidable(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        final String INVESTIGATE = "11";
        final String LEAVE = "12";
        map.put(ENTRY, new NavigableText("You warped close to a minefield, fortunately their sensors did notice you yet",
                new NavigableTextOption[]{new NavigableTextOption("Investigate the minefield", INVESTIGATE),
                        new NavigableTextOption("Stay away from minefield", LEAVE)}));
        map.put(INVESTIGATE, new NavigableText("There just might be something valuable!", callbacks.get(EventResult.EMBARK)));
        map.put(LEAVE, new NavigableText("It seems too dangerous, you stay in safe distance", callbacks.get(EventResult.MOVE)));
        DynamicText dynamicText = new DynamicText(map, ENTRY);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Noisy signal"));
        scannerInfo.add(new ScannerInfo(5, "Many inactive entities"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo);
        return ret;
    }

    public static WorldEvent minefield(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        map.put(ENTRY, new NavigableText("You are in the middle of the minefield!",callbacks.get(EventResult.EMBARK)));
        DynamicText dynamicText = new DynamicText(map, ENTRY);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Noisy signal"));
        scannerInfo.add(new ScannerInfo(5, "Many inactive entities"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
