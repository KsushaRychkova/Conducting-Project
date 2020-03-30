/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
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
