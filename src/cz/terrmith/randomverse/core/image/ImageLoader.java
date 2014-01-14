package cz.terrmith.randomverse.core.image;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ImageLoader from Killer Game Development book
 */
public class ImageLoader {

    public static final String IMAGE_DIR_DEFAULT = "images/";
    private final String imageDirctory;

    /* The key is the filename prefix, the object (value)
       is an ArrayList of BufferedImages */
    private Map<String, List<BufferedImage>> images;

    /* The key is the 'g' <name> string, the object is an
     ArrayList of filename prefixes for the group. This is used to
     access a group image by its 'g' name and filename. */
    private Map<String, List<String>> gNames;


    private GraphicsConfiguration gc;


    /**
     * Creates new image loader and loads images from specified file
     *
     * @param configFilePath       file name
     * @param imageDirectory path to directory that contains images
     */
    public ImageLoader(String configFilePath, String imageDirectory) {
        if (imageDirectory != null) {
            this.imageDirctory = imageDirectory;
        } else {
            this.imageDirctory = IMAGE_DIR_DEFAULT;
        }
        initLoader();
        // begin by loading the images specified in fnm
        loadImagesFile(configFilePath);
    }

    /**
     * Creates empty image loader. Images has to be loaded by calling 'load' methods provided by ImageLoader
     *
     * @param imageDirectory path to directory that contains images
     */
    public ImageLoader(String imageDirectory) {
        if (imageDirectory != null) {
            this.imageDirctory = imageDirectory;
        } else {
            this.imageDirctory = IMAGE_DIR_DEFAULT;
        }
        initLoader();
    }


    private void initLoader() {

        images = new HashMap<String, List<BufferedImage>>();
        gNames = new HashMap<String, List<String>>();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    }

    /**
     * Loads all images defined in a text file
     * <p/>
     * Formats:
     * o <fnm>                     // a single image
     * n <fnm*.ext> <number>       // a numbered sequence of images
     * s <fnm> <number>            // an images strip
     * g <name> <fnm> [ <fnm> ]*   // a group of images
     * <p/>
     * blank lines and comment lines (//) are ignored
     *
     * @param configFilePath path to file with configuration
     */
    private void loadImagesFile(String configFilePath) {
        String imsFNm = imageDirctory + configFilePath;
        System.out.println("Reading file: " + imsFNm);
        try {
            InputStream in = this.getClass().getResourceAsStream(configFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            // BufferedReader br = new BufferedReader( new FileReader(imsFNm));
            String line;
            while ((line = br.readLine()) != null) {

                //ignore line if empty or contains comment
                if (line.length() == 0 || line.startsWith("//")) {
                    // blank line
                    continue;
                }

                ImageDescription imd = new ImageDescription(line);

                if (imd.getType() == ImageType.SINGLE_IMAGE) {  // a single image
                    loadSingleImage(imd.getFileName());
                } else if (imd.getType() == ImageType.NUMBERED_IMAGES) {
                    // a numbered sequence of images
                    loadNumImages(imd);

                } else if (imd.getType() == ImageType.IMAGE_STRIP) {
                    // an images strip
                    loadStripImages(imd);
                } else if (imd.getType() == ImageType.IMAGE_GROUP) {
                    // a group of images
                    loadGroupImages(imd);
                } else {
                    System.out.println("Unknwon line format: " + line);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + imsFNm);
            System.exit(1);
        }
    }

    /**
     * Loads single image with given filename
     *
     * @param fnm filename
     * @return true if image was laoded correctly
     */
    public boolean loadSingleImage(String fnm) {
        ImageDescription imd = new ImageDescription("o " + fnm);
        String name = imd.getPrefix();

        if (images.containsKey(name)) {
            System.out.println("Error: " + name + "already used");
            return false;
        }

        BufferedImage bi = loadImage(fnm);
        if (bi != null) {
            List<BufferedImage> imsList = new ArrayList<BufferedImage>();
            imsList.add(bi);
            images.put(name, imsList);
            System.out.println("  Stored " + name + "/" + fnm);
            return true;
        } else {
            return false;
        }
    }  // end of loadSingleImage()

    /**
     * Load image in a series with given number
     *
     * @param imd image description
     * @return number of loaded images
     */
    private int loadNumImages(ImageDescription imd) {
        BufferedImage bi;
        List<BufferedImage> imsList = new ArrayList<BufferedImage>();
        int loadCount = 0;

        // load imd.getPrefix(number) + <i> + postfix, where i = 0 to <number-1>
        for (int i = 0; i < imd.getImageCount(); i++) {
            if ((bi = loadImage(imd.getFileName(i))) != null) {
                loadCount++;
                imsList.add(bi);
                System.out.print(i + " ");
            }
        }
        System.out.println();


        if (loadCount == 0) {
            System.out.println("No images loaded for " + imd.getPrefix());
        } else {
            images.put(imd.getPrefix(), imsList);
        }
        return loadCount;
    }

    /**
     * Loads strip image with given image description
     *
     * @param imd image description
     * @return number of loaded images
     */
    public int loadStripImages(ImageDescription imd) {
        String name = imd.getPrefix();
        if (images.containsKey(name)) {
            System.out.println("Error: " + name + "already used");
            return 0;
        }
        // load the images into an array
        BufferedImage[] strip = loadStripImageArray(imd.getFileName(), imd.getImageCount());
        if (strip == null) {
            return 0;
        }

        List<BufferedImage> imsList = Arrays.asList(strip);
        System.out.println("  Adding " + name + "/" + imd.getFileName() + "... 0 - " + strip.length);

        if (imsList.isEmpty()) {
            System.out.println("No images loaded for " + name);
        } else {
            images.put(name, imsList);
        }

        return imsList.size();
    }

    /**
     * Loads image group
     *
     * @return number of laoded images
     */
    public int loadGroupImages(ImageDescription imd) {
        if (images.containsKey(imd.getGroupName())) {
            System.out.println("Error: " + imd.getGroupName() + "already used");
            return 0;
        }

        BufferedImage bi;
        List<String> nms = new ArrayList<String>();
        List<BufferedImage> imsList = new ArrayList<BufferedImage>();
        int loadCount = 0;

        for (int i = 0; i < imd.getImageCount(); i++) {    // load the files
            if ((bi = loadImage(imd.getFileName(i))) != null) {
                loadCount++;
                imsList.add(bi);
                nms.add(imd.getPrefix(i));
                System.out.print(imd.getPrefix(i) + "/" + imd.getFileName(i) + " ");
            }
        }
        System.out.println();

        if (loadCount == 0) {
            System.out.println("No images loaded for " + imd.getGroupName());
        } else {
            images.put(imd.getGroupName(), imsList);
            gNames.put(imd.getGroupName(), nms);
        }

        return loadCount;
    }  // end of loadGroupImages()

    /**
     * Get the image associated with <name>. If there are
     * several images stored under that name, return the
     * first one in the list.
     *
     * @param name reuested iamge name without suffix
     * @return image
     */
    public BufferedImage getImage(String name) {
        List<BufferedImage> imsList = images.get(name);
        if (imsList == null) {
            System.out.println("No image(s) stored under " + name);
            return null;
        }
        return imsList.get(0);
    }


    /**
     * Get the image associated with <name> at position <posn>
     * in its list. If <posn> is < 0 then return the first image
     * in the list. If posn is bigger than the list's size, then
     * calculate its value modulo the size.
     *
     * @param name requested image name
     * @param posn index of image in a list
     * @return image
     */
    public BufferedImage getImage(String name, int posn) {
        List<BufferedImage> imsList = images.get(name);
        if (imsList == null) {
            System.out.println("No image(s) stored under " + name);
            return null;
        }

        int size = imsList.size();
        if (posn < 0) {
            return imsList.get(0);   // return first image
        } else if (posn >= size) {
            int newPosn = posn % size;   // modulo
            return imsList.get(newPosn);
        }

        return imsList.get(posn);
    }


    /**
     * Get the image associated with the group <name> and filename
     * prefix <fnmPrefix>.
     *
     * @param name      requested image name
     * @param fnmPrefix filename prefix
     * @return image
     */
    public BufferedImage getImage(String name, String fnmPrefix) {
        List<BufferedImage> imsList = images.get(name);
        if (imsList == null) {
            System.out.println("No image(s) stored under " + name);
            return null;
        }

        int posn = getGroupPosition(name, fnmPrefix);
        if (posn < 0) {
            return imsList.get(0);   // return first image
        }

        return imsList.get(posn);
    }

    /**
     * Search the hashmap entry for <name>, looking for <fnmPrefix>.
     *
     * @param name      group name of requested image
     * @param fnmPrefix name of requested image
     * @return Return its position in the list, or -1.
     */
    private int getGroupPosition(String name, String fnmPrefix) {
        List<String> groupNames = (ArrayList<String>) gNames.get(name);
        if (groupNames == null) {
            System.out.println("No group names for " + name);
            return -1;
        }

        String nm;
        for (int i = 0; i < groupNames.size(); i++) {
            nm = groupNames.get(i);
            if (nm.equals(fnmPrefix)) {
                return i;          // the posn of <fnmPrefix> in the list of names
            }
        }

        System.out.println("No " + fnmPrefix + " group name found for " + name);
        return -1;
    }  // end of getGroupPosition()


    /**
     * return all the BufferedImages for the given name
     *
     * @param name requested image name
     * @return return all the BufferedImages for the given name
     */
    public List<BufferedImage> getImages(String name) {
        List<BufferedImage> imsList = (ArrayList<BufferedImage>) images.get(name);
        if (imsList == null) {
            System.out.println("No image(s) stored under " + name);
            return null;
        }

        System.out.println("Returning all images stored under " + name);
        return imsList;
    }

    /**
     * is <name> a key in the images hashMap?
     *
     * @param name
     * @return
     */
    public boolean isLoaded(String name) {
        List<BufferedImage> imsList = images.get(name);
        if (imsList == null) {
            return false;
        }
        return true;
    }

    /**
     * how many images are stored under <name>?
     *
     * @param name
     * @return
     */
    public int numImages(String name) {
        List<BufferedImage> imsList = (ArrayList<BufferedImage>) images.get(name);
        if (imsList == null) {
            System.out.println("No image(s) stored under " + name);
            return 0;
        }
        return imsList.size();
    }


    /**
     * Load the image from <fnm>, returning it as a BufferedImage
     * which is compatible with the graphics device being used.
     * <p/>
     * Uses ImageIO.
     *
     * @param fnm
     * @return
     */
    public BufferedImage loadImage(String fnm) {
        try {

	        URL url = getClass().getResource(imageDirctory + fnm);
	        if (url == null) {
		        throw new IllegalArgumentException(fnm + " could not be loaded. Does given file exist?");
	        }
            BufferedImage im = ImageIO.read(url);
            // An image returned from ImageIO in J2SE <= 1.4.2 is
            // _not_ a managed image, but is deactivate copying!

            int transparency = im.getColorModel().getTransparency();
            BufferedImage copy = gc.createCompatibleImage(
                    im.getWidth(), im.getHeight(),
                    transparency);
            // create a graphics context
            Graphics2D g2d = copy.createGraphics();
            // g2d.setComposite(AlphaComposite.Src);

            // reportTransparency(IMAGE_DIR + fnm, transparency);

            // copy image
            g2d.drawImage(im, 0, 0, null);
            g2d.dispose();
            return copy;
        } catch (IOException e) {
            System.out.println("Load Image error for " + imageDirctory + "/" + fnm + ":\n" + e);
            return null;
        }
    } // end of loadImage() using ImageIO


    private void reportTransparency(String fnm, int transparency) {
        System.out.print(fnm + " transparency: ");
        switch (transparency) {
            case Transparency.OPAQUE:
                System.out.println("opaque");
                break;
            case Transparency.BITMASK:
                System.out.println("bitmask");
                break;
            case Transparency.TRANSLUCENT:
                System.out.println("translucent");
                break;
            default:
                System.out.println("unknown");
                break;
        } // end switch
    }

    /**
     * Extract the individual images from the strip image file, <fnm>.
     * We assume the images are stored in a single row, and that there
     * are <number> of them. The images are returned as an array of
     * BufferedImages
     *
     * @param fnm
     * @param number
     * @return
     */
    public BufferedImage[] loadStripImageArray(String fnm, int number) {
        if (number <= 0) {
            System.out.println("number <= 0; returning null");
            return null;
        }

        BufferedImage stripIm;
        if ((stripIm = loadImage(fnm)) == null) {
            System.out.println("Returning null");
            return null;
        }

        int imWidth = (int) stripIm.getWidth() / number;
        int height = stripIm.getHeight();
        int transparency = stripIm.getColorModel().getTransparency();

        BufferedImage[] strip = new BufferedImage[number];
        Graphics2D stripGC;

        // each BufferedImage from the strip file is stored in strip[]
        for (int i = 0; i < number; i++) {
            strip[i] = gc.createCompatibleImage(imWidth, height, transparency);

            // create a graphics context
            stripGC = strip[i].createGraphics();
            // stripGC.setComposite(AlphaComposite.Src);

            // copy image
            stripGC.drawImage(stripIm,
                    0, 0, imWidth, height,
                    i * imWidth, 0, (i * imWidth) + imWidth, height,
                    null);
            stripGC.dispose();
        }
        return strip;
    }
}
