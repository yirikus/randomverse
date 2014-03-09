package cz.terrmith.randomverse.world.events.util;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
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
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class SpaceHighwayEvents {
    public static WorldEvent highway(Map<EventResult, NavigableTextCallback<Mission>> callbacks) {

        NavigableTextBranch navigableText = new NavigableTextBranch("A space highway is blocking your way! Since this is 2D space you can not fly over it, you will have to shoot your way through or find some other way..", "");
        NavigableText attackHighway = new NavigableTextLeaf("...", callbacks.get(EventResult.EMBARK), null);
        navigableText.addOption(new NavigableTextBranch("Attack the highway!", "Fuck police, you are going this way!",new NavigableText[]{attackHighway}));
        navigableText.addOption(new NavigableTextLeaf("Find another way", callbacks.get(EventResult.NONE), null));

        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Galactic channel: space highway, federal property, no scans allowed"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo, "1");
        return ret;
    }
}
