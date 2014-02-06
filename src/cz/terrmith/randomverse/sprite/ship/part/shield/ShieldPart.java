package cz.terrmith.randomverse.sprite.ship.part.shield;

import cz.terrmith.randomverse.ability.AbilityGroup;
import cz.terrmith.randomverse.ability.Shield;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.properties.Ability;
import cz.terrmith.randomverse.core.sprite.properties.ProvidesAbility;
import cz.terrmith.randomverse.sprite.ship.ExtensionPoint;
import cz.terrmith.randomverse.sprite.ship.part.ShipPart;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiri.kus
 */
public class ShieldPart extends ShipPart implements ProvidesAbility{

	private static final Map<String, Ability> abilities;
	static {
		Map<String, Ability> aMap = new HashMap<String, Ability>();
		aMap.put(AbilityGroup.ACTION_2.name(), new Shield());
		abilities = Collections.unmodifiableMap(aMap);
	}

	private final SpriteCollection spc;


	public ShieldPart(ShieldPart sprite) {
		super(sprite);
		spc = sprite.spc;
	}

	public ShieldPart(int totalHealth,
	                  Map<String, ImageLocation> imageForStatus,
	                  Set<ExtensionPoint> extensions,
	                  int price,
	                  SpriteCollection spriteCollection) {
		super(totalHealth, imageForStatus, extensions, price);
		this.spc = spriteCollection;
	}

	@Override
	public void updateSprite() {
		super.updateSprite();
		if (getCurrentHealth() < 1) {
			for (Ability a : getAbilities().values()) {
				if (a.isActive()) {
					setActive(false);
					spc.remove(SpriteLayer.SHIELD, a);
				}
			}
		} else {
			for (Ability a : getAbilities().values()) {
				a.updateSprite();
			}
		}

	}

	@Override
	public void useAbility(String group, Sprite parent) {
		Ability a = abilities.get(group);
		System.out.println("ShieldPart: " + a);
		if (a != null) {
            if (!a.isActive()) {
                spc.put(SpriteLayer.SHIELD, a);
            } else {
                spc.remove(SpriteLayer.SHIELD, a);
            }
            a.useAbility(parent);
		}
	}

	@Override
	public Map<String, Ability> getAbilities() {
		return abilities;
	}

    @Override
    public Sprite copy() {
        return new ShieldPart(this);
    }

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        super.drawSprite(g, ims);
        Shield s = (Shield)abilities.get(AbilityGroup.ACTION_2.name());
        g.setColor(Color.GREEN);
        g.drawString(String.valueOf(s.getCurrentHealth()), (int)s.getXPosn(), (int)s.getYPosn());
    }
}
