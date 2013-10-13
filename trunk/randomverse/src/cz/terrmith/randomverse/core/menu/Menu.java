package cz.terrmith.randomverse.core.menu;

import cz.terrmith.randomverse.core.geometry.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu
 */
public class Menu {

    private Position position;
    private List<String> menuItems;
    private int selected = 0;

    public Menu(Position position) {
        this(position,null);
    }

    public Menu(Position position, List<String> menuItems) {
        this.position = position;
        if (menuItems != null) {
            this.menuItems = menuItems;
        } else {
            this.menuItems = new ArrayList<String>();
        }
    }

    public void addItem(String menuItem) {
        menuItems.add(menuItem);

    }

    public void selectNext() {
        selected = (selected + 1) % menuItems.size();
    }

    public void selectPrevious() {
        selected = (menuItems.size() + selected - 1) % menuItems.size();
    }

    public String getSelected() {
        return menuItems.get(selected);
    }

    public void drawMenu(Graphics g) {
        Font font = new Font("system",Font.BOLD,20);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        int lineSpace = 10;
        for (int i = 0; i < menuItems.size(); i++) {
            if (selected == i) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.YELLOW);
            }
            g.drawString(menuItems.get(i), (int)position.getX(), (int)position.getY() + i * (metrics.getHeight() + lineSpace));
        }
    }
}
