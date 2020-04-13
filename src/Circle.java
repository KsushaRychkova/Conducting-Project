/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.awt.Color;


public class Circle {
	// This class is used in the RightHand class to easier keep track of previous positions and colors of the trail of circles.

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
