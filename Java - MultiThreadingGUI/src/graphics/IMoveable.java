package graphics;

import vehicles.Point;

/**
 * 
 * @author benja
 *
 */
public interface IMoveable {

	public String getVehicleName();

	public int getSpeed();

	public int getFuelConsumption();

	public boolean move(Point p);

	/* Homework-3 additions */
	public int getDurability(); /*
								 * Durability: BenzineCar == SolarCar > Carriage > Bike
								 * Cars Durability = 300
								 * Carriage Durability = 200 (PackAnimal class)
								 * Bike Durability = 100
								 */

}
