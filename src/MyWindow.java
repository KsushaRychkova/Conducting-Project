/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;



public class MyWindow extends JFrame {
	
	// global variables
	private final int WIDTH = 900;
	private final int HEIGHT = 600;
	
	public MyWindow() {
		
		initUI();
		
	}
	
	private void initUI() {
		
        MyPanel panel = new MyPanel();
        add(panel);

        setSize(WIDTH, HEIGHT);
        setTitle("Conducting Animation");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	
	
}