package cz.terrmith.randomverse.core.sprite.properties;

import java.util.Map;

/**
 *  Object can provide abilities. Every ability belongs to different group.
 *  User can trigger ability of given group
 *
 * @author jiri.kus
 */
public interface ProvidesAbility {

	/**
	 * Group of ability
	 * @param group
	 */
	void useAbility(String group);

	/**
	 * Returns list of abilities which are provided by this object
 	 * @return map [Group, ability name]
	 */
	Map<String, Ability> getAbilities();
}
