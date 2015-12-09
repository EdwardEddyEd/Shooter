package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.statics.GameMethods;

public class DeathParticle extends Entity{
	
	private Player player;
	private Color color;
	private double speedX, speedY;
	private float opacity;
	private int diameter;
	private int counter;
	
	public DeathParticle(double x, double y, int counter, Color color, Player player){
		super(x,y);
		this.counter = counter + 1;
		this.color = color;
		this.player = player;
		
		diameter = (int)(Math.random() * 5) + 5;
		opacity = 1f;
		speedX = (Math.random() * 6) - 2.5;
		speedY = (Math.random() * 6) - 2.5;
	}
	
	public DeathParticle(double x, double y, int counter, int speed, Color color, Player player){
		super(x,y);
		this.counter = counter + 1;
		this.color = color;
		this.player = player;
		
		diameter = (int)(Math.random() * 5) + 5;
		opacity = 1f;
		
		int theta = (int) (Math.random() * 360);
		this.speedX = Math.cos(Math.toRadians(theta)) * speed;
		this.speedY = Math.sin(Math.toRadians(theta)) * speed;
	}
	
	public void removeSelf(){
		player.removeParticle(this);
	}
	
	public void tick(){
		counter--;
		if(counter <= 0) removeSelf();
		if(counter < 15) opacity = counter/15f;
		
		x += speedX;
		y += speedY;
		
		// Decrement Speed
		if(speedX > 0){
			speedX -= 0.05;
			if(speedX < 0) speedX = 0;
		}
		if(speedY > 0){
			speedY -= 0.05;
			if(speedY < 0) speedY = 0;
		}
	}
	
	public void render(Graphics2D g2d){
		g2d.setColor(color);
		GameMethods.setOpacity(g2d, opacity);
		g2d.fillOval((int)x, (int)y, diameter, diameter);
	}
}
