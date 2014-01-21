package cz.terrmith.randomverse.ability;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.properties.Ability;

/**
 * @author jiri.kus
 */
public class Shield extends Ability {


	/**
	 * Creates an instance
	 */
	public Shield() {
		super(AbilityGroup.ACTION_2.name());
	}

	/**
	 * Switches shield on or off
	 */
	@Override
	public void useAbility() {
		setActive(!isActive());
		if (isActive()) {
			//update parent [x, y]

		}
	}

	@Override
	public void updateSprite() {
		super.updateSprite();
	}
}
