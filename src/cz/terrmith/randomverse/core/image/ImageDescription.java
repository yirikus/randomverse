package cz.terrmith.randomverse.core.image;

import com.sun.javafx.collections.UnmodifiableListSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * String that represents and image
 *  Formats:
 * o <fnm>                     // a single image
 * n <fnm*.ext> <number>       // a numbered sequence of images
 * s <fnm> <number>            // an images strip
 * g <name> <fnm> [ <fnm> ]*   // a group of images
 */
public class ImageDescription {

    private final ImageType type;
    private List<String> fileNames;
    private String groupName;
    private final int imageCount;

    /**
     * Creates new image description by given line
     * @param line
     */
    public ImageDescription(String line){
        StringTokenizer tokens = new StringTokenizer(line);
        int tokenCount = tokens.countTokens();
        this.fileNames = new ArrayList<String>();

        String stringType = tokens.nextToken();
        if ("o".equals(stringType)) {
            this.type = ImageType.SINGLE_IMAGE;
            if (tokenCount != 2) {
                throw new IllegalArgumentException("Malformed line, expected o <fnm>, but was: " + line);
            }
            this.fileNames.add(tokens.nextToken());
            this.imageCount = fileNames.size();
        } else if ("n".equals(stringType)) {
            this.type = ImageType.NUMBERED_IMAGES;
            if (tokenCount != 3) {
                throw new IllegalArgumentException("Malformed line, expected n <fnm*.ext> <number>, but was: " + line);
            }

            String fileName = tokens.nextToken();
            int imageCount = Integer.parseInt(tokens.nextToken());
            for(int i = 0; i < imageCount; i++) {
                fileNames.add(fileName.replace("*", String.valueOf(i)));
            }
            this.imageCount = fileNames.size();
        } else if ("s".equals(stringType)) {
            this.type = ImageType.IMAGE_STRIP;
            if (tokenCount != 3) {
                throw new IllegalArgumentException("Malformed line, expected s <fnm> <number>, but was: " + line);
            }
            this.fileNames.add(tokens.nextToken());
            this.imageCount = Integer.parseInt(tokens.nextToken());
        } else if ("g".equals(stringType)) {
            this.type = ImageType.IMAGE_GROUP;
            if (tokenCount < 3) {
                throw new IllegalArgumentException("Malformed line, expected g <name> <fnm> [ <fnm> ]*, but was: " + line);
            }
            this.groupName = tokens.nextToken();
            while(tokens.countTokens() > 0){
                fileNames.add(tokens.nextToken());
            }
            this.imageCount = fileNames.size();
        } else {
            throw new IllegalArgumentException("Unknown image type, must be one of 'o', 'n', 's', 'g', but was: " + stringType);
        }
    }

    public static ImageDescription singleImage() {
        //TODO
        throw new UnsupportedOperationException();
    }

    public static ImageDescription stripImage() {
        //TODO
        throw new UnsupportedOperationException();
    }

    public static ImageDescription imageGroup() {
        //TODO
        throw new UnsupportedOperationException();
    }

    public static ImageDescription numberedImage() {
        //TODO
        throw new UnsupportedOperationException();
    }

    public ImageType getType() {
        return type;
    }

    public List<String> getFileNames() {
        return Collections.unmodifiableList(fileNames);
    }

    public String getGroupName() {
        return groupName;
    }

    public int getImageCount() {
        return this.imageCount;
    }

    /**
     * Returns filename for single image or first filename for image sequence
     * @return
     */
    public String getFileName(){
        return getFileName(0);
    }

    /**
     * Returns filename of image with given number (numbers from 0)
     * @param index
     * @return
     */
    public String getFileName(int index) {
        if (index >= getImageCount()) {
            throw new IllegalArgumentException("Requested image with number " + index + ", but maximum index is " + (getImageCount() - 1));
        }
        return fileNames.get(index);
    }

    /**
     * Filename without extension of image n
     * @param index number of image
     * @return
     */
    public String getPrefix(int index) {
       if (index >= getImageCount()) {
           throw new IllegalArgumentException("Requested image with number " + index + ", but maximum index is " + (getImageCount() - 1));
       }
        int posn = this.fileNames.get(index).lastIndexOf(".");
        if ( posn == -1) {
            System.out.println("No prefix found for filename: " + this.fileNames.get(index));
            return this.fileNames.get(index);
        } else {
            return this.fileNames.get(index).substring(0, posn);
        }
    }

    /**
     * Prefix of filename of single image or first image
     * @return
     */
    public String getPrefix(){
        return getPrefix(0);
    }

    /**
     * Extension of filename of image n
     * @param index number of image
     * @return
     */
    public String getExtension(int index) {
        if (index >= getImageCount()) {
            throw new IllegalArgumentException("Requested image with number " + index + ", but maximum index is " + (getImageCount() - 1));
        }
        int posn = this.fileNames.get(index).lastIndexOf(".");
        if ( posn == -1) {
            System.out.println("No prefix found for filename: " + this.fileNames.get(index));
            return this.fileNames.get(index);
        } else {
            return this.fileNames.get(index).substring(posn + 1);
        }
    }

    /**
     * Extension of filename of single image or first image
     * @return
     */
    public String getExtension(){
        return getExtension(0);
    }

}
