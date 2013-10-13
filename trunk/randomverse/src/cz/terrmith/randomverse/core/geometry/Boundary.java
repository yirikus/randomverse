package cz.terrmith.randomverse.core.geometry;

/**
 * Represents top, bottom, left, right boundary
 *
 * Origin [0,0] is in top left corner
 */
public class Boundary {
    private int top;
    private int bottom;
    private int left;
    private int right;

    public Boundary(int left, int right, int top, int bottom) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    /**
     * Returns true if given position is inside of this boundary
     * @param p
     * @return
     */
    public boolean withinBoundary(Position p){
        return (int)p.getX() > getLeft() && (int)p.getX() < getRight()
            && (int)p.getY() > getTop()  && (int)p.getY() < getBottom();
    }

    @Override
    public String toString() {
        return "Boundary{" +
                "top=" + top +
                ", bottom=" + bottom +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
