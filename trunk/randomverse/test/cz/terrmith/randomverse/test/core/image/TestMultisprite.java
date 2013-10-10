package cz.terrmith.randomverse.test.core.image;

import cz.terrmith.randomverse.core.sprite.MultiSprite;
import cz.terrmith.randomverse.core.sprite.SimpleSprite;
import cz.terrmith.randomverse.core.sprite.Tile;
import junit.framework.Assert;
import org.junit.Test;

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
        Assert.assertEquals(0,tile00.getX());
        Assert.assertEquals(0,tile01.getX());
        Assert.assertEquals(0,tile0_1.getX());
        Assert.assertEquals(-1,tile10.getX());
        Assert.assertEquals(1,tile_10.getX());

        Assert.assertEquals(0,tile00.getY());
        Assert.assertEquals(1,tile01.getY());
        Assert.assertEquals(-1,tile0_1.getY());
        Assert.assertEquals(0,tile10.getY());
        Assert.assertEquals(0,tile_10.getY());

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

        Assert.assertEquals(0,tile00.getX());
        Assert.assertEquals(0,tile01.getX());
        Assert.assertEquals(0,tile0_1.getX());
        Assert.assertEquals(1,tile10.getX());
        Assert.assertEquals(-1,tile_10.getX());

        Assert.assertEquals(0,tile00.getY());
        Assert.assertEquals(-1,tile01.getY());
        Assert.assertEquals(1,tile0_1.getY());
        Assert.assertEquals(0,tile10.getY());
        Assert.assertEquals(0,tile_10.getY());

    }
}
