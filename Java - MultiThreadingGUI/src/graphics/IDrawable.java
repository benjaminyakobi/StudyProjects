package graphics;

import java.awt.Graphics;

/**
 * 
 * @author benja
 *
 */
public interface IDrawable {

	 public final static String PICTURE_PATH = "/graphics/graphics.pictures/";
	 public void loadImages(String nm);
	 public void drawObject (Graphics g);
	 public String getColor();

}
