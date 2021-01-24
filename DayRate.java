package project;

//importing packages
import javax.swing.*;
import javax.swing.border.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.io.FileWriter;   // Import the FileWriter class
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DayRate extends JPanel {
	
	//method to check if string is a number
	public static boolean isNumeric(String str) {
	    for (char c : str.toCharArray()) {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public static String previousDate(String date) {
		int day = Integer.parseInt(date.substring(0,2));//get days
		int month = Integer.parseInt(date.substring(3,5));//get months
		int year = Integer.parseInt(date.substring(6,10)); //get year
		
		if (day>1) {
			day--;
		} else {
			month--;
			if(month==1 || month==3 || month==5 || month==6 || month==8 || month==10 || month==0) {
				day=31;
			} else {
				if(month==4 || month==7 || month==9 || month==11) {
					day=31;
				} else {
					if((year%4==0 && year%100!=0) || year%400==0) {
						day=29;
					} else {
						day=28;
					}
				}
			}
			if(month==0) {
				month=12;
				year--;
			}
		}

		String dayStr = "", monthStr = "";
		dayStr+=day;
		monthStr+=month;
		if(day<10) {
			dayStr = "0" + day;
		}
		if(month<10) {
			monthStr = "0" + month;
		}
		return dayStr + "." + monthStr + "." + year;
	}
	
	public static boolean isFromTheSameYear(String date, String year) {
		return date.substring(6,10).equals(year);
	}
	
	public DayRate() {
		
		//layout
		this.setLayout(null);
		
		this.setBackground(Color.white);
		
		
		//Label - What was something good that happened to you today?
		JLabel label1 = new JLabel("What was something good that happened to you today?");
		label1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		label1.setBounds(30, 40, 370, 30);
		this.add(label1); 

		//Label - What was something bad that happened to you today?
		JLabel label2 = new JLabel("What was something bad that happened to you today?");
		label2.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		label2.setBounds(30, 160, 370, 30);
		this.add(label2);
				
		//Label - Rate your day out of 10
		JLabel label3 = new JLabel("Rate your day out of 10");
		label3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		label3.setBounds(30, 280, 150, 30);
		this.add(label3);
		
		//Label - Select date
		
		JLabel calLabel = new JLabel("<html>What date do you want to submit for?<br>Default = Current Date</html>");
		
		calLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		calLabel.setBounds(200, 280, 220, 70);
		this.add(calLabel);
				
		//Text Field - What was something good that happened to you today? - Answer
		Font font = new Font("SansSerif", Font.BOLD, 20);
		
		JTextField goodThingText = new JTextField();
		goodThingText.setBounds(30, 70, 370, 60);
		this.add(goodThingText);
		goodThingText.setColumns(10);
		goodThingText.setBackground(Color.LIGHT_GRAY);
		goodThingText.setFont(font);
		
		//Text Field - What was something bad that happened to you today? - Answer
		JTextField badThingText = new JTextField();
		badThingText.setColumns(10);
		badThingText.setBounds(30, 190, 370, 60);
		this.add(badThingText);
		badThingText.setBackground(Color.LIGHT_GRAY);
		badThingText.setFont(font);
		
		//Text Field - Rate your day out of 10 - Answer
		JTextField ratingText = new JTextField();
		ratingText.setBounds(30, 320, 150, 50);
		this.add(ratingText);
		ratingText.setColumns(10);
		ratingText.setBackground(Color.LIGHT_GRAY);
		ratingText.setFont(font);
	    
		//CALENDAR
	    JDatePickerImpl datePicker;
	    
	    SqlDateModel model = new SqlDateModel();
	    Properties p = new Properties();
	    model.setSelected(true);
	    p.put("text.day", "Day");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl panel = new JDatePanelImpl(model,p);
	    datePicker = new JDatePickerImpl(panel, new AbstractFormatter() {
			
			@Override
			public String valueToString(Object value) throws ParseException {
				// TODO Auto-generated method stub
				if(value!=null) {
					Calendar cal = (Calendar) value;
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					String strDate = format.format(cal.getTime());
					
					return strDate;
				} else {
					return "";
				}
			}
			
			@Override	
			public Object stringToValue(String text) throws ParseException {
				// TODO Auto-generated method stub
				return "";
			}
		});
	    //panel.setBounds(0,0,100,100);
	    datePicker.setBounds(200,350,200,26);
	    this.add(datePicker);
	    this.setVisible(true);
	    
		//Text Area - Display statistics and history
		JTextArea displayText = new JTextArea();
		JScrollPane sp = new JScrollPane(displayText,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setBounds(450, 100, 350, 350);
		//displayText.setEditable(false);
		sp.setVisible(true);
		this.add(displayText);
		this.add(sp);
		displayText.setBackground(Color.orange);;
		displayText.setColumns(10);	
		
		//Show the history from each day
		File historyFile = new File("history.txt");//history file
		
	    JButton statsButton = new JButton("Show statistics");
		statsButton.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		
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
				int days[],months[],years[];
				days = new int[SIZE];
				months = new int[SIZE];
				years = new int [SIZE];
				
				String text = "";//text to display in text area
				
				
				//read statistics
				try {
					Scanner myReader = new Scanner(historyFile);//myReader reads "statistics.txt"
					Date dateDate = new Date();
					
					
					
					String date2 =  new SimpleDateFormat("dd.MM.yyyy").format(dateDate);
					date2 = date2.substring(0,2) + "." + date2.substring(3,5) + "." + date2.substring(6,10);
					
					String dates[];
					dates = new String[365];
					
					double weeklyAverage=0;
					int weeklyBestIndex=0,weeklyWorstIndex=0;
					double weeklyBestRating=0,weeklyWorstRating=0;
					
					double monthlyAverage=0;
					int monthlyBestIndex=0, monthlyWorstIndex=0;
					double monthlyBestRating=0, monthlyWorstRating=0;
					
					double yearlyAverage=0;
					int yearlyBestIndex=0,yearlyWorstIndex=0;
					double yearlyBestRating=0, yearlyWorstRating=0;
					
					int j=1;
					dates[0]=date2;
					while (j<31) {
						dates[j]=previousDate(dates[j-1]);
						
						j++;
					}
					int howManyWeek=0;
					int howManyMonth=0;
					int howManyYear=0;
					
					while (myReader.hasNextLine()) {//while there is still to read in myReader (has next line)

						//get info from file
						date[i]=myReader.nextLine();
						good[i]=myReader.nextLine();
						bad[i]=myReader.nextLine();
						rate[i]=Double.parseDouble(myReader.nextLine());
						average+=rate[i];
						
						if(rate[i]<min) {//search for lowest-rated day
							min=rate[i];
							minIndex=i;
						}
						if(rate[i]>max) {//search for highest-rated day
							max=rate[i];
							maxIndex=i;
						}
						
						for(int m=0; m<7; m++) {
							if(date[i].equals(dates[m])) {
								if(howManyWeek==0) {
									weeklyBestIndex=i;
									weeklyWorstIndex=i;
									weeklyBestRating=rate[i];
									weeklyWorstRating=rate[i];
								} else {
									if(rate[i]>weeklyBestRating) {
										weeklyBestRating=rate[i];
										weeklyBestIndex=i;
									}
									if(rate[i]<weeklyWorstRating) {
										weeklyWorstRating=rate[i];
										weeklyWorstIndex=i;
									}
								}
								weeklyAverage+=rate[i];
								howManyWeek++;
								break;
							}
						}
						
						for(int m=0; m<31; m++) {
							if(date[i].equals(dates[m])) {
								if(howManyMonth==0) {
									monthlyBestIndex=i;
									monthlyWorstIndex=i;
									monthlyBestRating=rate[i];
									monthlyWorstRating=rate[i];
								} else {
									if(rate[i]>monthlyBestRating) {
										monthlyBestRating=rate[i];
										monthlyBestIndex=i;
									}
									if(rate[i]<monthlyWorstRating) {
										monthlyWorstRating=rate[i];
										monthlyWorstIndex=i;
									}
								}
								monthlyAverage+=rate[i];
								howManyMonth++;
								break;
							}
						}
						
						for(int m=0; m<365; m++) {
							if(date[i].equals(dates[m])) {
								if(howManyYear==0) {
									yearlyBestIndex=i;
									yearlyWorstIndex=i;
									yearlyBestRating=rate[i];
									yearlyWorstRating=rate[i];
								} else {
									if(rate[i]>yearlyBestRating) {
										yearlyBestRating=rate[i];
										yearlyBestIndex=i;
									}
									if(rate[i]<yearlyWorstRating) {
										yearlyWorstRating=rate[i];
										yearlyWorstIndex=i;
									}
								}
								yearlyAverage+=rate[i];
								howManyYear++;
								break;
							}
						}
						i++;
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
					+ "This was the best thing that happened : " + good[minIndex] + ".\n"
					+ "And this was the worst thing that happened: " + bad[minIndex] + "\n"
					+ "------------------------------------------------------------------------------------" + "\n"
					+ "Your average last week was " + weeklyAverage/howManyWeek + "\n"
					+ "This was the best thing that happened : \n" + good[weeklyBestIndex] + "\nDay was rated " + rate[weeklyBestIndex] + " "
					+ "on the " + date[weeklyBestIndex] + "\n"
					+ "And this was the worst thing that happened: \n" + bad[weeklyWorstIndex] + "\nDay was rated " + rate[weeklyWorstIndex] + " "
					+ "on the " + date[weeklyWorstIndex] + "\n"
					+ "------------------------------------------------------------------------------------" + "\n"
					+ "Your average last month was " + monthlyAverage/howManyMonth + "\n"
					+ "This was the best thing that happened : \n" + good[monthlyBestIndex] + "\nDay was rated " + rate[monthlyBestIndex] + " "
					+ "on the " + date[monthlyBestIndex] + "\n"
					+ "And this was the worst thing that happened: \n" + bad[monthlyWorstIndex] + "\nDay was rated " + rate[monthlyWorstIndex] + " "
					+ "on the " + date[monthlyWorstIndex] + "\n"
					+ "------------------------------------------------------------------------------------" + "\n"
					+ "Your average last year was " + yearlyAverage/howManyMonth + "\n"
					+ "This was the best thing that happened : \n" + good[yearlyBestIndex] + "\nDay was rated " + rate[yearlyBestIndex] + " "
					+ "on the " + date[yearlyBestIndex] + "\n"
					+ "And this was the worst thing that happened: \n" + bad[yearlyWorstIndex] + "\nDay was rated " + rate[yearlyWorstIndex] + " "
					+ "on the " + date[yearlyWorstIndex] + "\n"
					+ "------------------------------------------------------------------------------------" + "\n";
					
					displayText.setText(text); //display Text (text area named "displayText")
					myReader.close();//close myReader
				} catch (FileNotFoundException e) {//file not found -> create file
					try {
						historyFile.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		statsButton.setBounds(450, 30, 150, 50);
		statsButton.setBackground(Color.GRAY);
		this.add(statsButton); 
		
		JButton historyButton = new JButton("Show history");
		historyButton.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
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
		historyButton.setBackground(Color.GRAY);
		this.add(historyButton);
		
		//Submit your daily answers
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		//action if "Submit" button click
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//Before entering info in file, check if user is trying to replace info for a certain date			
				int alreadySubmitted=0;//if value becomes 2 then user is trying to replace info
				String displayT = displayText.getText();//getting text
				//If user wished to replace info for a certain date, then the message shoud still be "You already submitted for .........."
				if(displayT.length()>=11) {//if text is under 11 characters when comparing it will give error
					if(displayT.substring(0,11).equals("You already")) {//checking if it begins with "You already" (only such message)
						alreadySubmitted=1;
					}
				}
				
				//get text from text fields
				String goodThing = goodThingText.getText();
				String badThing = badThingText.getText();
				boolean allRight=true;//is input correct?
				
				if(goodThing.isEmpty() || badThing.isEmpty()) {
					allRight=false;//invalid input
					displayText.setText("Please enter text");	
				}
				
				double rating=0.0;
				
				//checking if rating is a number between 0 and 10
				if(isNumeric(ratingText.getText()) && allRight) {//if ratingText is a number
					rating = Double.parseDouble(ratingText.getText());//convert string to double
					if(!(rating>=0 && rating<=10)) {//if between 0 and 10
						displayText.setText("Number should be between 0 and 10. Please enter a valid number!");//error message
						allRight=false;//input error
					}
				} else {//if ratingText is not a number
					if(allRight) {
						displayText.setText("Not a number. Please enter a number between 0 and 10!");//error message
						allRight=false;//input error
					}
				}
				
				//saving info in files
				if(allRight) {//no input error
					//get the current date in dd-mm-yyyy
					String dateDate = datePicker.getJFormattedTextField().getText();
					
					//separate date into day,month and year
					int day = Integer.parseInt(dateDate.substring(0,2));//get days
					int month = Integer.parseInt(dateDate.substring(3,5));//get months in numbers - 1,2,3....
					int year = Integer.parseInt(dateDate.substring(6,10)); //get year
					
					int dayDay=0, monthMonth=0, yearYear=0;//used to check if the input date is the same as any other date
					
					try {
						//reading "history.txt"
						Scanner sc = new Scanner(historyFile);
						
						//searching if date has been used before						
						while (sc.hasNextLine()) {
							
							String dateHistory = sc.nextLine();
							//we're only looking for the lines with dates (1st line, 5th line, .....)
							sc.nextLine();
							sc.nextLine();
							sc.nextLine();
							//dates don't get saved in file as dd-mm if days or months are one-digit numbers
							//find out on what indexes of the string days and months start
							int dayEnd=2, monthEnd=5;
							if(dateHistory.charAt(1)=='.') {
								dayEnd=1;
							}
							if(dateHistory.charAt(3)=='.') {
								monthEnd=3;
							}
							if(dateHistory.charAt(4)=='.') {
								monthEnd=4;
							}
							
						
							//convert from line in the file to date
							dayDay=Integer.parseInt(dateHistory.substring(0,dayEnd));
							monthMonth=Integer.parseInt(dateHistory.substring(dayEnd+1,monthEnd));
							yearYear=Integer.parseInt(dateHistory.substring(monthEnd+1,monthEnd+5));

							if(dayDay==day && monthMonth==month && yearYear==year) {//if date coincides with the one we input
								alreadySubmitted++;//becomes 2 if there was a warning
								if(alreadySubmitted==1) {//if there wasn't a warning
									displayText.setText("You already submitted for " + day + "." + month + "." + year + "." + "\n"
									+ " Press Confirm again to replace the info");//warning
								} else {
									
									//save everything from historyFile except info from the date we want to delete 
									File inputFile = historyFile;
									
									BufferedReader reader = new BufferedReader(new FileReader(inputFile));
									String dayDay0="" + dayDay,monthMonth0="" + monthMonth;
									if(dayDay<10) {
										dayDay0="0" + dayDay;
									}
									if(monthMonth<10) {
										monthMonth0="0" + monthMonth;
									}
									String lineToRemove = dayDay0 + "." + monthMonth0 + "." + yearYear;//setting line to search for
									String currentLine;
									String remember="";

									while((currentLine = reader.readLine()) != null) {//while there are still lines to read
										if(currentLine.equals(lineToRemove)) {//if we find the one to remove
											//those lines aren't being saved in remember, so they will be deleted
									    	reader.readLine();
									    	reader.readLine();
									    	reader.readLine();
									    	continue;//don't read rest of while this step, continue to next step
									    }
									    
										remember+=currentLine + "\n"
										+ reader.readLine() + "\n"
										+ reader.readLine() + "\n"
										+ reader.readLine() + "\n";
									    
									} 
									reader.close(); 
									
									try {
										//write to "history.txt"
										FileWriter fw = new FileWriter(historyFile);//it will replace what's in the file
										fw.write(remember);
									    fw.close();
									    
									    //let user know he submitted
									    //displayText.setText("Successfully submitted your daily moments!");
									} catch (IOException e) {//an error occurred
									    e.printStackTrace();
									}
								}
							}
						}
						sc.close();//close scanner
					} catch (FileNotFoundException e) {//file not found -> create file
						try {
							historyFile.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(alreadySubmitted!=1) {
					
						//text to write to "history.txt"
						String day0 = "" + day,month0 = "" + month;
						if(day<10) {
							day0 = "0" + day;
						}
						if(month<10) {
							month0 = "0" + month;
						}
						String paste = day0 + "."
						+ month0 + "."
						+ year + "\n"
						+ goodThing + "\n"
						+ badThing + "\n"
						+ rating + "\n";
						
						try {
							//write to "history.txt"
							FileWriter fw = new FileWriter(historyFile,true);
							fw.write(paste);
						    fw.close();
						    
						    //let user know he submitted
						    displayText.setText("Successfully submitted your daily moments!");//display message in displayText
						} catch (IOException e) {//an error occurred
						    e.printStackTrace();
						}
					}
				}
			}
		});
		submitButton.setBounds(30, 400, 150, 50);
		submitButton.setBackground(Color.GRAY);
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
	
	public class CustomFormat extends AbstractFormatter {

		@Override
		public Object stringToValue(String text) throws ParseException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}