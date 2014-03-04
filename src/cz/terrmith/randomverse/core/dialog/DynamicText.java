package cz.terrmith.randomverse.core.dialog;

import cz.terrmith.randomverse.core.util.StringUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

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

    public static final String DEFAULT_KEY = "key";
    Map<String, NavigableText> textMap;
    private String currentKey;
    private int currentOption = 0;

    /**
     * Creates dynamic text with single message... that makes it kinda static... erm..
     * @param text
     */
    public DynamicText(String text) {
        this.textMap = new HashMap<String, NavigableText>();
        textMap.put(DEFAULT_KEY, new NavigableText(text, new NavigableTextOption[]{}));
        this.currentKey = DEFAULT_KEY;
    }

    public DynamicText(Map<String, NavigableText> textMap, String firstKey) {
        if (textMap == null) {
            throw new IllegalArgumentException("text map must not be null");
        }
        NavigableText nt = textMap.get(firstKey);
        if (null == nt) {
            throw new IllegalArgumentException("No value under key: " + this.currentKey);
        }

        this.textMap = textMap;
        this.currentKey = firstKey;
    }

    /**
     * Moves to navigable text under currently selected option
     * @param key
     */
    public void navigate() {
        if (!getNavigableText().getOptions().isEmpty()) {
            navigate(getNavigableText().getOptions().get(currentOption).getKey());
        } else {
            System.out.println("Houston, dynamic text calls back");
            getNavigableText().callback();
        }
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
     * Moves to navigable text under given key
     * @param key
     */
    public void navigate(String key) {
        if (getNavigableText().containsOption(key)) {
            currentKey = key;
            currentOption = 0;
        } else {
            throw new IllegalArgumentException ("Cannot navigate to '" + key + "'options: " + getNavigableText().printOptions());
        }
    }

    /**
     * Returns current navigableText
     * @return
     */
    public NavigableText getNavigableText() {
       return textMap.get(this.currentKey);
    }

    public void draw(Graphics g, int x, int y, int width){
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

        int lineSpace = 10;
        for (int i = 0; i < getNavigableText().getOptions().size(); i++) {
            if (currentOption == i) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(getNavigableText().getOptions().get(i).getText(), x, nextLineY + i * (metrics.getHeight() + lineSpace));
        }
    }
}
