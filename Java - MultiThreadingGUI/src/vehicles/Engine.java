package vehicles;

import graphics.IClonable;

/**
 * abstract class Engine
 * 
 * @author benja
 *
 */
public abstract class Engine implements IClonable{

	/* Class fields */
	private int BenzeneUsagePrMile;
	private int ContainerCapacity;

	/**
	 * Default constructor
	 */
	public Engine() {
		this.BenzeneUsagePrMile = 1; /* default */
		this.ContainerCapacity = 90; /* default */
	}

	/**
	 * Constructor
	 * 
	 * @param BenzenePrMile     for BenzeneUsagePrMile
	 * @param ContainerCapacity
	 */
	public Engine(int BenzenePrMile, int newContainerCapacity) {
		this.BenzeneUsagePrMile = BenzenePrMile;
		this.ContainerCapacity = newContainerCapacity;
	}

	/**
	 * toString method for Engine
	 */
	public String toString() {
		return "[ Container capacity: " + this.ContainerCapacity + "Lietrs, Benzene usage: " + this.BenzeneUsagePrMile + "Liters Pr KM ]\n";
	}

	/**
	 * Getter
	 * 
	 * @return BenzeneContainerCapacity
	 */
	public int GetContainerCapacity() {
		return this.ContainerCapacity;
	}

	/**
	 * Setter
	 * @param NewCapacity is set into: this.ContainerCapacity
	 * @return true for every method call
	 */
	protected boolean SetContainerCapacity(int NewCapacity) {
		this.ContainerCapacity = NewCapacity;
		return true;
	}

	/**
	 * Getter
	 * 
	 * @return BenzeneUsagePrMile
	 */
	public int GetBenzeneUsagePrMile() {
		return this.BenzeneUsagePrMile;
	}

}
