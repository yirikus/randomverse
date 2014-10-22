package cz.terrmith.randomverse.world.events.factory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableTextBranch;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextLeaf;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.world.LevelMinefield;
import cz.terrmith.randomverse.world.events.*;

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
public class LevelMineFieldEvents extends WorldEventFactory {

    public LevelMineFieldEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent minefieldAvoidable() {
        NavigableTextBranch navigableText = new NavigableTextBranch("You warped close to a minefield, fortunately their sensors did notice you yet", "");
        WorldEventResult result = new WorldEventResult(null, new LevelMinefield(getSpc(), getPlayer(), getAi()));
        navigableText.addOption(new NavigableTextLeaf("Investigate the minefield", getCallbacks().get(EventCallbackResult.EMBARK), result));
        navigableText.addOption(new NavigableTextLeaf("It seems too dangerous, you stay in safe distance", getCallbacks().get(EventCallbackResult.MOVE), null));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Noisy signal"));
        scannerInfo.add(new ScannerInfo(5, "Many inactive entities"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo);
        return ret;
    }

    public WorldEvent minefield() {
        WorldEventResult result = new WorldEventResult(null, new LevelMinefield(getSpc(), getPlayer(), getAi()));
        NavigableTextLeaf navigableText = new NavigableTextLeaf("You are in the middle of the minefield!",getCallbacks().get(EventCallbackResult.EMBARK), result);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Noisy signal"));
        scannerInfo.add(new ScannerInfo(5, "Many inactive entities"));

        return new WorldEvent(dynamicText, scannerInfo);
    }
}
