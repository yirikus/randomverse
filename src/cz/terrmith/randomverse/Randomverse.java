package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.input.UserCommand;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;
import cz.terrmith.randomverse.world.LevelOne;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Contains global constants
 */
public class Randomverse implements GameEngine {

    public static final String WINDOW_NAME = "Randomverse";
    public static final int STEP = 6;

    private final UserCommand command;
    private final LevelOne world;
    private SpriteCollection spriteCollection;
    private Ship player;
    private Random random = new Random();

    private int createEnemies = 3;
    private int killedEnemies = 0;

    public Randomverse(UserCommand cmd){
        this.command = cmd;
        this.spriteCollection = new SpriteCollection();
        createPlayer();
        spriteCollection.put(SpriteLayer.PLAYER, player);
        this.world = new LevelOne(this.spriteCollection);

    }

    private void createPlayer() {
        this.player = new Ship(300,300);
        player.addTile(-1, 1, new SimpleGun(-1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, spriteCollection));
        SimpleGun flippedGun = new SimpleGun(1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, spriteCollection);
        flippedGun.flipHorizontal();
        player.addTile(1, 1, flippedGun);
    }

    @Override
    public void update() {
        updateProjectiles();
	    updateNpcs();
        updatePlayer();
        updateWorld();
    }

    private void updateWorld() {
        world.update();
    }

    private void updateNpcs() {
		Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.NPC).iterator();
		while (iterator.hasNext()) {
			Sprite s = iterator.next();
			if (s.isActive()) {
				s.updateSprite();
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
				System.out.println("collision with: "+ s.getXPosn() + "," + s.getYPosn());
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
            player.attack();
        }

        player.setStep(dx, dy);
        player.updateSprite();

    }

    @Override
    public SpriteCollection getSpriteCollection() {
        return this.spriteCollection;
    }


}
