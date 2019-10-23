/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


public abstract class ConductingPattern {

	
	// class variables
	protected int bpMin; // beats per minute
	protected int bpBar; // beats per bar
	protected double beatLength; // length of one beat, in milliseconds; depends on bpMin
	protected double fps;
	protected double framesperBeat; // length of one beat in frames
	
	protected int xstart; // starting x coordinate
	protected int ystart; // starting y coordinate
	
	protected int xloc; // current x coordinate
	protected int yloc; // current y coordinate
	
	public ConductingPattern(double fps, int bpM, int xstart, int ystart) {
		bpMin = bpM;
		beatLength = 60000.0 / (double)bpMin; // 60,000 milliseconds in a minute
		this.xstart = xstart;
		this.ystart = ystart;
		xloc = xstart;
		yloc = ystart;
	}
	
	abstract void update(); // method to update the variables of the right hand

	public void setXStart(int x) {
		xstart = x;
	}
	public void setYStart(int y) {
		ystart = y;
	}
	public int getXStart() {
		return xstart;
	}
	public int getYStart() {
		return ystart;
	}
	public void setX(int x) {
		xloc = x;
	}
	public void setY(int y) {
		yloc = y;
	}
	public int getX() {
		return xloc;
	}
	public int getY() {
		return yloc;
	}
	public void setbpMin(int bpm) {
		bpMin = bpm;
		beatLength = 60000.0 / (double)bpMin;
	}
	public int getBpMin() {
		return bpMin;
	}
	
}
