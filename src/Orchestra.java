/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

public class Orchestra {

	private final int LEFT_BOUND = 50; // the leftmost x coordinate for the orchestra; it will be between this and the right bound
	private final int RIGHT_BOUND = 700; // the rightmost x coordinate
	private final int TOP_BOUND = 400; // top bound
	private final Color FONT_COLOR = Color.white; // font color
	private final Color UP_COLOR = Color.orange; // color of up arrow for dynamics
	private final Color DOWN_COLOR = Color.blue; // color of down arrow for dynamics
	
	// triangle points -- these are used as a basis
	private int[] xPointsUpTriangle; // x coordinates of the "up" triangle vertices
	private int[] yPointsUpTriangle; // y coordinates of the "up" triangle vertices
	private int[] xPointsDownTriangle; // x coordinates of the "down" triangle vertices
	private int[] yPointsDownTriangle; // y coordinates of the "down" triangle vertices
	
	// triangle points -- these are the ones actually used to draw the triangles
	private int[] xUp;
	private int[] yUp;
	private int[] xDown;
	private int[] yDown;
	
	private Color[] colorList; // array of colors; to be used in picking colors for the orchestra blocks
	
	private int numParts; // number of parts (instruments) in the  piece
	private List<MusicPart> partList;
	private String[] instrumentList;
	private int bpBar; // beats per bar to display time signature
	private int beatType; // beat type to display time signature
	
	// dynamics
	private int[] dynamics; // array of dynamics, one index per instrument
	private int[] nextDynamics; // array of upcoming dynamics, so that we can compare to current dynamics
	private int[] dynamicsChange; // how the dynamics are changing for each instrument in the current measure. <0 for decreasing, >0 for increasing
	private int[] wordAlphas; // alphas for fading out the dynamics word display
	
	// rest measures
	private boolean[] restMeasures; // array of current rest measures, array is updated at the start of each measure
	private boolean[] prevRestMeasures; // saves the previous measure's rest status
	private boolean[] nextRestMeasures; // the upcoming rest measures
	private int[] instrumentAlphas; // alpha is color transparency, and the factor by which the colors are faded if it's a rest measure

	private Color tempColor; // declare space for a temporary color now so we can just overwrite at every iteration
	
	private int currentMeasure; // the measure we are on
	
	private int colorListOffset; // offset in the color list
	private int blockLength; // length of the blocks we will be displaying for the orchestra
	private int blockHeight; // height of the blocks
	private Font instrumentFont; // font for the labels
	private Font measureNumFont; // font for the measure number display
	private Font dynamicsFont; // font for the dynamics word display
	
	
	public Orchestra(List<MusicPart> partList) {
		
		this.partList = partList;
		numParts = partList.size();
		
		instrumentList = new String[numParts];
		dynamics = new int[numParts];
		nextDynamics = new int[numParts];
		dynamicsChange = new int[numParts];
		wordAlphas = new int[numParts];
		
		restMeasures = new boolean[numParts];
		prevRestMeasures = new boolean[numParts];
		nextRestMeasures = new boolean[numParts];
		instrumentAlphas = new int[numParts];
		
		currentMeasure = 0;
		
		// get the instrument names into our instrumentList array, also set up the dynamics and rest measures arrays
		int i = 0;
		for(MusicPart part : partList) {
			instrumentList[i] = part.getInstrumentName();
			
			dynamics[i] = 72; // initial dynamics are all 72 (default value)
			nextDynamics[i] = part.getMeasures().get(0).getDynamics(); // dynamics for the first measure
			dynamicsChange[i] = nextDynamics[i] - dynamics[i]; // decreasing if <0, increasing if >0
			wordAlphas[i] = 255; // when each word appears, it will be fully opaque and will then fade out
			
			restMeasures[i] = part.getMeasures().get(0).isRestMeasure(); // see if the first measure is a rest measure for any of the parts
			prevRestMeasures[i] = false;
			if(currentMeasure < part.getMeasures().size()) { // if there even is another measure
				nextRestMeasures[i] = part.getMeasures().get(1).isRestMeasure(); // next measure
			}
			if(restMeasures[i] == true) { // if the starting measure is a rest measure for instrument at index i...
				instrumentAlphas[i] = 30; // start off transparent
			}
			else{ // otherwise...
				instrumentAlphas[i] = 255; // start off completely opaque
			}
			
			i++;
		}
		
		// color list
		colorList = new Color[12];
		initColors(); // sets up the individual colors
		colorListOffset = (int)(Math.random() * 12.0);
		
		blockLength = (int)((RIGHT_BOUND - LEFT_BOUND) / numParts); // the distance we have, split between numParts
		blockHeight = 150;
		
		instrumentFont = new Font("Cambria", Font.PLAIN, 26); // font for the labels
		measureNumFont = new Font("Cambria", Font.PLAIN, 40); // font for the measure number display
		dynamicsFont = new Font("Cambria", Font.PLAIN, 20); // font for the dynamics word display
		
		bpBar = partList.get(0).getMeasures().get(0).getBeats(); // beats per bar
    	beatType = partList.get(0).getMeasures().get(0).getBeatType(); // beat type
		
		initTriangles(); //sets up the individual triangles
		
		
	}
	
	private void initColors() { // set up the colors
		
		Color c1 = new Color(255, 239, 1); // yellow
		Color c2 = new Color(248, 182, 44); // yellow-orange
		Color c3 = new Color(236, 84, 21); // red-orange
		Color c4 = new Color(231, 0, 18); // red
		Color c5 = new Color(226, 0, 126); // magenta
		Color c6 = new Color(147, 7, 132); // purple
		Color c7 = new Color(41, 64, 169); // indigo
		Color c8 = new Color(0, 110, 185); // blue
		Color c9 = new Color(0, 161, 233); // sky blue
		Color c10 = new Color(1, 153, 68); // green
		Color c11 = new Color(144, 194, 33); // green-yellow
		Color c12 = new Color(218, 224, 0); // yellow-green
		
		colorList[0] = c1;
		colorList[1] = c2;
		colorList[2] = c3;
		colorList[3] = c4;
		colorList[4] = c5;
		colorList[5] = c6;
		colorList[6] = c7;
		colorList[7] = c8;
		colorList[8] = c9;
		colorList[9] = c10;
		colorList[10] = c11;
		colorList[11] = c12;
		
	}
	
	private void initTriangles() { // set up the triangles
		
		// initialize coordinates for up triangle and down triangle with respect to the center of a 20x20 square
		xPointsUpTriangle = new int[3];
		yPointsUpTriangle = new int[3];
		xPointsDownTriangle = new int[3];
		yPointsDownTriangle = new int[3];
		
		xUp = new int[3];
		yUp = new int[3];
		xDown = new int[3];
		yDown = new int[3];
				
		// the the locations of the triangle vertices; top center is the origin
		xPointsUpTriangle[0] = -30; // bottom left corner
		yPointsUpTriangle[0] = 40;
		xPointsUpTriangle[1] = 0; // top center
		yPointsUpTriangle[1] = 0;
		xPointsUpTriangle[2] = 30; // bottom right corner
		yPointsUpTriangle[2] = 40;
				
		xPointsDownTriangle[0] = -30; // top left corner
		yPointsDownTriangle[0] = 0;
		xPointsDownTriangle[1] = 0; // bottom center
		yPointsDownTriangle[1] = 40;
		xPointsDownTriangle[2] = 30; // top right corner
		yPointsDownTriangle[2] = 0;
		
	}
	
	public void update(int measureNum) {
		
		for(int i = 0; i < numParts; i++) {
			adjustAlphas(i);
		}
		
		if(currentMeasure != measureNum) {
			
			currentMeasure = measureNum; // update the current measure
			
			int i = 0;
			for(MusicPart part : partList) {
				
				// dynamics
				wordAlphas[i] = 255; // reset back to fully opaque
				
				
				dynamics[i] = nextDynamics[i];
				if(part.getMeasures().size() > measureNum) { // check if there even is a next measure
					nextDynamics[i] = part.getMeasures().get(measureNum).getDynamics();
				}
				if(nextDynamics[i] == 72) { // if we're going back to normal dynamics
					dynamicsChange[i] = 0; // we want this here so that we don't display an up in dynamics every time we're getting out of a down
				}
				else {
					dynamicsChange[i] = nextDynamics[i] - dynamics[i]; // decreasing if <0, increasing if >0
				}
				
				
				
				// rest measures
				prevRestMeasures[i] = restMeasures[i]; // save the previous measure's rest statuses
				restMeasures[i] = nextRestMeasures[i]; // update restMeasures with current measure's rests
				if(part.getMeasures().size() > measureNum + 1) { // check if there even is a next measure
					nextRestMeasures[i] = part.getMeasures().get(measureNum+1).isRestMeasure(); // set nextRestMeasures to the next measure's rests
				}	
				
				i++;
			}
			
		}
		
		
	}
	
	private void drawUpTriangle(Graphics g, int instrument) { // draws the up triangle, given the instrument index
		
		for(int i = 0; i < 3; i++) { // loop through each point in the triangle
			xUp[i] = xPointsUpTriangle[i] + LEFT_BOUND + blockLength * instrument + (int)(blockLength / 2);
			yUp[i] = yPointsUpTriangle[i] + TOP_BOUND - 50; // padding of 10 pixels
		}
		g.fillPolygon(xUp, yUp, 3); // up triangle
		
	}
	private void drawDownTriangle(Graphics g, int instrument) { // draws the down triangle, given the instrument index
		
		for(int i = 0; i < 3; i++) { // loop through each point in the triangle
			xDown[i] = xPointsDownTriangle[i] + LEFT_BOUND + blockLength * instrument + (int)(blockLength / 2);
			yDown[i] = yPointsDownTriangle[i] + TOP_BOUND + blockHeight + 15; // padding of 15 pixels
		}
		g.fillPolygon(xDown, yDown, 3); // down triangle
		
	}
	
	private void adjustAlphas(int i) { // fix the alphas[i] based on prevRestMeasures[i] and restMeasures[i]
		
		if(prevRestMeasures[i] == true && restMeasures[i] == false) { // if prev is a rest measure and current is not, fade in
			instrumentAlphas[i] = instrumentAlphas[i] + 3;
			if(instrumentAlphas[i] > 255) instrumentAlphas[i] = 255; // max allowed is 255 (completely opaque)
		}
		else if(restMeasures[i] == false && nextRestMeasures[i] == true) { // if current is not a rest measure and next is, fade out
			instrumentAlphas[i] = instrumentAlphas[i] - 3; // decrease alpha
			if(instrumentAlphas[i] < 30) instrumentAlphas[i] = 30; // alpha goes no lower than 30, since we still want to see a block there
		}

	}
	
	private String getDynamicsString(int d) { // given the integer of the dynamics, return the string that would represent this
		if(d <= 25) return "pianississimo"; // ppp; average between 16(ppp) and 33(pp)
		if(d <= 41) return "pianissimo"; // pp; average between 33(pp) and 49(p)
		if(d <= 57) return "piano"; // p; average between 49(p) and 64(mp)
		if(d <= 72) return "mezzo-piano"; // mp; average between 64(mp) and 80(mf)
		if(d <= 88) return "mezzo-forte"; // mf; average between 80(mf) and 96(f)
		if(d <= 104) return "forte"; // f; average between 96(f) and 112(ff)
		if(d <= 120) return "fortissimo"; // ff; average between 112(ff) and 127(fff)
		else return "fortississimo"; // fff
	}
	
	public void draw(Graphics g) {
		
		for(int i = 0; i < numParts; i++) { // draw the block for each part
			
			// draw the blocks
			tempColor = colorList[(colorListOffset + i) % 12]; // the color we're supposed to draw
			tempColor = new Color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue(), instrumentAlphas[i]); // set the new alpha value
			g.setColor(tempColor); // use this color
			g.fillRoundRect(LEFT_BOUND + blockLength * i, TOP_BOUND, blockLength - 10, blockHeight, 20, 20);
			
			
			// draw the words for the instruments
			g.setFont(instrumentFont);
			g.setColor(FONT_COLOR);
			g.drawString(instrumentList[i], LEFT_BOUND + blockLength * i + 5, TOP_BOUND + blockHeight + 10);

			
			// draw the arrows and words for dynamics
			if(dynamicsChange[i] < 0) { // dynamics are decreasing; down arrow
				// arrow
				g.setColor(DOWN_COLOR);
				drawDownTriangle(g, i);
				
				// draw the dynamics words below the arrow
				if(nextDynamics[i] != 72) { // if we are aren't just going back to the default...
					g.setFont(dynamicsFont);
					g.setColor(new Color(255, 255, 255, wordAlphas[i])); // white, fading out
					g.drawString(getDynamicsString(nextDynamics[i]), LEFT_BOUND + blockLength * i + (int)(blockLength / 2) - 25, TOP_BOUND + blockHeight + 85);
					if(wordAlphas[i] > 0) wordAlphas[i] = wordAlphas[i] - 2; // decrement alpha for a fade effect
					if(wordAlphas[i] < 0) wordAlphas[i] = 0; // can't have a negative alpha!
				}
			}
			if(dynamicsChange[i] > 0) { // dynamics are increasing; up arrow
				g.setColor(UP_COLOR);
				drawUpTriangle(g, i);
				
				// draw the dynamics words above the arrow
				if(nextDynamics[i] != 72) { // if we are aren't just going back to the default...
					g.setFont(dynamicsFont);
					g.setColor(new Color(255, 255, 255, wordAlphas[i])); // white, fading out
					g.drawString(getDynamicsString(nextDynamics[i]), LEFT_BOUND + blockLength * i + (int)(blockLength / 2) - 25, TOP_BOUND - 50);
					if(wordAlphas[i] > 0) wordAlphas[i] = wordAlphas[i] - 2; // decrement alpha for a fade effect
					if(wordAlphas[i] < 0) wordAlphas[i] = 0; // can't have a negative alpha!
				}
			}
			
			
		}
		
		// draw the words for the measure number and time signature
		g.setFont(measureNumFont);
		g.setColor(FONT_COLOR);
		g.drawString("Time Signature: ", 600, 700);
		g.drawString("" + bpBar, 875, 675);
		g.drawString("" + beatType, 895, 705);
		g.drawString("Measure:   " + partList.get(0).getMeasures().get(currentMeasure).getNumber() + "       out of   " + partList.get(0).getMeasures().size(), 600, 800);
		g.drawString("Tempo:  " + partList.get(0).getMeasures().get(currentMeasure).getTempo(), 600, 900);
		
	}
	
	
	
	
	
}
