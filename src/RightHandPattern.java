/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public abstract class RightHandPattern {
	
	protected final int INITIAL_X = 150; // initial (relative) coordinates we need to get back to
	protected final int INITIAL_Y = 0;
	
	protected double fpBeat; // frames per beat
	protected int xloc;
	protected int yloc;
	protected int beatNum; // the beat we are on of the bar (reset to 0 every time we reach the end of the cycle)
	protected int frameNum; // the frame we are on of the beat (reset to 0 every time we go to next beat)
	protected int measureNum; // the measure number that we are on.
	
	protected double beatframecount; // count the frames we've had to ensure we didn't lose any if fpBeat is a decimal
	protected long totalframes; // total number of frames we have actually drawn
	protected int fpBeatInt; // how many frames per beat we actually have to display
	
	protected int bpBar; // this one changes based on each pattern

	
	public RightHandPattern(double fpB) {
		fpBeat = fpB;
		fpBeatInt = round(fpBeat);
		xloc = INITIAL_X;
		yloc = INITIAL_Y;
		beatNum = 0;
		frameNum = 0;
		measureNum = 0;
		beatframecount = fpBeat; // start at fpBeat so we know how many we need to do next
		totalframes = 0;
	}
	
	abstract void update(); // OVERRIDE IN EVERY PATTERN SUBCLASS
	
	// increase beat number, but make sure it's within the beats per bar number
	protected void incBeatNum() {
		beatNum++;
		if(beatNum == bpBar) {
			beatNum = 0;
			measureNum++; 
		}
		beatframecount += fpBeat; // how many frames we should have had so far
		fpBeatInt = round(beatframecount) - (int)totalframes; // how many frames we need to do this beat is based off how many we have already done.
		
		frameNum = 0; // reset frame number back to 0
	}
	
	// rounds the double n by adding 0.5 and then chopping off the decimal and returning as an int
	protected int round(double n) {
		n += 0.5;
		return (int)n; // chops off the decimal, similar to flooring
	}
	
	
	public int getX() {
		return xloc;
	}
	public int getY() {
		return yloc;
	}
	public int getMeasureNum() {
		return measureNum;
	}
	
}
