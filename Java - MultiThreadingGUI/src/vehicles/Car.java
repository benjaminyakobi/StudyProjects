package vehicles;

import DesignPatterns.ObservableVehicles;
import DesignPatterns.Observer;
import graphics.CityPanel;
import javafx.scene.layout.Border;

/**
 * extends abstract class HasEngine
 * 
 * @author benja
 *
 */
public class Car extends HasEngine implements ObservableVehicles {

	/* class fields */
	private final static int CarNumberOfWheels = 4;
	private final static int CarContainerCapacity = 400; /* Changed to 400 instead of 40 to simulate the driving */
	private final static int CarSpeed = 4;
//	private final static int CarSeats = 5;
	private final int carDurability = 300;

	/**
	 * Default constructor
	 */
	public Car() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param SpecificEngineType for HasEngine constructor
	 * @param BQuantity          HasEngine constructor
	 * @param VPlate             for HasEngine constructor (super in HasEngine)
	 * @param Vcolor             for HasEngine constructor (super in HasEngine)
	 * @param VWheelNum          for HasEngine constructor (super in HasEngine)
	 * @param VMileage           for HasEngine constructor (super in HasEngine)
	 * @param VLights            for HasEngine constructor (super in HasEngine)
	 * @param Vloc               for for HasEngine constructor (super in HasEngine)
	 */
	public Car(Engine SpecificEngineType, int BQuantity, int VPlate, Color Vcolor, float VMileage, boolean VLights,
			Location Vloc, int fuelConsumption, boolean borderFlag) {
		super(SpecificEngineType, BQuantity, VPlate, Vcolor, CarNumberOfWheels, VMileage, VLights, Vloc, borderFlag);
		this.fuelConsumption = fuelConsumption;
	}
	
	/**
	 * Copy constructor Assigned for deep copy
	 * 
	 * @param copyCar is the object that we copy
	 */
	public Car(Car copyCar) {
		this(copyCar.GetEngine(), copyCar.GetBenzeneQuantity(), copyCar.getVehicleLicensePlate(),
				copyCar.getVehicleColor(), copyCar.GetVehicleMileage(), copyCar.GetVehicleLights(),
				new Location(copyCar.GetLocation()), copyCar.getFuelConsumption(), copyCar.getBorderFlag());
	}
	
	/**
	 * Runnable method
	 */
	@Override
	public void run() {
		while (true) {

			updateMainTable(); /* Setting updated data into info-table */

			if (move(nextLocation()) == false) {
				try {
					if (CityPanel.hash.containsKey(this)) {
						synchronized (CityPanel.hash.get(this)) {
							CityPanel.hash.get(this).wait();
						}
					}
				} catch (InterruptedException e) {
				}
			}

			if (CityPanel.hash.containsKey(this) && CityPanel.hash.get(this).isCancelled()) {
				break;
			}

			try {
				Thread.sleep(ArbitraryTimeToSleep / CarSpeed);
			} catch (InterruptedException e) {
			}

		} /* end of while (true) */

	} /* end of public void run () */

	/**
	 * toString method
	 */
	@Override
	public String toString() {
		return super.toString() + "[ Speed: " + CarSpeed + " KM/H ]/n";
	}

	/**
	 * drive method, override of Vehicle.drive. in this method we updating the
	 * Benzene usage of every vehicle that have engine. return true if the car move
	 * from point a to b return false if the car doesn't move.
	 */
	@Override
	public boolean drive(Point point) {

		super.SetBenzeneQuantity(super.GetBenzeneQuantity() - this.GetEngine().GetBenzeneUsagePrMile()
				* Point.DistanceBetweenTwoPoints(point, this.previousVehicleLocation.GetPoint()));

		if (this.GetBenzeneQuantity() == 0) {

			this.notifyObservers("Empty");
			return false;
		}

		int Distance = Point.DistanceBetweenTwoPoints(point, this.previousVehicleLocation.GetPoint());

		this.VehicleMileage = this.VehicleMileage + Distance;

		if (this.GetEngine() instanceof BenzineEngine) {
			this.fuelConsumption += Distance * BenzineEngine.GetBenzeneEngineBenzeneUsagePrKm();
		}

		if (this.GetEngine() instanceof SolarEngine) {
			this.fuelConsumption += Distance * SolarEngine.GetSolarEngineBenzeneUsagePrKm();
		}

		return true;
	}

	/**
	 * Getter - public because there are no classes that inherit class Cae
	 * 
	 * @return static value of CarContainerCapacity
	 */
	public static int GetCarContainerCapacity() {
		return CarContainerCapacity;
	}

	/**
	 * Getter - public because there are no classes that inherit class Car
	 * 
	 * @return static value of CarNumberOfWheels
	 */
	public static int GetCarNumberOfWheels() {
		return CarNumberOfWheels;
	}

	/**
	 * Getter - public because there are no classes that inherit class Car
	 * 
	 * @return static value of CarSpeed
	 */
	public static int GetCarSpeed() {
		return CarSpeed;
	}

	// *********** Interface's Implementations! **********//
	/**
	 * return car durability
	 */
	@Override
	public int getDurability() {
		return this.carDurability;
	}

	/**
	 * non-static Getter
	 * 
	 * @return CarSpeed
	 */
	@Override
	public int getSpeed() {
		return CarSpeed;

	}

	/**
	 * non-static Getter
	 * 
	 * @return BenzineCar or SolarCar (depend on Engine type)
	 */
	@Override
	public String getVehicleName() {
		return this.GetEngine().toString() + "Car";
	}

	@Override
	public void registerObserver(Observer ob) {
		this.ObserversList.add(ob);

	}

	@Override
	public void unregisterObserver(Observer ob) {
		this.ObserversList.remove(this.ObserversList.lastIndexOf(ob));

	}

	@Override
	public void notifyObservers(String msg) {
		
		for (Observer ob : this.ObserversList) {
			ob.notifyFromObservables(msg);
		}

	}

}
