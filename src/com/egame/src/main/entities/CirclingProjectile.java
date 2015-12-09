package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;

public class CirclingProjectile extends Projectile{

	private int colorCounter = 16;
	private int diameter = 10;
	private int colorBlue = 255;
	private int fullDistance = 20;
	private int counter = 300;
	private int theta = 0;
	
	public CirclingProjectile(double x, double y, Byte player, Level level) {
		super(x, y, player, new Color(0xff0000ff), level);
		
		SoundResources.SHOOT.play();
	}

	public void tick(){
		super.tick();
		if(--counter <= 0){
			x = -200;
			y = -200;
			return;
		}
		
		int distance;
		if(counter > 290)	  distance = (int)(-fullDistance * (counter - 300)/10f);
		else if(counter > 10) distance = fullDistance;
		else				  distance = (int)(fullDistance * (counter/10f));
		
		theta = (theta + 8)%360;
		
		Player selfPlayer;
		if(player == 1) selfPlayer = level.getPlayer1();
		else			selfPlayer = level.getPlayer2();
		
		// Update Movement
		x = selfPlayer.getCenterX() + (Math.cos(Math.toRadians(theta)) * distance) - (diameter/2);
		y = selfPlayer.getCenterY() + (Math.sin(Math.toRadians(theta)) * distance) - (diameter/2);
		
		// Brighten and Darken the Color of the Bullet
		if(colorCounter >= 9){
			colorBlue -= 20;
		}
		else if(colorCounter >= 1) {
			colorBlue += 20;
		}
		color = new Color(0x00, 0x00, colorBlue);
		
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
