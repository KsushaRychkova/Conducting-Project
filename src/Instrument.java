/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

public class Instrument {

	private String partName;
	private String instrumentName; // this one is optional, but if it's there, we will use it
	private String partID;
	
	
	public Instrument(String partID) {
		
		this.partID = partID;
		partName = "";
		instrumentName = "";
		
	}
	
	
	
	// set
	public void setPartName(String name) {
		partName = name;
	}
	public void setInstrumentName(String name) {
		instrumentName = name;
	}
	public void setPart(String id) {
		partID = id;
	}
	
	// get
	public String getName() {
		if(!instrumentName.equals("")) {
			return instrumentName;
		}
		return partName;
	}
	public String getPart() {
		return partID;
	}
	
}
