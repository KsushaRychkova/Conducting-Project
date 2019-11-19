/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.Queue;


public class RightHand {
	
	// constants
	private final int DIAMETER = 20; // diameter of circle
	private final int WINDOW_LENGTH = 300; // relative sizes, will be scaled appropriately in MyPanel
	private final int WINDOW_HEIGHT = 300;
	private final int TRAIL_LIMIT = 50; // the number of previous circle positions to be stored in trail

	// initial variables that probably won't be changed
	private int xOffset, yOffset;
	private int bpMin; // beats per minute
	private int bpBar; // beats per bar
	private double fps;
	private int fpBeat; // length of one beat in frames = fps * 60 / bpmin
	
	// variables that will be changed
	private int xloc, yloc; // current location; NOTE: these locations are only with respect to the window
	private Color color;
	
	// variables to do with the trail
	private Queue<Circle> trail;
	private Boolean queueFull;
	private Color bgColor;
	
	private RightHandPattern pattern;
	
	
	// constructors
	public RightHand(double fps, int bpM, int bpB, Color color, int x0, int y0, Color bg) {
		this.fps = fps;
		bpMin = bpM;
		bpBar = bpB;
		this.color = color;
		xOffset = x0;
		yOffset = y0;
		bgColor = bg;
		
		fpBeat = (int)(fps * 60.0 / (double)bpMin);
		
		switch(bpBar) { // decide which pattern to use based off of the beats per bar
			case 1:
				
				break;
			case 2:
				pattern = new TwoFourPattern(fpBeat);
				break;
			case 3:
				pattern = new ThreeFourPattern(fpBeat);
				break;
			case 4:
				pattern = new FourFourPattern(fpBeat);
				break;
		}
		
		trail = new LinkedList<>();
		queueFull = false;
		
	}
	
	
	
	// main methods
	public void update() {
		
		pattern.update();
		xloc = pattern.getX();
		yloc = pattern.getY();
		
	}
	public void draw(Graphics g) {
		
		trail.add(new Circle(color, DIAMETER, xloc, yloc)); // add new circle to the end of the queue
		if(!queueFull) {
			if(trail.size() >= TRAIL_LIMIT) queueFull = true;
		}
		else{ // only remove members of the queue if the queue is full
			trail.remove();
		}
		
		// draw each circle in the trail
		int stepnum = 0;
		for(Circle circle : trail) {
			g.setColor(darken(circle.getColor(), stepnum));
			g.fillOval(xOffset+circle.getXloc(), yOffset+circle.getYloc(), circle.getDiameter(), circle.getDiameter());
			stepnum++;
		}
		
		/*
		g.setColor(color);
		g.fillOval(xOffset+scale*xloc, yOffset+scale*yloc, DIAMETER, DIAMETER);
		*/
		
	}
	
	// other methods
	private Color darken(Color color, int stepnum) { // creates the right color for the step in the gradient
		
		double percent = (double)stepnum / (double)TRAIL_LIMIT;
		int r = (int)((double)color.getRed() * percent + (double)bgColor.getRed() * (1.0 - percent));
		int g = (int)((double)color.getGreen() * percent + (double)bgColor.getGreen() * (1.0 - percent));
		int b = (int)((double)color.getBlue() * percent + (double)bgColor.getBlue() * (1.0 - percent));
		
		//int a = (int)((percent) * 255.0); // alpha value is opacity
		//Color result = new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
		
		Color result = new Color(r, g, b);
		return result;
		
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
