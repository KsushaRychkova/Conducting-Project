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
	
	// constants
	private final int DIAMETER = 20; // diameter of circle
	private final int WINDOW_LENGTH = 100; // relative sizes, will be scaled appropriately in MyPanel
	private final int WINDOW_HEIGHT = 100;

	// initial variables that probably won't be changed
	private int xOffset, yOffset;
	private int scale;
	private int bpMin; // beats per minute
	private int bpBar; // beats per bar
	private double fps;
	private int fpBeat; // length of one beat in frames = fps * 60 / bpmin
	
	// variables that will be changed
	private int xloc, yloc; // current location; NOTE: these locations are only with respect to the window
	private Color color;
	
	private RightHandPattern pattern;
	
	
	// constructors
	public RightHand(double fps, int bpM, int bpB, Color color, int x0, int y0, int scale) {
		this.fps = fps;
		bpMin = bpM;
		bpBar = bpB;
		this.color = color;
		xOffset = x0;
		yOffset = y0;
		this.scale = scale;
		
		fpBeat = (int)(fps * 60.0 / (double)bpMin);
		
		switch(bpBar) { // decide which pattern to use based off of the beats per bar
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				pattern = new FourFourPattern(fpBeat);
				break;
		}
		
	}
	
	
	
	// main methods
	public void update() {
		
		pattern.update();
		xloc = pattern.getX();
		yloc = pattern.getY();
		
	}
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.fillOval(xOffset+scale*xloc, yOffset+scale*yloc, DIAMETER, DIAMETER);
		
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
	public void setbpM(int bpM) {
		bpMin = bpM;
		fpBeat = (int)(fps * 60.0 / (double)bpMin);
	}

}
