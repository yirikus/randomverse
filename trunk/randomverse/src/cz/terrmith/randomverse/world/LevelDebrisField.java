package cz.terrmith.randomverse.world;

import cz.terrmith.randomverse.core.ai.ArtificialIntelligence;
import cz.terrmith.randomverse.core.ai.movement.formation.SimpleSpriteContainer;
import cz.terrmith.randomverse.core.ai.movement.formation.SpriteContainer;
import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.SpriteCollection;
import cz.terrmith.randomverse.core.sprite.SpriteLayer;
import cz.terrmith.randomverse.core.world.World;
import cz.terrmith.randomverse.sprite.enemy.debris.Debris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Debris level
 */
public class LevelDebrisField extends World {

    private static Random random = new Random();
    private final Sprite player;
    private final Color[] starColours;
    private final Position[] starCoordinates;
    private final ArtificialIntelligence ai;

    /**
     * @param spriteCollection sprite collection
     */
    public LevelDebrisField(SpriteCollection spriteCollection, Sprite player, ArtificialIntelligence ai) {
        super(spriteCollection, 5, 5);
        this.player = player;
        this.ai = ai;

        int stars = random.nextInt(3);
        this.starColours = new Color[stars];
        this.starCoordinates = new Position[stars];
        for (int i = 0; i < starColours.length; i++) {
            starColours[i] = new Color(161,123,0 + random.nextInt(167));
            starCoordinates[i] = new Position(random.nextDouble(), random.nextDouble());
        }
    }

    @Override
    protected void createSprites() {
        Debris enemy = new Debris(random.nextInt() % 600 + 100, -100, getSpriteCollection(), player);
        getSpriteCollection().put(SpriteLayer.NPC, enemy);
        SpriteContainer scn = new SimpleSpriteContainer(enemy);
        ai.registerSpriteContainer(scn);
        scn.registerObserver(this, String.valueOf(getUpdateCount()));
        enemy.setStep(0, enemy.getSpeed());
    }

    @Override
    public void drawMapIcon(Graphics g, Position position, int size) {
        //background
        g.setColor(Color.BLACK);
        g.fillRect((int) position.getX(),
                (int) position.getY(),
                size, size);
        //stars
        for (int i =0; i < starColours.length; i++) {
            g.setColor(starColours[i]);
            int x = (int)position.getX() + (int)(starCoordinates[i].getX() * size);
            int y = (int)position.getY() + (int)(starCoordinates[i].getY() * size);
            g.fillRect(x, y, 3, 3);
        }

    }
}
