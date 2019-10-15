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
	
	// global variables
    private final int RIGHT_HAND_DIAMETER = 50;
    //private final int INITIAL_X = 200;
    //private final int INITIAL_Y = 300;
    private final int DELAY = 40; // 40 millisecond delay = 25 fps
    private final int MOVEMENT_RADIUS = 100; // radius of the circular path we move on
    private final int MOVEMENT_CENTER_X = 300;
    private final int MOVEMENT_CENTER_Y = 300;
    
    // class variables
    private Image orchestra;
    private Thread animator; // not sure if we need this
    private int x, y; // the location of the right hand
    private RightHand rightHand;

	private double theta;
	
	
	public MyPanel() {
		
		initPanel();
		
	}
	
    private void loadImage() {

        //ImageIcon ii = new ImageIcon("src/resources/star.png");
        //star = ii.getImage();
    }
    
    private void initPanel() {

    	// background
    	setOpaque(true);
		setBackground(Color.BLACK);
		
		// variables
		//x = INITIAL_X;
		//y = INITIAL_Y;
		x = MOVEMENT_CENTER_X - MOVEMENT_RADIUS;
		y = MOVEMENT_CENTER_Y;
		theta = 0; // initialize at 0
		
		loadImage(); // this is only if we have images we need to load up
		
		Color handColor = new Color(102, 255, 255);
		rightHand = new RightHand(RIGHT_HAND_DIAMETER, handColor, x, y);
		
		// timer stuff
    	Timer timer = new Timer(15, this);
    	timer.start();
		
    	
    }
    
    // this is the method where we make changes to the variables at every frame
    private void update() {
    	
    	// moving in a circle
    	theta += 0.05;
    	x = (int) (MOVEMENT_RADIUS * Math.cos(theta) + MOVEMENT_CENTER_X);
    	y = (int) (MOVEMENT_RADIUS * Math.sin(theta) + MOVEMENT_CENTER_Y);
    	rightHand.setLoc(x, y);
    	
    }
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		setBackground(Color.BLACK);
		rightHand.draw(g);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) { // the action is the timer reaching each delay point

		update();
		repaint();
		
	}
	
	
}