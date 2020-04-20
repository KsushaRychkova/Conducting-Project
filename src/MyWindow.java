/* Mentored Research Fall 2019 - Spring 2020
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Conducting Animation Project
 * 		The program is able to take as input a .musicxml file and its respective .mid (midi) file, parse the musicxml, and output a 
 * 		conducting animation to play alongside the music from the midi file.
 */

import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;


public class MyWindow extends JFrame implements Runnable, MouseListener {
	
	// global variables
	private final int WIDTH = 1500;
	private final int HEIGHT = 1000;
	
	private MyPanel panel;
	
	
	public MyWindow(List<MusicPart> partList, PieceInfo pieceInfo, File midifile, int startBeat) {
		
		initUI(partList, pieceInfo, midifile, startBeat);
		
	}
	
	private void initUI(List<MusicPart> partList, PieceInfo pieceInfo, File midifile, int startBeat) {
		
		// jpanel stuff
		setSize(WIDTH, HEIGHT);
        setTitle("Conducting Animation");
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
        panel = new MyPanel(partList, pieceInfo, midifile, startBeat); // create panel
        add(panel);
        
		this.addMouseListener(this); // add mouse listener, so that when the user clicks, it pauses or unpauses the animation
        
	}
	
	
	// needed for Runnable
	public void run() {
		setVisible(true);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		panel.reversePause(); // unpause if it's paused, or pause if it's unpaused
		
	}

	
	
	// =========== we don't need these, but MouseListener requires them here anyway ===================
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}