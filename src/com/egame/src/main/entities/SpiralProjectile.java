package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;

public class SpiralProjectile extends Projectile{

	private int colorCounter = 16;
	private int colorGreen = 255;
	private int diameter = 10;
	
	private double initialX, initialY;
	private double theta = 0;
	private double thetaIncrement;
	private double radius = 0;
	
	
	public SpiralProjectile(double x, double y, Byte lastDirectionalKey, Byte player, Level level) {
		super(x, y, player, new Color(0xff00ff00), level);
		this.initialX = x;
		this.initialY = y;
		
		// Determines whether the projectile will spin counterclockwise or clockwise based on player's last up or down input
		if(lastDirectionalKey == 1)		{thetaIncrement = -2.5;}	// Spin counterclockwise
		else if(lastDirectionalKey == 2){thetaIncrement = 2.5;}		// Spin clockwise
		
		SoundResources.SHOOT.play();
	}
	
	public void tick(){
		super.tick();
		
		// Update Movement
		radius += 1;
		theta += thetaIncrement;
		x = initialX + (Math.cos(Math.toRadians(theta)) * radius);
		y = initialY + (Math.sin(Math.toRadians(theta)) * radius);
		
		// Brighten and Darken the Color of the Bullet
		if(colorCounter >= 9){
			colorGreen -= 20;
		}
		else if(colorCounter >= 1) {
			colorGreen += 20;
		}
		color = new Color(0x00, colorGreen, 0x00);
		
		if(--colorCounter <= 0) colorCounter = 16;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillOval((int)x, (int)y, diameter, diameter);
	}

	public int getRadius(){
		return diameter/2;
	}
	
	public double getCenterX(){
		return x + diameter/2;
	}
	
	public double getCenterY(){
		return y + diameter/2;
	}
}
