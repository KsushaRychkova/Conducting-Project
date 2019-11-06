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


public class MyPanel extends JPanel implements ActionListener {
	
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

	
	
	public MyPanel() {
		
		initPanel();
		
	}
	
    private void loadImages() {

        //ImageIcon ii = new ImageIcon("src/resources/star.png");
        //star = ii.getImage();
    }
    
    private void initPanel() {

    	// variables
		fps = 1000.0 / (double)DELAY;
		bpMin = 60; // TEMPORARY: THIS DATA WILL BE PULLED FROM MUSICXML FILE
		bpBar = 4; // TEMPORARY: THIS DATA WILL BE PULLED FROM MUSICXML FILE
    	
    	// background
    	setOpaque(true);
		setBackground(BG_COLOR);
		
		// images
		loadImages(); // this is only if we have images we need to load up
		
		// timer stuff
    	Timer timer = new Timer(DELAY, this);
    	timer.start();
    	
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
	public void actionPerformed(ActionEvent e) { // the action is the timer reaching each delay point

		update();
		repaint();
		
	}
	
	
}