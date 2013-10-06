package cz.terrmith.randomverse.core.image;

/**
 * Describes location of an image by name and number
 */
public class ImageLocation {
    private int number;
    private String name;

    public ImageLocation(String name, int number) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
