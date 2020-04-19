package vehicles;

import DesignPatterns.ObservableVehicles;
import DesignPatterns.Observer;
import graphics.CityPanel;
import graphics.IAnimal;
import graphics.IClonable;

/**
 * extends abstract class Vehicle
 * 
 * @author benja
 */
public class Carriage extends Vehicle implements IAnimal, IClonable, ObservableVehicles {

	/* Class fields */
	private final static int CarriageSpeedPrSecond = 1;
	private final static int CarriageNumberOfWheels = 4;
//	private final static int CarriageSeats = 2;
	private final static int EnergyPrKm = 20;
	private PackAnimal AnimalPullCarriage;

	/**
	 * Default constructor
	 */
	public Carriage() {
		super();
		this.AnimalPullCarriage = new PackAnimal(); /* default option */
	}

	/**
	 * Constructor
	 * 
	 * @param CLicenseP          for VehicleLicensePlate
	 * @param CColor             for VehicleColor
	 * @param CMileage           for VehicleMileage
	 * @param CLights            for VehicleLights
	 * @param CLocation          for VehicleLocation
	 * @param AnimalPullCarriage - name of the animal that pulls the carriage
	 */
	public Carriage(int CLicenseP, Color CColor, float CMileage, boolean CLights, Location CLocation, String AnimalName,
			int animalEnergy, boolean borderFlag) {
		super(CLicenseP, CColor, CarriageNumberOfWheels, CMileage, CLights, CLocation, borderFlag);
		this.AnimalPullCarriage = new PackAnimal(AnimalName, animalEnergy);

	}

	/**
	 * Copy constructor Assigned for deep copy
	 * 
	 * @param copyCarriage is the object that we copy
	 */
	public Carriage(Carriage copyCarriage) {
		this(copyCarriage.getVehicleLicensePlate(), copyCarriage.VehicleColor, copyCarriage.VehicleMileage,
				copyCarriage.GetVehicleLights(), new Location(copyCarriage.GetLocation()), copyCarriage.getAnimalName(),
				copyCarriage.getAnimalPullCarriage().getAnimalEnergy(), copyCarriage.getBorderFlag());
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
				Thread.sleep(ArbitraryTimeToSleep / CarriageSpeedPrSecond);
			} catch (InterruptedException e) {
			}

		} /* end of while (true) */

	} /* end of public void run () */

	/**
	 * Carriage toString method
	 */
	@Override
	public String toString() {
		return super.toString() + "[ Speed: " + CarriageSpeedPrSecond + "KM/H ]\n" + this.AnimalPullCarriage.toString();
	}

	public boolean drive(Point point) {

		this.AnimalPullCarriage.setAnimalEnergy(this.AnimalPullCarriage.getAniamelEnergy()
				- EnergyPrKm * Point.DistanceBetweenTwoPoints(point, this.getPreviousVehicleLocation().GetPoint()));

		if (this.getAnimalPullCarriage().getAniamelEnergy() == 0) {
			this.notifyObservers("Empty");
			return false;

		}

		int Distance = Point.DistanceBetweenTwoPoints(point, this.previousVehicleLocation.GetPoint());
		this.VehicleMileage = this.VehicleMileage + Distance;

		this.fuelConsumption += Distance * EnergyPrKm;

		return true;

	}

	/**
	 * Getter
	 */
	public PackAnimal getAnimalPullCarriage() {
		return this.AnimalPullCarriage;
	}

	// *********** Interface's Implementations! ********** //

	/**
	 * return carriage durability
	 */
	@Override
	public int getDurability() {
		return this.AnimalPullCarriage.getDurability();
	}

	@Override
	public String getAnimalName() {
		return "PackAnimal";
	}

	@Override
	public boolean eat() {
		this.AnimalPullCarriage.setAnimalEnergy(PackAnimal.getMaximumAnimalEnergy());
		return false;
	}

	@Override
	public int getSpeed() {
		return CarriageSpeedPrSecond;
	}

	@Override
	public String getVehicleName() {
		return "Carriage";
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
