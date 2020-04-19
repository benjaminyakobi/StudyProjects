package vehicles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import DesignPatterns.DecoratorBorderVehicle;
import DesignPatterns.Observer;
import graphics.AddVehicleDialog;
import graphics.CityFrame;
import graphics.CityPanel;
import graphics.IClonable;
import graphics.IDrawable;
import graphics.IMoveable;

/**
 * abstract class
 * 
 * @author benja
 */
public abstract class Vehicle implements IDrawable, IMoveable, IClonable, Runnable {

	/* Vehicle */
	public static int RandomIdAndAccessToVehicle = 1000;
	protected int size;
	protected int fuelConsumption;
	protected int VehicleWheelNum;
	protected int VehicleLicensePlate;
	protected float VehicleMileage;
	protected boolean VehicleLights;
	protected Color VehicleColor;
	protected Location VehicleLocation;

	/* Services */
	private String startPosition;
	private Random dice = new Random();
	private int random = 0;
	private BufferedImage imgEastVehicle, imgWestVehicle, imgSouthVehicle, imgNorthVehicle;
	private boolean borderFlag;
	protected ArrayList<Observer> ObserversList = new ArrayList<Observer>();
	protected Location previousVehicleLocation;
	protected final int ArbitraryTimeToSleep = 50;
	public static CityPanel pan;
	public static BufferedImage imgCity;

	/**
	 * Default constructor
	 */
	public Vehicle() {
		this.VehicleLicensePlate = RandomIdAndAccessToVehicle;
		this.VehicleColor = Color.WHITE;
		this.VehicleMileage = 0;
		this.VehicleWheelNum = 4;
		this.VehicleLights = false;
		this.VehicleLocation = new Location();
		this.fuelConsumption = 0; /* always zero */
		this.size = 65;
		this.previousVehicleLocation = new Location();
		this.borderFlag = false;
	}

	/**
	 * Constructor
	 * 
	 * @param vlp    is VehicleLicensePlate
	 * @param vc     is VehicleColor
	 * @param vwn    is VehicleWheelNum
	 * @param vm     is VehicleMileage
	 * @param lights is Lights
	 * @param l      is location
	 */
	public Vehicle(int vlp, Color vc, int vwn, float vm, boolean lights, Location l, boolean borderFlag) {
		this.VehicleLicensePlate = vlp;
		this.VehicleColor = vc;
		this.VehicleMileage = vm;
		this.VehicleWheelNum = vwn;
		this.VehicleLights = lights;
		this.VehicleLocation = l;
		this.fuelConsumption = 0; /* always 0 */
		this.size = 65;
		this.previousVehicleLocation = new Location();
		this.borderFlag = borderFlag;
	}

	/**
	 * toString method
	 * 
	 * @return Vehicle class's details
	 */
	public String toString() {

		return this.VehicleLocation.toString() + "[ Plate: " + this.VehicleLicensePlate + ", Color: "
				+ this.VehicleColor + ", Mileage: " + this.VehicleMileage + ", Wheels: " + this.VehicleWheelNum
				+ ", Lights on? " + this.VehicleLights + " ]" + "\n";
	}

	/**
	 * drive method, driving the vehicle from point a to point b this function is a
	 * base-function which will be extended in other classes.
	 * 
	 * @param point: updates Vehicle's location
	 * @return false if the vehicle does'nt move, else: true
	 */
	protected boolean drive(Point point) {
		/* extracting current location into CurrentLocationX and CurrentLocationY */
		int CurrentLocationX = this.VehicleLocation.GetPoint().GetX();
		int CurrentLocationY = this.VehicleLocation.GetPoint().GetY();

		if (CurrentLocationX == point.GetX() && CurrentLocationY == point.GetY()) {
			return false; /* if it's the same point: the vehicle does'nt move: return value is false */
		}

		else {
			/* extracting distance from DistanceBetweenTwoPoints */
			int Distance = Point.DistanceBetweenTwoPoints(point, this.VehicleLocation.GetPoint());

			/* updating the location now */
			this.VehicleLocation.GetPoint().SetX(point.GetX());
			this.VehicleLocation.GetPoint().SetY(point.GetY());

			/* and now updating Mileage */
			this.VehicleMileage = this.VehicleMileage + Distance;
			this.fuelConsumption += Distance * BenzineEngine.GetBenzeneEngineBenzeneUsagePrKm();
			return true;
		}
	}

	/**
	 * Service method that used in ({@link #drawObject(Graphics)}) method.
	 * 
	 * this method fills the vehiclesArrayList which use as the main array list to
	 * demonstrate queue (with vaultVehiclesArrayList which stores the vehicles that
	 * wait to be drawn).
	 */
	public synchronized static boolean fillVehicleArrayQueue() {

		/*
		 * IF ALREADY THERE ARE VEHICLES ON VEHICLES ARRAY LIST: Here we switch from NEW
		 * state to a RUNNABLE state by calling start() on each of vehiclesArrayList
		 * that store the running vehicles on the frame.
		 */
		if (AddVehicleDialog.vehiclesArrayList.size() < 5 && !AddVehicleDialog.vehiclesArrayList.isEmpty()
				&& !AddVehicleDialog.vaultVehiclesArrayList.isEmpty()) {

			if (AddVehicleDialog.vehiclesArrayList.get(AddVehicleDialog.vehiclesArrayList.size() - 1)
					.GetVehicleMileage() > 170) {

				synchronized (AddVehicleDialog.vaultVehiclesArrayList) {
					AddVehicleDialog.vehiclesArrayList.add(AddVehicleDialog.vaultVehiclesArrayList.remove(0));

					CityPanel.hash.put(
							AddVehicleDialog.vehiclesArrayList.get(AddVehicleDialog.vehiclesArrayList.size() - 1),
							AddVehicleDialog.exe.submit(AddVehicleDialog.vehiclesArrayList
									.get(AddVehicleDialog.vehiclesArrayList.size() - 1)));

					CityPanel.index++;

				}

				/* Updating status on info table */
				CityPanel.mainTable.setValueAt("On Panel", AddVehicleDialog.vehiclesArrayList
						.get(AddVehicleDialog.vehiclesArrayList.size() - 1).getVehicleLicensePlate() - 1001, 9);

			} /* end INNER if statement */

		} /* end OUTTER if statement */

		/*
		 * IF VEHICLES ARRAY LIST IS EMPTY (AFTER CLEAR BUTTON): Here we switch from NEW
		 * state to a RUNNABLE state by calling start() on each of vehiclesArrayList
		 * that store the running vehicles on the frame.
		 */
		if (AddVehicleDialog.vehiclesArrayList.isEmpty() && !AddVehicleDialog.vaultVehiclesArrayList.isEmpty()) {

			AddVehicleDialog.vehiclesArrayList.add(AddVehicleDialog.vaultVehiclesArrayList.remove(0));

			CityPanel.hash.put(AddVehicleDialog.vehiclesArrayList.get(AddVehicleDialog.vehiclesArrayList.size() - 1),
					AddVehicleDialog.exe.submit(
							AddVehicleDialog.vehiclesArrayList.get(AddVehicleDialog.vehiclesArrayList.size() - 1)));

			CityPanel.index++;

			/* Updating status on info table */
			CityPanel.mainTable
					.setValueAt(
							"On Panel", AddVehicleDialog.vehiclesArrayList
									.get(AddVehicleDialog.vehiclesArrayList.size() - 1).getVehicleLicensePlate() - 1001,
							9);

		} /* end INNER if statement */

		return true;

	}

	/**
	 * Helper method for drawObject for loop which uses these images!
	 * 
	 * @return BufferedImage which is not null, if all the images are null than
	 *         return null as default.
	 */
	public BufferedImage getVehicleImage() {
		if (imgEastVehicle != null) {
			return imgEastVehicle;
		}

		if (imgWestVehicle != null) {
			return imgWestVehicle;
		}

		if (imgSouthVehicle != null) {
			return imgSouthVehicle;
		}

		if (imgNorthVehicle != null) {
			return imgNorthVehicle;
		}
		return null;
	}

	/**
	 * private Service method. this method checks if there is a possible accident
	 * between 2 vehicles. if there is an accident, than the vehicle which is
	 * "weaker" will be removed from the AddVehicleDialog.vehiclesArrayList and by
	 * that it's image will also be removed from the city background.
	 * 
	 * if two vehicles with the same type will be in one accident, the latest
	 * vehicle will continue while the earlier vehicle will be removed
	 *
	 * @return true if there is an accident & false if not.
	 */
	private boolean CheckIfAccidentOccured() {
		/*
		 * for loop which iterates on the AddVehicleDialog.vehiclesArrayList which
		 * contains the all the vehicles that we have created. the check for accidents
		 * handled by ValueRange operator.
		 */
		for (int index = 0; index < AddVehicleDialog.vehiclesArrayList.size(); index++) {
			/* if true IT MEANS there was an accident & we'll remove the weaker vehicle */
			if (ValueRange
					.of(AddVehicleDialog.vehiclesArrayList.get(index).GetLocation().GetPoint().GetX() - 80,
							AddVehicleDialog.vehiclesArrayList.get(index).GetLocation().GetPoint().GetX() + 80)
					.isValidValue((int) this.VehicleLocation.GetPoint().GetX()) &&

					ValueRange
							.of(AddVehicleDialog.vehiclesArrayList.get(index).GetLocation().GetPoint().GetY() - 80,
									AddVehicleDialog.vehiclesArrayList.get(index).GetLocation().GetPoint().GetY() + 80)
							.isValidValue((int) this.VehicleLocation.GetPoint().GetY())
					&& AddVehicleDialog.vehiclesArrayList.size() > 1) {

				/* if current's durability is lower, remove the current vehicle */
				if (this.getDurability() < AddVehicleDialog.vehiclesArrayList.get(index).getDurability()) {

					/* Updating info table with the correct collision between vehicles */
					CityPanel.mainTable.setValueAt(
							"Collided with " + AddVehicleDialog.vehiclesArrayList.get(index).getClass().getSimpleName()
									+ ", ID: " + AddVehicleDialog.vehiclesArrayList.get(index).getVehicleLicensePlate(),
							this.getVehicleLicensePlate() - 1001, 9);

					CityPanel.hash.get(this).cancel(true); /* Cancel the tasks */

					/* removing the relevant vehicle */
					AddVehicleDialog.vehiclesArrayList.remove(this); /* remove from vehicle Array List */
					CityPanel.index = AddVehicleDialog.vehiclesArrayList.size();

					fillVehicleArrayQueue();
					return true;

				} /* end of 1st inner if statement */

				/* if current's durability is greater, remove the other vehicle */
				if (this.getDurability() > AddVehicleDialog.vehiclesArrayList.get(index).getDurability()) {

					/* Updating info table with the correct collision between vehicles */
					CityPanel.mainTable.setValueAt(
							"Collided with " + this.getClass().getSimpleName() + ", ID: "
									+ this.getVehicleLicensePlate(),
							AddVehicleDialog.vehiclesArrayList.get(index).getVehicleLicensePlate() - 1001, 9);

					CityPanel.hash.get(AddVehicleDialog.vehiclesArrayList.get(index))
							.cancel(true); /* Cancel the tasks */

					/* removing the relevant vehicle */
					AddVehicleDialog.vehiclesArrayList.remove(AddVehicleDialog.vehiclesArrayList.get(index));
					CityPanel.index = AddVehicleDialog.vehiclesArrayList.size();

					fillVehicleArrayQueue();
					return true;

				} /* end of 2nd inner if statement */

				/* if durabilities of both are equal: remove both */
				if (this.getDurability() == AddVehicleDialog.vehiclesArrayList.get(index).getDurability()
						&& this != AddVehicleDialog.vehiclesArrayList.get(index)) {

					/* 1st: Updating info table with the correct collision between vehicles */
					CityPanel.mainTable.setValueAt(
							"Collided with " + this.getClass().getSimpleName() + ", ID: "
									+ AddVehicleDialog.vehiclesArrayList.get(index).getVehicleLicensePlate(),
							this.getVehicleLicensePlate() - 1001, 9);

					/* 2nd: Updating info table with the correct collision between vehicles */
					CityPanel.mainTable.setValueAt(
							"Collided with " + this.getClass().getSimpleName() + ", ID: "
									+ this.getVehicleLicensePlate(),
							AddVehicleDialog.vehiclesArrayList.get(index).getVehicleLicensePlate() - 1001, 9);

					/* Cancel the task which will interrupt the thread that executing the task */
					synchronized (CityPanel.hash.get((AddVehicleDialog.vehiclesArrayList.get(index)))) {
						CityPanel.hash.get((AddVehicleDialog.vehiclesArrayList.get(index))).notify();
					}

					CityPanel.hash.get(AddVehicleDialog.vehiclesArrayList.get(index))
							.cancel(true); /* Cancel the tasks */

					/* first remove */
					AddVehicleDialog.vehiclesArrayList.remove(AddVehicleDialog.vehiclesArrayList.get(index));
					CityPanel.index = AddVehicleDialog.vehiclesArrayList.size();

					/* Cancel the task which will interrupt the thread that executing the task */
					synchronized (CityPanel.hash.get(this)) {
						CityPanel.hash.get((this)).notify();
					}

					CityPanel.hash.get(this).cancel(true); /* Cancel the tasks */

					/* second remove */
					AddVehicleDialog.vehiclesArrayList.remove(this);
					CityPanel.index = AddVehicleDialog.vehiclesArrayList.size();

					fillVehicleArrayQueue();
					return true;

				} /* end of 3rd inner if statement */

			} /* end of OUTTER if statement */

		} /* end of for loop */
		return false;
	}

	/**
	 * Service method to update Info table.
	 * 
	 * this method dose not return any value - just updating
	 * 
	 * this method is called from each thread of each vehicle that we create in each
	 * type constructor.
	 * 
	 * this method dose not require synchronization because every time that we enter
	 * the method, each line of code access another part of the table. there is no
	 * possibility of accessing the same cell (rowXcolumn) with two different
	 * thread. each cell is a unique for a specific data.
	 */
	protected synchronized boolean updateMainTable() {
		/* Iterating on vehicleArrayList that stores all of our vehicles */
		for (int i = 0; i < AddVehicleDialog.vehiclesArrayList.size(); i++) {
			Vehicle v = AddVehicleDialog.vehiclesArrayList.get(i);
			/* Specific changes for Car type Vehicles */
			if (v instanceof Car) {
				CityPanel.mainTable.setValueAt(((Car) v).getFuelConsumption() + " Liters",
						((Car) v).getVehicleLicensePlate() - 1001, 7);

				CityPanel.mainTable.setValueAt(((Car) v).GetBenzeneQuantity() + " Liters",
						((Car) v).getVehicleLicensePlate() - 1001, 5);
			} /* end of 1st */

			/* Specific changes for Carriage type Vehicles */
			if (v instanceof Carriage) {
				CityPanel.mainTable.setValueAt(((Carriage) v).getFuelConsumption() + " Energy",
						((Carriage) v).getVehicleLicensePlate() - 1001, 7);
				CityPanel.mainTable.setValueAt(((Carriage) v).getAnimalPullCarriage().getAniamelEnergy() + " Energy",
						((Carriage) v).getVehicleLicensePlate() - 1001, 5);
			} /* end of 2nd */

			CityPanel.mainTable.setValueAt(v.getBorderFlag(), v.getVehicleLicensePlate() - 1001, 10);
			
			CityPanel.mainTable.setValueAt(v.GetVehicleMileage() + " KM", v.getVehicleLicensePlate() - 1001, 6);

			CityPanel.mainTable.setValueAt(v.GetVehicleLights(), v.getVehicleLicensePlate() - 1001, 8);

		} /* end of for loop */

		return true;

	}

	/**
	 * this method calculates the next point the vehicle on the panel should drive
	 * to.
	 * 
	 * @return this.VehicleLocation.GetPoint()
	 */
	public Point nextLocation() {
		CheckIfAccidentOccured();

		if (!AddVehicleDialog.vaultVehiclesArrayList.isEmpty()) {
			fillVehicleArrayQueue();
		}

		/* Extracting previous point for next calculations */
		this.previousVehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX(),
				this.VehicleLocation.GetPoint().GetY());

		/* Extracting vehicle direction (into Capitalized word) */
		this.startPosition = this.GetLocation().GetVDirection().toString().substring(0, 1).toUpperCase()
				+ this.GetLocation().GetVDirection().toString().substring(1).toLowerCase();

		/*
		 * Switch case of cases: East, South, West, North every case determines where
		 * the vehicle have to go and by the calculation calls this.loadImage to load
		 * the correct image
		 */
		switch (this.startPosition) {

		case "East":
			this.random = 0;

			this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX() + this.getSpeed(),
					this.VehicleLocation.GetPoint().GetY());

			if (this.VehicleLocation.GetPoint().GetX() >= CityFrame.getInstance().getWidth() - 110) {
				imgEastVehicle = null;
				this.startPosition = "South";
				this.VehicleLocation.SetVDirection(Orientation.SOUTH); /* Updating vehicle orientation */
				CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */
			}

			break;

		case "South":
			if (this.random == 0) {
				this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX(),
						this.VehicleLocation.GetPoint().GetY() + this.getSpeed());

				if (this.VehicleLocation.GetPoint().GetY() == CityFrame.getInstance().getHeight() / 2 - 85) {
					this.random = dice.nextInt(2);
				}
			}

			if (this.random == 1) {
				imgSouthVehicle = null;
				this.startPosition = "West";
				this.VehicleLocation
						.SetVDirection(Orientation.WEST); /* calling CityPanel.imageLoader to update the image */
				CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */
			}

			if (this.VehicleLocation.GetPoint().GetY() >= CityFrame.getInstance().getHeight() - 165) {
				imgSouthVehicle = null;
				this.startPosition = "West";
				this.VehicleLocation.SetVDirection(Orientation.WEST); /* Updating vehicle orientation */
				CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */
			}

			break;

		case "West":
			if (this.random == 1) {
				this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX() - this.getSpeed(),
						this.VehicleLocation.GetPoint().GetY());
				this.random = 0;
			}

			this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX() - this.getSpeed(),
					this.VehicleLocation.GetPoint().GetY());

			if (this.VehicleLocation.GetPoint().GetX() <= 0
					&& this.VehicleLocation.GetPoint().GetY() == CityFrame.getInstance().getHeight() / 2 - 85) {
				this.random = dice.nextInt(2);
			}

			if (this.random == 1) {
				imgWestVehicle = null;
				this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX() + this.getSpeed(),
						this.VehicleLocation.GetPoint().GetY());
				this.startPosition = "East";
				this.VehicleLocation.SetVDirection(Orientation.EAST); /* Updating vehicle orientation */
				CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */
			}

			if (this.VehicleLocation.GetPoint().GetX() == 0 && this.random == 0) {
				imgWestVehicle = null;
				this.startPosition = "North";
				this.VehicleLocation.SetVDirection(Orientation.NORTH); /* Updating vehicle orientation */
				CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */
			}

			break;

		case "North":
			if (this.random == 0) {
				this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX(),
						this.VehicleLocation.GetPoint().GetY() - this.getSpeed());

				if (this.VehicleLocation.GetPoint().GetY() == CityFrame.getInstance().getHeight() / 2 - 30) {
					this.random = 1;
				}

				if (this.random == 1) {
					imgNorthVehicle = null;
					this.startPosition = "East";
					this.VehicleLocation.SetVDirection(Orientation.EAST); /* Updating vehicle orientation */
					CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */

					this.VehicleLocation.SetPoint(this.VehicleLocation.GetPoint().GetX() + this.getSpeed(),
							this.VehicleLocation.GetPoint().GetY() - this.size / 2 - 10);
					this.random = 0;
				}
			}

			if (this.VehicleLocation.GetPoint().GetY() - this.getSpeed() < 0) {
				imgNorthVehicle = null;
				this.startPosition = "East";
				this.VehicleLocation.SetVDirection(Orientation.EAST); /* Updating vehicle orientation */
				CityPanel.imageLoader(this); /* calling CityPanel.imageLoader to update the image */
			}

			break;

		default:

		} /* end of switch case */

		return this.VehicleLocation.GetPoint(); /* the next point the vehicle will drive to */
	}

	// *********** Interface's Implementations! ********** //

	/**
	 * This method (Interface : IDrawable) loads the images of the vehicles if the
	 * image cannot be loaded, there will be appropriate message. this method is
	 * activated from the constructor of class CityPanel
	 */
	@Override
	public void loadImages(String nm) {

		try {
			if (nm.contains("East")) {
				imgEastVehicle = ImageIO.read(this.getClass().getResource(IDrawable.PICTURE_PATH + nm));
			}
			if (nm.contains("West")) {
				imgWestVehicle = ImageIO.read(this.getClass().getResource(IDrawable.PICTURE_PATH + nm));
			}
			if (nm.contains("South")) {
				imgSouthVehicle = ImageIO.read(this.getClass().getResource(IDrawable.PICTURE_PATH + nm));
			}
			if (nm.contains("North")) {
				imgNorthVehicle = ImageIO.read(this.getClass().getResource(IDrawable.PICTURE_PATH + nm));
			}

		} catch (IOException e) {
			System.out.println(e.getMessage() + ": Cannot load image");
		}
	}

	/**
	 * This method (Interface : IDrawable) draw the images of the vehicles on the
	 * CityBackground image. this method is activated from the paintComponent method
	 * of class CityPanel if the relevant image is null, there will be no any new
	 * drawing on the CityBackground image
	 */
	@Override
	public void drawObject(Graphics g) {
		/*
		 * for loop to print all the vehicles which stored in the vehicles data
		 * structure, this for loop is sliced to EAST-WEST, SOUTH & NORTH to ADJUST the
		 * vehicles image size and location to the correct position on the city
		 * background image
		 */
		for (int vIndex = 0; vIndex < AddVehicleDialog.vehiclesArrayList.size(); vIndex++) {

			Vehicle v = AddVehicleDialog.vehiclesArrayList.get(vIndex);

			if (v.getBorderFlag()) {
				DecoratorBorderVehicle ve = new DecoratorBorderVehicle(v);
				ve.drawObject(g);
			}

			else {

				/* East-West size & position */
				if (v.GetLocation().GetVDirection() == Orientation.EAST
						|| v.GetLocation().GetVDirection() == Orientation.WEST) {
					g.drawImage(v.getVehicleImage(), v.VehicleLocation.GetPoint().GetX(),
							v.VehicleLocation.GetPoint().GetY() + 5, this.size + 25, this.size - 15, Vehicle.pan);
				}

				/* South size & position */
				else if (v.GetLocation().GetVDirection() == Orientation.SOUTH) {
					g.drawImage(v.getVehicleImage(), v.VehicleLocation.GetPoint().GetX() + 35,
							v.VehicleLocation.GetPoint().GetY(), this.size - 15, this.size + 25, Vehicle.pan);
				}

				/* North size & position */
				else if (v.GetLocation().GetVDirection() == Orientation.NORTH) {
					g.drawImage(v.getVehicleImage(), v.VehicleLocation.GetPoint().GetX() + 10,
							v.VehicleLocation.GetPoint().GetY(), this.size - 15, this.size + 25, Vehicle.pan);
				}
			}
		}
	}

	/**
	 * this method get Point object p and activate this.drive method with the given
	 * point. this method use Thread.sleep() method to call this.drive every 0.1
	 * seconds.
	 */
	@Override
	public boolean move(Point p) {

		/* activating this.drive for Bike instance */
		if (this instanceof Bike && AddVehicleDialog.vehiclesArrayList.contains(this)) {
			this.drive(p);
		}

		/* activating this.drive for Car instance */
		if (this instanceof Car && AddVehicleDialog.vehiclesArrayList.contains(this)) {
			/* if there is enough fuel to drive to the given point */
			if (((Car) this).GetBenzeneQuantity() > 0) {
				this.drive(p);
			} else {
				return false;
			}
		}

		/* activating this.drive for Car instance */
		if (this instanceof Carriage && AddVehicleDialog.vehiclesArrayList.contains(this)) {
			/* if there is enough energy to drive to the given point */
			if (((Carriage) this).getAnimalPullCarriage().getAnimalEnergy() > 0) {
				this.drive(p);
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return vehicleColor.
	 */
	@Override
	public String getColor() {
		return this.VehicleColor.toString();
	}

	// *********** Class Setters & Getters! ********** //

	/**
	 * Setter. value = true : border value = false : no border
	 * 
	 * @param value for border flag.
	 */
	public boolean setBorderFlag(boolean value) {
		this.borderFlag = value;
		return true;
	}

	/**
	 * Setter.
	 * 
	 * @param position represent the next direction
	 * @return
	 */
	public boolean setStartPosition(String position) {
		this.startPosition = position;
		return true;
	}

	/**
	 * Getter
	 * 
	 * @return borderFlag : boolean
	 */
	public boolean getBorderFlag() {
		return this.borderFlag;
	}

	/**
	 * @return the previousVehicleLocation
	 */
	public Location getPreviousVehicleLocation() {
		return this.previousVehicleLocation;
	}

	/**
	 * Getter for Vehicle's Mileage
	 * 
	 * @return VehicleMileage field
	 */
	public float GetVehicleMileage() {
		return this.VehicleMileage;
	}

	/**
	 * Getter for Vehicle's Location
	 * 
	 * @return VehicleLocation field
	 */
	public Location GetLocation() {
		return this.VehicleLocation;
	}

	/**
	 * 
	 * @return Vehicle's color
	 */
	public Color getVehicleColor() {
		return this.VehicleColor;
	}

	/**
	 * 
	 * @return Vehicle's Lights
	 */
	public boolean GetVehicleLights() {
		return this.VehicleLights;
	}

	/**
	 * Getter
	 * 
	 * 
	 * @return Vehicel's fuel consumption
	 */
	public int getFuelConsumption() {
		return this.fuelConsumption;
	}

	/**
	 * @return the vehicleLicensePlate
	 */
	public int getVehicleLicensePlate() {
		return this.VehicleLicensePlate;
	}

	/**
	 * @param vehicleLicensePlate the vehicleLicensePlate to set
	 */
	protected boolean setVehicleLicensePlate(int vehicleLicensePlate) {
		VehicleLicensePlate = vehicleLicensePlate;
		return true;
	}

	/**
	 * 
	 * @param value is set into: this.VehicleLights
	 */
	public boolean SetVehicleLights(boolean value) {
		this.VehicleLights = value;
		return true;
	}

}