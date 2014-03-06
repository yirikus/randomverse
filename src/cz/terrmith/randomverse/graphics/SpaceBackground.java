package cz.terrmith.randomverse.graphics;

import cz.terrmith.randomverse.core.geometry.Position;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Temporaary class for background generation
 * User: TERRMITh
 * Date: 6.3.14
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class SpaceBackground {

    private static final Random random = new Random();
    private final Color[] starColours;
    private final Position[] starCoordinates;
    private int[] starSize;

    public SpaceBackground(int maxStarCount) {
        int stars = 1 + random.nextInt(maxStarCount);
        this.starColours = new Color[stars];
        this.starCoordinates = new Position[stars];
        this.starSize = new int[stars];
        for (int i = 0; i < starColours.length; i++) {
            starColours[i] = new Color(155 + random.nextInt(100),155 + random.nextInt(100),155 + random.nextInt(100));
            starCoordinates[i] = new Position(random.nextDouble(), random.nextDouble());
            starSize[i] = 1 + random.nextInt(3);
        }
    }

    public void drawBackground(Graphics g, Position p, int size) {
        for (int i =0; i < starColours.length; i++) {
            g.setColor(starColours[i]);
            int x = (int)p.getX() + (int)(starCoordinates[i].getX() * size);
            int y = (int)p.getY() + (int)(starCoordinates[i].getY() * size);
            g.fillRect(x, y, starSize[i], starSize[i]);
        }
    }
}
