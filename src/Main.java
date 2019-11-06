/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import java.awt.EventQueue;


public class Main {

	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(() -> {
			MyWindow window = new MyWindow();
            window.setVisible(true);
        });
		
		/*
		MyWindow window = new MyWindow();
		window.setVisible(true);
		*/
			
	}
	
}
