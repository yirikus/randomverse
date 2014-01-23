package cz.terrmith.randomverse.ability;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.sprite.Sprite;
import cz.terrmith.randomverse.core.sprite.properties.Ability;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author jiri.kus
 */
public class Shield extends Ability {


    private Sprite parent;
    private static final int THICKNESS = 4;

    /**
	 * Creates an instance
	 */
	public Shield() {
		super(AbilityGroup.ACTION_2.name());
	}

	/**
	 * Switches shield on or off
	 */
	@Override
	public void useAbility(Sprite parent) {
		setActive(!isActive());
		if (isActive()) {
			//update parent [x, y]
            this.parent = parent;
		}
	}

    @Override
    public void drawSprite(Graphics g, ImageLoader ims) {
        Rectangle rect = parent.getBoundingBox();
        g.setColor(new Color(0, 81, 250,50));
        g.fillRect((int)rect.getX() - THICKNESS,
                   (int)rect.getY() - THICKNESS,
                   (int)rect.getWidth() + THICKNESS,
                   (int)rect.getHeight() + THICKNESS);
    }

    public void updateSprite(Sprite parent) {
		super.updateSprite();
        if (isActive()) {
            Rectangle rectangle = parent.getBoundingBox();
            setPosition(rectangle.getX(), rectangle.getY());
        }
	}
}
