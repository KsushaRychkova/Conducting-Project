/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public class CirclePattern extends RightHandPattern {

	private final int BEATS_PER_BAR = 1;
	private double theta;
	
	
	public CirclePattern(int fpB) {
		super(fpB);
		bpBar = BEATS_PER_BAR;
		beatNum = 0; // starting beat number; MAKE SURE TO CHANGE THIS IF NEEDED
		theta = 0.0;
	}

	@Override
	void update() {
		

		
    	theta += 0.05;
    	xloc = (int) (150.0 * Math.cos(theta) + 150.0);
    	yloc = (int) (150.0 * Math.sin(theta) + 150.0);
		
		

		
	}
	
	
}
