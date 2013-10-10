package cz.terrmith.randomverse.core.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 11.10.13
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
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
