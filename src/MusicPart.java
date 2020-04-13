/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.util.ArrayList;
import java.util.List;

public class MusicPart {

	private String id;
	private String instrument_name;
	
	private List<Measure> measureList; // list of measures in this part
	
	public MusicPart(String id) {
		
		this.id = id;
		measureList = new ArrayList<>();
		
	}
	
	
	
	
	// set methods
	public void addMeasure(Measure m) {
		measureList.add(m);
	}
	public void setInstrument(String s) {
		instrument_name = s;
	}
	
	// get methods
	public List<Measure> getMeasures(){
		return measureList;
	}
	public String getInstrumentName() {
		return instrument_name;
	}
	public String getID() {
		return id;
	}
	
}
