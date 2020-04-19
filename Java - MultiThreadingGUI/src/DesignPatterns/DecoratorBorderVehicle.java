package DesignPatterns;

import java.awt.Graphics;

import graphics.AddVehicleDialog;
import vehicles.Orientation;
import vehicles.Vehicle;

/**
 * Decorator Design pattern class extends VehicleDecorator to implement it's
 * methods
 * 
 * @author benja
 *
 */

public class DecoratorBorderVehicle extends DecoratorVehicle {

	private int size = 65;

	public DecoratorBorderVehicle(Vehicle dv) {
		super(dv);
	}

	/**
	 * DECORATED METHOD!
	 * 
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

				/* East-West size & position */
				if (v.GetLocation().GetVDirection() == Orientation.EAST
						|| v.GetLocation().GetVDirection() == Orientation.WEST) {

					g.drawImage(v.getVehicleImage(), v.GetLocation().GetPoint().GetX(),
							v.GetLocation().GetPoint().GetY() + 5, this.size + 25, this.size - 15, Vehicle.pan);

					g.setColor(java.awt.Color.WHITE);
					g.drawRect(v.GetLocation().GetPoint().GetX(), v.GetLocation().GetPoint().GetY() + 5,
							this.size + 25, this.size - 15);

				}

				/* South size & position */
				if (v.GetLocation().GetVDirection() == Orientation.SOUTH) {

					g.drawImage(v.getVehicleImage(), v.GetLocation().GetPoint().GetX() + 35,
							v.GetLocation().GetPoint().GetY(), this.size - 15, this.size + 25, Vehicle.pan);

					g.setColor(java.awt.Color.WHITE);
					g.drawRect(v.GetLocation().GetPoint().GetX() + 35, v.GetLocation().GetPoint().GetY(),
							this.size - 15, this.size + 25);

				}

				/* North size & position */
				if (v.GetLocation().GetVDirection() == Orientation.NORTH) {

					g.drawImage(v.getVehicleImage(), v.GetLocation().GetPoint().GetX() + 10,
							v.GetLocation().GetPoint().GetY(), this.size - 15, this.size + 25, Vehicle.pan);

					g.setColor(java.awt.Color.WHITE);
					g.drawRect(v.GetLocation().GetPoint().GetX() + 10, v.GetLocation().GetPoint().GetY(),
							this.size - 15, this.size + 25);

				} 
				
			} /* end : if v.getBorderFlag() */
		} /* end : for loop */
	} /* end of method */

	/**
	 * Already been implemented in Class Vehicle
	 */
	@Override
	public void loadImages(String nm) {
	}

	/**
	 * Already been implemented in Class Vehicle
	 */
	@Override
	public String getColor() {
		return null;
	}

}
