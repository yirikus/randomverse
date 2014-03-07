package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.world.ScannerInfo;
import cz.terrmith.randomverse.core.world.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class LevelEmptyEvents {

    private LevelEmptyEvents() {
    }

    public static WorldEvent shop(Map<EventResult, NavigableTextCallback> callbacks) {
        NavigableText navigableText = new NavigableTextLeaf("Lets shop!", callbacks.get(EventResult.SHOP));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Ad network: 'Visit Atsep Icnu's Intergalactic Spaceparts shop! Best prices in known universe!'"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
