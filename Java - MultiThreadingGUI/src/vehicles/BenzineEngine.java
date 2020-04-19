package vehicles;

/**
 * extends abstract class Engine
 * 
 * @author benja
 *
 */
public class BenzineEngine extends Engine {

	/* Class fields */
	private final static int BenzeneEngineBenzeneUsagePrKm = 2;

	/**
	 * Default constructor
	 */
	public BenzineEngine() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param ContainerCapacity for BenzeneContainerCapacity
	 */
	public BenzineEngine(int BenzeneContainerCapacity) {
		super(BenzeneEngineBenzeneUsagePrKm, BenzeneContainerCapacity);
	}

	/**
	 * toString method for BenzineEngine
	 */
	@Override
	public String toString() {
		return "Benzine";
	}

	/**
	 * Getter
	 * 
	 * @return BenzeneEngineBenzeneUsagePrKm
	 */
	public static int GetBenzeneEngineBenzeneUsagePrKm() {
		return BenzeneEngineBenzeneUsagePrKm;
	}

}
