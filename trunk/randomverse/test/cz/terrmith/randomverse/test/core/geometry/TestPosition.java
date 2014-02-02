package cz.terrmith.randomverse.test.core.geometry;

import cz.terrmith.randomverse.core.geometry.Position;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 28.1.14
 * Time: 23:04
 * To change this template use File | Settings | File Templates.
 */
public class TestPosition {

    @Test
    public void testPointOnCircle() throws Exception {
        Assert.assertEquals(new Position(0,-1),Position.pointOnCircle(new Position(0, 0), 1, Math.PI / 2, true));
        Assert.assertEquals(new Position(-1,0),Position.pointOnCircle(new Position(0, 0), 1, Math.PI , true));
        Assert.assertEquals(new Position(0, 1),Position.pointOnCircle(new Position(0, 0), 1, -Math.PI / 2 , true));
        Assert.assertEquals(new Position(1, 0),Position.pointOnCircle(new Position(0, 0), 1, 2 * Math.PI, true));
        Assert.assertEquals(new Position(1, 0),Position.pointOnCircle(new Position(0, 0), 1, 0, true));

        Assert.assertEquals(new Position(0, 1),Position.pointOnCircle(new Position(0, 0), 1, Math.PI / 2, false));
        Assert.assertEquals(new Position(-1,0),Position.pointOnCircle(new Position(0, 0), 1, Math.PI , false));
        Assert.assertEquals(new Position(0, -1),Position.pointOnCircle(new Position(0, 0), 1, -Math.PI / 2 , false));
        Assert.assertEquals(new Position(1, 0),Position.pointOnCircle(new Position(0, 0), 1, 2 * Math.PI, false));
        Assert.assertEquals(new Position(1, 0),Position.pointOnCircle(new Position(0, 0), 1, 0, false));

    }

    @Test
    public void testNormalizedVector () {
        Assert.assertEquals(new Position(1,0),Position.normalizedVector(new Position(2,0), new Position (5,0)));
        Assert.assertEquals(new Position(-1,0),Position.normalizedVector(new Position(5,0), new Position (2,0)));
        Assert.assertEquals(Position.normalizedVector(new Position(5,5), new Position (2,2)),
                            Position.normalizedVector(new Position(15,15), new Position (5,5)));
    }
}
