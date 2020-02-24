import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

public class Orchestra {

	private final int LEFT_BOUND = 50; // the leftmost x coordinate for the orchestra; it will be between this and the right bound
	private final int RIGHT_BOUND = 700; // the rightmost x coordinate
	private final int TOP_BOUND = 400; // top bound
	private final Color FONT_COLOR = Color.white; // font color

	
	private Color[] colorList; // array of colors; to be used in 
	
	private int numParts; // number of parts (instruments) in the  piece
	private List<MusicPart> partList;
	private String[] instrumentList;
	
	private int colorListOffset; // offset in the color list
	private int blockLength; // length of the blocks we will be displaying for the orchestra
	private int blockHeight; // height of the blocks
	private Font font; // font for the labels
	
	
	public Orchestra(List<MusicPart> partList) {
		
		this.partList = partList;
		numParts = partList.size();
		
		instrumentList = new String[numParts];
		
		// get the instrument names into our instrumentList array
		int i = 0;
		for(MusicPart part : partList) {
			instrumentList[i] = part.getInstrumentName();
			System.out.println(instrumentList[i]);
		}
		
		// color list
		colorList = new Color[12];
		initColors();
		colorListOffset = (int)(Math.random() * 12.0);
		
		blockLength = (int)((RIGHT_BOUND - LEFT_BOUND) / numParts); // the distance we have, split between numParts
		blockHeight = 150;
		
		font = new Font("Arial Rounded MT Bold", Font.PLAIN, 20); // font for the labels
		
		
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
	
	public void update() {
		
	}
	
	public void draw(Graphics g) {
		
		for(int i = 0; i < numParts; i++) { // draw the block for each part
			
			// draw the blocks
			g.setColor(colorList[(colorListOffset + i) % 12]); // pick the next color
			g.fillRoundRect(LEFT_BOUND + blockLength * i, TOP_BOUND, blockLength - 10, blockHeight, 20, 20);
			
			// draw the words
			g.setColor(FONT_COLOR);
			//g.drawString(instrumentList[i], LEFT_BOUND + blockLength * i + 5, TOP_BOUND + blockHeight + 10);
		}
		
		
	}
	
	
	
	
	
}
