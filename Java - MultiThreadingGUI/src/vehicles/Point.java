package vehicles;

import graphics.IClonable;

/**
 * class Point which represents point (x,y) on 2D map.
 * @author benja
 */
public class Point implements IClonable {

	/* Class fields */
	private int x;
	private int y;

	/**
	 * Default Constructor
	 */
	public Point() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructor
	 * 
	 * @param newX: int type
	 * @param newY: int type
	 */
	public Point(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	}

	/**
	 * toString Method
	 */
	public String toString() {
		return "Point: (" + this.x + ", " + this.y + ")";
	}

	/**
	 * This method meant to calculate the distance between to given points.
	 * 
	 * @param p1: first Point
	 * @param p2: second Point
	 * @return: Distance between two points by this calculation: |x1 - x2| + |y1 -
	 *          y2|
	 */
	public static int DistanceBetweenTwoPoints(Point p1, Point p2) {
		Integer newX = p1.GetX() - p2.GetX();
		Integer newY = p1.GetY() - p2.GetY();
		return Math.abs(newX) + Math.abs(newY);
	}

	/**
	 * Setter
	 * 
	 * @param newX: new value for this.x
	 * @return true value if succeed
	 */
	protected boolean SetX(int newX) {
		this.x = newX;
		return true;
	}

	/**
	 * Setter
	 * 
	 * @param newY: new value for this.y
	 * @return true value if succeed
	 */
	protected boolean SetY(int newY) {
		this.y = newY;
		return true;
	}

	/**
	 * Getter
	 * 
	 * @return value of this.x
	 */
	public int GetX() {
		return this.x;
	}

	/**
	 * Getter
	 * 
	 * @return value of this.y
	 */
	public int GetY() {
		return this.y;
	}

}
