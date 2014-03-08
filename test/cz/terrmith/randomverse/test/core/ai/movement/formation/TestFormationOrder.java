package cz.terrmith.randomverse.test.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.formation.FormationOrder;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 8.3.14
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class TestFormationOrder {
    @Test
    public void testGroupSequence() throws Exception {
        FormationOrder result = FormationOrder.groupSequence(1, 2, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{1,2}, result.getOrders()));

        result = FormationOrder.groupSequence(1, 0, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{}, result.getOrders()));

        result = FormationOrder.groupSequence(2, 5, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{1, 1, 2, 2, 3}, result.getOrders()));

        result = FormationOrder.groupSequence(3, 7, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{1,1,1, 2,2,2, 3}, result.getOrders()));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGroupSequenceIAE() throws Exception {
        FormationOrder.groupSequence(0, 2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGroupSequenceIAE2() throws Exception {
        FormationOrder.groupSequence(1, -1, null);
    }

    @Test
    public void testRepetaedSequence() throws Exception {
        FormationOrder result = FormationOrder.repeatedSequence(new Integer[]{1}, 0, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{}, result.getOrders()));

        result = FormationOrder.repeatedSequence(new Integer[]{1,1,1}, 9, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{1,1,1 ,1,1,1 ,1,1,1}, result.getOrders()));

        result = FormationOrder.repeatedSequence(new Integer[]{1,2,3}, 9, null);
        Assert.assertTrue(Arrays.equals(new Integer[]{1,2,3 ,1,2,3 ,1,2,3}, result.getOrders()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRepetaedSequenceIAE() throws Exception {
        FormationOrder.repeatedSequence(new Integer[]{1}, -1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRepetaedSequenceIAE2() throws Exception {
        FormationOrder.repeatedSequence(null, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRepetaedSequenceIAE3() throws Exception {
        FormationOrder.repeatedSequence(new Integer[]{}, 1, null);

    }
}
