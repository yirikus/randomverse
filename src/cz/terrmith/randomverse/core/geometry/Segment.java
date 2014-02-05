package cz.terrmith.randomverse.core.geometry;

import java.awt.Rectangle;

/**
 * Line Segment is defined by two points.
 * Order is preserved, so it can be considered as vector
 */
public class Segment {
    Position a;
    Position b;

    /**
     * Creates new line segment with direction AB
     * @param a first point
     * @param b second point
     */
    public Segment(Position a, Position b) {
        this.b = b;
        this.a = a;
    }

    /**
     * Return lenght of the segment
     * @return
     */
    public double length() {
        return a.distanceFrom(b);
    }

    /**
     * Returns segment with larger length
     * @return segment or null
     */
    public static Segment max(Segment s1, Segment s2){
        if (s2 == null || s1 == null) {
            return s1;
        }

        if (s1.length() > s2.length()) {
            return s1;
        }

        return s2;
    }

    /**
     * Returns inetrsection segment from border of the first rectangle.
     *
     * @param rectA first rectangle
     * @param rectB second rectangle
     * @return If two segments are in the intersection, the larger is returned
     */
    public static Segment rectangleIntersection(Rectangle rectA, Rectangle rectB) {
        if (rectA == null || rectB == null) {
            throw new IllegalArgumentException("both rectangles must not be null, but was: A=" + rectA + ", B=" + rectB);
        }

        if (!rectA.intersects(rectB)) {
            return null;
        }
        Rectangle intersection = rectA.intersection(rectB);
        /* p4 - p3
           |     |
           p1 _ p2 */
        Position i1 = new Position (intersection.getX(), intersection.getY() + intersection.getHeight());
        Position i2 = new Position (intersection.getX() + intersection.getWidth(), intersection.getY() + intersection.getHeight());
        Position i3 = new Position (intersection.getX() + intersection.getWidth(), intersection.getY());
        Position i4 = new Position (intersection.getX(), intersection.getY());

//        Position r1 = new Position (rectA.getX(), rectA.getY() + rectA.getHeight());
        Position r2 = new Position (rectA.getX() + rectA.getWidth(), rectA.getY() + rectA.getHeight());
        //Position r3 = new Position (rectA.getX() + rectA.getWidth(), rectA.getY());
        Position r4 = new Position (rectA.getX(), rectA.getY());

        //top
        Segment top = null;
        if ((int)i4.getY() == (int)r4.getY()) {
            top = new Segment(i4,i3);
        }
        //left
        Segment left = null;
        if ((int)i4.getX() == (int)r4.getX()) {
            left = new Segment(i4,i1);
        }
        //right
        Segment right = null;
        if ((int)i2.getX() == (int)r2.getX()) {
            right = new Segment(i2,i3);
        }
        //bottom
        Segment bottom = null;
        if ((int)i2.getY() == (int)r2.getY()) {
            bottom = new Segment(i1,i2);
        }

        return max(max(max(left, right), bottom),top);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Segment segment = (Segment) o;

        if (a != null ? !a.equals(segment.a) : segment.a != null) {
            return false;
        }
        if (b != null ? !b.equals(segment.b) : segment.b != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    public Position getA() {
        return a;
    }

    public Position getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
