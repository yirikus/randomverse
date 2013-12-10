package cz.terrmith.randomverse.test.core.geometry;

import cz.terrmith.randomverse.core.geometry.GridLocation;
import cz.terrmith.randomverse.core.geometry.RelativePosition;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dell
 * Date: 12/10/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGridLocation {

    @org.junit.Test
    public void testGetRelativePositionTo() throws Exception {
        List<GridLocation> list = new ArrayList<GridLocation>();
        list.add(new GridLocation(0,0));
        Assert.assertEquals(RelativePosition.NEIGHBOURHOOD_4,
                            GridLocation.getRelativePositionTo(new GridLocation(-1, 0), list));
        Assert.assertEquals(RelativePosition.NEIGHBOURHOOD_8,
                GridLocation.getRelativePositionTo(new GridLocation(-1, -1), list));
        Assert.assertEquals(RelativePosition.NOT_CONTAINS,
                GridLocation.getRelativePositionTo(new GridLocation(-2, 0), list));
        Assert.assertEquals(RelativePosition.CONTAINS,
                GridLocation.getRelativePositionTo(new GridLocation(0, 0), list));

        // adding a location can change nhood-8 to nhood-4
        list.add(new GridLocation(-1, 0));
        Assert.assertEquals(RelativePosition.CONTAINS,
                GridLocation.getRelativePositionTo(new GridLocation(-1, 0), list));
        Assert.assertEquals(RelativePosition.NEIGHBOURHOOD_4,
                GridLocation.getRelativePositionTo(new GridLocation(-1, -1), list));
        list.add(new GridLocation(-1, -1));
        Assert.assertEquals(RelativePosition.CONTAINS,
                GridLocation.getRelativePositionTo(new GridLocation(-1, -1), list));


    }
}
