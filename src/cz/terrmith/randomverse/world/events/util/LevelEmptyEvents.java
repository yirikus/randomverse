package cz.terrmith.randomverse.world.events.util;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
import cz.terrmith.randomverse.core.dialog.NavigableTextBranch;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.inventory.Mission;
import cz.terrmith.randomverse.world.events.EventResult;
import cz.terrmith.randomverse.world.events.ScannerInfo;
import cz.terrmith.randomverse.world.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 7.3.14
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class LevelEmptyEvents {

    private static final Random random = new Random();

    private LevelEmptyEvents() {
    }

    public static WorldEvent shop(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        NavigableText navigableText = new NavigableTextLeaf("Lets shop!", callbacks.get(EventResult.SHOP),null);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Ad network: 'Visit Atsep Icnu's Intergalactic Spaceparts shop! Best prices in known universe!'"));

        return new WorldEvent(dynamicText, scannerInfo, "1");
    }

    public static WorldEvent goSomewhere(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        NavigableTextBranch navigableText = new NavigableTextBranch("I was wondering if you could fly somewhere. No reason. Just curious. You will get reward!", "");
        Mission mission = new Mission("Go somewhere", "Go where you are told", new GridLocation(1 + random.nextInt(10), 1 + random.nextInt(16)), 1);
        navigableText.addOption(new NavigableTextLeaf<Mission>("Sure", callbacks.get(EventResult.MOVE), mission));
        navigableText.addOption(new NavigableTextLeaf("Fuck you", callbacks.get(EventResult.MOVE), null));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Just a small beep"));
        scannerInfo.add(new ScannerInfo(5, "Seems like one lone ship"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo, "1");
        return ret;
    }


}
