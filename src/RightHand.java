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
	private final int DIAMETER = 50; // NOT RELATIVE diameter of circle
	private final int WINDOW_LENGTH = 100; // relative sizes, will be scaled appropriately in MyPanel
	private final int WINDOW_HEIGHT = 100;
	private final int INITIAL_X = 50; // initial (relative) coordinates we need to get back to
	private final int INITIAL_Y = 10;

	// initial variables that probably won't be changed
	private int bpMin; // beats per minute
	private int bpBar; // beats per bar
	private double fps;
	private int fpBeat; // length of one beat in frames = fps * 60 / bpmin
	
	// variables that will be changed
	private int xloc, yloc; // current location; NOTE: these locations are only with respect to the window
	private Color color;
	private int currentBeat;
	private int currentFrame; // the frame we are on of the beat (reset to 0 every time we go to next beat)
	
	
	// constructors
	public RightHand(double fps, int bpM, int bpB, Color color) {
		this.fps = fps;
		bpMin = bpM;
		bpBar = bpB;
		this.color = color;
		
		fpBeat = (int)(fps * 60.0 / (double)bpMin);
		
		currentBeat = 0; // initialize at 0
		currentFrame = 0; // initialize at 0
	}
	
	
	
	// main methods
	public void update() {
		
		// TEMPORARY: THIS IS ONLY FOR 4/4 TIME SIGNATURE
		switch(currentBeat) {
		
			case 0: // beat 0 moves from initial point straight down
				xloc = xloc; // xloc unchanged since we are moving along y only
				yloc = (int)( Math.sin(2.0 * Math.PI * (double)currentFrame / (double)fpBeat) );
			case 1: // beat 1 moves from beat 0 loc to the left
				xloc = xloc;
				yloc = yloc;
			case 2: // beat 2 moves from beat 1 loc to the far right
				xloc = xloc;
				yloc = yloc;
			case 3: // beat 3 moves from beat 2 loc up and left to reach initial point
				xloc = xloc;
				yloc = yloc;
		}
		
	}
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.fillOval(xloc, yloc, DIAMETER, DIAMETER);
		
	}
	
	
	
	
	// internal functions
	private void incBeatNum() {
		currentBeat++;
		currentBeat = currentBeat % bpBar;
		currentFrame = 0; // reset current frame back to 0
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

}
