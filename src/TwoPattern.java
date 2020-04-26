/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */


public class TwoPattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 2;
	
	
	public TwoPattern(double fpB, int startBeat) {
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
		
			case 0: // beat 0 moves from initial point straight down, then curves right
				if( (double)frameNum / (double)fpBeatInt <= 0.5 ) { // first half: straight down
					xloc = xloc; // xloc unchanged since we are moving along y only; start at 150, end at 150
					yloc = (int)( -200.0 * t + 200.0); // start at 0, end at 400** (since this is half total time of the beat, it will land halfway)
				}
				else { // second half: curve along a half-circle path
					xloc = (int)( -50.0 * Math.cos(t * Math.PI) + 200.0 ); // start at 150, go in half circle, end at 250
					yloc = (int)( -50.0 * Math.sin(t * Math.PI) + 200.0 ); // start at 200, go in half circle, end at 200
				}
				break;
			case 1: // beat 1 moves from beat 0 loc curves left, then moves up along a slightly diagonal line to the initial point
				x = 50.0 * t + 200.0;
				xloc = (int)x; // start at 250, end at 150
				yloc = (int)( -0.05 * (x-220)*(x-220) + 245 ); // start at 200, end at 0
				break;
				
		}
		
		frameNum++;
		totalframes++;
		
	}
	
	
}
