/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MusicXMLHandler extends DefaultHandler {
	
	private List<MusicPart> partList;
	private MusicPart part;
	private Measure measure;
	
	private StringBuilder data;
	
	// booleans used to help with parsing
	private boolean bBeats = false;
	private boolean bBeatType = false;

	
	
	public MusicXMLHandler() {
		
		partList = null;
		part = null;
		measure = null;
		data = null;
		
		
	}
	
	/*
	*********************************************************
	How this works:
	
	When the SAX parser notices the starting instance of the string listed (e.g. "part" or "beats"), it performs the tasks listed in the code.
	For elements that contain a value between the two tags (e.g. "<beats>4</beats>"), we raise a flag when we notice the starting keyword (by setting
	the respective boolean to true), and then address it in endElement().
	
	***********************************************************
	*/
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		// part
		if(qName.equalsIgnoreCase("part")) { // if we're looking at a part...
			String partID = attributes.getValue("id"); // get the part id value
			part = new MusicPart(partID);
			
			if(partList == null) partList = new ArrayList<>();
		}
		
		//measure
		if(qName.equalsIgnoreCase("measure")) {
			String measureNumS = attributes.getValue("number"); // get the number of the measure
			if(measureNumS != null) {
				int measureNum = Integer.parseInt(measureNumS); // convert string to int
				measure = new Measure(measureNum); // create the measure with that number
			}
			
			
		}
		
		// time signature (part of measure)
		else if(qName.equalsIgnoreCase("beats")) {
			bBeats = true;
		}
		else if(qName.equalsIgnoreCase("beat-type")) {
			bBeatType = true;
		}
		// tempo (part of measure)
		else if(qName.equalsIgnoreCase("sound")) {
			String tempoS = attributes.getValue("tempo"); // tempo as a string
			if(tempoS != null) {
				int tempo = Integer.parseInt(tempoS); // tempo as an int
				measure.setTempo(tempo); // set the tempo value
			}
			String dynamicsS = attributes.getValue("dynamics"); // dynamics string
			if (dynamicsS != null) {
				int dynamics = Integer.parseInt(dynamicsS);
				measure.setDynamics(dynamics); // set the measure's dynamics value
			}
		}
		
		/*
		if (qName.equalsIgnoreCase("wedge")) { // if the word in question is equal to "wedge"...
			String wedgeType = attributes.getValue("type"); // get the wedge type
			attr = new Wedge();
			emp.setId(Integer.parseInt(id));
			// initialize list
			if (empList == null)
				empList = new ArrayList<>();
		}
		else if (qName.equalsIgnoreCase("name")) {
			// set boolean values for fields, will be used in setting Employee variables
			bName = true;
		}
		else if (qName.equalsIgnoreCase("age")) {
			bAge = true;
		}
		else if (qName.equalsIgnoreCase("gender")) {
			bGender = true;
		}
		else if (qName.equalsIgnoreCase("role")) {
			bRole = true;
		}
		*/
		
		data = new StringBuilder();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		// time signature
		if(bBeats) {
			measure.setBeats(Integer.parseInt(data.toString())); // set the measure's beats value
			bBeats = false; // set back to false
		}
		if(bBeatType) {
			measure.setBeatType(Integer.parseInt(data.toString())); // set the measure's beat type value
			bBeatType = false; // set back to false
		}
		// since some measures don't have a beats or beat-type listed, those measures will not set those values and will have the default values instead (0)
		
		
		
		
		if(qName.equalsIgnoreCase("measure")) { // if we've reached the end of the measure
			part.addMeasure(measure); // add the measure to the part
		}
		if(qName.equalsIgnoreCase("part")) { // if we've reached the end of the part
			partList.add(part); // add the part to the part list
		}
		
		/*
		if (bAge) {
			// age element, set Employee age
			emp.setAge(Integer.parseInt(data.toString()));
			bAge = false;
		} else if (bName) {
			emp.setName(data.toString());
			bName = false;
		} else if (bRole) {
			emp.setRole(data.toString());
			bRole = false;
		} else if (bGender) {
			emp.setGender(data.toString());
			bGender = false;
		}
		
		if (qName.equalsIgnoreCase("Employee")) {
			// add Employee object to list
			empList.add(emp);
		}
		
		*/
		
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		data.append(new String(ch, start, length));
	}
	
	
	
	public List<MusicPart> getPartList(){
		return partList;
	}
	
}
