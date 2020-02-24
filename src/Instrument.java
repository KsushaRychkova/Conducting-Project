/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */



public class Instrument {

	private String name;
	private String partID;
	
	
	public Instrument(String partID) {
		
		this.partID = partID;
		
	}
	
	
	
	// set
	public void setName(String name) {
		this.name = name;
	}
	public void setPart(String id) {
		partID = id;
	}
	
	// get
	public String getName() {
		return name;
	}
	public String getPart() {
		return partID;
	}
	
}
