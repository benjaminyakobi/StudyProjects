package graphics;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;


import DesignPatterns.Observer;
import DesignPatterns.VehicleFactory;
import vehicles.Bike;
import vehicles.Car;
import vehicles.Carriage;
import vehicles.PackAnimal;
import vehicles.Vehicle;

/**
 * @author benja
 *
 */
public class AddVehicleDialog extends JDialog {

	/***** Class fields *****/

	/* JOptionDialog */
	private String[] vehicleOptions = { "BenzineCar", "SolarCar", "Carriage", "Bike" };
	private String[] colorOptions = { "Red", "Green", "White", "Silver" };

	/* JSlider */
	private JSlider slider = new JSlider(1, 10);
	private JPanel sliderPanel = new JPanel();
	private int sliderGearValue;

	/* Data structure */
	public static ArrayList<Vehicle> vehiclesArrayList = new ArrayList<Vehicle>();
	public static ArrayList<Vehicle> vaultVehiclesArrayList = new ArrayList<Vehicle>();

	/* ThreadPool & Observer */
	public static ExecutorService exe = Executors.newFixedThreadPool(5);
	private Observer ob = Vehicle.pan;

	/**
	 * Constructor of a dialogs which we choose our Vehicle and it's Color. After
	 * every choose of Vehicle & Car we create it by the vehicle factory.
	 */
	public AddVehicleDialog() {
		if (vaultVehiclesArrayList.size() < 5) {
			/* First dialog box */
			String vehicleChoiceFromBox = (String) JOptionPane.showInputDialog(null, "Choose vehicle...",
					"Create Vehicle Window", JOptionPane.QUESTION_MESSAGE, null, vehicleOptions, vehicleOptions[0]);

			/* Slider for bike */
			this.slider.setMajorTickSpacing(1);
			this.slider.setPaintTicks(true);
			this.slider.setPaintLabels(true);
			this.slider.setValue(5);
			this.sliderPanel.add(this.slider);

			/* Second dialog box (only for bikes) */
			if (vehicleChoiceFromBox == "Bike") {
				int dialogResponse = JOptionPane.showOptionDialog(this, this.sliderPanel, "Choose gears",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (JOptionPane.OK_OPTION == dialogResponse) {
					sliderGearValue = slider.getValue();
				} else {
					sliderGearValue = 5;
				}
			}

			/* Second dialog box for car & carriage / third dialog box for bike */
			String colorChoiceFromBox = (String) JOptionPane.showInputDialog(null, "Choose color...",
					"Create Vehicle Window", JOptionPane.QUESTION_MESSAGE, null, colorOptions, colorOptions[0]);

			/* if the user have choose some vehicle */
			if (colorChoiceFromBox != null && vehicleChoiceFromBox != null) {
				Vehicle.RandomIdAndAccessToVehicle++;

				/* we call factory method & initiate info table with the information */
				initiateDataStructures(vehicleChoiceFromBox, Vehicle.RandomIdAndAccessToVehicle, colorChoiceFromBox,
						VehicleFactory.getVehicle(colorChoiceFromBox, vehicleChoiceFromBox, sliderGearValue));
			}

		} /* end of if (vaultVehiclesArrayList.size() < 5) */

	} /* end of Constructor */

	/**
	 * Service method. This method initiate data structures with all the necessary
	 * information.
	 * 
	 * @param vehicleChoiceFromBox which is the type of the vehicle (String)
	 * @param id                   (Integer)
	 * @param color                (String)
	 * @param vehicle              (Vehicle)
	 */
	private boolean initiateDataStructures(String vehicleChoiceFromBox, int id, String color, Vehicle vehicle) {
		/* setting vehicle's info into JTable */

		if (vehicle instanceof Car) {
			/* setting vehicle's info into JTable */
			CityPanel.defaultMainTable.addRow(new Object[] { vehicleChoiceFromBox, Integer.toString(id), color,
					Integer.toString(Car.GetCarNumberOfWheels()), Integer.toString(Car.GetCarSpeed()) + " KM/H",
					Integer.toString(((Car) vehicle).GetBenzeneQuantity()) + " Liters",
					Float.toString(vehicle.GetVehicleMileage()) + " Kilometers",
					Integer.toString(vehicle.getFuelConsumption()) + " Liters",
					Boolean.toString(vehicle.GetVehicleLights()), "On Panel", vehicle.getBorderFlag() });

			vaultVehiclesArrayList.add(vehicle); /* adding data into data-structures */
			CityPanel.mainTable.setValueAt("Pending on Queue", vehicle.getVehicleLicensePlate() - 1001,
					9); /* updating the status of this vehicle */

			vehicle.loadImages(color + "CarEast.png"); /* Loading vehicle image */
			((Car) vehicle).registerObserver(this.ob); /* Register Observer to check fuel quantity */
		} /* end instance of Car */

		if (vehicle instanceof Carriage) {
			/* setting vehicle's info into JTable */
			CityPanel.defaultMainTable.addRow(new Object[] { vehicleChoiceFromBox, Integer.toString(id), color,
					Integer.toString(Car.GetCarNumberOfWheels()), Integer.toString(Car.GetCarSpeed()) + " KM/H",
					Integer.toString(PackAnimal.getMaximumAnimalEnergy()) + " Energy",
					Float.toString(((Carriage) vehicle).GetVehicleMileage()) + " Kilometers",
					Integer.toString(((Carriage) vehicle).getFuelConsumption()) + " Liters",
					Boolean.toString(((Carriage) vehicle).GetVehicleLights()), "On Panel",
					((Carriage) vehicle).getBorderFlag() });

			vaultVehiclesArrayList.add(vehicle); /* adding data into data-structures */
			CityPanel.mainTable.setValueAt("Pending on Queue", vehicle.getVehicleLicensePlate() - 1001,
					9); /* updating the status of this vehicle */

			vehicle.loadImages(color + "CarriageEast.png"); /* Loading vehicle image */
			((Carriage) vehicle).registerObserver(this.ob); /* Register Observer to check fuel quantity */
		} /* end instance of Carriage */

		if (vehicle instanceof Bike) {
			/* setting vehicle's info into JTable */
			CityPanel.defaultMainTable.addRow(new Object[] { vehicleChoiceFromBox, Integer.toString(id), color,
					Integer.toString(Bike.getBikeWheels()), Integer.toString(Bike.getBikeSpeedPrSecond()) + " KM/H",
					"0 - No fuel", Float.toString(((Bike) vehicle).GetVehicleMileage()) + " Kilometers", "0 - No fuel",
					Boolean.toString(((Bike) vehicle).GetVehicleLights()), "On Panel",
					((Bike) vehicle).getBorderFlag() });

			vaultVehiclesArrayList.add(vehicle); /* adding data into data-structures */
			CityPanel.mainTable.setValueAt("Pending on Queue", vehicle.getVehicleLicensePlate() - 1001,
					9); /* updating the status of this vehicle */
			vehicle.loadImages(color + "BikeEast.png"); /* Loading vehicle image */
		} /* end instance of Bike */
		
		return true;

	}

}