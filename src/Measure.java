
public class Measure {

	private final int DEFAULT_DYNAMICS = 90; // musicxml's default value for the dynamics. The dynamics listed in the musicxml are percentages
	
	private int number; // the number value from the musicxml file
	private int beats; // beats per measure
	private int beatType; // which note equals to a beat
	private int tempo; // beats per minute
	private int dynamics; // value of the dynamics
	
	public Measure(int number) {
		
		this.number = number;
		beats = 0;
		beatType = 0;
		tempo = 0;
		dynamics = 100; // 100% is the default 
		
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
	
	//get methods
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
	
}
