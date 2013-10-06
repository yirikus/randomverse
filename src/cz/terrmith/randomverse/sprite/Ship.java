package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * SimpleSprite of a ship
 */
public class Ship extends MultiSprite {

    private static final Map<SpriteStatus, String> imageForStatus;
    static {
        Map<SpriteStatus, String> aMap = new HashMap<SpriteStatus, String>();
        aMap.put(SpriteStatus.DEFAULT, "midParts");
        imageForStatus = Collections.unmodifiableMap(aMap);
    }

    public Ship(int x, int y) {
        super(x, y);

        Random random = new Random();
        Map<SpriteStatus, ImageLocation> cockpit = new HashMap<SpriteStatus, ImageLocation>();
        cockpit.put(SpriteStatus.DEFAULT, new ImageLocation("cockpit", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 0, new SimpleSprite(0, 0, 8, 8, cockpit));

        Map<SpriteStatus, ImageLocation> engine = new HashMap<SpriteStatus, ImageLocation>();
        engine.put(SpriteStatus.DEFAULT, new ImageLocation("midParts",(int)(random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 1, new SimpleSprite(0, 1, 8, 8, engine));

        Map<SpriteStatus, ImageLocation> thruster = new HashMap<SpriteStatus, ImageLocation>();
        thruster.put(SpriteStatus.DEFAULT, new ImageLocation("bottomEngines", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(0, 2, new SimpleSprite(0, 2, 8, 8, thruster));

        Map<SpriteStatus, ImageLocation> gun = new HashMap<SpriteStatus, ImageLocation>();
        gun.put(SpriteStatus.DEFAULT, new ImageLocation("sideGun", (int) (random.nextInt() + System.currentTimeMillis()) % 4));
        addTile(1, 1, new SimpleSprite(-1, 1, 8, 8, gun));

        setPosition(300,300);
    }
}
