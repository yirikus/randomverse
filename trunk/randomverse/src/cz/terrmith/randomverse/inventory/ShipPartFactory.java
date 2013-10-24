package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 24.10.13
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
public class ShipPartFactory {

    private SpriteCollection spriteCollection;
    private Damage.DamageType damageType;

    public ShipPartFactory(SpriteCollection spriteCollection, Damage.DamageType damageType) {
        this.spriteCollection = spriteCollection;
        this.damageType = damageType;
    }

    /**
     * Creates all ship part in their order and returns it as a collection
     * @return
     */
    public Collection<? extends SimpleSprite> createAll() {
        List parts = new ArrayList<SimpleSprite>();

        for (int i = 0; i < 16; i++) {
            parts.add(create(i));
        }
        return parts;
    }

    /**
     * Creates a ship part for given index
     * @param i
     * @return
     */
    public SimpleSprite create(int i) {
        switch (i) {
            //SpriteCollection spriteCollection, int rateOfFire, Damage damage, ImageLocation imageLocation
            case 0: return new SimpleGun(spriteCollection,8,new Damage(1, damageType),new ImageLocation("sideGun",0));
            case 1: return new SimpleGun(spriteCollection,8,new Damage(2, damageType),new ImageLocation("sideGun",1));
            case 2: return new SimpleGun(spriteCollection,7,new Damage(2, damageType),new ImageLocation("sideGun",2));
            case 3: return new SimpleGun(spriteCollection,6,new Damage(1, damageType),new ImageLocation("sideGun",3));
            case 4: return createSprite(new ImageLocation("cockpit",0));
            case 5: return createSprite(new ImageLocation("cockpit",1));
            case 6: return createSprite(new ImageLocation("cockpit",2));
            case 7: return createSprite(new ImageLocation("cockpit",3));
            case 8: return createSprite(new ImageLocation("midParts",0));
            case 9: return createSprite(new ImageLocation("midParts",1));
            case 10: return createSprite(new ImageLocation("midParts",2));
            case 11: return createSprite(new ImageLocation("midParts",3));
            case 12: return createSprite(new ImageLocation("bottomEngines",0));
            case 13: return createSprite(new ImageLocation("bottomEngines",1));
            case 14: return createSprite(new ImageLocation("bottomEngines",2));
            case 15: return createSprite(new ImageLocation("bottomEngines",3));
        }

        return null;
    }

    public SimpleSprite createSprite(ImageLocation image){
        Map<SpriteStatus, ImageLocation> cockpit0 = new HashMap<SpriteStatus, ImageLocation>();
        cockpit0.put(SpriteStatus.DEFAULT, image);
        return new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, cockpit0);
    }
}
