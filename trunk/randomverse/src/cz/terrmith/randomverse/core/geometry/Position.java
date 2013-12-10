package cz.terrmith.randomverse.core.geometry;

/**
 * Position in a real space
 */
public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y +"]";
    }
}
