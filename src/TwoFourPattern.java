/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public class TwoFourPattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 2;
	
	
	public TwoFourPattern(int fpB) {
		super(fpB);
		bpBar = BEATS_PER_BAR;
		beatNum = 0; // starting beat number; MAKE SURE TO CHANGE THIS IF NEEDED
	}

	@Override
	void update() {
		
		if(frameNum >= fpBeat) {
			incBeatNum();
		}
		
		// t is a variable to make it easier to follow the math. It also represents the speed at which the xlocs and ylocs change.
		double t = Math.cos( Math.PI * (double)frameNum / (double)fpBeat);
		
		// saving xloc as a double so we can use it to calculate yloc
		double x;
		
		switch(beatNum) {
		
			case 0: // beat 0 moves from initial point straight down, then curves right
				if( (double)frameNum / (double)fpBeat <= 0.5 ) { // first half: straight down
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
		
	}
	
	
}
