package DesignPatterns;

import java.awt.Graphics;

import graphics.IDrawable;
import vehicles.Vehicle;

/**
 * Decorator Design pattern class implements IDrawable to improve it
 * 
 * @author benja
 *
 */
public abstract class DecoratorVehicle implements IDrawable {

	protected IDrawable decoratedVehicle;

	public DecoratorVehicle(Vehicle dv) {
		this.decoratedVehicle = dv;
	}

	public void drawObject(Graphics g) {
		this.decoratedVehicle.drawObject(g);
	}

}
