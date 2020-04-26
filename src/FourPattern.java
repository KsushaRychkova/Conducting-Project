/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

public class FourPattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 4;
	
	
	public FourPattern(double fpB, int startBeat) {
		super(fpB, startBeat);
		bpBar = BEATS_PER_BAR;
	}

	@Override
	void update() {
		
		if(frameNum >= fpBeatInt) {
			incBeatNum();
		}
		
		switch(beatNum) {
		
			case 0: // beat 0 moves from initial point straight down
				xloc = xloc; // xloc unchanged since we are moving along y only; start at 150, end at 150
				yloc = (int)( -150.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt) + 150.0); // start at 0, end at 300
				break;
			case 1: // beat 1 moves from beat 0 loc to the left
				xloc = (int)( 75.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt) + 75.0); // start at 150, end at 0
				yloc = (int)( 75.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt) + 225.0); // start at 300, end at 150
				break;
			case 2: // beat 2 moves from beat 1 loc to the far right
				xloc = (int)( -150.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt) + 150.0); // start at 0, end at 300
				yloc = yloc; // yloc unchanged
				break;
			case 3: // beat 3 moves from beat 2 loc up and left to reach initial point
				xloc = (int)( 75.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt) + 225.0); // start at 300, end at 150
				yloc = (int)( 75.0 * Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt) + 75.0); // start at 150, end at 0
				break;
		}
		
		frameNum++;
		totalframes++;
		
	}
	
	
}
