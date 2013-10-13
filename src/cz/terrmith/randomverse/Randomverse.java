package cz.terrmith.randomverse;

import cz.terrmith.randomverse.core.GameEngine;
import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.input.UserCommand;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.abilitiy.Damage;
import cz.terrmith.randomverse.core.sprite.abilitiy.DamageDealer;
import cz.terrmith.randomverse.core.sprite.abilitiy.Destructible;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;
import cz.terrmith.randomverse.world.LevelOne;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contains global constants
 */
public class Randomverse implements GameEngine {

    public static final String WINDOW_NAME = "Randomverse";
    public static final int STEP = 6;

    private final UserCommand command;
    private int screenWidth;
    private int screenHeight;
    private final LevelOne world;
    private SpriteCollection spriteCollection;
    private Ship player;

    public Randomverse(UserCommand cmd, int screenWidth, int screenHeight) {
        this.command = cmd;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Boundary screenBoundary = new Boundary(0, screenWidth, 0, screenHeight);
        Boundary extendedBoundary = new Boundary(-screenWidth, 2 * screenWidth, -screenHeight, 2 * screenHeight);
        this.spriteCollection = new SpriteCollection(screenBoundary, extendedBoundary);
        createPlayer();
        spriteCollection.put(SpriteLayer.PLAYER, player);
        this.world = new LevelOne(this.spriteCollection);

    }

    private void createPlayer() {
        this.player = new Ship(300, 300);
        player.addTile(-1, 1, new SimpleGun(-1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, spriteCollection, Damage.DamageType.NPC));
        SimpleGun flippedGun = new SimpleGun(1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, spriteCollection, Damage.DamageType.NPC);
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
        Boundary b = getSpriteCollection().getBoundary(SpriteLayer.NPC);
        while (iterator.hasNext()) {
            Sprite s = iterator.next();

            // set sprites that are not within as inactive
            Position spritePos = new Position(s.getXPosn(), s.getYPosn());
            if (!b.withinBoundary(spritePos)) {
                s.setActive(false);
            }

            // delete inactive sprites
            if (s.isActive()) {
                s.updateSprite();
            } else {
                iterator.remove();
            }
        }
    }


    private void updateProjectiles() {
        Iterator<Sprite> iterator = getSpriteCollection().getSprites(SpriteLayer.PROJECTILE).iterator();
        Boundary b = getSpriteCollection().getBoundary(SpriteLayer.PROJECTILE);
        while (iterator.hasNext()) {
            Sprite s = iterator.next();

            // set sprites that are not within as inactive
            Position spritePos = new Position(s.getXPosn(), s.getYPosn());
            if (!b.withinBoundary(spritePos)) {
                s.setActive(false);
            }

            if (s.isActive()) {
                s.updateSprite();
                if (s instanceof DamageDealer) {
                    DamageDealer d = (DamageDealer) s;
                    dealDamage(d);
                }
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * Checks collisions for given damage dealer and deals damage
     *
     * @param dmgDealer damage dealer
     */
    private void dealDamage(DamageDealer dmgDealer) {
        List<Destructible> collidingSprites;
        // non player collision
        if (Damage.DamageType.NPC.equals(dmgDealer.getDamage().getType())
                || Damage.DamageType.BOTH.equals(dmgDealer.getDamage().getType())) {

            collidingSprites = findCollisions(dmgDealer, SpriteLayer.NPC);
            // player collision
        } else {
            collidingSprites = findCollisions(dmgDealer, SpriteLayer.PLAYER);
        }
        dmgDealer.dealDamage(collidingSprites);
    }

    /**
     * Returns all sprites from given layer that collide with given damage dealer
     *
     * @param dmgDealer   damage dealer
     * @param spriteLayer layer of sprites
     * @return
     */
    private List<Destructible> findCollisions(DamageDealer dmgDealer, SpriteLayer spriteLayer) {
        List<Destructible> collidingSprites = new ArrayList<Destructible>();
        for (Sprite s : getSpriteCollection().getSprites(spriteLayer)) {
            if (s instanceof Destructible && dmgDealer.collidesWith(s)) {
                System.out.println("collision with: " + s.getXPosn() + "," + s.getYPosn());
                collidingSprites.add((Destructible) s);
            }
        }
        return collidingSprites;
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

    @Override
    public void drawGUI(Graphics2D g2) {
        Font font = new Font("system",Font.BOLD,12);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(player.getCurrentHealth() + "/" + player.getTotalHealth(), 10, screenHeight -50);
    }


}
