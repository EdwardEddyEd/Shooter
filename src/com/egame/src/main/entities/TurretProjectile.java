package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;

public class TurretProjectile extends Projectile{

	private int colorCounter = 16;
	private int colorPurple = 255;
	private int diameter = 10;
	private double xSpeed;
	private double ySpeed;

	public TurretProjectile(double x, double y, Byte player, Level level) {
		super(x, y, player, new Color(0xffff00ff), level);
		
		Player otherPlayer;
		if(player == 1) otherPlayer = level.getPlayer2();
		else 		    otherPlayer = level.getPlayer1();
		
		xSpeed = (otherPlayer.getCenterX() - x)/30;
		ySpeed = (otherPlayer.getCenterY() - y)/30;
		
		SoundResources.SHOOT.play();	
	}

	public void tick(){
		super.tick();
		
		// Update Movement
		x += xSpeed;
		y += ySpeed;

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
