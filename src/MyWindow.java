/* Mentored Research
 * Student: Kseniya Rychkova
 * Mentor: Dr. Salgian
 * Project: Testing out the use of JFrame and JPanel to create 2D animations with Java.
 */


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;



public class MyWindow extends JFrame implements Runnable, MouseListener {
	
	// global variables
	private final int WIDTH = 1500;
	private final int HEIGHT = 1000;
	
	private File selectedFile;
	private MyPanel panel;
	
	
	public MyWindow(List<MusicPart> partList, PieceInfo pieceInfo, File midifile) {
		
		initUI(partList, pieceInfo, midifile);
		
	}
	
	private void initUI(List<MusicPart> partList, PieceInfo pieceInfo, File midifile) {
		
		// jpanel stuff
		setSize(WIDTH, HEIGHT);
        setTitle("Conducting Animation");
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
        panel = new MyPanel(partList, pieceInfo, midifile); // create panel
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