/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.*;


public class MyPanel extends JPanel implements Runnable {
	
	// constants
    //private final int DELAY = 20; // 20 millisecond delay = 50 fps
    private final int DELAY = 10; // 10 millisecond delay = 100 fps
    private final Color BG_COLOR = Color.BLACK;
	private final Color FONT_COLOR = Color.white; // color of the font
    
    // right hand coordinates - passed to RightHand in constructor
    private final int RH_WINDOW_X0 = 1000; // x coordinate of top lefthand corner of the right hand's window
    private final int RH_WINDOW_Y0 = 275; // y coordinate of top lefthand corner of the right hand's window
    
    // left hand coordinates - passed to LeftHand in constructor
    private final int LH_WINDOW_X0 = 750; // x coordinate of top lefthand corner of the left hand's window
    private final int LH_WINDOW_Y0 = 500; // y coordinate of top lefthand corner of the left hand's window
    
    // class variables
    private RightHand rightHand;
    private Orchestra orchestra;
    private double fps;
    private int bpMin;
    private int bpBar;
    private int initDynamics; // initial dynamic
    private List<MusicPart> partList;
    private PieceInfo pieceInfo;
    
    private int measureNum; // which measure we are currently on
    
    private boolean isEnd; // switched on if it's the end of the music
    private Thread myThread;
    private Sequencer sequencer; // to play the midi file
    private Sequence sequence; // the info to be given to the sequencer
    
    private boolean musicRunning; // if the music is already running or not

	
	
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
//    	initDynamics = partList.get(0).getMeasures().get(0).getDynamics(); // partList's first part, first measure's dynamics
    	
    	
    	// variables
		fps = 1000.0 / (double)DELAY;
		measureNum = 0;
		isEnd = false;
		musicRunning = false;
    	
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
    	
    	checkIfEnd();
    	
    }
    
	public void checkIfEnd() {
		for(MusicPart part : partList) {
			if(measureNum < part.getMeasures().size()) { // if there is still a part that hasn't reached the end...
				isEnd = false; // set it to false
				return;
			}
		}
		isEnd = true; // if our current measure number is greater or equal to the number of measures in every part, set it to true
	}
	
	private void drawTitle(Graphics g) { // this draws the title, subtitle, and contributors strings at the top center of the panel
		
		int x = 0; // x coordinate
		
		g.setColor(FONT_COLOR);
		
		Font titleFont = new Font("Goudy Old Style", Font.PLAIN, 60); // font for the title
		FontMetrics titleMetrics = g.getFontMetrics(titleFont); // information about that font
		
		Font subtitleFont = new Font("Goudy Old Style", Font.PLAIN, 50); // font for the subtitle
		FontMetrics subtitleMetrics = g.getFontMetrics(subtitleFont); // information about that font
		
		Font contribFont = new Font("Goudy Old Style", Font.PLAIN, 36); // font for the contributors
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
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		setBackground(BG_COLOR);
		rightHand.draw(g);
		orchestra.draw(g);
		drawTitle(g);
		
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
			
            update();
            repaint();
            
            if(musicRunning == false) {
            	sequencer.start(); // start the music!
            	musicRunning = true;
            }
            
            timeDiff = System.currentTimeMillis() - beforeTime; // how long it took us to perform update and repaint
            sleep = DELAY - timeDiff; // how much longer we need to wait for the frame to be up

            if (sleep < 0) { // if the update and repaint took longer than the delay, do this
                // don't do anything for now
            }
                
            try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            beforeTime = System.currentTimeMillis(); // set the beforetime to what it is at the end of the frame, to be used in the next one
        }
		
		sequencer.close(); // close it when we are done
		
	}
	
	
	
	
}