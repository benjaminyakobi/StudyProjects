package DesignPatterns;

import vehicles.BenzineEngine;
import vehicles.Bike;
import vehicles.Car;
import vehicles.Carriage;
import vehicles.Color;
import vehicles.Location;
import vehicles.Orientation;
import vehicles.PackAnimal;
import vehicles.Point;
import vehicles.SolarEngine;
import vehicles.Vehicle;

/**
 * Class VehicleFactory implements the Factory Method Design Pattern concept.
 * 
 * @author benja
 *
 */
public class VehicleFactory {

	/**
	 * The factory
	 * 
	 * @param color     represent the vehicle's color
	 * @param type      represent the vehicle's type
	 * @param gearValue represent the number of gears (only for Bike)
	 * @return
	 */
	public static Vehicle getVehicle(String color, String type, int gearValue) {

		/*
		 * here we extract the specific Color into global variable to avoid multiple if
		 * statements using Color[] & chosenColor
		 */
		Color[] colors = Color.values();
		Color chosenColor = null;
		for (Color c : colors) {
			if (c.toString().contains(color.toUpperCase())) {
				chosenColor = c;
			}
		}

		if (type.contains("SolarCar")) {
			return new Car(new SolarEngine(Car.GetCarContainerCapacity()), Car.GetCarContainerCapacity(),
					Vehicle.RandomIdAndAccessToVehicle, chosenColor, 0, false,
					new Location(new Point(0, 0), Orientation.EAST), 0, false);
		}

		if (type.contains("BenzineCar")) {
			return new Car(new BenzineEngine(Car.GetCarContainerCapacity()), Car.GetCarContainerCapacity(),
					Vehicle.RandomIdAndAccessToVehicle, chosenColor, 0, false,
					new Location(new Point(0, 0), Orientation.EAST), 0, false);
		}

		if (type.contains("Carriage")) {
			return new Carriage(Vehicle.RandomIdAndAccessToVehicle, chosenColor, 0, false,
					new Location(new Point(0, 0), Orientation.EAST), "Horse", PackAnimal.getMaximumAnimalEnergy(),
					false);
		}

		if (type.contains("Bike")) {
			return new Bike(Vehicle.RandomIdAndAccessToVehicle, chosenColor, 2, 0, false,
					new Location(new Point(0, 0), Orientation.EAST), 10, false);
		}

		return null;

	}

}
