package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;

public class HomingProjectile extends Projectile{

	private static final float speedIncrement = 0.075f;
	private int colorCounter = 16;
	private int colorRed = 255;
	private int diameter = 10;
	private double xSpeed;
	private double ySpeed;
	
	private Player otherPlayer;
	
	public HomingProjectile(double x, double y, Byte player, Level level) {
		super(x, y, player, new Color(0xffff0000), level);
		
		if(player == 1) otherPlayer = level.getPlayer2();
		else 		    otherPlayer = level.getPlayer1();
		
		xSpeed = (otherPlayer.getCenterX() - x)/50;
		ySpeed = (otherPlayer.getCenterY() - y)/50;
		
		SoundResources.SHOOT.play();
		}

	public void tick(){
		super.tick();
		
		// Update Movement
		x += xSpeed;
		y += ySpeed;
		
		if(getCenterX() < otherPlayer.getCenterX())	xSpeed += speedIncrement;
		else 										xSpeed -= speedIncrement;
		if(getCenterY() < otherPlayer.getCenterY()) ySpeed += speedIncrement;
		else										ySpeed -= speedIncrement;
		
		// Brighten and Darken the Color of the Bullet
		if(colorCounter >= 9){
			colorRed -= 20;
		}
		else if(colorCounter >= 1) {
			colorRed += 20;
		}
		color = new Color(colorRed, 0x00, 0x00);
		
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
