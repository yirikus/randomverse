package cz.terrmith.randomverse.core.sprite.collision;

import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.Collections;
import java.util.List;

/**
 * DTO that holds Sprite and all sprites it collides with
 *
 * @author jiri.kus
 */
public class Collision {

	private Sprite sprite;
	private List<Sprite> collidingSprites;

	public Collision(Sprite sprite, List<Sprite> collidingSprites) {
		this.collidingSprites = collidingSprites;
		this.sprite = sprite;
	}

	public List<Sprite> getCollidingSprites() {
		return Collections.unmodifiableList(collidingSprites);
	}

	public Sprite getSprite() {
		return sprite;
	}
}
