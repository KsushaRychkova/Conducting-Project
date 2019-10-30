/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public class FourFourPattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 4;
	
	
	public FourFourPattern(int fpB) {
		super(fpB);
		bpBar = BEATS_PER_BAR;
		beatNum = 0; // starting beat number; MAKE SURE TO CHANGE THIS IF NEEDED
	}

	@Override
	void update() {
		
		if(frameNum >= fpBeat) {
			incBeatNum();
		}
		
		switch(beatNum) {
		
			case 0: // beat 0 moves from initial point straight down
				xloc = xloc; // xloc unchanged since we are moving along y only
				yloc = (int)( -50.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeat) + 50.0); // start at 0, end at 100
				break;
			case 1: // beat 1 moves from beat 0 loc to the left
				xloc = (int)( 25.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeat) + 25.0); // start at 50, end at 0
				yloc = (int)( 25.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeat) + 75.0); // start at 100, end at 50
				break;
			case 2: // beat 2 moves from beat 1 loc to the far right
				xloc = (int)( -50.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeat) + 50.0); // start at 0, end at 100
				yloc = yloc; // yloc unchanged
				break;
			case 3: // beat 3 moves from beat 2 loc up and left to reach initial point
				xloc = (int)( 25.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeat) + 75.0); // start at 100, end at 50
				yloc = (int)( 25.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeat) + 25.0); // start at 50, end at 0
				break;
		}
		
		frameNum++;
		
	}
	
	
}
