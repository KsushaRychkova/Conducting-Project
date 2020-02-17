/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class Main {

	//private static File inputFile; // static means we can access it without creating an instance of Main
	
	
	public static void main(String[] args) {
		
		File inputFile;
		List<MusicPart> partList = null;
		
		
		inputFile = new File("MozartTrio.musicxml");
		//inputFile = new File("SchbAvMaSample.musicxml");
		
		/*
		if(args.length > 0) { // if we included the filename in args
            inputFile = new File(args[0]);
		}
		else { // if we didn't include the filename in args, ask for user to enter in a filename
			System.out.println("Please enter in a file name:");
			Scanner in = new Scanner(System.in);
			inputFile = new File(in.nextLine());
		}
		*/
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		
		try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        MusicXMLHandler handler = new MusicXMLHandler();
	        saxParser.parse(inputFile, handler); // this parses the file as instructed in MusicXMLHandler class
	        
	        partList = handler.getPartList(); // obtains the partList after parsing
	        

	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	    
		
		EventQueue.invokeLater(new MyWindow(partList));
		
			
	}
	
}
