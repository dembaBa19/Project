package project;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayRate extends JPanel {

	public DayRate() {
		
		JFrame f=new JFrame("Day Rate"); 
	    f.setSize(850, 550);  
	    f.setLayout(null);  
	    f.setVisible(true);  
	    
	    //Show the statistics (average day rating for week, month, year ect.
	    JButton statsButton = new JButton("Show statistics");
		statsButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		statsButton.setBounds(450, 30, 150, 50);
		f.add(statsButton); 
		
		//Show the history from each day
		JButton historyButton = new JButton("Show history");
		historyButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		historyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		historyButton.setBounds(650, 30, 150, 50);
		f.add(historyButton);
		
		//Submit your daily answers
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		submitButton.setBounds(50, 400, 150, 50);
		f.add(submitButton);
		
		//What was something good that happened to you today?
		JLabel label1 = new JLabel("What was something good that happened to you today?");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label1.setBounds(50, 40, 335, 30);
		f.add(label1); 

		//What was something bad that happened to you today?
		JLabel label2 = new JLabel("What was something bad that happened to you today?");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label2.setBounds(50, 150, 335, 30);
		f.add(label2);
		
		//Rate your day out of 10
		JLabel label3 = new JLabel("Rate your day out of 10");
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label3.setBounds(50, 280, 150, 30);
		f.add(label3);
		
		//Text Field - What was something good that happened to you today? - Answer
		JTextField goodThingText = new JTextField();
		goodThingText.setBounds(50, 70, 335, 60);
		f.add(goodThingText);
		goodThingText.setColumns(10);
		
		//Text Field - What was something bad that happened to you today? - Answer
		JTextField badThingText = new JTextField();
		badThingText.setColumns(10);
		badThingText.setBounds(50, 200, 335, 60);
		f.add(badThingText);
		
		//Text Field - Rate your day out of 10 - Answer
		JTextField ratingText = new JTextField();
		ratingText.setBounds(50, 320, 150, 50);
		f.add(ratingText);
		ratingText.setColumns(10);
		
		//Text Field - Display statistics and history
		JTextField displayText = new JTextField();
		displayText.setBounds(450, 100, 350, 350);
		f.add(displayText);
		displayText.setColumns(10);

	}
	
	private static void createAndShowGUI() {
	    //Create and set up the window.
	    JFrame frame = new JFrame("BoxAlignmentDemo");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //Create and set up the content pane.
		DayRate newContentPane = new DayRate();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);
	    //Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
	       //Schedule a job for the event-dispatching thread:
	       //creating and showing this application's GUI.
	       javax.swing.SwingUtilities.invokeLater(new Runnable() {
	           public void run() {
	               createAndShowGUI();
	           }
	       });
	   }

}
