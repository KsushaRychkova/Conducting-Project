
public class Measure {

	private String number; // the number value from the musicxml file
	private int beats; // beats per measure
	private int beatType; // which note equals to a beat
	private int tempo; // beats per minute
	
	public Measure(String number) {
		
		this.number = number;
		beats = 0;
		beatType = 0;
		tempo = 0;
		
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
	
}
