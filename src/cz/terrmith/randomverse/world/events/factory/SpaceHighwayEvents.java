package cz.terrmith.randomverse.world.events.factory;

import cz.terrmith.randomverse.Player;
import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.dialog.*;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.world.LevelSpaceHighWay;
import cz.terrmith.randomverse.world.events.*;

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
public class SpaceHighwayEvents extends WorldEventFactory {

    public SpaceHighwayEvents(Map<EventCallbackResult, NavigableTextCallback<WorldEventResult>> callbacks, ArtificialIntelligence ai, SpriteCollection spc, Player player) {
        super(callbacks, ai, spc, player);
    }

    public WorldEvent highway() {

        NavigableTextBranch navigableText = new NavigableTextBranch("A space highway is blocking your way! Since this is 2D space you can not fly over it, you will have to shoot your way through or find some other way..", "");
        WorldEventResult result = new WorldEventResult(null, new LevelSpaceHighWay(getSpc(), getAi()));
        NavigableText attackHighway = new NavigableTextLeaf("...", getCallbacks().get(EventCallbackResult.EMBARK), result);
        navigableText.addOption(new NavigableTextBranch("Attack the highway!", "Fuck police, you are going this way!",new NavigableText[]{attackHighway}));
        navigableText.addOption(new NavigableTextLeaf("Find another way", getCallbacks().get(EventCallbackResult.NONE), null));

        DynamicText dynamicText = new DynamicText(navigableText);

        List<ScannerInfo> scannerInfo = new ArrayList<ScannerInfo>();
        scannerInfo.add(new ScannerInfo(1, "Galactic channel: space highway, federal property, no scans allowed"));

        WorldEvent ret = new WorldEvent(dynamicText, scannerInfo);
        return ret;
    }
}
