package cz.terrmith.randomverse.test.core.geometry;

import cz.terrmith.randomverse.core.geometry.Position;
import cz.terrmith.randomverse.core.geometry.Segment;
import junit.framework.Assert;
import org.junit.Test;

import java.awt.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.2.14
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
public class TestSegment {
    @Test
    public void testRectangleIntersection() throws Exception {
        // there is an intersection
        Rectangle r1 = new Rectangle(3, 3, 3, 3);
        Rectangle r2 = new Rectangle(1, 2, 3, 3);

        Segment result = Segment.rectangleIntersection(r1, r2);

        Segment expected = new Segment(new Position(3,3), new Position(3,5));
        Assert.assertEquals(expected, result);

        // there is no intersection
        Rectangle r3 = new Rectangle(0, 0, 1, 1);
        result = Segment.rectangleIntersection(r1, r3);
        Assert.assertEquals(null, result);

    }
}
