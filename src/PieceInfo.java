/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import java.util.ArrayList;

public class PieceInfo {
	
	/* PieceInfo includes the information about the piece, including the work title, work number, movement title, movement number,
	 * and the creators. This class selects which of this information MyPanel should display to the user.
	 */

	private String workTitle;
	private String workNumber;
	private String movementTitle;
	private String movementNumber;
	private ArrayList<String> creators;
	
	private String title; // title will have workTitle + workNumber, or movementTitle + movementNumber if there are no workTitle + workNumber
	private String subtitle; // subtitle will have either movementTitle + movementNumber, or contributors if there are no movementTitle + movementNumber
	private String contributors; // list of contributors
	
	
	public PieceInfo() {
		workTitle = null;
		workNumber = null;
		movementTitle = null;
		movementNumber = null;
		creators = new ArrayList<>();
		
		title = "Untitled Piece"; // default setting
		subtitle = null;
		contributors = null;
	}
	

	public void createTitles() { // this method sets up the title, subtitle, and contributors strings that will be displayed in MyPanel
		if(workTitle != null) {
			title = workTitle;
			if(workNumber != null) {
				title = title + "  -  " + workNumber;
			}
			if(movementTitle != null) {
				subtitle = movementTitle;
				if(movementNumber != null) {
					subtitle = subtitle + "  -  " + movementNumber;
				}
			}
			else if(movementNumber != null) {
				subtitle = movementNumber;
			}
		}
		else if(workNumber != null){
			title = workNumber;
			if(movementTitle != null) {
				subtitle = movementTitle;
				if(movementNumber != null) {
					subtitle = subtitle + "  -  " + movementNumber;
				}
			}
			else if(movementNumber != null) {
				subtitle = movementNumber;
			}
		}
		else if(movementTitle != null) {
			title = movementTitle;
			if(movementNumber != null) {
				title = title + "  -  " + movementNumber;
			}
		}
		else if(movementNumber != null){
			title = movementNumber;
		}
		
		if(creators.size() > 0) {
			for(String creator : creators) {
				if(contributors == null) { // first one
					contributors = creator;
				}
				else {
					contributors = contributors + " ,  " + creator;
				}
			}
		}
		
	}
	
	
	// set
	public void setWorkTitle(String title) {
		workTitle = title;
	}
	public void setWorkNumber(String number) {
		workNumber = number;
	}
	public void setMovementTitle(String title) {
		movementTitle = title;
	}
	public void setMovementNumber(String number) {
		movementNumber = number;
	}
	public void addCreator(String creator) {
		creators.add(creator);
	}
	
	// get
	public String getWorkTitle() {
		return workTitle;
	}
	public String getWorkNumber() {
		return workNumber;
	}
	public String getMovementTitle() {
		return movementTitle;
	}
	public String getMovementNumber() {
		return movementNumber;
	}
	public ArrayList<String> getCreators() {
		return creators;
	}
	public String getTitle() {
		return title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public String getContributors() {
		return contributors;
	}
	
	
}
