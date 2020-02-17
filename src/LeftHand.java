/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class LeftHand {

	private final int INITIAL_X = 100; // xloc and yloc
	private final int INITIAL_Y = 0;
	private final int DIAMETER = 20; // diameter of circle
	private final Color MAX_COLOR = new Color(255, 0, 0); // color of circle at the highest dynamics
	private final Color MIN_COLOR = new Color(67, 0, 144); // color of circle at the lowest dynamics
	private final Color FONT_COLOR = Color.white;

	
	private Color color;
	private int[] xPointsUpTriangle; // x coordinates of the "up" triangle vertices
	private int[] yPointsUpTriangle; // y coordinates of the "up" triangle vertices
	private int[] xPointsDownTriangle; // x coordinates of the "down" triangle vertices
	private int[] yPointsDownTriangle; // y coordinates of the "down" triangle vertices
	
	private int xloc, yloc; // location of the left hand
	private int xOffset, yOffset; // based on the location of this window on the panel
	
	private int currentMeasure;
	private int dynamics; // the current dynamics
	private int alpha; // color transparency

	
	private List<MusicPart> partList;
	
	
	
	public LeftHand(int x0, int y0, int dynamics, List<MusicPart> partList) {
		
		xOffset = x0;
		yOffset = y0;
		xloc = INITIAL_X;
		yloc = INITIAL_Y;
		this.dynamics = dynamics;
		this.partList = partList;
		currentMeasure = 0;
		
		color = dynamicsToColor(dynamics); // initial color based on initial dynamics
		alpha = 255; //completely opaque
		
		// initialize coordinates for up triangle and down triangle with respect to the center of a 20x20 square
		xPointsUpTriangle = new int[3];
		yPointsUpTriangle = new int[3];
		xPointsDownTriangle = new int[3];
		yPointsDownTriangle = new int[3];
		
		// the location of the triangle is the offset from MyPanel + its location on Left Hand's window, with vertices lying around that point
		xPointsUpTriangle[0] = 0 + xOffset + xloc; // bottom left corner
		yPointsUpTriangle[0] = 20 + yOffset + yloc;
		xPointsUpTriangle[1] = 10 + xOffset + xloc; // top center
		yPointsUpTriangle[1] = 0 + yOffset + yloc;
		xPointsUpTriangle[2] = 20 + xOffset + xloc; // bottom right corner
		yPointsUpTriangle[2] = 20 + yOffset + yloc;
		
		xPointsDownTriangle[0] = 0 + xOffset + xloc; // top left corner
		yPointsDownTriangle[0] = 0 + yOffset + yloc;
		xPointsDownTriangle[1] = 10 + xOffset + xloc; // bottom center
		yPointsDownTriangle[1] = 20 + yOffset + yloc;
		xPointsDownTriangle[2] = 20 + xOffset + xloc; // top right corner
		yPointsDownTriangle[2] = 0 + yOffset + yloc;
		
		
	}
	
	public void update(int measureNum) {
		
		if(currentMeasure != measureNum) { // if we reached a new measure...

			currentMeasure = measureNum; // update it
			if(alpha <= 0) alpha = 255; // set alpha back to 255 if it's at or below 0
			
			int averageNextDynamics = 0;
			// update the upcoming dynamics for this measure
			int partnum = 0;
			for(MusicPart part : partList) { // for each part...
				averageNextDynamics = averageNextDynamics + part.getMeasures().get(measureNum).getDynamics();
				partnum++;
			}
			averageNextDynamics = averageNextDynamics / partnum;
		
			dynamics = averageNextDynamics;
			System.out.println("Measure: " + measureNum + " Average Dynamics: " + averageNextDynamics);
		
			// update the color
			color = dynamicsToColor(dynamics);
		
		}
		
	}
	
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.setFont(new Font("Brush Script MT", Font.PLAIN, 36));
		
		if(dynamics == 76) { // if the dynamics are staying the same...
			g.fillOval(xOffset+xloc, yOffset+yloc, DIAMETER, DIAMETER); // draw a circle
			
		}
		else if(dynamics > 76){ // if the dynamics are getting louder...
			g.fillPolygon(xPointsUpTriangle, yPointsUpTriangle, 3); // up triangle
			g.setColor(new Color(FONT_COLOR.getRed(), FONT_COLOR.getGreen(), FONT_COLOR.getBlue(), alpha));
			g.drawString("Forte", xOffset + 75, yOffset + 100);
			if(alpha > 0) alpha = alpha - 2; // decrement alpha for a fade effect
			if(alpha < 0) alpha = 0; // can't have a negative alpha!
		}
		else { // if the dynamics are getting softer...
			g.fillPolygon(xPointsDownTriangle, yPointsDownTriangle, 3); // down triangle
			g.setColor(new Color(FONT_COLOR.getRed(), FONT_COLOR.getGreen(), FONT_COLOR.getBlue(), alpha));
			g.drawString("Piano", xOffset + 75, yOffset + 100);
			if(alpha > 0) alpha = alpha - 2; // decrement alpha for a fade effect
			if(alpha < 0) alpha = 0; // can't have a negative alpha!
		}
		
		
	}
	
	// Copied from RightHand class
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
	
	
}
