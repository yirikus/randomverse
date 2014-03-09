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
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.3.14
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class LevelMineFieldEvents {

    public static WorldEvent minefieldAvoidable(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        NavigableTextBranch navigableText = new NavigableTextBranch("You warped close to a minefield, fortunately their sensors did notice you yet", "");
        navigableText.addOption(new NavigableTextLeaf("Investigate the minefield", callbacks.get(EventResult.EMBARK), null));
        navigableText.addOption(new NavigableTextLeaf("It seems too dangerous, you stay in safe distance", callbacks.get(EventResult.MOVE), null));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Noisy signal"));
        scannerInfo.add(new ScannerInfo(5, "Many inactive entities"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo, "1");
        return ret;
    }

    public static WorldEvent minefield(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {
        NavigableTextLeaf navigableText = new NavigableTextLeaf("You are in the middle of the minefield!",callbacks.get(EventResult.EMBARK), null);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Noisy signal"));
        scannerInfo.add(new ScannerInfo(5, "Many inactive entities"));

        return new WorldEvent(dynamicText, scannerInfo, "2");
    }
}
