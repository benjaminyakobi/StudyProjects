package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import DesignPatterns.MementoCareTaker;
import DesignPatterns.MementoOriginator;
import DesignPatterns.Observer;
import vehicles.BenzineEngine;
import vehicles.Bike;
import vehicles.Car;
import vehicles.Carriage;
import vehicles.SolarEngine;
import vehicles.Vehicle;

/**
 * Singleton Class!
 * 
 * @author benja
 *
 */
public class CityPanel extends JPanel implements ActionListener, Observer {

	/* Table */
	public static JTable mainTable = new JTable();
	public static DefaultTableModel defaultMainTable = new DefaultTableModel(0, 0);
	private static String[] columnNames = { "Vehicle", "ID", "Color", "Wheels", "Speed", "Fuel Amount", "Distance",
			"Fuel Consumption", "Lights", "Status on panel", "Have Border?" };

	/* Helpers & Services & Panel */
	private JFrame InfoFrame = new JFrame("Info");
	private Hashtable<String, Integer> nameIdAndIndexHashtable = new Hashtable<String, Integer>();
	private static JScrollPane infoScrollPane = new JScrollPane(mainTable);
	public static Hashtable<Vehicle, Future<?>> hash = new Hashtable<Vehicle, Future<?>>();
	public static int index = 0;

	/* Buttons */
	private JButton AddVehicleButton = new JButton("Add Vehicle");
	private JButton ClearButton = new JButton("Clear");
	private JButton FuelFoodButton = new JButton("Fuel/Food");
	private JButton LightsButton = new JButton("Lights");
	private JButton InfoButton = new JButton("Info");
	private JButton VehicleBorderButton = new JButton("Vehicle Border");
	private JButton ExitButton = new JButton("Exit");
	private JButton setMementoButton = new JButton("Memento - SAVE STATE");
	private JButton getMementoButton = new JButton("Memento - RESTORE STATE");

	/* Memento & Singleton */
	private MementoOriginator originator = new MementoOriginator();
	private MementoCareTaker careTaker = new MementoCareTaker();
	private static CityPanel singlePanel = new CityPanel();

	/**
	 * default constructor
	 */
	private CityPanel() {

		/* Setting action listener to buttons */
		this.AddVehicleButton.addActionListener(this);
		this.ClearButton.addActionListener(this);
		this.FuelFoodButton.addActionListener(this);
		this.LightsButton.addActionListener(this);
		this.InfoButton.addActionListener(this);
		this.ExitButton.addActionListener(this);
		this.VehicleBorderButton.addActionListener(this);
		this.setMementoButton.addActionListener(this);
		this.getMementoButton.addActionListener(this);

		/* Setting panel buttons */
		this.setLayout(null);
		this.AddVehicleButton.setBounds(1, 552, 100, 60);
		this.AddVehicleButton.setSize(108, 23);
		this.add(this.AddVehicleButton);

		this.ClearButton.setBounds(110, 552, 100, 60);
		this.ClearButton.setSize(98, 23);
		this.add(this.ClearButton);

		this.FuelFoodButton.setBounds(210, 552, 100, 60);
		this.FuelFoodButton.setSize(109, 23);
		this.add(this.FuelFoodButton);

		this.LightsButton.setBounds(321, 552, 100, 60);
		this.LightsButton.setSize(115, 23);
		this.add(this.LightsButton);

		this.VehicleBorderButton.setBounds(438, 552, 100, 60);
		this.VehicleBorderButton.setSize(150, 23);
		this.add(this.VehicleBorderButton);

		this.InfoButton.setBounds(590, 552, 100, 60);
		this.InfoButton.setSize(100, 23);
		this.add(this.InfoButton);

		this.ExitButton.setBounds(693, 552, 100, 60);
		this.ExitButton.setSize(100, 23);
		this.add(this.ExitButton);

		this.setMementoButton.setBounds(1, 527, 100, 60);
		this.setMementoButton.setSize(393, 23);
		this.add(this.setMementoButton);

		this.getMementoButton.setBounds(400, 527, 100, 60);
		this.getMementoButton.setSize(393, 23);
		this.add(this.getMementoButton);

		/*
		 * Initiating the table. at the rest of the program, each part & level will
		 * update the table according to the state of its specific instance.
		 */
		defaultMainTable.setColumnIdentifiers(columnNames);
		mainTable.setModel(defaultMainTable);
		mainTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(3).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(4).setPreferredWidth(60);
		mainTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		mainTable.getColumnModel().getColumn(6).setPreferredWidth(100);
		mainTable.getColumnModel().getColumn(7).setPreferredWidth(100);
		mainTable.getColumnModel().getColumn(8).setPreferredWidth(40);
		mainTable.getColumnModel().getColumn(9).setPreferredWidth(220);
		mainTable.setRowHeight(25);
		mainTable.setFont(new Font("Verdana", Font.PLAIN, 14));
		mainTable.getTableHeader().setFont(new Font("Verdana", Font.PLAIN, 12));
	}

	/**
	 * Singleton Getter for Singleton class CityPanel
	 * 
	 * @return
	 */
	public static CityPanel getInstance() {
		return singlePanel;
	}

	/**
	 * Getter
	 * 
	 * @return FuelFoodButton : JButton
	 */
	public JButton getFuelFoodButton() {
		return this.FuelFoodButton;
	}

	/**
	 * paintComponent method is called automatically by the system to paint the
	 * component we want to paint. in this method we call super.paintComponent to
	 * paint the other parts that we doesn't care for directly. in this method we
	 * call drawObject to print our images "Manually". using
	 * SwingUtilities.updateComponentTreeUI() to update the frame every millisecond
	 * and by that we can see new drawing of object in the Background without touch
	 * anything.
	 */
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(Vehicle.imgCity, 0, 0, CityFrame.getInstance().getWidth() - 10,
				CityFrame.getInstance().getHeight() - 110, null);

		/* if the array list which contains the objects is not empty than... */
		if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) {
			/* get index 0 (only one vehicle at a time) & static cast to determine action */
			Object v = AddVehicleDialog.vehiclesArrayList.get(index - 1);

			if (v instanceof Bike) {
				if (((Bike) v).getBorderFlag()) { /* Moving & Drawing the object! */
					((Vehicle) v).drawObject(g);
				}
			}

			if (v instanceof Car) {
				if (((Car) v).GetBenzeneQuantity() > 0) {
					((Vehicle) v).drawObject(g); /* Moving & Drawing the object! */
				}
			}

			if (v instanceof Carriage) {
				if (((Carriage) v).getAnimalPullCarriage().getAniamelEnergy() > 0) {
					((Vehicle) v).drawObject(g); /* Moving & Drawing the object! */
				}
			}

			((Vehicle) v).drawObject(g); /* Drawing the object */

		} /* end if (!AddVehicleDialog.getVehiclesAndPanelsArrayList().isEmpty()) */

		SwingUtilities.updateComponentTreeUI(CityFrame.getInstance()); /* Automate frame update */
	}

	/**
	 * Service method to update images while the threads are running. this service
	 * method assigned to prevent code-duplication in various places in the whole
	 * program (Vehicle & CityPanel mostly)
	 * 
	 * @param vehicle represent the vehicle we want to update it's image
	 */
	public static boolean imageLoader(Vehicle vehicle) {

		String vehicleType = vehicle.getVehicleName();
		vehicleType = vehicleType.replace("Benzine", "");
		vehicleType = vehicleType.replace("Solar", "");

		vehicle.loadImages(vehicle.getColor().toLowerCase() + vehicleType
				+ vehicle.GetLocation().GetVDirection().toString().substring(0, 1).toUpperCase()
				+ vehicle.GetLocation().GetVDirection().toString().substring(1).toLowerCase() + ".png");

		return true;
	}

	/**
	 * Setting action listener for each button of mainPanel
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		/* AddVehicle button actions */
		if (e.getSource() == this.AddVehicleButton) {
			new AddVehicleDialog();
			Vehicle.fillVehicleArrayQueue();
		}

		/* exit button actions */
		if (e.getSource() == this.ExitButton) {
			notifyFromObservables("Exit");
		}

		/* Fuel/Food button actions */
		if (e.getSource() == this.FuelFoodButton) {
			String[] options = { "Benzine", "Solar", "Food" };

			/* if the array list which contains the objects is not empty than... */
			if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) {

				/* Updating nameIdAndIndexHashtable */
				nameIdAndIndexHashtable.clear(); /* Clearing the hash table to refuel it again next time */
				for (int i = 0; i < AddVehicleDialog.vehiclesArrayList.size(); i++) {
					Vehicle vId = AddVehicleDialog.vehiclesArrayList.get(i);
					nameIdAndIndexHashtable.put(
							vId.getVehicleName() + " - " + Integer.toString(vId.getVehicleLicensePlate()),
							AddVehicleDialog.vehiclesArrayList.indexOf(vId));
				}

				/* Converting Hash table keys to String[] */
				String[] idListArray = new String[nameIdAndIndexHashtable.size()];
				idListArray = nameIdAndIndexHashtable.keySet().toArray(idListArray);

				/* Popping up a dialog box to choose a vehicle to refuel */
				String idChoiceFromBox = (String) JOptionPane.showInputDialog(null, "Choose vehicle to refuel",
						"Choose vehicle to refuel", JOptionPane.QUESTION_MESSAGE, null, idListArray, idListArray[0]);

				/* handling JoptionPane cancel button */
				if (idChoiceFromBox != null) {
					try {

						/* Extracting vehicle by using its key from the hash table */
						Object v = AddVehicleDialog.vehiclesArrayList.get(nameIdAndIndexHashtable.get(idChoiceFromBox));
						nameIdAndIndexHashtable.clear(); /* Clearing the hash table to refuel it again next time */

						if (v instanceof Bike) {
							throw new Exception("You can't refuel a Bike!");
						}

						int returnValue = JOptionPane.showOptionDialog(null, "Please choose food",
								"Fuel for cars/Food for animals", JOptionPane.INFORMATION_MESSAGE,
								JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

						if (v instanceof Car) {
							if (((Car) v).GetEngine() instanceof BenzineEngine) {
								if (returnValue != 0) {
									throw new Exception("It's Benzine Engine!");
								}
								/* Refuel & notify thread */
								synchronized (hash.get(((Car) v))) {
									((Car) v).Refuel();
									hash.get(((Car) v)).notify();
								}
							}

							if (((Car) v).GetEngine() instanceof SolarEngine) {
								if (returnValue != 1) {
									throw new Exception("It's Solar Engine!");
								}
								/* Refuel & notify thread */
								synchronized (hash.get(((Car) v))) {
									((Car) v).Refuel();
									nameIdAndIndexHashtable.remove(v);
									hash.get(((Car) v)).notify();
								}
							}

						} /* end if (v instance of Car) */

						if (v instanceof Carriage) {
							if (returnValue != 2) {
								throw new Exception("You only can FEED the animal!");
							}
							/* Refuel & notify thread */
							synchronized (hash.get(((Carriage) v))) {
								((Carriage) v).eat();
								nameIdAndIndexHashtable.remove(v);
								hash.get(((Carriage) v)).notify();
							}
						} /* end if (v instance of Carriage) */

					} catch (IndexOutOfBoundsException ex) {
						JOptionPane.showMessageDialog(null, "There was collision, try again to refuel", "Error", 0);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex, "Error", 0);
					}

				} /* end of if (idChoiceFromBox != null) : handling JoptionPane cancel button */

			} /* end of if if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) */

			boolean flag = false;
			for (Vehicle v : AddVehicleDialog.vehiclesArrayList) {

				if (v instanceof Car) {
					if (((Car) v).GetBenzeneQuantity() == 0) {
						flag = true;
					}
				}

				if (v instanceof Carriage) {
					if (((Carriage) v).getAnimalPullCarriage().getAniamelEnergy() == 0) {
						flag = true;
					}
				}

			} /* end of FOR-LOOP */

			if (!flag) {
				notifyFromObservables("Full");
			}

		} /* end of if (e.getSource() == this.FuelFoodButton) */

		/* info button actions */
		if (e.getSource() == this.InfoButton) {
			/* Setting frame commands */
			this.InfoFrame.add(infoScrollPane);
			this.InfoFrame.setSize(1200, 350);
			this.InfoFrame.setLocationRelativeTo(CityFrame.getInstance());
			this.InfoFrame.setResizable(false);
			this.InfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.InfoFrame.setVisible(true);
		} /* end if (e.getSource() == this.InfoButton) */

		/* Lights button actions */
		if (e.getSource() == this.LightsButton) {

			if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) {
				/* Updating nameIdAndIndexHashtable */
				nameIdAndIndexHashtable.clear(); /* Clearing the hash table to refuel it again next time */
				for (int i = 0; i < AddVehicleDialog.vehiclesArrayList.size(); i++) {
					Vehicle vId = AddVehicleDialog.vehiclesArrayList.get(i);
					nameIdAndIndexHashtable.put(
							vId.getVehicleName() + " - " + Integer.toString(vId.getVehicleLicensePlate()),
							AddVehicleDialog.vehiclesArrayList.indexOf(vId));
				}

				/* Converting Hash table keys to String[] */
				String[] idListArray = new String[nameIdAndIndexHashtable.size()];
				idListArray = nameIdAndIndexHashtable.keySet().toArray(idListArray);

				/* Popping up a dialog box to choose a vehicle to refuel */
				String idChoiceFromBox = (String) JOptionPane.showInputDialog(null, "Choose vehicle to change lights",
						"Choose vehicle to change lights", JOptionPane.QUESTION_MESSAGE, null, idListArray,
						idListArray[0]);

				/* handling JoptionPane cancel button */
				if (idChoiceFromBox != null) {

					try {
						/* Extracting vehicle by using his key from the hash table */
						Vehicle v = AddVehicleDialog.vehiclesArrayList
								.get(nameIdAndIndexHashtable.get(idChoiceFromBox));
						nameIdAndIndexHashtable.clear(); /* Clearing the hash table to refuel it again next time */

						if (v != null) {
							if (v.GetVehicleLights() == false) {
								v.SetVehicleLights(true);
								mainTable.setValueAt(true, v.getVehicleLicensePlate() - 1001, 8);
							} else {
								v.SetVehicleLights(false);
								mainTable.setValueAt(false, ((Vehicle) v).getVehicleLicensePlate() - 1001, 8);
							}
						}

					} catch (IndexOutOfBoundsException ex) {
						JOptionPane.showMessageDialog(null, "There was collision, try again", "Error", 0);
					}

				} /* end of if (idChoiceFromBox != null) : handling JoptionPane cancel button */
			} /* end of if if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) */
		} /* end if (e.getSource() == this.LightsButton) */

		/* Clear button actions */
		if (e.getSource() == this.ClearButton) {
			FuelFoodButton.setBackground(null);

			/* Updating the info table of vehicles that have been cleared from the screen */
			for (Vehicle v : AddVehicleDialog.vehiclesArrayList) {
				/* Cancel the task which will interrupt the thread that executing the task */
				hash.get(v).cancel(true);
				mainTable.setValueAt("Thread Interrupted - Removed", v.getVehicleLicensePlate() - 1001, 9);
			}

			/* Clearing the screen by clearing the vehicle array list */
			AddVehicleDialog.vehiclesArrayList.clear();
			index = 0;

			Vehicle.fillVehicleArrayQueue(); /* filling the vehiclesArray after clearing the cityBackground */

		} /* end if (e.getSource() == this.ClearButton) */

		if (e.getSource() == this.VehicleBorderButton) {

			if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) {

				/* Clearing & Re-build the hash table to the next time refuel */
				nameIdAndIndexHashtable.clear();
				for (int i = 0; i < AddVehicleDialog.vehiclesArrayList.size(); i++) {
					Vehicle vId = AddVehicleDialog.vehiclesArrayList.get(i);
					nameIdAndIndexHashtable.put(
							vId.getVehicleName() + " - " + Integer.toString(vId.getVehicleLicensePlate()),
							AddVehicleDialog.vehiclesArrayList.indexOf(vId));
				}

				/* Converting Hash table keys to String[] */
				String[] idListArray = new String[nameIdAndIndexHashtable.size()];
				idListArray = nameIdAndIndexHashtable.keySet().toArray(idListArray);

				/* Popping up a dialog box to choose a vehicle to refuel */
				String idChoiceFromBox = (String) JOptionPane.showInputDialog(null, "Choose vehicle to change lights",
						"Choose vehicle to change lights", JOptionPane.QUESTION_MESSAGE, null, idListArray,
						idListArray[0]);

				/* handling JoptionPane cancel button */
				if (idChoiceFromBox != null) {
					try {
						/* Extracting vehicle by using his key from the hash table */
						Vehicle v = AddVehicleDialog.vehiclesArrayList
								.get(nameIdAndIndexHashtable.get(idChoiceFromBox));
						nameIdAndIndexHashtable.clear(); /* Clearing the hash table to refuel it again next time */

						if (v != null && v instanceof Vehicle) {

							/* setting flag to correct state */
							if (v.getBorderFlag() == true) {
								v.setBorderFlag(false);
								mainTable.setValueAt(v.getBorderFlag(), ((Vehicle) v).getVehicleLicensePlate() - 1001,
										10);
							} else {
								v.setBorderFlag(true);
								mainTable.setValueAt(v.getBorderFlag(), ((Vehicle) v).getVehicleLicensePlate() - 1001,
										10);
							}

						}

					} catch (IndexOutOfBoundsException ex) {
						JOptionPane.showMessageDialog(null, "There was collision, try again", "Error", 0);
					}

				} /* end of if (idChoiceFromBox != null) : handling JoptionPane cancel button */

			} /* end if (!AddVehicleDialog.vehiclesArrayList.isEmpty()) */

		} /* end if(e.getSource() == this.VehicleBorderButton) */

		if (e.getSource() == this.setMementoButton) {

			careTaker.getMementoList().clear();

			for (int index = 0; index < AddVehicleDialog.vehiclesArrayList.size(); index++) {
				Vehicle mementoVehicle = AddVehicleDialog.vehiclesArrayList.get(index);

				if (mementoVehicle instanceof Bike) {
					mementoVehicle = new Bike((Bike) AddVehicleDialog.vehiclesArrayList.get(index));
				}

				else if (mementoVehicle instanceof Car) {
					mementoVehicle = new Car((Car) AddVehicleDialog.vehiclesArrayList.get(index));
				}

				else if (mementoVehicle instanceof Carriage) {
					mementoVehicle = new Carriage((Carriage) AddVehicleDialog.vehiclesArrayList.get(index));
				}

				originator.setState(mementoVehicle);
				careTaker.add(originator.saveStateToMemento());

			}

		}

		if (e.getSource() == this.getMementoButton) {

			/* Cleaning everything before restoration */
			if (!careTaker.getMementoList().isEmpty()) { /* if mementoList isn't empty */

				for (Vehicle v : AddVehicleDialog.vehiclesArrayList) {
					hash.get(v).cancel(true); /* Cancel the tasks */
					mainTable.setValueAt(
							"Memento - Removed", AddVehicleDialog.vehiclesArrayList
									.get(AddVehicleDialog.vehiclesArrayList.size() - 1).getVehicleLicensePlate() - 1001,
							9);
				}

				AddVehicleDialog.vehiclesArrayList.clear();
				index = 0;
			}

			/* Restoring the previous state */
			for (int index = careTaker.getMementoList().size() - 1; index >= 0; index--) {

				originator.getStateFromMemento(careTaker.get(index));
				Observer ob = Vehicle.pan;

				if (originator.getState() instanceof Car) {
					AddVehicleDialog.vehiclesArrayList.add((Car) originator.getState());
					((Car) originator.getState()).registerObserver(ob);
				}

				if (originator.getState() instanceof Carriage) {
					AddVehicleDialog.vehiclesArrayList.add((Carriage) originator.getState());
					((Carriage) originator.getState()).registerObserver(ob);
				}

				if (originator.getState() instanceof Bike) {
					AddVehicleDialog.vehiclesArrayList.add((Bike) originator.getState());
				}

				CityPanel.hash.put(
						AddVehicleDialog.vehiclesArrayList.get(AddVehicleDialog.vehiclesArrayList.size() - 1),
						AddVehicleDialog.exe.submit(
								AddVehicleDialog.vehiclesArrayList.get(AddVehicleDialog.vehiclesArrayList.size() - 1)));

				CityPanel.index++;

				mainTable.setValueAt(
						"Memento - Restored : On Panel", AddVehicleDialog.vehiclesArrayList
								.get(AddVehicleDialog.vehiclesArrayList.size() - 1).getVehicleLicensePlate() - 1001,
						9); /* Updating info table */

				imageLoader(AddVehicleDialog.vehiclesArrayList
						.get(AddVehicleDialog.vehiclesArrayList.size() - 1)); /* Loading relevant image */

			}

			careTaker.getMementoList().clear();

		}

	} /* end public void actionPerformed(ActionEvent e) */

	/**
	 * Observer Design Pattern - this method gets updates from observabels!
	 */
	@Override
	public void notifyFromObservables(String msg) {
		if (msg.contains("Empty")) {
			Vehicle.pan.FuelFoodButton.setBackground(Color.RED);
		}

		if (msg.contains("Full")) {
			Vehicle.pan.FuelFoodButton.setBackground(null);
		}

		if (msg.contains("Exit")) {
			for (Vehicle v : AddVehicleDialog.vehiclesArrayList) {
				/* Cancel the task which will interrupt the thread that executing the task */
				Future<?> future = AddVehicleDialog.exe.submit(v);
				future.cancel(true);
			}
			AddVehicleDialog.exe.shutdown();
			/* after interrupting every thread, exit cleanly */
			System.exit(0);
		}

	}

}
