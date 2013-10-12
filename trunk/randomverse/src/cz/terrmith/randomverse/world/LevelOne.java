package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.attack.RandomAttackPattern;
import cz.terrmith.randomverse.core.ai.movement.TopDownMovement;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.sprite.Tile;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.Ship;
import cz.terrmith.randomverse.sprite.gun.SimpleGun;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 12.10.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class LevelOne extends World {
    private Random random = new Random();

    public LevelOne(SpriteCollection spriteCollection) {
        super(spriteCollection,3);

    }

    private Sprite createEnemy(int x, int y) {
        ArtificialIntelligence ai = new ArtificialIntelligence(new TopDownMovement(),new RandomAttackPattern(64));
        Ship enemy = new Ship(x,y, ai);
        if (random.nextBoolean()) {
            enemy.addTile(-1, 1, new SimpleGun(-1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, getSpriteCollection()));
        }
        if (random.nextBoolean()) {
            SimpleGun flippedGun = new SimpleGun(1, 1, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, getSpriteCollection());
            flippedGun.flipHorizontal();
            enemy.addTile(1, 1, flippedGun);
        }
        enemy.flipVertical();
        return enemy;
    }

    @Override
    protected void createSprites() {
        if (getUpdateCount() < 5) {
            createBoxFormation(random.nextInt(600),random.nextInt(3) + 1,random.nextInt(3)+ 1);
        } else if (getUpdateCount() < 10) {
            createBoxFormation(random.nextInt(500),random.nextInt(4)+ 1,random.nextInt(4)+ 1);
        } else if (getUpdateCount() < 15) {
        createBoxFormation(random.nextInt(400),random.nextInt(5)+ 1,random.nextInt(5)+ 1);
        } else if (getUpdateCount() < 15) {
            createBoxFormation(random.nextInt(300),random.nextInt(6)+ 1,random.nextInt(6)+ 1);
        } else if (getUpdateCount() < 15) {
            createBoxFormation(random.nextInt(200),random.nextInt(7)+ 1,random.nextInt(7)+ 1);
        } else if (getUpdateCount() < 15) {
            createBoxFormation(random.nextInt(200),random.nextInt(8)+ 1,random.nextInt(8)+ 1);
        } else {
            createBoxFormation(random.nextInt(200),random.nextInt(9)+ 1,random.nextInt(9)+ 1);
        }
    }

    /**
     * Creates box formation
     * @param xPos position x
     * @param enemiesPerRow enemies per row
     * @param rows number of rows
     */
    private void createBoxFormation(int xPos, int enemiesPerRow, int rows) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < enemiesPerRow; x++) {
                Sprite enemy = createEnemy(xPos,-100);
                enemy.translate(x * enemy.getWidth(), -y * enemy.getHeight());
                getSpriteCollection().put(SpriteLayer.NPC, enemy);
            }
        }
    }
}
