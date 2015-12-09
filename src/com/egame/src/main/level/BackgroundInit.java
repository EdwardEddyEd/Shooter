package com.egame.src.main.level;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BackgroundInit {
	
	
	
	public static BufferedImage backgroundOne(int width, int height){
		BufferedImage background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = background.createGraphics();
		
		// White Background
		g2d.setColor(new Color(0xffFFFFff));
		g2d.fillRect(0, 0, width, height);
		
		// Black Grid - 1 px thick
		g2d.setColor(new Color(0xff555555));
		g2d.setStroke(new BasicStroke(3));
		
		for(int x = 44; x < width; x += 40){	// Vertically Line
			g2d.drawLine(x, 0, x, height);
		}
		for(int y = 44; y < width; y += 40){	// Horizontal Line
			g2d.drawLine(0, y, width, y);
		}
				
		// Black Borders - 5 px thick
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(10));
		g2d.drawRect(3, 3, width - 6, height - 6);
		
		g2d.dispose();
		return background;
	}
}
