package cz.terrmith.randomverse.core.sprite.properties;

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
				List<Sprite> collidingSprites = s.collidesWith(dmgDealer);
				for (Sprite cs : collidingSprites ) {
					if (cs instanceof Destructible) {
						collidingDestructibles.add((Destructible)cs);
					}
				}
			}
		}
		return collidingDestructibles;
	}

    /**
     * Returns all sprites from given layer that collide with given damage dealer
     *
     * @param solid   solid sprite
     * @param spriteLayer layer of sprites
     * @return
     */
    public List<Destructible> findCollisions(Solid solid, SpriteLayer spriteLayer) {
        List<Destructible> collidingDestructibles = new ArrayList<Destructible>();
        for (Sprite s : spriteCollection.getSprites(spriteLayer)) {
            if (s instanceof Destructible) {
                // solid might be multisprite
                List<Sprite> collidingSprites = s.collidesWith(solid);
                for (Sprite cs : collidingSprites ) {
                    if (cs instanceof Destructible) {
                        collidingDestructibles.add((Destructible)cs);
                    }
                }
            }
        }
        return collidingDestructibles;
    }
}
