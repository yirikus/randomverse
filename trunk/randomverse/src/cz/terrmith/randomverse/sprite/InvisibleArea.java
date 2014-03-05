package cz.terrmith.randomverse.sprite;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.Destructible;
import cz.terrmith.randomverse.core.sprite.properties.Solid;
import cz.terrmith.randomverse.core.sprite.properties.SpritePropertyHelper;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.3.14
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public class InvisibleArea extends SimpleSprite implements Destructible, Solid {

    public static final int TOTAL_HEALTH = 1;
    private int currentHealth = TOTAL_HEALTH;

    public InvisibleArea(double x, double y, int w, int h) {
        super(x, y, w, h, null);
    }


    @Override
    public int getImpactDamage() {
        return 0;
    }

    @Override
    public void collide(Sprite s) {
        super.collide(s);
        SpritePropertyHelper.dealImpactDamage(this, s);
    }

    @Override
    public int getTotalHealth() {
        return TOTAL_HEALTH;
    }

    @Override
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public void reduceHealth(int amount) {
       this.currentHealth -= amount;
        if (this.currentHealth < 1) {
            setActive(false);
        }
    }

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        super.drawSprite(g, ims);
        g.setColor(new Color(71, 219, 71,100));
        g.fillRect((int)getXPosn(), (int)getYPosn(), getWidth(), getHeight());
    }
}
