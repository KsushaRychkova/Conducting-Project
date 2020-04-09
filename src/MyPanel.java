/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.*;


public class MyPanel extends JPanel implements Runnable {
	
	// constants
    private final int DELAY = 20; // 20 millisecond delay = 50 fps
    //private final int DELAY = 10; // 10 millisecond delay = 100 fps
    private final Color BG_COLOR = Color.BLACK;
	private final Color FONT_COLOR = Color.white; // color of the font
    
    // right hand coordinates - passed to RightHand in constructor
    private final int RH_WINDOW_X0 = 1000; // x coordinate of top lefthand corner of the right hand's window
    private final int RH_WINDOW_Y0 = 275; // y coordinate of top lefthand corner of the right hand's window
    
    // class variables
    private RightHand rightHand;
    private Orchestra orchestra;
    private double fps;
    private int bpMin; // represents tempo
    private int newTempo; // a new tempo
    private int bpBar;
    private List<MusicPart> partList;
    private PieceInfo pieceInfo;
    
    private int measureNum; // which measure we are currently on
    
    private boolean isEnd; // switched on if it's the end of the music
    private volatile boolean paused; // true if the panel is paused, false if it isn't
    private boolean musicIsPlaying; // true if music is playing, false otherwise
    private Thread myThread;
    private Sequencer sequencer; // to play the midi file
    private Sequence sequence; // the info to be given to the sequencer

	
	public MyPanel(List<MusicPart> partList, PieceInfo pieceInfo, File midifile) {
		
		this.partList = partList;
		this.pieceInfo = pieceInfo;
		this.pieceInfo.createTitles(); // need to do this now that we have all the information we need
		
		initPanel();
		
		// set up midi stuff for the sound
		try {
			sequencer = MidiSystem.getSequencer(); // default sequencer
			if (sequencer == null) {
				System.err.println("Sequencer device not supported");
		        return;
		    }
			sequence = MidiSystem.getSequence(midifile); // use the midi file to get a sequence
			sequencer.setSequence(sequence); // load it to the sequencer
		} catch (MidiUnavailableException | InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    private void initPanel() {

    	// find what we need from partList
    	bpBar = partList.get(0).getMeasures().get(0).getBeats(); // partList's first part, first measure's number of beats
    	bpMin = partList.get(0).getMeasures().get(0).getTempo(); // partList's first part, first measure's tempo
    	
    	
    	// variables
		fps = 1000.0 / (double)DELAY;
		measureNum = 0;
		isEnd = false;
		paused = true; // begin paused!!
		musicIsPlaying = false;
    	
    	// background
    	setOpaque(true);
		setBackground(BG_COLOR);
		
		
    	// right hand
		rightHand = new RightHand(fps, bpMin, bpBar, RH_WINDOW_X0, RH_WINDOW_Y0, this.getBackground(), partList);
		
		
		// orchestra
		orchestra = new Orchestra(partList);
		
    	
    }
    
    // this is the method where we make changes to the variables at every frame
    private void update() {
    	
    	rightHand.update(); // right hand takes care of the updates
    	orchestra.update(measureNum);
    	measureNum = rightHand.getMeasureNum();
    	if(measureNum < partList.get(0).getMeasures().size()) {
    		newTempo = partList.get(0).getMeasures().get(measureNum).getTempo(); // get the tempo of the new measure
    	}
    	if(newTempo <= 0) newTempo = bpMin; // default is 0, if the tempo was not specified for this measure, it's the same as previous measure
    	else {
    		bpMin = newTempo; // set the new tempo
    		rightHand.setbpM(newTempo); // set the new tempo for rightHand, too. It automatically updates the rest of its variables
    	}
    	
    	checkIfEnd();
    	
    }
    
	private void checkIfEnd() {
		for(MusicPart part : partList) {
			if(measureNum < part.getMeasures().size()) { // if there is still a part that hasn't reached the end...
				isEnd = false; // set it to false
				return;
			}
		}
		isEnd = true; // if our current measure number is greater or equal to the number of measures in every part, set it to true
	}
	
	// this method is so that other classes are able to pause and unpause MyPanel
	public void reversePause() {
		paused = !paused; // pause if it's unpaused, unpause if it's paused
	}
	
	private void drawTitle(Graphics g) { // this draws the title, subtitle, and contributors strings at the top center of the panel
		
		int x = 0; // x coordinate
		
		g.setColor(FONT_COLOR);
		
		Font titleFont = new Font("Cambria", Font.PLAIN, 60); // font for the title
		FontMetrics titleMetrics = g.getFontMetrics(titleFont); // information about that font
		
		Font subtitleFont = new Font("Cambria", Font.PLAIN, 50); // font for the subtitle
		FontMetrics subtitleMetrics = g.getFontMetrics(subtitleFont); // information about that font
		
		Font contribFont = new Font("Cambria", Font.PLAIN, 36); // font for the contributors
		FontMetrics contribMetrics = g.getFontMetrics(contribFont); // information about that font
		
		String title = pieceInfo.getTitle();
		String subtitle = pieceInfo.getSubtitle();
		String contributors = pieceInfo.getContributors();
		
		g.setFont(titleFont); 
		
		x = 750 - (int)(titleMetrics.stringWidth(title)/2); // x position is such that the title is centered
		g.drawString(title, x, 60);
		
		if(subtitle != null) { // if we have a subtitle...
			g.setFont(subtitleFont);
			x = 750 - (int)(subtitleMetrics.stringWidth(subtitle)/2); // change it for the subtitle
			g.drawString(subtitle, x, 110);
		}
		
		if(contributors != null) { // if we have contributors...
			g.setFont(contribFont);
			x = 750 - (int)(contribMetrics.stringWidth(contributors)/2); // change it for the contributors
			g.drawString(contributors, x, 160);
		}
		
		
	}
	
	private void drawPlayButton(Graphics g) {
		int[] xPoly = {750,750,800};
		int[] yPoly = {400,450,425};
		g.setColor(new Color(109, 182, 254)); // a light blue
		g.fillPolygon(xPoly, yPoly, 3);
	}
	
	private void drawPauseButton(Graphics g) { // draw the pause button if the animation is paused
		g.setColor(new Color(109, 182, 254)); // a light blue
		g.fillOval(700, 450, 100, 100);
		g.setColor(Color.WHITE);
		/*
		g.fillRect(730, 480, 10, 60);
		g.fillRect(750, 480, 10, 60);
		*/
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		if(paused == false) {
			setBackground(BG_COLOR);
			rightHand.draw(g);
			orchestra.draw(g);
			drawTitle(g);
		}
		else {
			// don't redraw background
			drawTitle(g);
			drawPlayButton(g); // draw the pause button over the panel
		}

		
		
	}
	
	@Override
    public void addNotify() { // this automatically calls run()
        super.addNotify();

        myThread = new Thread(this);
        myThread.start();
    }
	
	@Override
	public void run() {
		
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		try {
			sequencer.open();
		} catch (MidiUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		while (isEnd == false) { // while it's not yet the end of the music...
			
			if(paused == false) { // if not paused...
				// do the usual stuff!
				
				musicIsPlaying = true;
				sequencer.start(); // start the music!
				
				update();
				repaint();
            
            
				timeDiff = System.currentTimeMillis() - beforeTime; // how long it took us to perform update and repaint
				sleep = DELAY - timeDiff; // how much longer we need to wait for the frame to be up

				if (sleep < 0) { // if the update and repaint took longer than the delay, do this
					sleep = 0; // we'll be losing a small part of a frame, but hopefully this won't be that big of an issue.
					System.out.println("NOTICE: this program is having trouble running. Please look into lightening the load on your processor.");
				}
                
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				beforeTime = System.currentTimeMillis(); // set the beforetime to what it is at the end of the frame, to be used in the next one
			}
			
			else { // if we are paused...
				
				// do not update!
				
				if(musicIsPlaying) { // have this so we don't have to repeat the action a million times
					sequencer.stop(); // pause the music; when we start() again, it will begin where we left off
					repaint(); // repaint only once
					musicIsPlaying = false;
				}
				
				beforeTime = System.currentTimeMillis(); // we still need to catch the beforeTime here to use in the next frame
			}
            
        }
		
		sequencer.close(); // close it when we are done
		
	}
	
	
	
	
}