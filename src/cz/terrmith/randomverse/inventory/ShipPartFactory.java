package cz.terrmith.randomverse.inventory;

import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.factory.SpriteFactory;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.sprite.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ShieldPart;
import cz.terrmith.randomverse.sprite.ShipPart;
import cz.terrmith.randomverse.sprite.factory.MissileFactory;
import cz.terrmith.randomverse.sprite.factory.ProjectileFactory;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.util.*;

/**
 * Factory class that creates ship part instances
 */
public class ShipPartFactory {

    private SpriteCollection spriteCollection;
    private Damage.DamageType damageType;

	public enum Item{
		GUN_1, GUN_2, GUN_3, GUN_4,
		COCKPIT_1,COCKPIT_2,COCKPIT_3,COCKPIT_4,
		MID_1, MID_2, MID_3, MID_4,
		ENGINE_1, ENGINE_2, ENGINE_3, ENGINE_4,;

	}

    public ShipPartFactory(SpriteCollection spriteCollection, Damage.DamageType damageType) {
        this.spriteCollection = spriteCollection;
        this.damageType = damageType;
    }

    /**
     * Creates all ship part in their order and returns it as a collection
     * @return ship part collection
     */
    public Collection<ShipPart> createAll() {
        List<ShipPart> parts = new ArrayList<ShipPart>();

        for (int i = 0; i < 16; i++) {
            parts.add(create(i));
        }
        return parts;
    }

    /**
     * Creates a ship part for given index
     * @param i index of ship part that should be created
     * @return new shipPart instance
     */
    public ShipPart create(int i) {
        switch (i) {
            //SpriteCollection spriteCollection, int rateOfFire, Damage damage, ImageLocation imageLocation
            case 0:  return (ShipPart) createGun(new ImageLocation("sideGun", 0), 1, 2, 8, new ProjectileFactory(new Damage(1, damageType)));//return new SimpleGun(spriteCollection,8,new Damage(1, damageType),new ImageLocation("sideGun",0),1,2);
            case 1:  return (ShipPart) createGun(new ImageLocation("sideGun", 1), 1, 8, 4, new ProjectileFactory(new Damage(2, damageType)));//return new SimpleGun(spriteCollection,4,new Damage(2, damageType),new ImageLocation("sideGun",1),1,8);
            case 2:  return (ShipPart) createGun(new ImageLocation("sideGun", 3), 1, 6, 8, new ProjectileFactory(new Damage(4, damageType)));//return new SimpleGun(spriteCollection,8,new Damage(3, damageType),new ImageLocation("sideGun",2),1,6);
            case 3: return (ShipPart) createGun(new ImageLocation("sideGun", 2), 2, 4, 12, new MissileFactory(new Damage(3, damageType), this.spriteCollection));
            case 4: return createCockpit(new ImageLocation("cockpit",0),2, 2);
            case 5: return createCockpit(new ImageLocation("cockpit",1),3, 4);
            case 6: return createCockpit(new ImageLocation("cockpit",2),4, 6);
            case 7: return createCockpit(new ImageLocation("cockpit",3),1, 3);
            case 8: return createMidPart(new ImageLocation("midParts",0),-0.5, 2, 2);
            case 9: return createMidPart(new ImageLocation("midParts",1),-1, 3, 3);
            case 10: return createMidPart(new ImageLocation("midParts",2),-1.5, 4, 4);
            case 11: return createShieldPart(new ImageLocation("midParts", 3), -2, 5, 5);
            case 12: return createBottomEngine(new ImageLocation("bottomEngines",0), 4, 2, 2);
            case 13: return createBottomEngine(new ImageLocation("bottomEngines",1), 6, 1, 4);
            case 14: return createBottomEngine(new ImageLocation("bottomEngines",2), 8, 1, 6);
            case 15: return createBottomEngine(new ImageLocation("bottomEngines",3), 10, 1, 8);
        }

        return null;
    }

	private SimpleSprite createGun(ImageLocation imageLocation, int health, int price, int attackRate, SpriteFactory factory) {
        return new SimpleGun(spriteCollection, attackRate, imageLocation, health, price, factory);
	}

	public ShipPart createShipPart(ImageLocation image, Set<ExtensionPoint> extensions, int health, int price){
        Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
        imageForStatus.put(DefaultSpriteStatus.DEFAULT.name(), image);
	    ImageLocation damagedImage = new ImageLocation(image.getName() + "_damaged", image.getNumber());
	    imageForStatus.put(DefaultSpriteStatus.DAMAGED.name(), damagedImage);
	    return new ShipPart(health, imageForStatus, extensions, price);
    }

    public ShipPart createSprite(ImageLocation image, Set<ExtensionPoint> extensions, int health, int price){
        Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
        imageForStatus.put(DefaultSpriteStatus.DEFAULT.name(), image);
        ImageLocation damagedImage = new ImageLocation(image.getName() + "_damaged", image.getNumber());
        imageForStatus.put(DefaultSpriteStatus.DAMAGED.name(), damagedImage);
        return new ShipPart(health, imageForStatus, extensions, price);
    }

	public ShipPart createCockpit(ImageLocation image, int health, int price) {
		Set<ExtensionPoint> extensions = new HashSet<ExtensionPoint>();
		extensions.add(ExtensionPoint.BOTTOM);
		return createShipPart(image, extensions, health, price);
	}

	public ShipPart createMidPart(ImageLocation image, double speed, int health, int price) {
		Set<ExtensionPoint> extensions = new HashSet<ExtensionPoint>();
		extensions.add(ExtensionPoint.BOTTOM);
		extensions.add(ExtensionPoint.LEFT);
		extensions.add(ExtensionPoint.RIGHT);
		extensions.add(ExtensionPoint.TOP);
		ShipPart part = createShipPart(image, extensions, health, price);
		part.setSpeed(speed);
		return part;
	}

    public ShieldPart createShieldPart(ImageLocation image, double speed, int health, int price) {
        Set<ExtensionPoint> extensions = new HashSet<ExtensionPoint>();
        extensions.add(ExtensionPoint.BOTTOM);
        extensions.add(ExtensionPoint.LEFT);
        extensions.add(ExtensionPoint.RIGHT);
        extensions.add(ExtensionPoint.TOP);
        ShieldPart part = createShieldPart(image, extensions, health, price);
        part.setSpeed(speed);
        return part;
    }

	public ShieldPart createShieldPart(ImageLocation image, Set<ExtensionPoint> extensions, int health, int price){
		Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
		imageForStatus.put(DefaultSpriteStatus.DEFAULT.name(), image);
		ImageLocation damagedImage = new ImageLocation(image.getName() + "_damaged", image.getNumber());
		imageForStatus.put(DefaultSpriteStatus.DAMAGED.name(), damagedImage);
		return new ShieldPart(health, imageForStatus, extensions, price, spriteCollection);
	}

	public ShipPart createBottomEngine(ImageLocation image, double speed, int health, int price) {
		Set<ExtensionPoint> extensions = new HashSet<ExtensionPoint>();
		extensions.add(ExtensionPoint.TOP);
		ShipPart shipPart = createShipPart(image, extensions, health, price);
		shipPart.setSpeed(speed);
		return shipPart;
	}



}
