package cz.terrmith.randomverse.world.events;

import cz.terrmith.randomverse.core.dialog.DynamicText;
import cz.terrmith.randomverse.core.dialog.NavigableText;
import cz.terrmith.randomverse.core.dialog.NavigableTextCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Static factory class of event messages for DebrisField level
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 16:52
 */
public final class LevelOneEvents {

    private LevelOneEvents(){}

    public static DynamicText shop(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        map.put(ENTRY, new NavigableText("Lets shop!",callbacks.get(EventResult.SHOP)));
        DynamicText ret = new DynamicText(map, ENTRY);

        return ret;
    }

    public static DynamicText surrounded(Map<EventResult, NavigableTextCallback> callbacks) {
        Map<String, NavigableText> map = new HashMap<String, NavigableText>();
        final String ENTRY = "1";
        map.put(ENTRY, new NavigableText("You, sir are surrounded!",callbacks.get(EventResult.EMBARK)));
        DynamicText ret = new DynamicText(map, ENTRY);

        return ret;
    }
}
