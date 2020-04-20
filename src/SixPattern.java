/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

public class SixPattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 6;
	
	
	public SixPattern(double fpB, int startBeat) {
		super(fpB, startBeat);
		bpBar = BEATS_PER_BAR;
	}

	@Override
	void update() {
		
		if(frameNum >= fpBeatInt) {
			incBeatNum();
		}
		
		// t is a variable to make it easier to follow the math. It also represents the speed at which the xlocs and ylocs change.
		double t = Math.cos( Math.PI * (double)frameNum / (double)fpBeatInt);
		
		// saving xloc as a double so we can use it to calculate yloc
		double x;
		
		switch(beatNum) {
		
			case 0: // beat 0 moves from initial point straight down
				xloc = xloc; // xloc unchanged since we are moving along y only; start at 150, end at 150
				yloc = (int)( -150.0 * t + 150.0); // start at 0, end at 300
				break;
			case 1: // beat 1 jumps from loc0 to the right
				x = 25.0 * t + 125.0; // x moves linearly from 150 to 100 (since t moves from 1 to -1)
				xloc = (int)x; // start at 150, end at 100
				yloc = (int)( 0.04 * (x - 125.0)*(x - 125.0) + 275.0 ); // start at 300, end at 300
				break;
			case 2: // beat 2 jumps from loc1 to the right again
				x = 25.0 * t + 75.0; // x moves linearly from 100 to 50 (since t moves from 1 to -1)
				xloc = (int)x; // start at 100, end at 50
				yloc = (int)( 0.04 * (x - 75.0)*(x - 75.0) + 275.0 ); // start at 300, end at 300
				break;
			case 3: // beat 3 jumps from loc2 to the left side
				x = -100.0 * t + 150.0; // x moves linearly from 50 to 250 (since t moves from 1 to -1)
				xloc = (int)x; // start at 50, end at 250
				yloc = (int)(-Math.sqrt(200.0 * (x-50.0)) + (x-50.0) + 300); // start at 300, end at 300
				break;
			case 4: // beat 4 jumps from loc3 to the left
				x = -25.0 * t + 275.0; // x moves linearly from 250 to 300 (since t moves from 1 to -1)
				xloc = (int)x; // start at 250, end at 300
				yloc = (int)( 0.04 * (x - 275.0)*(x - 275.0) + 275.0 ); // start at 300, end at 300
				break;
			case 5: // beat 5 swings up from loc4 to the initial point
				x = 75.0 * t + 225.0; // x moves linearly from 300 to 150 (since t moves from 1 to -1)
				xloc = (int)x; // start at 300, end at 150
				yloc = (int)(150.0 * t + 150.0); // start at 300, end at 0
				break;
				
		}
		frameNum++;
		totalframes++;
		
	}
	
	
}
