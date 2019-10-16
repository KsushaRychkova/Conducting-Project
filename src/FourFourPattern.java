/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public class FourFourPattern extends ConductingPattern {

	private final int BEATS_PER_BAR = 4;
	
	private int beatNumber;
	
	
	public FourFourPattern(int bpM, int xstart, int ystart) {
		super(bpM, xstart, ystart);
		bpBar = BEATS_PER_BAR;
		beatNumber = 0; // starting beat number; MAKE SURE TO CHANGE THIS IF NEEDED
	}

	@Override
	void update() {
		
		switch(beatNumber) {
		
			case 0:
				
			case 1:
		
			case 2:
				
			case 3:
		
		}
		
	}
	
	// increase beat number, but make sure it's within the beats per bar number
	public void incBeatNum() {
		beatNumber++;
		beatNumber = beatNumber % BEATS_PER_BAR;
	}
	
	
}
