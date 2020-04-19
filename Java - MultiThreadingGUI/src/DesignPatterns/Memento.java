package DesignPatterns;

import vehicles.Vehicle;

/**
 * Memento Design pattern Class
 * 
 * @author benja
 *
 */
public class Memento {

	private Vehicle vehicle;
	
	public Memento(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public Vehicle getState() {
		return this.vehicle;
	}
}
