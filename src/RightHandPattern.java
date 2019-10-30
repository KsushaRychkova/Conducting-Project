/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public abstract class RightHandPattern {
	
	protected final int INITIAL_X = 50; // initial (relative) coordinates we need to get back to
	protected final int INITIAL_Y = 0;
	
	protected int fpBeat; // frames per beat
	protected int xloc;
	protected int yloc;
	protected int beatNum; // the beat we are on of the bar (reset to 0 every time we reach the end of the cycle)
	protected int frameNum; // the frame we are on of the beat (reset to 0 every time we go to next beat)
	
	protected int bpBar; // this one changes based on each pattern

	public RightHandPattern(int fpB) {
		fpBeat = fpB;
		
		xloc = INITIAL_X;
		yloc = INITIAL_Y;
		beatNum = 0;
		frameNum = 0;
	}
	
	abstract void update(); // OVERRIDE IN EVERY PATTERN SUBCLASS
	
	// increase beat number, but make sure it's within the beats per bar number
	protected void incBeatNum() {
		beatNum++;
		beatNum = beatNum % bpBar;
		
		frameNum = 0; // reset frame number back to 0
	}
	
	
	
	public int getX() {
		return xloc;
	}
	public int getY() {
		return yloc;
	}
	
}
