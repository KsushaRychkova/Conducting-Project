/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import javax.swing.JFrame;
import java.util.List;



public class MyWindow extends JFrame implements Runnable {
	
	// global variables
	private final int WIDTH = 900;
	private final int HEIGHT = 600;
	
	public MyWindow(List<MusicPart> partList) {
		
		initUI(partList);
		
	}
	
	private void initUI(List<MusicPart> partList) {
		
        MyPanel panel = new MyPanel(partList);
        add(panel);

        setSize(WIDTH, HEIGHT);
        setTitle("Conducting Animation");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void run() {
		setVisible(true);
	}

	
	
}