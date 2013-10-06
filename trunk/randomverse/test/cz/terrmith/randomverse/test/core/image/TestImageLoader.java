package cz.terrmith.randomverse.test.core.image;

import cz.terrmith.randomverse.core.image.ImageLoader;
import junit.framework.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * ImageLoader test
 */
public class TestImageLoader {

    @Test
    public void testLoading() throws Exception {
        ImageLoader iml = new ImageLoader("/image_config.txt","/images/");
        BufferedImage img = iml.getImage("sideGun", 2);
        Assert.assertNotNull(img);
    }
}
