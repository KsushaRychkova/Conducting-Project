/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MyPanel extends JPanel implements Runnable {
	
	// constants
    //private final int DELAY = 20; // 20 millisecond delay = 50 fps
    private final int DELAY = 10; // 10 millisecond delay = 100 fps
    private final Color BG_COLOR = Color.BLACK;
    
    // right hand coordinates - passed to RightHand in constructor
    private final int RH_WINDOW_X0 = 1000; // x coordinate of top lefthand corner of the right hand's window
    private final int RH_WINDOW_Y0 = 275; // y coordinate of top lefthand corner of the right hand's window
    
    // class variables
    private Image orchestra;
    private RightHand rightHand;
    private double fps;
    private int bpMin;
    private int bpBar;
    private int initDynamics; // initial dynamic
    private List<MusicPart> partList;
    
    private int measureNum; // which measure we are currently on
    
    private boolean isEnd; // switched on if it's the end of the music
    private Thread myThread;

	
	
	public MyPanel(List<MusicPart> partList) {
		
		this.partList = partList;
		initPanel();
		
	}
	
    private void loadImages() {

        //ImageIcon ii = new ImageIcon("src/resources/star.png");
        //star = ii.getImage();
    }
    
    private void initPanel() {

    	// find what we need from partList
    	bpBar = partList.get(0).getMeasures().get(0).getBeats(); // partList's first part, first measure's number of beats
    	bpMin = partList.get(0).getMeasures().get(0).getTempo(); // partList's first part, first measure's tempo
    	initDynamics = partList.get(0).getMeasures().get(0).getDynamics(); // partList's first part, first measure's dynamics
    	
    	
    	// variables
		fps = 1000.0 / (double)DELAY;
		measureNum = 0;
		isEnd = false;
    	
    	// background
    	setOpaque(true);
		setBackground(BG_COLOR);
		
		// images
		loadImages(); // this is only if we have images we need to load up
		
    	// right hand
    	//Color rightHandColor = new Color(102, 255, 255);
		rightHand = new RightHand(fps, bpMin, bpBar, initDynamics, RH_WINDOW_X0, RH_WINDOW_Y0, this.getBackground(), partList);
    	
    }
    
    // this is the method where we make changes to the variables at every frame
    private void update() {
    	
    	rightHand.update(); // right hand takes care of the updates
    	measureNum = rightHand.getMeasureNum();
//    	System.out.println(bpMin + " / " + bpBar);
		System.out.println("Measure #: " + measureNum + "   Total Measures: " + partList.get(0).getMeasures().size());
    	
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
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		setBackground(BG_COLOR);
		rightHand.draw(g);
		
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
		
		while (isEnd == false) { // while it's not yet the end of the music...

            update();
            repaint();
            
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
		
	}
	
	
	
	
}