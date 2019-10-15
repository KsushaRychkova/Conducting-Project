/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class RightHand {
	
	// class variables
	private int diameter;
	private Color color;
	private int xloc, yloc; // location
	
	
	// constructors
	public RightHand() { // default values
		diameter = 50;
		color = new Color(255,255,0); // yellow
	}
	public RightHand(int diameter, Color color) { // set your own
		this.diameter = diameter;
		this.color = color;
	}
	public RightHand(int diameter, Color color, int x, int y) {
		this.diameter = diameter;
		this.color = color;
		xloc = x;
		yloc = y;
	}
	
	
	// methods
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(xloc, yloc, diameter, diameter);
	}
	
	
	// get and set
	public void setColor(int r, int g, int b) { // set the color using rgb values
		color = new Color(r,g,b);
	}
	public void setColor(Color c) { // set color using a preset color
		color = c;
	}
	public Color getColor() {
		return color;
	}
	public void setDiameter(int d) {
		diameter = d;
	}
	public int getDiameter() {
		return diameter;
	}
	public void setLoc(int x, int y) {
		xloc = x;
		yloc = y;
	}
	public void setX(int x) {
		xloc = x;
	}
	public void setY(int y) {
		yloc = y;
	}
	public int getX() {
		return xloc;
	}
	public int getY() {
		return yloc;
	}

}
