/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class Main {

	
	public static void main(String[] args) {
		
		File inputFile; // the musicxml file
		File midiFile; // the midi file
		List<MusicPart> partList = null;
		PieceInfo pieceInfo = null;
		
		
		// ============================== get the files ==============================
		MusicxmlFileSelectWindow musicxmlSelect = new MusicxmlFileSelectWindow();
		Thread t1 = new Thread(musicxmlSelect); // create a thread for the musicxml file selection window
		t1.start(); // run the thread
		try {
			t1.join(); // main thread will wait for t1 to finish
		} catch (InterruptedException e1) {
			// do nothing
		}
		inputFile = musicxmlSelect.getSelectedFile(); // get the file
		musicxmlSelect.dispatchEvent(new WindowEvent(musicxmlSelect, WindowEvent.WINDOW_CLOSING)); // tell the jframe to close
		
		
		
		MidiFileSelectWindow midiSelect = new MidiFileSelectWindow();
		Thread t2 = new Thread(midiSelect); // create a thread for the midi file selection window
		t2.start(); // run the thread
		try {
			t2.join(); // main thread will wait for t2 to finish
		} catch (InterruptedException e1) {
			// do nothing
		}
		midiFile = midiSelect.getSelectedFile();
		midiSelect.dispatchEvent(new WindowEvent(midiSelect, WindowEvent.WINDOW_CLOSING)); // tell the jframe to close
		
		
		
		// ============================== parse the musicxml ==============================
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		
		try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        MusicXMLHandler handler = new MusicXMLHandler();
	        saxParser.parse(inputFile, handler); // this parses the file as instructed in MusicXMLHandler class
	        
	        partList = handler.getPartList(); // obtains the partList after parsing
	        pieceInfo = handler.getPieceInfo(); // obtains the PieceInfo
	        

	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	    
		// ============================== begin the program ==============================
		EventQueue.invokeLater(new MyWindow(partList, pieceInfo, midiFile));
		
			
	}
	
}
