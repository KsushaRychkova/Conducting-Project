
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MusicxmlFileSelectWindow extends JFrame implements Runnable, ActionListener{

	// global variables
	private final int WIDTH = 500;
	private final int HEIGHT = 400;
	
	private JLabel label; // label to display information on what the user has selected
	private JLabel extraText; // additional text to display
	private File file; // the files that are selected
	
	private boolean selected; // true if the user selected a file
	private volatile boolean confirmed; // true if the user confirmed file selection

	
	// create and handle a JFileChooser window to select the files we need for the Conducting Animation
	public MusicxmlFileSelectWindow() {
		
		// initialize some variables
		selected = false; // these will be switched to true after the user does the actions in that order
		confirmed = false;
		

		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		
		if(command.equals("Select a file...")) { // if user clicked the select button
			
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false); // only allow for one file to be selected
			chooser.setDialogTitle("Select a .musicxml file"); // title of the chooser window
			
			FileNameExtensionFilter restriction = new FileNameExtensionFilter("MusicXML Files (*.musicxml)", "musicxml"); // only .musicxml files
            chooser.setFileFilter(restriction); // restrict the files user can select
			
			int result = chooser.showSaveDialog(null);  // what did the user select?
			
			if(result == JFileChooser.APPROVE_OPTION) { // if user hit confirm...
				
				file = chooser.getSelectedFile(); // get the file
				label.setText(file.getName()); // label shows the name of the file the user just selected
				selected = true; // flag on
				
			}
			else {
				//label.setText("Cancelled.");
			}

		}
		
		else if(command.equals("Confirm selection")) { // if user clicked the confirm button
			
			if(selected) { // if the user has selected a file...
				confirmed = true; // confirm the selection!
			}
			else { // if they haven't selected a file...

			}
			
		}
		
	}
	
	public boolean getConfirmed() { // returns true only if confirmed is true (which is only true if selected is true)
		return confirmed;
	}
	public File getSelectedFile() {
		return file;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		// initialize jpanel
		setSize(WIDTH, HEIGHT);
        setTitle("Conducting Animation - Select the .musicxml file");
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // this closes just the jframe but does not end the program!!

        
        // create the two buttons
        JButton b1 = new JButton("Select a file...");
        JButton b2 = new JButton("Confirm selection");
        
        // give them an actionlistener, so that the user is able to click on them
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        // create a jpanel to display these buttons
        JPanel panel = new JPanel();
        panel.add(b1);
        panel.add(b2);
		
        label = new JLabel("no file selected"); // set the label
        panel.add(label); // add label to the panel
        extraText = new JLabel("Please select a .musicxml file, then click confirm.");
        panel.add(extraText); // add it to the panel
        
        add(panel); // add the panel to the jframe
        
        setVisible(true);
		
        while(!confirmed) {
        	// wait
        }
        
        return;
		
	}
	

}
