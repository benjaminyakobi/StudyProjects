package vehicles;

/**
 * extends abstract class Engine
 * 
 * @author benja
 *
 */
public class SolarEngine extends Engine {

	/* Class fields */
	private final static int SolarEngineBenzeneUsagePrKm = 1;

	/**
	 * Default constructor
	 */
	public SolarEngine() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param ContainerCapacity        for BenzeneContainerCapacity
	 * @param CurrentContainerCapacity - initialized to full container
	 */
	public SolarEngine(int SolarContainerCapacity) {
		/* BenzeneUsagePrMile is 1 in this class */
		super(SolarEngineBenzeneUsagePrKm, SolarContainerCapacity);
	}

	/**
	 * toString method for SolarEngine
	 */
	@Override
	public String toString() {
		return "Solar";
	}

	/**
	 * Getter
	 * @return static field SolarEngineBenzeneUsagePrKm
	 */
	public static int GetSolarEngineBenzeneUsagePrKm() {
		return SolarEngineBenzeneUsagePrKm;
	}

}
