package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;
import cz.terrmith.randomverse.core.dialog.NavigableTextOption;

import java.util.HashMap;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class DebrisFieldEvents {

    private DebrisFieldEvents(){}

    public static DynamicText shipwreck(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        map.put("1", new NavigableText("You found a shipwreck",
                new NavigableTextOption[]{new NavigableTextOption("Investigate the shipwreck", "11"),
                                          new NavigableTextOption("Leave", "12")}));
        map.put("11", new NavigableText("You move closer to investigate", callbacks.get(EventResult.EMBARK)));
        map.put("12", new NavigableText("It seems too dangerous, so you leave..", callbacks.get(EventResult.MOVE)));
        DynamicText ret = new DynamicText(map, "1");

        return ret;
    }
}
