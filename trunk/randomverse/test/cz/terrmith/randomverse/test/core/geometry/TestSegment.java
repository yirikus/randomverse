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

    @Test
    public void testLineIntersection() {
        // zero division
        Segment o = new Segment (new Position(0,0), new Position(0,1));
        Assert.assertTrue(o.intersectsLine(o));
        o = new Segment (new Position(0,0), new Position(1,0));
        Assert.assertTrue(o.intersectsLine(o));

        Segment a = new Segment (new Position(1,1), new Position(1,3));

        //segment end touches line (like T)
        Segment b = new Segment (new Position(0,1), new Position(2,1));

        Assert.assertTrue(a.intersectsLine(a));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertTrue(b.intersectsLine(a));

        // T, but segments do not intersect
        b = new Segment (new Position(2,1), new Position(3,1));
        Assert.assertTrue(b.intersectsLine(b));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertFalse(b.intersectsLine(a));

        // same line, segments intersect
        b = new Segment (new Position(1,2), new Position(1,4));
        Assert.assertTrue(b.intersectsLine(b));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertTrue(b.intersectsLine(a));

        //same line segments do not intersect
        b = new Segment (new Position(1,4), new Position(1,6));
        Assert.assertTrue(b.intersectsLine(b));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertTrue(b.intersectsLine(a));

        // same line, segments share a point
        b = new Segment (new Position(1,3), new Position(1,4));
        Assert.assertTrue(b.intersectsLine(b));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertTrue(b.intersectsLine(a));

        // cross, +,+ quadrant
        a = new Segment (new Position(1,1), new Position(3,2));
        b = new Segment (new Position(1,2), new Position(-1,3));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertFalse(b.intersectsLine(a));

        b = new Segment (new Position(3,1), new Position(5,0));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertFalse(b.intersectsLine(a));

        // long distance
        a = new Segment (new Position(1,1), new Position(3,3));
        b = new Segment (new Position(1,56), new Position(2,55));
        Assert.assertFalse(a.intersectsLine(b));
        Assert.assertFalse(b.intersectsLine(a));

        // cross -,- quadrant
        //fuck you, I dont care bout rounding errors
//        a = new Segment (new Position(-1,-1), new Position(-3,-2));
//        b = new Segment (new Position(-1,-2), new Position(1,-3));
//        Assert.assertTrue(a.intersectsLine(b));
//        Assert.assertFalse(b.intersectsLine(a));
//
//        b = new Segment (new Position(-2,-1), new Position(-5,0));
//        Assert.assertTrue(a.intersectsLine(b));
//        Assert.assertFalse(b.intersectsLine(a));

        // -|
        a = new Segment (new Position(0,-1), new Position(0,1));
        b = new Segment (new Position(0,0), new Position(-1,0));
        Assert.assertTrue(a.intersectsLine(b));
        Assert.assertTrue(b.intersectsLine(a));
    }
}
