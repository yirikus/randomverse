package cz.terrmith.randomverse.core.util;

import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Utility class for string manipulation
 *
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 13:42
 */
public final class StringUtils {

    /**
     * Not intended for instantiation
     */
    private StringUtils() {}

    /**
     * Draws string that wraps
     *
     * @see http://stackoverflow.com/questions/400566/full-justification-with-a-java-graphics-drawstring-replacement
     *
     * @param g graphics
     * @param s string
     * @param x x pos
     * @param y y pos
     * @param width width of line
     *
     * @return y coordinate of line following the last line
     */
    public static int drawString(Graphics g, String s, int x, int y, int width) {
        // FontMetrics gives us information about the width,
        // height, etc. of the current Graphics object's Font.
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();

        int curX = x;
        int curY = y + lineHeight;

        String[] words = s.split(" ");

        for (String word : words) {
            // Find out the width of the word.
            int wordWidth = fm.stringWidth(word + " ");

            // If text exceeds the width, then move to next line.
            if (curX + wordWidth >= x + width) {
                curY += lineHeight;
                curX = x;
            }

            g.drawString(word, curX, curY);

            // Move over to the right for next word.
            curX += wordWidth;
        }

        return curY + lineHeight;
    }
}
