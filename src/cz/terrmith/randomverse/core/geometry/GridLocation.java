package cz.terrmith.randomverse.core.geometry;

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
}
