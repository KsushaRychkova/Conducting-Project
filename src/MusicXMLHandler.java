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
	private List<Instrument> instrumentList;
	private PieceInfo pieceInfo;
	private Instrument instrument;
	private MusicPart part;
	private Measure measure;
	
	private StringBuilder data;
	
	// booleans used to help with parsing
	private boolean bBeats = false;
	private boolean bBeatType = false;
	private boolean bInstrumentName = false;
	private boolean bWorkTitle = false;
	private boolean bWorkNumber = false;
	private boolean bCreator = false;
	private boolean bMovementTitle = false;
	private boolean bMovementNumber = false;
	
	// other variables
	private int tempo; // save the value of the tempo to be used in future measures
	private boolean isFirstMeasure; // true only for the first measure
	private boolean implicitFlag; // flag implicit measures so we know to ignore them
	
	
	public MusicXMLHandler() {
		
		instrumentList = null;
		instrument = null;
		partList = null;
		part = null;
		measure = null;
		data = null;
		pieceInfo = new PieceInfo();
		
		tempo = 0; // default to 0 so we can identify it later
		isFirstMeasure = false;
		implicitFlag = false;
		
		
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
		
		// title elements
		if(qName.equalsIgnoreCase("work-number")) {
			bWorkNumber = true;
		}
		if(qName.equalsIgnoreCase("work-title")) {
			bWorkTitle = true;
		}
		if(qName.equalsIgnoreCase("creator")) {
			bCreator = true;
		}
		if(qName.equalsIgnoreCase("movement-title")) {
			bMovementTitle = true;
		}
		if(qName.equalsIgnoreCase("movement-number")) {
			bMovementNumber = true;
		}
		
		
		// instruments (since this comes first in the musicxml file)
		if(qName.equalsIgnoreCase("score-part")) {
			String partID = attributes.getValue("id"); // get the part id value
			instrument = new Instrument(partID); // create instrument with this part id
			
			if(instrumentList == null) instrumentList = new ArrayList<>();
		}
		if(qName.equalsIgnoreCase("instrument-name")) {
			bInstrumentName = true;
		}
		
		// part
		if(qName.equalsIgnoreCase("part")) { // if we're looking at a part...
			String partID = attributes.getValue("id"); // get the part id value
			part = new MusicPart(partID);
			
			if(partList == null) partList = new ArrayList<>();
			isFirstMeasure = true;
		}
		
		//measure
		if(qName.equalsIgnoreCase("measure")) {
			String implicitS = attributes.getValue("implicit");
			String measureNumS = attributes.getValue("number"); // get the number of the measure
			if(measureNumS != null) {
				if(implicitS != null && isFirstMeasure) { // the measure is implicit and it's the first measure of the part...
					int measureNum = 0; // set the measure number to 0
					measure = new Measure(measureNum); // create the measure with that number
				}
				else if(implicitS != null && isFirstMeasure == false) { // if the measure is implicit and it's not the first measure...
					implicitFlag = true;
				}
				else {
					int measureNum = Integer.parseInt(measureNumS); // convert string to int
					measure = new Measure(measureNum); // create the measure with that number
				}
			}
		}
		
		// time signature (part of measure)
		if(qName.equalsIgnoreCase("beats")) {
			bBeats = true;
		}
		if(qName.equalsIgnoreCase("beat-type")) {
			bBeatType = true;
		}
		// tempo (part of measure)
		if(qName.equalsIgnoreCase("sound")) {
			String tempoS = attributes.getValue("tempo"); // tempo as a string
			if(tempoS != null) {
				tempo = Integer.parseInt(tempoS); // tempo as an int
				measure.setTempo(tempo); // set the tempo value
			}
			else if(tempoS == null) {
				measure.setTempo(tempo); // if there was no tempo listed, set it to whatever our tempo was previously
			}
			String dynamicsS = attributes.getValue("dynamics"); // dynamics string
			if (dynamicsS != null) {
				int dynamics = Integer.parseInt(dynamicsS);
				measure.setDynamics(dynamics); // set the measure's dynamics value
			}
		}
		// rest measures
		if(qName.equalsIgnoreCase("rest")) {
			String restS = attributes.getValue("measure"); // rest attribute as a string
			if(restS != null && restS.equals("yes")) { // if the musicxml file says this: <rest measure="yes"/>
				measure.setRest(true);
			}
		}
		
		
		data = new StringBuilder();
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		// title elements
		if(bWorkNumber) {
			pieceInfo.setWorkNumber(data.toString());
			bWorkNumber = false;
		}
		if(bWorkTitle) {
			pieceInfo.setWorkTitle(data.toString());
			bWorkTitle = false;
		}
		if(bCreator) {
			pieceInfo.addCreator(data.toString());
			bCreator = false;
		}
		if(bMovementTitle) {
			pieceInfo.setMovementTitle(data.toString());
			bMovementTitle = false;
		}
		if(bMovementNumber) {
			pieceInfo.setMovementNumber(data.toString());
			bMovementNumber = false;
		}
		
		// instruments
		if(bInstrumentName) {
			instrument.setName(data.toString()); // get the value
			instrumentList.add(instrument); // add it to the list
			bInstrumentName = false; // set back to false
		}
		
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
			if(implicitFlag) { // if it was an implicit measure, do not add it
				implicitFlag = false;
			}
			else { // add the measure to the part only if it's not an implicit measure
				part.addMeasure(measure); 
			}
			isFirstMeasure = false; // no longer the first measure after this point
		}
		if(qName.equalsIgnoreCase("part")) { // if we've reached the end of the part
			matchInstrumentToPart(); // get the instrument name for the part
			partList.add(part); // add the part to the part list
			adjustTempos(); // adjust the tempos for the parts we have
		}
		
		
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		data.append(new String(ch, start, length));
	}
	
	private void adjustTempos() { // since the tempo isn't named at the start sometimes, we are setting it to whichever value was named later on
		for(MusicPart part : partList) { // for each part...
			for(Measure measure : part.getMeasures()) { // for each of its measures...
				if(measure.getTempo() <= 0) measure.setTempo(tempo); // if the tempo is less than 1, set it to whatever value we have saved in tempo
			}
		}
	}
	
	private void matchInstrumentToPart() { // we get the instrument names before we get the parts. We can use this method to match them up
		for(Instrument instrum : instrumentList) { // loop through the instruments
			if(instrum.getPart().equalsIgnoreCase(part.getID())) { // if they match
				part.setInstrument(instrum.getName()); // give the instrument name to the part
			}
		}
	}
	
	public List<MusicPart> getPartList(){
		return partList;
	}
	public PieceInfo getPieceInfo() {
		return pieceInfo;
	}
	
}
