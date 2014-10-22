package cz.terrmith.randomverse.world.events.factory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.*;
import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.inventory.Mission;
import cz.terrmith.randomverse.world.events.*;

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
public class LevelEmptyEvents extends WorldEventFactory {

    private static final Random random = new Random();

    public LevelEmptyEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent goSomewhere() {
        NavigableTextBranch navigableText = new NavigableTextBranch("I was wondering if you could fly somewhere. No reason. Just curious. You will get reward!", "");
        Mission mission = new Mission("Go somewhere", "Go where you are told", new GridLocation(1 + random.nextInt(10), 1 + random.nextInt(16)), 1);
        navigableText.addOption(new NavigableTextLeaf<WorldEventResult>("Sure", getCallbacks().get(EventCallbackResult.MOVE), new WorldEventResult(mission, null)));
        navigableText.addOption(new NavigableTextLeaf("Fuck you", getCallbacks().get(EventCallbackResult.MOVE), null));
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Just a small beep"));
        scannerInfo.add(new ScannerInfo(5, "Seems like one lone ship"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo);
        return ret;
    }

    public WorldEvent shop() {
        NavigableText navigableText = new NavigableTextLeaf("Lets shop!", getCallbacks().get(EventCallbackResult.SHOP),null);
        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Ad network: 'Visit Atsep Icnu's Intergalactic Spaceparts shop! Best prices in known universe!'"));

        return new WorldEvent(dynamicText, scannerInfo);
    }


}
