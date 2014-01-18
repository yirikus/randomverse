package cz.terrmith.randomverse.core.geometry;

/**
 * Container for 4-neighbourhood - holds objects that are top, left, right and bottom
 */
public class NHood4<T> {

    private T top;
    private T left;
    private T right;
    private T bottom;

    public NHood4(T top, T left, T right, T bottom) {
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

    public T getTop() {
        return top;
    }

    public T getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }

    public T getBottom() {
        return bottom;
    }
}
