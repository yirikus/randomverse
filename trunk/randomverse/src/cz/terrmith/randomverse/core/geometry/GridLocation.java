package cz.terrmith.randomverse.core.geometry;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jiri.kus
 */
public class GridLocation {
	private int x;
	private int y;

	public GridLocation(int x, int y) {
		this.y = y;
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GridLocation)) {
			return false;
		}

		GridLocation that = (GridLocation) o;

		if (x != that.x) {
			return false;
		}
		if (y != that.y) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y +"]";
	}

    /**
     * Returns relative position of given point to given set of points
     * @param loc
     * @param list
     * @return
     */
    public static RelativePosition getRelativePositionTo(GridLocation loc, Collection<GridLocation> list) {
        //4 - hood
        GridLocation top = new GridLocation(loc.getX(), loc.getY() - 1);
        GridLocation bottom = new GridLocation(loc.getX(), loc.getY() + 1);
        GridLocation left = new GridLocation(loc.getX() - 1, loc.getY());
        GridLocation right = new GridLocation(loc.getX() + 1, loc.getY());

        // 8 hood
        GridLocation topLeft = new GridLocation(loc.getX() - 1, loc.getY() - 1);
        GridLocation topRight = new GridLocation(loc.getX() -1, loc.getY() + 1);
        GridLocation bottomLeft = new GridLocation(loc.getX() + 1,loc.getY() - 1);
        GridLocation bottomRight = new GridLocation(loc.getX() + 1,loc.getY() + 1);

        Set<RelativePosition> positions = new HashSet<RelativePosition>();

        for (GridLocation entry : list) {
           if (entry.equals(loc)) {
               positions.add(RelativePosition.CONTAINS);
           } else if (entry.equals(top) || entry.equals(bottom) || entry.equals(left) || entry.equals(right)) {
               positions.add(RelativePosition.NEIGHBOURHOOD_4);
           } else if (entry.equals(topLeft) || entry.equals(bottomLeft) || entry.equals(topRight) || entry.equals(bottomRight)) {
               positions.add(RelativePosition.NEIGHBOURHOOD_8);
           }
        }

        // since one location can have multiple relative positions, priority must be defined
        if (positions.contains(RelativePosition.CONTAINS)) {
            return RelativePosition.CONTAINS;
        } else  if (positions.contains(RelativePosition.NEIGHBOURHOOD_4)) {
            return RelativePosition.NEIGHBOURHOOD_4;
        } else  if (positions.contains(RelativePosition.NEIGHBOURHOOD_8)) {
            return RelativePosition.NEIGHBOURHOOD_8;
        } else {
            return RelativePosition.NOT_CONTAINS;
        }
    }
}
