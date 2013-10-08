package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.input.UserCommand;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.creators.SimpleProjectileCreator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contains global constants
 */
public class Randomverse implements GameEngine {

    public static final String WINDOW_NAME = "Randomverse";
    public static final int STEP = 3;

    private final UserCommand command;
    private SpriteCollection spriteCollection;
    private Ship player;

    public Randomverse(UserCommand cmd){
        this.command = cmd;
        this.spriteCollection = new SpriteCollection();
        this.player = new Ship(300,300);
        spriteCollection.put(SpriteLayer.PLAYER, player);
	    spriteCollection.put(SpriteLayer.NPC, new Ship(100,100));
	    spriteCollection.put(SpriteLayer.NPC, new Ship(200,100));
	    spriteCollection.put(SpriteLayer.NPC, new Ship(300,100));
    }


    @Override
    public void update() {
        updateProjectiles();
	    updateNpcs();
        updatePlayer();
    }

	private void updateNpcs() {
		Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.NPC).iterator();
		while (iterator.hasNext()) {
			Sprite s = iterator.next();
			if (s.isActive()) {
				// update
			} else {
				iterator.remove();
			}
		}
	}


	private void updateProjectiles() {
		Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.PROJECTILE).iterator();
		while (iterator.hasNext()) {
            Sprite s = iterator.next();
			  if (s.isActive()) {
                  s.updateSprite();
				  if(s instanceof DamageDealer) {
					  DamageDealer d = (DamageDealer)s;
					  checkForCollision(d);
				  }
			  } else {
				  iterator.remove();
			  }
        }
    }

	private void checkForCollision(DamageDealer dmgDealer) {
		List<Destructible> collidingSprites = new ArrayList<Destructible>();
		for (Sprite s: getSpriteCollection().getSprites(SpriteLayer.NPC)) {
			if (s instanceof Destructible && dmgDealer.collidesWith(s)) {
			   collidingSprites.add((Destructible)s);
			}
		}
		dmgDealer.dealDamage(collidingSprites);
	}

	private void updatePlayer() {
        int dx = 0;
        int dy = 0;
        if (command.isUp()) {
            dy -= STEP;
        }
        if (command.isDown()) {
            dy += STEP;
        }
        if (command.isLeft()) {
           dx -= STEP;
        }
        if (command.isRight()) {
           dx += STEP;
        }

        if (command.isShoot()) {
            player.attack(new SimpleProjectileCreator(getSpriteCollection()));
        }

        player.setStep(dx, dy);
        player.updateSprite();

    }

    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }


}
