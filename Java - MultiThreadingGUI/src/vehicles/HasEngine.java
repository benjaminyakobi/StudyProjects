package vehicles;

/**
 * abstract class HasEngine
 * 
 * @author benja
 */
public abstract class HasEngine extends Vehicle {

	/* Class fields */
	private Engine engine;
	private int BenzeneQuantity;

	/**
	 * Default constructor
	 */
	public HasEngine() {
		super();
		this.engine = new SolarEngine(); /* Default */
		this.BenzeneQuantity = this.engine.GetContainerCapacity(); /* Default */
	}

	/**
	 * @param SpecificEngineType for engine (Solar or Benzene)
	 * @param BQuantity          for BenzeneQuantity
	 * @param MinimalAge         for DrivingMinimalAgeLimitation
	 * @param VPlate             for Vehicle constructor
	 * @param Vcolor             for Vehicle constructor
	 * @param VWheelNum          for Vehicle constructor
	 * @param VMileage           for Vehicle constructor
	 * @param VLights            for Vehicle constructor
	 * @param Vloc               for Vehicle constructor
	 */
	public HasEngine(Engine SpecificEngineType, int BQuantity, int VPlate, Color Vcolor, int VWheelNum, float VMileage,
			boolean VLights, Location Vloc, boolean borderFlag) {

		super(VPlate, Vcolor, VWheelNum, VMileage, VLights, Vloc, borderFlag);
		this.engine = SpecificEngineType;
		this.BenzeneQuantity = Math.min(BQuantity, this.engine.GetContainerCapacity());
	}

	/**
	 * toString method for HasEngine class
	 */
	@Override
	public String toString() {
		return super.toString() + this.engine.toString() + "[ Current Benzene Qunatity in car container: "
				+ this.BenzeneQuantity + "Liters ]\n";
	}

	/**
	 * Getter
	 * 
	 * @return engine
	 */
	public Engine GetEngine() {
		return this.engine;
	}

	/**
	 * Getter
	 * 
	 * @return BenzeneQuantity
	 */
	public int GetBenzeneQuantity() {
		return this.BenzeneQuantity;
	}

	/**
	 * Setter
	 * 
	 * @param UpdatedQuantity is set into: this.BenzeneQuantity
	 * @return true for every method call
	 */
	public boolean SetBenzeneQuantity(int UpdatedQuantity) {
		this.BenzeneQuantity = Math.min(Car.GetCarContainerCapacity(), UpdatedQuantity);
		return true;
	}

	/**
	 * this method refuels the HasEngine Vehicles
	 * 
	 * @return true if the container wasn't full or false if container was full
	 */
	public boolean Refuel() {

		if (this.BenzeneQuantity == (Car.GetCarContainerCapacity())) {
			return false;
		}

		else {
			this.BenzeneQuantity = this.engine.GetContainerCapacity();
			return true;
		}
	}

}
