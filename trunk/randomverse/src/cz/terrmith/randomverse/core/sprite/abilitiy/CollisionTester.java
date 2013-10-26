package cz.terrmith.randomverse.core.sprite.abilitiy;

import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Collision testing
 * @author jiri.kus
 */
public class CollisionTester {

	private SpriteCollection spriteCollection;

	public CollisionTester(SpriteCollection spriteCollection) {
		this.spriteCollection = spriteCollection;
	}

	/**
	 * Returns all sprites from given layer that collide with given damage dealer
	 *
	 * @param dmgDealer   damage dealer
	 * @param spriteLayer layer of sprites
	 * @return
	 */
	public List<Destructible> findCollisions(DamageDealer dmgDealer, SpriteLayer spriteLayer) {
		List<Destructible> collidingDestructibles = new ArrayList<Destructible>();
		for (Sprite s : spriteCollection.getSprites(spriteLayer)) {
			if (s instanceof Destructible) {
				List<Sprite> collidingDprites = s.collidesWith(dmgDealer);
				for (Sprite cs : collidingDprites ) {
					if (cs instanceof Destructible) {
						collidingDestructibles.add((Destructible)cs);
					}
				}
			}
		}
		return collidingDestructibles;
	}
}
