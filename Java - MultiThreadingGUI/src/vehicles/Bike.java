package vehicles;

import graphics.CityPanel;

/**
 * extends abstract class Vehicle
 * 
 * @author benja
 */
public class Bike extends Vehicle {

	/* Class fields */
	private final static int BikeSpeedPrSecond = 2;
	private final static int BikeWheels = 2;
//	private final static int BikeSeats = 1;
	private final int bikeDurability = 100;

	private int BikeNumberOfGears;

	/**
	 * Default constructor
	 */
	public Bike() {
		super();
		this.BikeNumberOfGears = 1;
	}

	/**
	 * Constructor
	 * 
	 * @param BLicenseP  for VehicleLicensePlate
	 * @param BColor     for VehicleColor
	 * @param BWheels    for VehicleMileage
	 * @param BMileage   for VehicleMileage
	 * @param BLights    for VehicleLights
	 * @param BLocation  for VehicleLocation
	 * @param GearNumber
	 */
	public Bike(int BLicenseP, Color BColor, int BWheels, float BMileage, boolean BLights, Location BLocation,
			int GearNumber, boolean borderFlag) {
		super(BLicenseP, BColor, BWheels, BMileage, BLights, BLocation, borderFlag);
		this.BikeNumberOfGears = GearNumber;

	}

	/**
	 * Copy constructor Assigned for deep copy
	 * 
	 * @param copyBike is the object that we copy
	 */
	public Bike(Bike copyBike) {
		this(copyBike.getVehicleLicensePlate(), copyBike.getColorColor(), getBikeWheels(),
				copyBike.VehicleMileage, copyBike.GetVehicleLights(), new Location(copyBike.VehicleLocation),
				copyBike.BikeNumberOfGears, copyBike.getBorderFlag());
	}

	/**
	 * Runnable method
	 */
	@Override
	public void run() {

		while (true) {

			updateMainTable(); /* Setting updated data into info-table */
			move(nextLocation());
			if (CityPanel.hash.containsKey(this) && CityPanel.hash.get(this).isCancelled()) {
				break;
			}

			try {
				Thread.sleep(ArbitraryTimeToSleep / BikeSpeedPrSecond);
			} catch (InterruptedException e) {
			}

		} /* end of while (true) */

	} /* end of public void run () */

	/**
	 * Bike's toString method
	 */
	@Override
	public String toString() {
		return super.toString() + "[ Speed: " + BikeSpeedPrSecond + ", Gears: " + this.BikeNumberOfGears + " ]\n";
	}

	/**
	 * drive method for Bike type. sums the distance and update the fuelConsumption!
	 */
	public boolean drive(Point point) {

		int Distance = Point.DistanceBetweenTwoPoints(point, this.previousVehicleLocation.GetPoint());
		this.VehicleMileage = this.VehicleMileage + Distance;
		this.fuelConsumption += Distance;
		return true;
	}

	/**
	 * static Getter
	 * 
	 * @return BikeSpeedPrSecond
	 */
	public static int getBikeSpeedPrSecond() {
		return BikeSpeedPrSecond;
	}

	/**
	 * Getter
	 * 
	 * @return BikeWheels
	 */
	public static int getBikeWheels() {
		return BikeWheels;
	}

	public Color getColorColor() {
		return super.getVehicleColor();
	}

	// *********** Interface's Implementations! **********//

	/**
	 * return bike durability
	 */
	@Override
	public int getDurability() {
		return this.bikeDurability;
	}

	/**
	 * @return Vehicle's color (in this case vehicle type is bike)
	 */
	@Override
	public String getColor() {
		return super.getVehicleColor().toString();
	}

	/**
	 * @return name of the Vehicle which is Bike.
	 */
	@Override
	public String getVehicleName() {
		return "Bike";
	}

	/**
	 * non-static Getter
	 * 
	 * @return Speed of this specific Vehicle
	 */
	@Override
	public int getSpeed() {
		return BikeSpeedPrSecond;
	}

	/**
	 * Bike doesn't have fuel consumption thus we return 0
	 * 
	 * @return integer value : 0
	 */
	@Override
	public int getFuelConsumption() {
		return 0;
	}

}
