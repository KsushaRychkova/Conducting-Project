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
import java.util.List;
import java.util.Queue;


public class RightHand {
	
	// constants
	private final int DIAMETER = 20; // diameter of circle
	private final int TRAIL_LIMIT = 50; // the number of previous circle positions to be stored in trail
	private final Color MAX_COLOR = new Color(255, 0, 0); // color of circle at the highest dynamics
	private final Color MIN_COLOR = new Color(67, 0, 144); // color of circle at the lowest dynamics

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
	private int dynamics; // the current dynamics
	//private int nextDynamics; // when we have a change in dynamics, we want to get to it gradually, so this is the new dynamics we are changing to
	
	// variables to do with the trail
	private Queue<Circle> trail;
	private Boolean queueFull;
	private Color bgColor;
	
	private RightHandPattern pattern;
	private List<MusicPart> partList;
	
	
	// constructors
	public RightHand(double fps, int bpM, int bpB, int dynamics, int x0, int y0, Color bg, List<MusicPart> partList) {
		this.fps = fps;
		bpMin = bpM;
		bpBar = bpB;
		this.dynamics = dynamics;
		//nextDynamics = dynamics; // start at the same value
		xOffset = x0;
		yOffset = y0;
		bgColor = bg;
		this.partList = partList;
		
		fpBeat = fps * 60.0 / (double)bpMin; // find fbBeat from the fps and bpMin
		color = dynamicsToColor(dynamics); // set initial color based on dynamics
		
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
		measureNum = pattern.getMeasureNum();
		
		//updateDynamics();
		
	}
	/*
	public void updateDynamics() {
		if(measureNum > partList.get(0).getMeasures().size()) { // if the current measure is more than the total number of measures in the list...
			return;
		}
		else {
			nextDynamics = partList.get(0).getMeasures().get(measureNum-1).getDynamics(); // first part's measure that we are on's dynamics
			if(nextDynamics - dynamics == 1) { // if we're only 1 off, just make them equal
				dynamics = nextDynamics;
			}
			else if(dynamics != nextDynamics) { // if the dynamics changed this measure...
				dynamics += (int)((double)(nextDynamics - dynamics) / (double)(bpBar * 10)); // gradual increase throughout the measure
			}
		}
	}*/
	
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
	private Color dynamicsToColor(int dynamics) { // converts the dynamics value to a color
		// I'm choosing 20 to be the dynamic threshold at which the color reaches MIN_COLOR, 
		// and 200 to be the dynamic threshold at which the color reaches MAX_COLOR. If the
		// dynamic goes below the min, it stays at the min. If it goes above the max, it 
		// stays at the max. The gradient will be between 20 and 200.
		
		if(dynamics <= 20) {
			return MIN_COLOR;
		}
		else if(dynamics >= 200) {
			return MAX_COLOR;
		}
		
		double percent = (double)(dynamics - 20) / 180.0; // percentile where dynamics lies between 20 and 200
		int r = (int)((double)MAX_COLOR.getRed() * percent + (double)MIN_COLOR.getRed() * (1.0 - percent));
		int g = (int)((double)MAX_COLOR.getGreen() * percent + (double)MIN_COLOR.getGreen() * (1.0 - percent));
		int b = (int)((double)MAX_COLOR.getBlue() * percent + (double)MIN_COLOR.getBlue() * (1.0 - percent));
		
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
	public int getMeasureNum() {
		return measureNum;
	}

}
