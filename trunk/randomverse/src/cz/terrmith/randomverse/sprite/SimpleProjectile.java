package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.abilitiy.CanAttack;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.SpriteCreator;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 8.10.13
 * Time: 0:06
 * To change this template use File | Settings | File Templates.
 */
public class SimpleProjectile extends SimpleSprite implements DamageDealer {

    private static final Map<SpriteStatus, ImageLocation> imageForStatus;
    static {
        Map<SpriteStatus, ImageLocation> aMap = new HashMap<SpriteStatus, ImageLocation>();
        aMap.put(SpriteStatus.DEFAULT, new ImageLocation("shots",0));
        imageForStatus = Collections.unmodifiableMap(aMap);
    }

    /**
     * Creates sprite wihh initial position x, y and size w, h
     *
     * @param x              x position
     * @param y              y position
     */
    public SimpleProjectile(int x, int y) {
        super(x, y, 11, 12, null);
        setImageForStatus(this.imageForStatus);
    }

    @Override
    public int getImpactDamage() {
        return 10;
    }
}
