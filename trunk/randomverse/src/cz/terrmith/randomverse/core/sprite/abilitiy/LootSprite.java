package cz.terrmith.randomverse.core.sprite.abilitiy;

import cz.terrmith.randomverse.core.image.ImageLoader;
import cz.terrmith.randomverse.core.image.ImageLocation;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.SpriteStatus;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

/**
 * @author jiri.kus
 */
public class LootSprite extends SimpleSprite {

	private Loot loot;

	public LootSprite(LootSprite lootSprite,Loot loot) {
		super(lootSprite);
		this.loot = loot;
	}

	public LootSprite(double x, double y, int w, int h,
	                  Map<SpriteStatus, ImageLocation> imageForStatus, Loot loot) {
		super(x, y, w, h, imageForStatus);
		this.loot = loot;
		setStep(0,1);
	}

	public Loot pickUp() {
		setActive(false);
		return loot;
	}

	@Override
	public void drawSprite(Graphics g, ImageLoader ims) {
		g.setColor(Color.YELLOW);
		g.fillRect((int) getXPosn(), (int) getYPosn(), 10, 10);
	}
}