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
    private final int DELAY = 20; // 20 millisecond delay = 50 fps
    private final Color BG_COLOR = Color.BLACK;
    
    // right hand coordinates - passed to RightHand in constructor
    private final int RH_WINDOW_X0 = 200; // x coordinate of top lefthand corner of the right hand's window
    private final int RH_WINDOW_Y0 = 100; // y coordinate of top lefthand corner of the right hand's window
    //private final int RH_WINDOW_SCALE = 2; // right hand window is scaled by this value
    
    // class variables
    private Image orchestra;
    private RightHand rightHand;
    private double fps;
    private int bpMin;
    private int bpBar;
    private Thread myThread;

	
	
	public MyPanel(List<MusicPart> partList) {
		
		initPanel(partList);
		
	}
	
    private void loadImages() {

        //ImageIcon ii = new ImageIcon("src/resources/star.png");
        //star = ii.getImage();
    }
    
    private void initPanel(List<MusicPart> partList) {

    	// find what we need from partList
    	bpBar = partList.get(0).getMeasures().get(0).getBeats(); // partList's first part, first measure's number of beats
    	bpMin = partList.get(0).getMeasures().get(0).getTempo(); // partList's first part, first measure's tempo
    	
    	
    	// variables
		fps = 1000.0 / (double)DELAY;
		//bpMin = 60; // TEMPORARY: THIS DATA WILL BE PULLED FROM MUSICXML FILE
		//bpBar = 2; // TEMPORARY: THIS DATA WILL BE PULLED FROM MUSICXML FILE
    	
    	// background
    	setOpaque(true);
		setBackground(BG_COLOR);
		
		// images
		loadImages(); // this is only if we have images we need to load up
		
    	// right hand
    	Color rightHandColor = new Color(102, 255, 255);
		rightHand = new RightHand(fps, bpMin, bpBar, rightHandColor, RH_WINDOW_X0, RH_WINDOW_Y0, this.getBackground());
    	
    }
    
    // this is the method where we make changes to the variables at every frame
    private void update() {
    	
    	rightHand.update(); // right hand takes care of the updates
    	
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
		
		while (true) {

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