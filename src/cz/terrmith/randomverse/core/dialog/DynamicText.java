package cz.terrmith.randomverse.core.dialog;

import cz.terrmith.randomverse.core.util.StringUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Represents navigable text
 * Each state has [message + options], options lead to another [message + options]
 *
 * User: TERRMITh
 * Date: 4.3.14
 * Time: 13:56
 *
 */
public class DynamicText {

    private NavigableText navigableText;
    NavigableText root;
    private int currentOption = 0;

    /**
     * Creates dynamic text with single message... that makes it kinda static... erm..
     * @param text
     */
    public DynamicText(String text) {
        root = new NavigableTextBranch(text,"", new NavigableTextBranch[]{});
        this.navigableText = root;
    }

    public DynamicText(NavigableText root) {
        if (root == null) {
            throw new IllegalArgumentException("root must not be null");
        }
        this.root = root;
        this.navigableText = root;
    }

    /**
     * Moves to navigable text under currently selected option
     * @param key
     */
    public boolean navigate() {
        if (!getNavigableText().getOptions().isEmpty()) {
            navigableText = getNavigableText().getOptions().get(currentOption);
        }
        return navigableText.navigate();
    }

    public void nextOption(){
        int size = getNavigableText().getOptions().size();
        if (size > 0) {
            currentOption = (currentOption + 1 + size) % size;
        }
    }

    public void prevOption(){
        int size = getNavigableText().getOptions().size();
        if (size > 0) {
            currentOption = (currentOption - 1 + size) % size;
        }
    }

    /**
     * Returns current navigableText
     * @return
     */
    public NavigableText getNavigableText() {
       return this.navigableText;
    }

    /**
     * draws dynamic text
     * @param g graphics
     * @param x x of drawing start position
     * @param y y of drawing start position
     * @param width width
     * @return last line y on which was drawn
     */
    public int draw(Graphics g, int x, int y, int width){
        Font font = new Font("system", Font.BOLD, 15);
        FontMetrics metrics = g.getFontMetrics();
        g.setFont(font);

// centered
//        int dx = (getWidth() - metrics.stringWidth(text)) / 2;
//        g.setColor(Color.WHITE);
//        g.drawString(getText(),
//                getPosX() + dx,
//                getPosY() + getHeight() / 2);

        int nextLineY = StringUtils.drawString(g, getNavigableText().getMessage(), x, y, width);

        int lineSpace = 5;
        nextLineY += (2 * lineSpace);
        int lastLineY = nextLineY;
        for (int i = 0; i < getNavigableText().getOptions().size(); i++) {
            if (currentOption == i) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            lastLineY =  nextLineY + i * (metrics.getHeight() + lineSpace);
            g.drawString(getNavigableText().getOptions().get(i).getDescription(), x, lastLineY);
        }

        return lastLineY;
    }
}
