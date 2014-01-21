package cz.terrmith.randomverse.test.core.core.sprite;

import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import junit.framework.Assert;
import org.junit.Test;

import java.awt.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 10.10.13
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class TestMultisprite {
    @Test
    public void testHorizontalFlip() throws Exception {
        MultiSprite multisprite = new MultiSprite(300, 300);
        Tile tile00 = new Tile (0,0,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile00);
        Tile tile01 = new Tile (0,1,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile01);
        Tile tile0_1 = new Tile (0,-1,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile0_1);
        Tile tile10 = new Tile (1,0,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile10);
        Tile tile_10 = new Tile (-1,0,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile_10);

        multisprite.flipHorizontal();
        Assert.assertEquals(0,tile00.getTileX());
        Assert.assertEquals(0,tile01.getTileX());
        Assert.assertEquals(0,tile0_1.getTileX());
        Assert.assertEquals(-1,tile10.getTileX());
        Assert.assertEquals(1,tile_10.getTileX());

        Assert.assertEquals(0,tile00.getTileY());
        Assert.assertEquals(1,tile01.getTileY());
        Assert.assertEquals(-1,tile0_1.getTileY());
        Assert.assertEquals(0,tile10.getTileY());
        Assert.assertEquals(0,tile_10.getTileY());

    }

    @Test
    public void testVerticalFlip() throws Exception {
        MultiSprite multisprite = new MultiSprite(300, 300);
        Tile tile00 = new Tile (0,0,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile00);
        Tile tile01 = new Tile (0,1,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile01);
        Tile tile0_1 = new Tile (0,-1,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile0_1);
        Tile tile10 = new Tile (1,0,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile10);
        Tile tile_10 = new Tile (-1,0,new SimpleSprite(0,0,1,1,null));
        multisprite.addTile(tile_10);

        multisprite.flipVertical();

        Assert.assertEquals(0,tile00.getTileX());
        Assert.assertEquals(0,tile01.getTileX());
        Assert.assertEquals(0,tile0_1.getTileX());
        Assert.assertEquals(1,tile10.getTileX());
        Assert.assertEquals(-1,tile_10.getTileX());

        Assert.assertEquals(0,tile00.getTileY());
        Assert.assertEquals(-1,tile01.getTileY());
        Assert.assertEquals(1,tile0_1.getTileY());
        Assert.assertEquals(0,tile10.getTileY());
        Assert.assertEquals(0,tile_10.getTileY());

    }

	@Test
	public void testBoundingBox() throws Exception {
		MultiSprite m = new MultiSprite(100, 100);
		m.addTile(new Tile(0, 0, new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null)));
		m.addTile(new Tile(-1, 0, new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null)));
		m.addTile(new Tile(1, 0, new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null)));
		m.addTile(new Tile(0, -1, new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null)));
		m.addTile(new Tile(0, 1, new SimpleSprite(0, 0, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE, null)));

		Rectangle r = m.getBoundingBox();
		int x = 0;
	}
}
