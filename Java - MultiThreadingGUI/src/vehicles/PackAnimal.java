package vehicles;

import graphics.IAnimal;
import graphics.IClonable;

/**
 * 
 * @author benja
 *
 */
public class PackAnimal implements IAnimal, IClonable {

	/* Class fields */
	private final static int MaximumAnimalEnergy = 10000; /*
															 * Changed to 10,000 instead of 1,000 to simulate the
															 * driving
															 */
	private String AnimalName;
	private int AnimalEnergy;
	private final int carriageDurability = 200;

	/**
	 * default constructor
	 */
	public PackAnimal() {
		this.AnimalName = "PackAnimal";
		this.AnimalEnergy = MaximumAnimalEnergy;
	}

	/**
	 * constructor
	 * 
	 * @param newAnimalName : string - defines the animal name
	 */
	public PackAnimal(String newAnimalName, int AnimalEnergy) {
		this.AnimalName = newAnimalName;
		this.AnimalEnergy = Math.min(AnimalEnergy, MaximumAnimalEnergy);

	}

	/**
	 * toString method of class PackAnimal
	 */
	public String toString() {
		return "[ AnimalName: " + this.AnimalName + ", AnimalEnergy: " + this.AnimalEnergy + " ]\n";
	}

	/**
	 * getter
	 * 
	 * @return AnimalEnergy for class Carriage to implement move method
	 */
	public int getAniamelEnergy() {
		return this.AnimalEnergy;
	}

	/**
	 * Setter
	 * 
	 * @param newAnimalEnergy set into this.AnimalEnergy
	 * @return true for every method call
	 */
	public boolean setAnimalEnergy(int newAnimalEnergy) {
		this.AnimalEnergy = newAnimalEnergy;
		return true;
	}

	/**
	 * @return the maximum animal energy
	 */
	public static int getMaximumAnimalEnergy() {
		return MaximumAnimalEnergy;
	}

	/**
	 * Getter
	 * 
	 * @return this.AnimalEnergy
	 */
	public int getAnimalEnergy() {
		return this.AnimalEnergy;

	}

	// *********** Interface's Implementations! **********//

	/**
	 * return carriage durability
	 */
	@Override
	public int getDurability() {
		return this.carriageDurability;
	}

	/**
	 * this method has also been implemented in class Carriage
	 */
	@Override
	public String getVehicleName() {
		return "PackAnimal";
	}

	/**
	 * this method has been implemented in class Carriage
	 */
	@Override
	public int getSpeed() {
		return 0;
	}

	/**
	 * Carriage doesn't have fuel consumption thus we return 0 also this method has
	 * been implemented in class Carriage
	 * 
	 * @return integer value : 0
	 */
	@Override
	public int getFuelConsumption() {
		return 0;
	}

	/**
	 * this method has been implemented in class Carriage
	 */
	@Override
	public boolean move(Point p) {
		return true;
	}

	/**
	 * @return Animal's name that pull the carriage
	 */
	@Override
	public String getAnimalName() {
		return this.AnimalName;
	}

	/**
	 * 
	 * set the AnimalEnergy to the max and return true
	 */
	@Override
	public boolean eat() {
		this.AnimalEnergy = MaximumAnimalEnergy;
		return true;
	}

}
