package cz.terrmith.randomverse.world.events.util;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.world.LevelInvaders;
import cz.terrmith.randomverse.world.events.EventCallbackResult;
import cz.terrmith.randomverse.world.events.ScannerInfo;
import cz.terrmith.randomverse.world.events.WorldEvent;
import cz.terrmith.randomverse.world.events.WorldEventResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class LevelInvadersEvents extends WorldEventFactory{


    public LevelInvadersEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent invaders() {
        NavigableTextLeaf navigableText = new NavigableTextLeaf(
                "It's them! Fucking invaders!",
                getCallbacks().get(EventCallbackResult.EMBARK),
                // todo create correct level
                new WorldEventResult(null, new LevelInvaders(getSpc(), getAi())));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; invaders class ships"));

        return new WorldEvent(dynamicText, scannerInfo, LevelInvaders.Variation.CLASSIC.name());
    }

    public WorldEvent invadersWithUfos() {
        final NavigableTextLeaf navigableText = new NavigableTextLeaf(
                "It's them! Fucking invaders with something unidentifiable objects behind them!",
                getCallbacks().get(EventCallbackResult.EMBARK),
                // todo create correct level
                new WorldEventResult(null, new LevelInvaders(getSpc(), getAi())));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; invaders class ships; unidentifiable objects"));

        return new WorldEvent(dynamicText, scannerInfo, LevelInvaders.Variation.CLASSIC.name());
    }

    public WorldEvent aquabelles() {

        WorldEventResult result = new WorldEventResult(null, new LevelInvaders(getSpc(), getAi()));
        final NavigableTextLeaf navigableText = new NavigableTextLeaf(
                "You were just casually flying around when... wild invaders practicing their AQUABELLE show appeared!",
                getCallbacks().get(EventCallbackResult.EMBARK), result);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Minor activity;"));
        scannerInfo.add(new ScannerInfo(5, "Minor activy; invaders class ships"));

        return new WorldEvent(dynamicText, scannerInfo, LevelInvaders.Variation.AQUABELLE.name());
    }
}
