package vehicles;


/**
 * @author benja class Location which meant to represent Orientation on 2D map.
 */
public class Location {

	/* Class fields */
	private Point point;
	private Orientation VDirection;

	/**
	 * Default constructor
	 */
	public Location() {
		this.VDirection = Orientation.EAST; /* default */
		this.point = new Point();
	}
	
	public Location(Location copyLocation) {
		this(copyLocation.GetPoint(), copyLocation.GetVDirection());
	}
	
	/**
	 * Constructor
	 * 
	 * @param newPoint            for point
	 * @param newDirection
	 */
	public Location(Point newPoint, Orientation newDirection) {
		this.VDirection = newDirection;
		this.point = new Point(newPoint.GetX(), newPoint.GetY());
	}

	/**
	 * toString Method
	 */
	public String toString() {
		return "[ " + this.point.toString() + ", Direction : " + this.VDirection + " ]\n";
	}

	/**
	 * Getter of this.point
	 * 
	 * @return: Location's point
	 */
	public Point GetPoint() {
		return this.point;
	}

	/**
	 * Setter to set (x, y) values into Location's point
	 * 
	 * @param newX
	 * @param newY
	 * @return
	 */
	protected boolean SetPoint(int newX, int newY) {
		this.point.SetX(newX);
		this.point.SetY(newY);
		return true;
	}

	/**
	 * Setter
	 * @param newDirection set into this.VDirection
	 * @return true for every method call
	 */
	protected boolean SetVDirection(Orientation newDirection) {
		this.VDirection = newDirection;
		return true;
	}

	/**
	 * Getter
	 * @return this.VDirection which represent the orientation of a given vehicle
	 */
	public Orientation GetVDirection() {
		return this.VDirection;
	}
}
