/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MusicXMLHandler extends DefaultHandler {
	
	/* This is the class that parses the musicxml file. It's instantiated inside Main, and once it's done, Main obtains the completed
	 * partList as well as the musicInfo from this class. The partList contains all the information we need about the music, and 
	 * musicInfo contains all the information we need about the title, authors, and movement of the piece.
	 */
	
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
	private boolean bPartName = false;
	private boolean bType = false;
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
	private boolean measureZero; // flag turns on if we are on measure 0
	private double countBeats; // to add up the beats in measure 0
	private int startBeat; // find this out if there is an implicit measure 0
	private int beatType; // the bottom number of the time signature
	private boolean temposAdjusted; // true if we already called adjustTempos method
	
	
	public MusicXMLHandler() {
		
		instrumentList = null;
		instrument = null;
		partList = null;
		part = null;
		measure = null;
		data = null;
		pieceInfo = new PieceInfo();
		
		tempo = 120; // default is 120, if it's not listed then it's 120
		isFirstMeasure = false;
		implicitFlag = false;
		measureZero = false;
		countBeats = 0.0;
		startBeat = 0;
		temposAdjusted = false;
		
		
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
		if(qName.equalsIgnoreCase("part-name")) {
			bPartName = true;
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
		
		// measure
		if(qName.equalsIgnoreCase("measure")) {
			String implicitS = attributes.getValue("implicit");
			String measureNumS = attributes.getValue("number"); // get the number of the measure
			if(measureNumS != null) {
				if(implicitS != null && isFirstMeasure) { // the measure is implicit and it's the first measure of the part...
					int measureNum = 0; // set the measure number to 0
					measure = new Measure(measureNum); // create the measure with that number
					measureZero = true;
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
		
		// implicit measure 0 - count beats
		if(qName.equalsIgnoreCase("type") && measureZero) {
			bType = true;
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
		
		// dynamics (represented by p, f, pp, ff, etc.)
		if(qName.equalsIgnoreCase("ppp")) {
			measure.setDynamics(16);
		}
		if(qName.equalsIgnoreCase("pp")) {
			measure.setDynamics(33);
		}
		if(qName.equalsIgnoreCase("p")) {
			measure.setDynamics(49);
		}
		if(qName.equalsIgnoreCase("mp")) {
			measure.setDynamics(64);
		}
		if(qName.equalsIgnoreCase("mf")) {
			measure.setDynamics(80);
		}
		if(qName.equalsIgnoreCase("f")) {
			measure.setDynamics(96);
		}
		if(qName.equalsIgnoreCase("ff")) {
			measure.setDynamics(112);
		}
		if(qName.equalsIgnoreCase("fff")) {
			measure.setDynamics(127);
		}
		
		// rest measures
		if(qName.equalsIgnoreCase("rest")) {
			String restS = attributes.getValue("measure"); // rest attribute as a string
			if(restS != null && restS.equals("yes")) { // if the musicxml file says this: <rest measure="yes"/>
				measure.setRest(true);
			}
		}
		if(qName.equalsIgnoreCase("pitch")) { // if the measure contains a note that has a pitch...
			measure.setRest(false); // then it is not a rest measure.
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
		if(bPartName) {
			instrument.setPartName(data.toString()); // get the value
			bPartName = false; // set back to false
		}
		if(bInstrumentName) {
			instrument.setInstrumentName(data.toString());
			bInstrumentName = false;
		}
		
		// time signature
		if(bBeats) {
			measure.setBeats(Integer.parseInt(data.toString())); // set the measure's beats value
			bBeats = false; // set back to false
		}
		if(bBeatType) {
			beatType = Integer.parseInt(data.toString()); // save this for later; we need it for the tempo!!
			measure.setBeatType(beatType); // set the measure's beat type value
			bBeatType = false; // set back to false
		}
		// since some measures don't have a beats or beat-type listed, those measures will not set those values and will have the default values instead (0)
		
		
		// implicit measure 0 - count beats
		if(bType && measureZero) {
			int beatType = measure.getBeatType(); // this is the bottom number of the time signature
			int noteType = 1;
			if(data.toString().equals("eighth")) { // if the note type is an eighth
				noteType = 8;
			}
			else if(data.toString().equals("quarter")) { // if the note type is a quarter
				noteType = 4;
			}
			else if(data.toString().equals("half")) { // if the note type is a half
				noteType = 2;
			}
			// more can be added if needed
			countBeats += (double)beatType / (double)noteType; // add to the tally of beats
			bType = false;
		}
		
		
		if(qName.equalsIgnoreCase("score-part")) {
			instrumentList.add(instrument);
		}
		
		if(qName.equalsIgnoreCase("measure")) { // if we've reached the end of the measure
			if(implicitFlag) { // if it was an implicit measure, do not add it
				implicitFlag = false;
			}
			else { // add the measure to the part only if it's not an implicit measure
				part.addMeasure(measure); 
			}
			if(measureZero) {
				startBeat = measure.getBeats() - (int)countBeats; // startBeat = number of beats in a measure - number of beats in measure 0
				measureZero = false; // set back to false if it was true
				countBeats = 0.0; // reset back for the next time we find a measure 0 in the piece
			}
			isFirstMeasure = false; // no longer the first measure after this point
			
		}
		if(qName.equalsIgnoreCase("part")) { // if we've reached the end of the part
			matchInstrumentToPart(); // get the instrument name for the part
			partList.add(part); // add the part to the part list
		}
		
		
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		data.append(new String(ch, start, length));
	}
	
	
	
	private void adjustTempos() { // since the tempo isn't named at the start sometimes, we are setting it to whichever value was named later on
		// also adjust tempos to match with the time signature!!! tempo in musicxml is quarter notes per minute, not beats per minute!!
		for(MusicPart part : partList) { // for each part...
			for(Measure measure : part.getMeasures()) { // for each of its measures...
				if(measure.getTempo() <= 0) measure.setTempo(tempo); // if the tempo is less than 1, set it to whatever value we have saved in tempo
				measure.setTempo(measure.getTempo() * beatType / 4); // fix tempo to match beat type!!
			}
		}
		temposAdjusted = true; // switch this on so that we don't do it twice!
	}
	
	private void matchInstrumentToPart() { // we get the instrument names before we get the parts. We can use this method to match them up
		for(Instrument instrum : instrumentList) { // loop through the instruments
			if(instrum.getPart().equalsIgnoreCase(part.getID())) { // if they match
				part.setInstrument(instrum.getName()); // give the instrument name to the part
			}
		}
	}

	
	// ================================= get methods =====================================
	
	public List<MusicPart> getPartList(){
		if(!temposAdjusted) {
			adjustTempos(); // adjust the tempos; this is NOT GREAT to put it here, I just need it done once, once the parser is done parsing.
		}
		return partList;
	}
	public PieceInfo getPieceInfo() {
		return pieceInfo;
	}
	public int getStartBeat() {
		return startBeat;
	}
	
}
