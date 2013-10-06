package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Sprite of a ship
 */
public class Ship extends Sprite {

    private static final Map<SpriteStatus, String> imageForStatus;
    static {
        Map<SpriteStatus, String> aMap = new HashMap<SpriteStatus, String>();
        aMap.put(SpriteStatus.DEFAULT, "midParts");
        imageForStatus = Collections.unmodifiableMap(aMap);
    }

    public Ship(int x, int y) {
        super(x, y, 8, 8, imageForStatus);
    }
}
