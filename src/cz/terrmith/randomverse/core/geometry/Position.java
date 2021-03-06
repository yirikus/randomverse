package cz.terrmith.randomverse.core.geometry;

import java.util.Comparator;

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

    /**
     * Returns new position that is sum of given positions
     * @param a
     * @param b
     * @return
     */
    public static Position sum(Position a, Position b){
        return new Position (a.getX() + b.getX(),
                             a.getY() + b.getY());
    }

    /**
     * Returns new position that is difference of given positions (A - B)
     * @param a
     * @param b
     * @return
     */
    public static Position difference(Position a, Position b){
        return new Position (a.getX() - b.getX(),
                             a.getY() - b.getY());
    }

    /**
     * Returns point on a circle
     * @param center circle center point
     * @param radius circle radius
     * @param angle desired angle [0, 2PI]
     * @param screenCoords if true uses screenCoords, otherwise mathematical coords
     * @return
     */
    public static Position pointOnCircle(Position center, double radius, double angle, boolean screenCoords) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be positive, but was " + radius);
        }
        int coordsTransform = (screenCoords ? -1 : 1);

        double x = center.getX() + radius * Math.cos(angle);
        double y = center.getY() + (coordsTransform) * radius * Math.sin(angle);
        return new Position(x, y);
    }

    /**
     * Computes distance from given point
     * @return
     */
    public double distanceFrom(Position p) {
        return Math.sqrt(Math.pow(this.getX() - p.getX(),2) + Math.pow(this.getY() - p.getY(),2));
    }

     /**
     * Normalizes given vector AB.
      * ie vector is translated to origin and
     * @param a
     * @param b
     * @return
     */
    public static Position normalizedVector(Position a, Position b) {
        if ( a == null || b == null) {
            throw new IllegalArgumentException("Both vector points must not be null");
        }

        double distance = b.distanceFrom(a);
        return new Position((b.getX() - a.getX()) / distance,
                            (b.getY() - a.getY()) / distance);
    }

    /**
     * Normalizes given vector AB.
     * ie vector is translated to origin and
     * @param ab
     * @return
     */
    public static Position normalizedVector(Segment ab) {
       return normalizedVector(ab.getA(), ab.getB());
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;

        if (Double.compare(position.x, x) != 0 && !(Math.round(100000 * position.x) == Math.round(100000 * x))) {
            return false;
        }
        if (Double.compare(position.y, y) != 0 && !(Math.round(100000 * position.y) == Math.round(100000 * y))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y +"]";
    }

    public static Comparator<Position> xComparator() {
        return new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int)(o1.getX() - o2.getX());
            }
        };
    }

    public static Comparator<Position> yComparator() {
        return new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                return (int)(o1.getY() - o2.getY());
            }
        };
    }
}
