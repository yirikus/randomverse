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
}
