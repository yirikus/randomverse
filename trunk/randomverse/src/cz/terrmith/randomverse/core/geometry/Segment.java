package cz.terrmith.randomverse.core.geometry;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

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
     * Returnes normalized vector as segment,
     * @see Position.normalizedVector(a,b)
     * @param ab
     * @return
     */
    public static Segment normalizedVector(Segment ab) {
        return new Segment(new Position(0,0), Position.normalizedVector(ab));
    }

    /**
     * Returns segment translated to [0,0] (A is [0,0])
     * @param ab
     * @return
     */
    public static Segment zeroBased(Segment ab){
        return new Segment(new Position(0,0),
                           Position.difference(ab.getB(), ab.getA()));
    }

    /**
     * Return segment perpendicular to this segment (CCW 90 degree rotation)
     * @param a segment
     * @return perpendicular segment
     */
    public static Segment perpendicularSegment(Segment ab) {
        // translate to [0,0]
        Position zeroBased = Position.difference(ab.getB(), ab.getA());
        Position perpendicular = new Position(-zeroBased.getY(),
                                              zeroBased.getX());
        //move back to original position
        return new Segment(ab.getA(), Position.sum(perpendicular, ab.getA()));
    }

    /**
     * Returns: true if this segment intersects line defined by given segment
     * @param line line defined as segment
     * @return true if intersects
     */
    public boolean intersectsLine(Segment line) {


        int ay = (int) lineEquation(a.getX(), a.getY(), line);
        int by = (int) lineEquation(b.getX(), b.getY(), line);


        // one of the points lies on line
        if (ay == 0.0 || by == 0.0) {
            return true;
        }
        // XOR
        return ( ay < 0) ^ (by < 0);
    }

    /**
     * Return lenght of the segment
     * @return
     */
    public double length() {
        return a.distanceFrom(b);
    }

    /**
     * assignes given x to the line equation created from given segment and returns y
     * @param x x to assign
     * @param line line defined by segment
     * @return y or 0.0 if ll points lie in one horizontal line
     */
    public static double lineEquation(double x, double y, Segment line) {
        if (line == null || line.getA().equals(line.getB())) {
            throw new IllegalArgumentException("line must not be null and bot segment points must not be equal, was: " + line);
        }
        // all points lie in one horizontal line
        if ((int)x == (int)line.a.getX() && (int)x == (int)line.b.getX()) {
            return 0.0;
        }

        double y0 = line.a.getY();
        double y1 = line.b.getY();
        double x0 = line.a.getX();
        double x1 = line.b.getX();
        double zeroCoef = 0.0;
        // avoids zero division
        if (x1 == x0){
            zeroCoef = 0.00001;
        }


       // return (y0 + ((y1 - y0)/(x1 - x0 + zeroCoef)) * (x - x0)) - y;
        //(Bx - Ax) * (Cy - Ay) - (By - Ay) * (Cx - Ax)
        return (x1 - x0) * (y - y0) - (y1 - y0)*(x - x0);
    }

    /**
     * Returns segment with larger length
     * @return segment or null
     */
    public static Segment max(Segment s1, Segment s2){
        if (s2 == null) {
            return s1;
        }

        if (s1 == null) {
            return s2;
        }

        if (s1.length() > s2.length()) {
            return s1;
        }

        return s2;
    }

    public boolean containsPosition(Position p) {
        double res = Line2D.ptSegDist(a.getX(), a.getY(),
                                      b.getX(), b.getY(),
                                      p.getX(), p.getY());
        return res == 0.0;
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
