package com.harmonic.harmonicRadio;

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
	
	public Clock() {
		trans = new Color(0,0,0,0);
		clockFont = new Font("Sans-Serif", Font.BOLD, 80);
		start();
	}
//==========================================================================================//
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(clockFont);
		g.setColor(trans);
		BufferedImage img1 = null;
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
		g.drawImage(img1, 1,1, trans, null);
		g.drawImage(img2, 72,1, trans, null);
		//g.drawString(" : ", 106, 75);
		g.drawImage(img3, 143,1, 35, 50, trans, null);
		g.drawImage(img4, 179,1, 35, 50, trans, null);
		g.drawImage(img5, 143, 52, 70, 50, trans, null);
	}
//==========================================================================================//

	public char getImage(int num) {
		String time = timeNow();
		char first = time.charAt(0);
		char second = time.charAt(1);
		char third = time.charAt(2);
		char fourth = time.charAt(3);
		char fifth = time.charAt(4);
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

	public String timeNow() {
		int ampm = 0;
		Calendar now = Calendar.getInstance();
		int hrs = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		
		if(hrs > 11)
			ampm = 1;
		if(hrs > 12) {
			hrs -= 12;
		}
		
		String time = zero(hrs)+zero(min)+ampm;
		
		return time;
	}
//==========================================================================================//
	
	public String zero(int num) {
		String number = (num<10) ? ("0"+num) : (""+num);
		return number;
	}
//==========================================================================================//
	
	public void start() {
		if(runner == null) runner = new Thread(this);
		runner.start();
	}
//==========================================================================================//
	
	public void run() {
		while (runner == Thread.currentThread()) {
			repaint();
			
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				System.out.println("Thread failed");
			}
		}
	}
}
