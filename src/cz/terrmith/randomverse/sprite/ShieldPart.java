package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.ability.AbilityGroup;
import cz.terrmith.randomverse.ability.Shield;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.properties.Ability;
import cz.terrmith.randomverse.core.sprite.properties.ProvidesAbility;

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
	public void useAbility(String group) {
		Ability a = abilities.get(group);
		if (a != null) {
			a.useAbility();
		}
	}

	@Override
	public Map<String, Ability> getAbilities() {
		return abilities;
	}
}
