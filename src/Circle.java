import java.awt.Color;

/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */

// This class is used in the RightHand class to easier keep track of previous positions and colors of the trail of circles.
public class Circle {

	private Color color;
	private int diameter;
	private int xloc;
	private int yloc;
	
	
	public Circle(Color color, int diameter, int xloc, int yloc) {
		this.color = color;
		this.diameter = diameter;
		this.xloc = xloc;
		this.yloc = yloc;
	}
	
	
	public Color getColor() {
		return color;
	}
	public int getDiameter() {
		return diameter;
	}
	public int getXloc() {
		return xloc;
	}
	public int getYloc() {
		return yloc;
	}
	
	
	
}
