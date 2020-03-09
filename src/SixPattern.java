




public class SixPattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 6;
	
	
	public SixPattern(double fpB) {
		super(fpB);
		bpBar = BEATS_PER_BAR;
		beatNum = 0; // starting beat number; MAKE SURE TO CHANGE THIS IF NEEDED
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
				yloc = (int)( 0.04 * (x - 125)*(x - 125) + 275 ); // start at 300, end at 300
				break;
			case 2: // beat 2 jumps from loc1 to the right again
				x = 25.0 * t + 75.0; // x moves linearly from 100 to 50 (since t moves from 1 to -1)
				xloc = (int)x; // start at 100, end at 50
				yloc = (int)( 0.04 * (x - 75)*(x - 75) + 275 ); // start at 300, end at 300
				break;
			case 3: // beat 3 jumps from loc2 to the left side
				x = -50.0 * t + 150.0; // x moves linearly from 50 to 200 (since t moves from 1 to -1)
				xloc = (int)x; // start at 50, end at 200
				yloc = (int)(-75.0*(Math.sin(Math.pow(4.58343/150.0*x-7.11681, 2.0)/10.0) + 1.0/(10.0*(4.58343/150.0*x-7.11681))) + 300); // start at 300, end at 300
				System.out.println(yloc);
				break;
			case 4:
				
				break;
			case 5:
				
				break;
				
		}
		
		frameNum++;
		totalframes++;
		
	}
	
	
}
