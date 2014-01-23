package cz.terrmith.randomverse.core.sprite.properties;

import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.Tile;

/**
 *
 * @author jiri.kus
 */
public abstract class Ability extends SimpleSprite {

	private String group;

	public Ability(String group){
		super(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null);

		this.group = group;
	}

	public String getGroup() {
		return group;
	}

    /**
     * Triggers ability on/off
     * @param parent top parent sprite
     */
	public abstract void useAbility(Sprite parent);
}
