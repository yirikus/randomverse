package cz.terrmith.randomverse.game.states;

import cz.terrmith.randomverse.Randomverse;
import cz.terrmith.randomverse.core.dialog.DialogCallback;
import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.input.Command;
import cz.terrmith.randomverse.core.sprite.DefaultSpriteStatus;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.properties.*;
import cz.terrmith.randomverse.core.sprite.collision.Collision;
import cz.terrmith.randomverse.core.state.State;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.game.StateName;
import cz.terrmith.randomverse.sprite.ShipPart;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Game state - this is where the SHOOTING HAPPENS, WHEEE
 */
public class GameState implements State {
    // reference to command
    private final Command command;
    private final Randomverse stateMachine;

    //represent current level
    private World world;

    public GameState(Randomverse stateMachine) {
        this.command = stateMachine.getCommand();
        this.stateMachine = stateMachine;
    }

    @Override
    public String getName() {
        return StateName.GAME.name();
    }

    @Override
    public void update() {
        if (Command.State.PRESSED.equals(command.getPrevious())
                || Command.State.RELEASED_PRESSED.equals(command.getPrevious())) {
            stateMachine.setCurrentState(StateName.MAIN_MENU.name());
            command.clear();
        } else if (!stateMachine.getPlayer().getSprite().isActive()) {
            // add modal game over dialog
            DialogCallback callback = new DialogCallback() {
                @Override
                public void onClose() {
                    stateMachine.setCurrentState(StateName.MAIN_MENU.name());
                }
            };
            cz.terrmith.randomverse.core.dialog.Dialog dialog
                    = new cz.terrmith.randomverse.core.dialog.Dialog("GAME OVER",200,200,400,200,callback);
            stateMachine.showDialog(dialog);
            command.clear();
        } else  if (Command.State.PRESSED.equals(command.getInventory())
                || Command.State.RELEASED_PRESSED.equals(command.getInventory())) {
            stateMachine.setCurrentState(StateName.INVENTORY.name());
            command.setInventory(false);
        } else {
            updateProjectiles();
            updateNpcs();
            updateItems();
            stateMachine.getPlayer().update();
            updateWorld();
        }
    }

    private void updateItems() {
        for (Sprite s : stateMachine.getSpriteCollection().getSprites(SpriteLayer.ITEM)) {
            s.updateSprite();
            java.util.List<Sprite> collisionList = s.collidesWith(stateMachine.getPlayer().getSprite());
            if (!collisionList.isEmpty()) {
                Loot loot = ((LootSprite) s).drop();
                if (loot.isPowerup()) {
                    if (collisionList.get(0) instanceof ShipPart) {
                        ((ShipPart)collisionList.get(0)).addPowerup(loot);
                    }
                } else {
                    stateMachine.getPlayer().addLoot(loot);
                }
            }
        }

        stateMachine.getSpriteCollection().removeInactive(SpriteLayer.ITEM);
    }

    private void updateWorld() {
        world.update();
        if (world.completed()) {
            DialogCallback callback = new DialogCallback() {
                @Override
                public void onClose() {
                    stateMachine.setCurrentState(StateName.MAP.name());
                }
            };
            cz.terrmith.randomverse.core.dialog.Dialog dialog
                    = new cz.terrmith.randomverse.core.dialog.Dialog("YOU WIN, BITCH! SCORE: " +
                                                                     stateMachine.getPlayer().getMoney(),
                                                                     200, 200, 400, 200, callback);
            stateMachine.showDialog(dialog);
        }
    }

    private void updateNpcs() {
        Iterator<Sprite> iterator = stateMachine.getSpriteCollection().getSprites(SpriteLayer.NPC).iterator();
        Boundary b = stateMachine.getSpriteCollection().getBoundary(SpriteLayer.NPC);
        while (iterator.hasNext()) {
            Sprite npcSprite = iterator.next();

            // set sprites that are not within as inactive
            Position spritePos = new Position(npcSprite.getXPosn(), npcSprite.getYPosn());
            if (!b.withinBoundary(spritePos)) {
                npcSprite.setActive(false);
            }

            // test collision with player
            List<Collision> collisions = stateMachine.getPlayer().getSprite().findCollisionCollections(npcSprite);
            //deal damage
            for (Collision c : collisions) {

                if (c.getSprite() instanceof Solid) {
                    Solid solidPlayerPart = (Solid) c.getSprite();
                    for (Sprite s : c.getCollidingSprites()) {
                        if (s instanceof Solid) {
                            //deal damage
                            if (s instanceof Destructible) {
                                ((Destructible) s).reduceHealth(solidPlayerPart.getImpactDamage());
                            }

                            //obtain damage
                            if (c.getSprite() instanceof Destructible) {
                                ((Destructible) c.getSprite()).reduceHealth(((Solid) s).getImpactDamage());
                            }
                        }
                    }
                }
            }

            // update active, add loot
            if (npcSprite.isActive()) {
                npcSprite.updateSprite();
            } else {
                // if sprite was killed add loot to sprite collection
                if (DefaultSpriteStatus.DEAD.name().equals(npcSprite.getStatus()) && npcSprite instanceof Lootable) {
                    LootSprite loot = ((Lootable) npcSprite).getLootSprite();
                    if (loot != null) {
                        stateMachine.getSpriteCollection().put(SpriteLayer.ITEM, loot);
                    }
                }
            }
        }
        stateMachine.getSpriteCollection().removeInactive(SpriteLayer.NPC);
    }


    private void updateProjectiles() {
        Collection<Sprite> sprites = stateMachine.getSpriteCollection().getSprites(SpriteLayer.PROJECTILE);
        Boundary b = stateMachine.getSpriteCollection().getBoundary(SpriteLayer.PROJECTILE);
        //update
        // NOTE: update and remove must be separated (concurrency)
        for (Sprite s : sprites) {
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
                if (s instanceof Solid) {
                    dealImpactDamage((Solid) s);
                }
            }
        }
        //remove
        stateMachine.getSpriteCollection().removeInactive(SpriteLayer.PROJECTILE);
    }

    /**
     * Finds collision of solid sprite and deals damage to destructibles
     * @param solid solid sprite
     */
    private void dealImpactDamage(Solid solid) {
        List<Destructible> npcCollisions = stateMachine.getCollisionTester().findCollisions(solid, SpriteLayer.NPC);
        for (Destructible d : npcCollisions) {
            d.reduceHealth(solid.getImpactDamage());
            if (d instanceof Solid && solid instanceof Destructible) {
                ((Destructible) solid).reduceHealth(((Solid) d).getImpactDamage());
            }
        }
        List<Destructible> playerCollisions = stateMachine.getCollisionTester().findCollisions(solid, SpriteLayer.PLAYER);
        for (Destructible d : playerCollisions) {
            d.reduceHealth(solid.getImpactDamage());
            if (d instanceof Solid && solid instanceof Destructible) {
                ((Destructible) solid).reduceHealth(((Solid) d).getImpactDamage());
            }
        }
    }

    /**
     * Checks collisions for given damage dealer and deals damage
     *
     * @param dmgDealer damage dealer
     */
    private void dealDamage(DamageDealer dmgDealer) {
        List<Destructible> collidingSprites = new ArrayList<Destructible>();
        // non player collision
        if (Damage.DamageType.NPC.equals(dmgDealer.getDamage().getType())
                || Damage.DamageType.BOTH.equals(dmgDealer.getDamage().getType())) {

            collidingSprites = stateMachine.getCollisionTester().findCollisions(dmgDealer, SpriteLayer.NPC);
	        dmgDealer.dealDamage(collidingSprites);
        }
	    collidingSprites.clear();
	    // player collision
	    if (Damage.DamageType.PLAYER.equals(dmgDealer.getDamage().getType())
	      || Damage.DamageType.BOTH.equals(dmgDealer.getDamage().getType())) {
            collidingSprites = stateMachine.getCollisionTester().findCollisions(dmgDealer, SpriteLayer.SHIELD);
            if (collidingSprites.isEmpty()) {
                collidingSprites = stateMachine.getCollisionTester().findCollisions(dmgDealer, SpriteLayer.PLAYER);
            }
		    dmgDealer.dealDamage(collidingSprites);
        }

    }

    @Override
    public void draw(Graphics2D g2, ImageLoader iml) {
        Font font = new Font("system",Font.BOLD,20);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(stateMachine.getPlayer().getCurrentHealth() + "/" + stateMachine.getPlayer().getTotalHealth(), 10, stateMachine.getScreenHeight() -50);
        g2.drawString("$" + stateMachine.getPlayer().getMoney(), 10, stateMachine.getScreenHeight() -20);
    }

    @Override
    public void activate(State oldState) {
        if (StateName.INVENTORY.name().equals(oldState.getName())) {
            world.setPaused(false);
        } else {
            //Create new world
            stateMachine.getSpriteCollection().clear();
            stateMachine.getSpriteCollection().put(SpriteLayer.PLAYER, stateMachine.getPlayer().getSprite());
            //obtain level from map
	        this.world = stateMachine.getMap().createLevel(this.stateMachine.getSpriteCollection());
            command.clear();
            DialogCallback callback = new DialogCallback() {
                @Override
                public void onClose() {
                }
            };
            cz.terrmith.randomverse.core.dialog.Dialog dialog
                    = new cz.terrmith.randomverse.core.dialog.Dialog("GAME START",200,200,400,200,callback);
            stateMachine.showDialog(dialog);
        }
    }

    @Override
    public void deactivate(State newState) {
        world.setPaused(true);
    }
}
