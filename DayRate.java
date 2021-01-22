package project;

//importing packages
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.io.FileWriter;   // Import the FileWriter class
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DayRate extends JPanel {

	public DayRate() {
		
		//layout
		this.setLayout(null);
	    
		//Label - What was something good that happened to you today?
		JLabel label1 = new JLabel("What was something good that happened to you today?");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label1.setBounds(50, 40, 335, 30);
		this.add(label1); 

		//Label - What was something bad that happened to you today?
		JLabel label2 = new JLabel("What was something bad that happened to you today?");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label2.setBounds(50, 150, 335, 30);
		this.add(label2);
				
		//Label - Rate your day out of 10
		JLabel label3 = new JLabel("Rate your day out of 10");
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		label3.setBounds(50, 280, 150, 30);
		this.add(label3);
				
		//Text Field - What was something good that happened to you today? - Answer
		JTextField goodThingText = new JTextField();
		goodThingText.setBounds(50, 70, 335, 60);
		this.add(goodThingText);
		goodThingText.setColumns(10);
				
		//Text Field - What was something bad that happened to you today? - Answer
		JTextField badThingText = new JTextField();
		badThingText.setColumns(10);
		badThingText.setBounds(50, 200, 335, 60);
		this.add(badThingText);
		
		//Text Field - Rate your day out of 10 - Answer
		JTextField ratingText = new JTextField();
		ratingText.setBounds(50, 320, 150, 50);
		this.add(ratingText);
		ratingText.setColumns(10);
		
		//Text Area - Display statistics and history
		JTextArea displayText = new JTextArea();
		displayText.setBounds(450, 100, 350, 350);
		this.add(displayText);
		displayText.setColumns(10);	
		
		File statsFile = new File("statistics.txt");//statistics file
	    JButton statsButton = new JButton("Show statistics");
		statsButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		//action if "Show statistics" button clicked
		statsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//creating variables
				final int SIZE = 1000; //constant - size
				double average=0,rate[],max=0,min=10;//average rating [average], rating by days [rate], max rating [max], min rating [min]
				rate = new double[SIZE]; //allocate memory for rate
				int maxIndex = 0,minIndex = 0,i=0;//index for max rating [maxIndex], index for min rating [minIndex], current index [i]
				String good[],bad[],date[];//goodThing [good], badThing[bad], uglyThing[date]
				good = new String[SIZE];//allocate memory for the good
				bad = new String[SIZE];//allocate memory for the bad
				date = new String[SIZE];//allocate memory for the ugly
				
				String text = "";//text to display in text area
				
				//read statistics
				try {
					Scanner myReader = new Scanner(statsFile);//myReader reads "statistics.txt"
					while (myReader.hasNextLine()) {//while there is still to read in myReader (has next line)
						
						//get info from file
						rate[i]=Double.parseDouble(myReader.nextLine());
						average+=rate[i];
						date[i]=myReader.nextLine() + "." + myReader.nextLine() + "." + myReader.nextLine(); 
						good[i]=myReader.nextLine();
						bad[i]=myReader.nextLine();
						
						if(rate[i]<min) {//search for lowest-rated day
							min=rate[i];
							minIndex=i;
						}
						if(rate[i]>max) {//search for highest-rated day
							max=rate[i];
							maxIndex=i;
						}
						i++;//count number of days

					}
					
					//set text to print
					text = "Your average happines is : " + average/i + "\n"
					+ "------------------------------------------------------------------------------------" + "\n"
					+ "Your happiest day was on the " + date[maxIndex] + "\n"
					+ "You rated it " + rate[maxIndex] + "\n"
					+ "This was the best thing that happened : " + good[maxIndex] + "\n"
					+ "And this was the worst thing that happened : " + bad[maxIndex] + "\n"
					+ "------------------------------------------------------------------------------------" + "\n"
					+ "Your unhappiest day was on the " + date[minIndex] + "\n"
					+ "You rated it " + rate[minIndex] + "\n"
					+ "This was the best thing that happened : " + good[minIndex] + "\n"
					+ "And this was the worst thing that happened: " + bad[minIndex] + "\n"
					+ "------------------------------------------------------------------------------------" + "\n";
					displayText.setText(text); //display Text (text area named "displayText")
					myReader.close();//close myReader
				} catch (FileNotFoundException e) {//file not found -> create file
					try {
						statsFile.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		statsButton.setBounds(450, 30, 150, 50);
		this.add(statsButton); 
		
		//Show the history from each day
		File historyFile = new File("history.txt");//history file
		JButton historyButton = new JButton("Show history");
		historyButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		//action if "Show history" button clicked
		historyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String text = "";//text to display in displayText
		
				//printing "history.txt" file in displayText
				try {
					
					//text reads whole "history.txt"
					Scanner sc = new Scanner(historyFile);
					while (sc.hasNextLine()) {
						text += sc.nextLine() + "\n";
					}
					sc.close();//close scanner
				} catch (FileNotFoundException e) {//file not found -> create file
					try {
						historyFile.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				displayText.setText(text);
			}
		});
		historyButton.setBounds(650, 30, 150, 50);
		this.add(historyButton);
		
		//Submit your daily answers
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		//action if "Submit" button click
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//get text from text fields
				String goodThing = goodThingText.getText();
				String badThing = badThingText.getText();
				double rating = Double.parseDouble(ratingText.getText());
				
				//get the current date
				Date date = new Date();
				String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				LocalDate currentDate = LocalDate.parse(modifiedDate);
				int day = currentDate.getDayOfMonth();//get days
				Month monthText = currentDate.getMonth();//get months in words - January, February, March.....
				int month = date.getMonth() + 1;//get months in numbers - 1,2,3....
				int year = currentDate.getYear(); //get year
				
				//text to write to "history.txt"
				String paste = day + " "
				+ monthText + " "
				+ year + "\n"
				+ goodThing + "\n"
				+ badThing + "\n"
				+ rating + "\n";
				
				//text to write to "statistics.txt"
				String rate = rating + "\n"
				+ day + "\n"
				+ month + "\n"
				+ year + "\n"
				+ goodThing + "\n"
				+ badThing + "\n";
				
				try {
					//write to "history.txt"
					FileWriter fw = new FileWriter(historyFile,true);
					fw.write(paste);
				    fw.close();
				    
				    //write to "statistics.txt"
				    FileWriter fw2 = new FileWriter(statsFile,true);
				    fw2.write(rate);
				    fw2.close();
				    
				    //System.out.println("Successfully wrote to the file.");
				    
				    //let user know he submitted
				    displayText.setText("Successfully submitted your daily moments!");//display message in displayText
				} catch (IOException e) {//an error occured
				    System.out.println("An error occurred.");
				    e.printStackTrace();
				}
			}
		});
		submitButton.setBounds(50, 400, 150, 50);
		this.add(submitButton);	

	}
	
	//Create and show GUI
	private static void createAndShowGUI() {
		JFrame f=new JFrame("Day Rate");//creating a frame 
		
		f.setSize(850, 550);  
        f.setLayout(null);  
        f.setVisible(true);
        
        //Create and set up the content pane.
        DayRate newContentPane = new DayRate();
        newContentPane.setOpaque(true); //content panes must be opaque
        f.setContentPane(newContentPane);
        
	}
	
	public static void main(String[] args) {
		
	    //Create and show this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});		
	}

}