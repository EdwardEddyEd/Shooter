package com.egame.src.main.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.egame.src.main.level.Level;

public class Turret extends Projectile{

	private int colorCounter = 16;
	private int colorPurple = 255;
	private int counter = 1920;
	private double originalX, originalY;
	private int diameter;
	private double ySpeed;
	private boolean wasHit = false;
	
	public Turret(double x, double y, Byte player, Level level) {
		super(x, y, player, new Color(0xffff00ff), level);
		diameter = 10;
		ySpeed = 0;
		originalX = x;
		originalY = y;
	}
	
	public void hit(){
		if(!wasHit){
			counter = 29;
			wasHit = true;
		}
	}

	public void tick(){
		super.tick();
		
		counter--;
		if(counter >= 1890){
			diameter = (int)(((counter - 1920)/-3f) + 10);
			x = originalX + (((counter - 1920)/6f));
			y = originalY + (((counter - 1920)/6f));
		}
		else if(counter >= 30){
			if(counter%60 == 0)
				level.addEntity(new TurretProjectile(x + 5, y + 5, player, level));
		}
		else if(counter > 0){
			diameter = (int)((counter/3f) + 10);
			x = originalX - ((counter/6f));
			y = originalY - ((counter/6f));
		}
		else if(counter == 0){
			wasHit = true;
			diameter = 10;
			ySpeed = -10;
		}
		else{
			ySpeed++;
		}
		
		// Update Movement
		y -= ySpeed;
		
		// Brighten and Darken the Color of the Bullet
		if(colorCounter >= 9){
			colorPurple -= 20;
		}
		else if(colorCounter >= 1) {
			colorPurple += 20;
		}
		color = new Color(colorPurple, 0x00, colorPurple);
		
		if(--colorCounter <= 0) colorCounter = 16;
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(5));
		g2d.drawOval((int)x, (int)y, diameter, diameter);
		
	}
	
	public boolean getWasHit(){
		return wasHit;
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
