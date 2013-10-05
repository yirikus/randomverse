package cz.terrmith.randomverse.test.core.image;

import cz.terrmith.randomverse.core.image.ImageDescription;
import cz.terrmith.randomverse.core.image.ImageType;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.10.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class TestImageDescription {

    @Test
    public void testNumberedImages() throws Exception {
        ImageDescription ims = new ImageDescription("n obr*.png 3");
        Assert.assertEquals(ImageType.NUMBERED_IMAGES, ims.getType());
        Assert.assertEquals("obr0.png", ims.getFileName());
        Assert.assertEquals("obr2.png", ims.getFileName(2));
        Assert.assertEquals("png", ims.getExtension());
        Assert.assertEquals("obr0", ims.getPrefix());
        Assert.assertEquals(3, ims.getImageCount());
    }

    @Test
    public void testSingleImage() throws Exception {
        ImageDescription ims = new ImageDescription("o obr.png");
        Assert.assertEquals(ImageType.SINGLE_IMAGE, ims.getType());
        Assert.assertEquals("obr.png", ims.getFileName());
        Assert.assertEquals(1, ims.getImageCount());
    }

    @Test
    public void testImageGroup() throws Exception {
        ImageDescription ims = new ImageDescription("g groupName left.png right.png");
        Assert.assertEquals(ImageType.IMAGE_GROUP, ims.getType());
        Assert.assertEquals("left.png", ims.getFileName());
        Assert.assertEquals("right.png", ims.getFileName(1));
        Assert.assertEquals("groupName", ims.getGroupName());
        Assert.assertEquals(2, ims.getImageCount());
    }
}
