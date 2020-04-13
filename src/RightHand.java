/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;


public class RightHand {
	
	// constants
	private final int DIAMETER = 30; // diameter of circle
	private final int TRAIL_LIMIT = 50; // the number of previous circle positions to be stored in trail
	private final Color COLOR = Color.white;

	// initial variables that probably won't be changed
	private int xOffset, yOffset;
	private int bpMin; // beats per minute
	private int bpBar; // beats per bar
	private double fps;
	private double fpBeat; // length of one beat in frames = fps * 60 / bpmin
	
	// variables that will be changed
	private int xloc, yloc; // current location; NOTE: these locations are only with respect to the window
	private Color color; // dependent on the dynamics
	private int measureNum; // current measure
	
	// variables to do with the trail
	private Queue<Circle> trail;
	private Boolean queueFull;
	private Color bgColor;
	
	private RightHandPattern pattern;
	
	
	// constructors
	public RightHand(double fps, int bpM, int bpB, int x0, int y0, Color bg) {
		this.fps = fps;
		bpMin = bpM;
		bpBar = bpB;
		xOffset = x0;
		yOffset = y0;
		bgColor = bg;
		
		fpBeat = fps * 60.0 / (double)bpMin; // find fbBeat from the fps and bpMin
		color = COLOR;
		
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
			case 6:
				pattern = new SixPattern(fpBeat);
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
		measureNum = pattern.getMeasureNum();
		
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
		fpBeat = (int)(fps * 60.0 / (double)bpMin); // automatically update fpBeat since it depends entirely on the fps and bpMin
		pattern.setFpBeat(fpBeat); // update it in the pattern
	}
	public int getMeasureNum() {
		return measureNum;
	}

}
