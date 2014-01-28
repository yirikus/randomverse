package cz.terrmith.randomverse.sprite.enemy.debris;

import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.NHood4;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.sprite.properties.Damage;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
import cz.terrmith.randomverse.core.sprite.properties.LootSprite;
import cz.terrmith.randomverse.core.sprite.properties.Lootable;
import cz.terrmith.randomverse.core.sprite.properties.Solid;
import cz.terrmith.randomverse.loot.LootFactory;
import cz.terrmith.randomverse.sprite.projectile.Explosion;
import cz.terrmith.randomverse.sprite.projectile.Projectile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Part of an asteroid
 */
public class DebrisPart extends SimpleSprite implements Destructible, Solid {

    private static final int MAX_HEALTH = 3;
    private int currentHealth = MAX_HEALTH;
    private int maxHealth = MAX_HEALTH;
    private Random random = new Random();

    public enum DebrisPartType {REGULAR, EXPLODING, CARGO, CLUSTER, GUN, HARD, REFLECT}
    private DebrisPartType type;
    public enum DebrisPartStatus {DEFAULT, DEAD, NHOOD3, NHOOD2, NHOOD1, //statuses for regular type
                                  EXPLODING, CARGO, CLUSTER, GUN, HARD, REFLECT, HARD_DAMAGED}
    private Debris parent;
    /**
     * DebrisParts surrounding this debris part. usually not possible to set in constructor
     */
    private NHood4<DebrisPart> nhood = null;

    /**
     * Creates new debris part (part of debris multisprite)
     * @param x screen position
     * @param y screen position
     * @param parent reference to a parent
     */
    public DebrisPart(double x, double y, Debris parent, DebrisPartType type) {
        super(x, y, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null);
        Map<String, ImageLocation> imageForStatus = new HashMap<String, ImageLocation>();
        imageForStatus.put(DebrisPartStatus.DEFAULT.name(), new ImageLocation("debris", 0));
        imageForStatus.put(DebrisPartStatus.NHOOD2.name(), new ImageLocation("debris", 1));
        imageForStatus.put(DebrisPartStatus.NHOOD3.name(), new ImageLocation("debris", 2));
        imageForStatus.put(DebrisPartStatus.CARGO.name(), new ImageLocation("debrisParts", 0));
        imageForStatus.put(DebrisPartStatus.GUN.name(), new ImageLocation("debrisParts", 1));
        imageForStatus.put(DebrisPartStatus.EXPLODING.name(), new ImageLocation("debrisParts", 2));
        imageForStatus.put(DebrisPartStatus.CLUSTER.name(), new ImageLocation("debrisParts", 3));
        imageForStatus.put(DebrisPartStatus.REFLECT.name(), new ImageLocation("debrisParts", 4));
        imageForStatus.put(DebrisPartStatus.HARD.name(), new ImageLocation("debrisParts", 5));
        imageForStatus.put(DebrisPartStatus.HARD_DAMAGED.name(), new ImageLocation("debrisParts", 6));
        setImageForStatus(imageForStatus);
        this.type = type;
        this.parent = parent;

        if (type.equals(DebrisPartType.HARD)) {
            setStatus(DebrisPartStatus.HARD.name());
            this.maxHealth = maxHealth * 4;
            this.currentHealth = maxHealth;
        } else if (type.equals(DebrisPartType.CARGO)) {
            setStatus(DebrisPartStatus.CARGO.name());
        } else if (type.equals(DebrisPartType.GUN)) {
            setStatus(DebrisPartStatus.GUN.name());
        } else if (type.equals(DebrisPartType.EXPLODING)) {
            setStatus(DebrisPartStatus.EXPLODING.name());
        } else if (type.equals(DebrisPartType.CLUSTER)) {
            setStatus(DebrisPartStatus.CLUSTER.name());
        } else if (type.equals(DebrisPartType.REFLECT)) {
            setStatus(DebrisPartStatus.REFLECT.name());
        }
    }

    @Override
    public int getTotalHealth() {
        return maxHealth;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public void reduceHealth(int amount) {
        currentHealth -= amount;
        if (this.currentHealth < 1) {
            this.setStatus(DebrisPartStatus.DEAD.name());
            parent.updateSpriteStatus();
            if (type.equals(DebrisPartType.EXPLODING)) {
                // add explosion to projectiles
                Explosion explosion = new Explosion(getXPosn(), getYPosn(), new Damage(3, Damage.DamageType.BOTH));
                parent.getSpriteCollection().put(SpriteLayer.PROJECTILE, explosion);
            } else if (type.equals(DebrisPartType.CLUSTER)) {

                createClusterProjectile(0);
                createClusterProjectile(Math.PI / 4);
                createClusterProjectile(Math.PI / 2);
                createClusterProjectile((3 * Math.PI) / 4);

                createClusterProjectile(Math.PI);
                createClusterProjectile((5 * Math.PI) / 4);
                createClusterProjectile((3 * Math.PI) / 2);
                createClusterProjectile((7 * Math.PI) / 4);
            } else if (type.equals(DebrisPartType.CARGO)) {
                LootSprite lootSprite = new LootSprite(0, 0, 10, 10, null, LootFactory.randomLoot(1));
                lootSprite.setPosition(getXPosn() + getWidth() / 2, getYPosn() + getHeight() / 2);
                parent.getSpriteCollection().put(SpriteLayer.ITEM, lootSprite);
            }
        } else if (this.currentHealth < maxHealth/2 && this.type.equals(DebrisPartType.HARD)) {
            this.setStatus(DebrisPartStatus.HARD_DAMAGED.name());
        }
    }

    /**
     * Creates
     * @param startingAngle
     */
    private void createClusterProjectile(double startingAngle){
        Damage damage = new Damage(1, Damage.DamageType.BOTH);
        Position center = new Position(0, 0);

        Position pointOnCircle = Position.pointOnCircle(center, 2, (Math.PI / 4) * random.nextDouble() + startingAngle, true);
        Projectile projectile = new Projectile(center.getX(), center.getY(), damage);
        projectile.setStep(pointOnCircle.getX(), pointOnCircle.getY());
        projectile.setPosition(this.getXPosn() + getWidth() / 2, this.getYPosn() + getHeight() / 2);

        System.out.println("Created cluster projectile " + projectile.getXStep() + ", " + projectile.getYStep());

        parent.getSpriteCollection().put(SpriteLayer.PROJECTILE, projectile);
    }

    /**
     * Updates sprite status if necessary according to its neighbouthood
     * This method is called when an action iccurs that might trigger sprite status update.
     * @param tileLocation since debris part does not know its location in a tile set, parent must provide its location
     */
    public void updateSpriteStatus(GridLocation tileLocation) {
        if (this.getStatus().equals(DebrisPartStatus.DEAD.name()) || !type.equals(DebrisPartType.REGULAR)) {
            return;
        }
        // if neighborhood is not set, set it
        if (this.nhood == null) {
            List<Tile> tiles = parent.getTiles();
            Tile top = Tile.findTile(tiles, tileLocation.getX(), tileLocation.getY() - 1);
            Tile bottom = Tile.findTile(tiles, tileLocation.getX(), tileLocation.getY() + 1);
            Tile left = Tile.findTile(tiles, tileLocation.getX() - 1, tileLocation.getY());
            Tile right = Tile.findTile(tiles, tileLocation.getX() + 1, tileLocation.getY());

            nhood = new NHood4<DebrisPart>(top != null ? (DebrisPart) top.getSprite() : null,
                                           left != null ? (DebrisPart) left.getSprite() : null,
                                           right != null ? (DebrisPart) right.getSprite() : null,
                                           bottom != null ? (DebrisPart) bottom.getSprite() : null);
        }

        boolean top = !isDead(nhood.getTop());
        boolean bottom = !isDead(nhood.getBottom());
        boolean right = !isDead(nhood.getRight());
        boolean left = !isDead(nhood.getLeft());
        //set status according to neighborhood

        if (top && right && !bottom && !left) {
            setStatus(DebrisPartStatus.NHOOD2.name());
            //deflip
            setXFlip(true);
            setYFlip(true);
        } else if (top && !right && !bottom && left) {
            setStatus(DebrisPartStatus.NHOOD2.name());
            //flip
            setXFlip(false);
            setYFlip(true);
        } else if (!top && right && bottom && !left) {
            setStatus(DebrisPartStatus.NHOOD2.name());
            //flip
            setXFlip(true);
            setYFlip(false);
        } else if (!top && !right && bottom && left) {
            setStatus(DebrisPartStatus.NHOOD2.name());
            //flip
            setXFlip(false);
            setYFlip(false);
        } else if (top && !right && !bottom && !left) {
            setStatus(DebrisPartStatus.NHOOD1.name());
            //flip
            setYFlip(true);
        } else if (!top && !right && bottom && !left) {
            setStatus(DebrisPartStatus.NHOOD1.name());
            //flip
            setYFlip(false);
        } else if (!top && right && bottom && left) {
            setStatus(DebrisPartStatus.NHOOD3.name());
            //flip
            setYFlip(false);
        } else if (top && right && !bottom && left) {
            setStatus(DebrisPartStatus.NHOOD3.name());
            //flip
            setYFlip(true);
        } else {
            setStatus(DebrisPartStatus.DEFAULT.name());
        }

    }

    /**
     * Returns true if given debris part is null or DEAD
     * @param part debris part
     * @return true if null or DEAD
     */
    private boolean isDead(DebrisPart part) {
        return part == null || part.getStatus().equals(DebrisPartStatus.DEAD.name());
    }

	@Override
	public int getImpactDamage() {
		return 1;
	}
}
