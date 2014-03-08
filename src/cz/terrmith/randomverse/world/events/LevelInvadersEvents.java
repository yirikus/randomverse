package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.world.ScannerInfo;
import cz.terrmith.randomverse.core.world.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class LevelInvadersEvents {

    private LevelInvadersEvents(){}

    public static WorldEvent invaders(Map<EventResult, NavigableTextCallback> callbacks) {
        NavigableTextLeaf navigableText = new NavigableTextLeaf("It's them! Fucking invaders!",callbacks.get(EventResult.EMBARK));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; invaders class ships"));

        return new WorldEvent(dynamicText, scannerInfo);
    }

    public static WorldEvent invadersWithUfos(Map<EventResult, NavigableTextCallback> callbacks) {

        final NavigableTextLeaf navigableText = new NavigableTextLeaf("It's them! Fucking invaders with something unidentifiable objects behind them!",callbacks.get(EventResult.EMBARK));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; invaders class ships; unidentifiable objects"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
