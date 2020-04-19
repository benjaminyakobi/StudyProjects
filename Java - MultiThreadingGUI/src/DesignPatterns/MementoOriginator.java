package DesignPatterns;

import vehicles.Vehicle;

/**
 * Memento Design pattern Class
 * 
 * @author benja
 *
 */
public class MementoOriginator {

	private Vehicle vehicle;

	public void setState(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Vehicle getState() {
		return this.vehicle;
	}

	public Memento saveStateToMemento() {
		return new Memento(this.vehicle);
	}

	public void getStateFromMemento(Memento memento) {
		this.vehicle = memento.getState();
	}
}
