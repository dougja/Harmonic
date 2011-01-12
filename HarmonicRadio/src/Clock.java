import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;

class Clock extends JPanel implements Runnable{
	Thread runner;
	Font clockFont;
	Color trans;
	
	//Create Clock and set constants
	public Clock() {
		trans = new Color(0,0,0,0);
		clockFont = new Font("Sans-Serif", Font.BOLD, 80);
		start();
	}
//==========================================================================================//
	
	//This function puts together all the graphics for the clock and is only ever called
	//by 'repaint()' in function 'run()'
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(clockFont);
		g.setColor(trans);
		BufferedImage img1 = null;
		
		//each of these works out the correct image to place in the 4 positions
		//(HH:MM:am/pm)
		try {
			img1 = ImageIO.read(new File(getImage(0)+".png"));
		}catch (IOException e) {
			System.err.println("Image file not found");
		}
		
		BufferedImage img2 = null;
		try {
			img2 = ImageIO.read(new File(getImage(1)+".png"));
		}catch (IOException e) {
			System.err.println("Image file not found");
		}
		
		BufferedImage img3 = null;
		try {
			img3 = ImageIO.read(new File(getImage(2)+".png"));
		}catch (IOException e) {
			System.err.println("Image file not found");
		}
		
		BufferedImage img4 = null;
		try {
			img4 = ImageIO.read(new File(getImage(3)+".png"));
		}catch (IOException e) {
			System.err.println("Image file not found");
		}
		
		BufferedImage img5 = null;
		try {
			img5 = ImageIO.read(new File(getImage(4)+".png"));
		}catch (IOException e) {
			System.err.println("Image file not found");
		}
		
		//draw each image for the numbers and position them
		g.drawImage(img1, 1,1, trans, null);
		g.drawImage(img2, 72,1, trans, null);
		//g.drawString(" : ", 106, 75);
		g.drawImage(img3, 143,1, 35, 50, trans, null);
		g.drawImage(img4, 179,1, 35, 50, trans, null);
		g.drawImage(img5, 143, 52, 70, 50, trans, null);
	}
//==========================================================================================//

	//this function returns the correct string to locate the image for positions
	public char getImage(int num) {
		//get current time in a custom format from 'timeNow():'
		String time = timeNow();
		
		//split the custom time format into it's components
		char first = time.charAt(0);
		char second = time.charAt(1);
		char third = time.charAt(2);
		char fourth = time.charAt(3);
		char fifth = time.charAt(4);
		
		//depending on what the calling function wanted, return correct string
		switch (num) {
		case 0:
			return first;
		case 1:
			return second;
		case 2:
			return third;
		case 3:
			return fourth;
		case 4:
			if(fifth == '1')
				return 'p';
			else
				return 'a';
		default:
			return 'n';
		}	
	}

	//this function returns the current time in a custom format to be read by 'getImage(int num)'
	public String timeNow() {
		int ampm = 0;
		
		//get current time
		Calendar now = Calendar.getInstance();
		
		//extract from the 'Calendar' class the values for current hours and minutes
		int hrs = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		
		//adjust from 24 hour clock to 12 and set am/pm appropriately
		if(hrs > 11)
			ampm = 1;
		if(hrs > 12) {
			hrs -= 12;
		}
		
		//construct custom string
		String time = zero(hrs)+zero(min)+ampm;
		
		//return to calling function (usually 'getImage(int num)'
		return time;
	}
//==========================================================================================//
	
	//if a number is less than the necessary two digits, this function adds the extra 0
	//it was constructed using information from multiple forums
	public String zero(int num) {
		String number = (num<10) ? ("0"+num) : (""+num);
		return number;
	}
//==========================================================================================//
	
	//this is what starts the clock as a separate thread
	public void start() {
		if(runner == null) runner = new Thread(this);
		runner.start();
	}
//==========================================================================================//
	
	//this is the thread construct
	public void run() {
		
		//loop while the thread is still active
		while (runner == Thread.currentThread()) {
			
			//redraw the graphics of the clock
			repaint();
			
			//if possible, wait 1000 miliseconds before continuing
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				System.out.println("Thread failed");
			}
		}
	}
}
