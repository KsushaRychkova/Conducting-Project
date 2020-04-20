/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

public class Measure {

	private final int DEFAULT_DYNAMICS = 72; // I'm making the default 72 because it's the average of mp (64) and mf (80)
	
	private int number; // the number value from the musicxml file
	private int beats; // beats per measure
	private int beatType; // which note equals to a beat
	private int tempo; // beats per minute
	private int dynamics; // value of the dynamics
	private boolean rest; // true if this is a rest measure, false otherwise
	
	public Measure(int number) {
		
		this.number = number;
		beats = 0;
		beatType = 0;
		tempo = 0;
		dynamics = DEFAULT_DYNAMICS;
		rest = true; // default is true
		
	}
	
	
	
	
	// set methods
	public void setBeats(int beats) {
		this.beats = beats;
	}
	public void setBeatType(int beatType) {
		this.beatType = beatType;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public void setDynamics(int dynamics) {
		this.dynamics = dynamics;
	}
	public void setRest(boolean rest) {
		this.rest = rest;
	}
	
	//get methods
	public int getNumber() {
		return number;
	}
	public int getBeats() {
		return beats;
	}
	public int getBeatType() {
		return beatType;
	}
	public int getTempo() {
		return tempo;
	}
	public int getDynamics() {
		return dynamics;
	}
	public boolean isRestMeasure() {
		return rest;
	}
	
}
