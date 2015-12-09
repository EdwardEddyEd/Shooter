package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;

public class BasicProjectile extends Projectile{

	private int colorCounter = 16;
	private int colorYellow = 255;
	private int diameter = 10;
	private double xSpeed;
	private double ySpeed;
	
	public BasicProjectile(double x, double y, double speed, double theta, Byte player, Level level) {
		super(x, y, player, new Color(0xffffff00), level);
		xSpeed = Math.cos(Math.toRadians(theta)) * speed;
		ySpeed = Math.sin(Math.toRadians(theta)) * speed;
	}

	public void tick(){
		super.tick();
		
		// Update Movement
		x += xSpeed;
		y += ySpeed;
		
		// Brighten and Darken the Color of the Bullet
		if(colorCounter >= 9){
			colorYellow -= 20;
		}
		else if(colorCounter >= 1) {
			colorYellow += 20;
		}
		color = new Color(colorYellow, colorYellow, 0x00);
		
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
