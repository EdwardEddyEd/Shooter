package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;

public class Projectile extends Entity{

	protected Level level;
	protected Color color;
	protected Byte player;
	
	public Projectile(double x, double y, Byte player, Color color, Level level) {
		super(x, y);
		this.player = player;
		this.color = color;
		this.level = level;
	}
	
	public void tick(){

	}

	public void render(Graphics g) {

	}

	public void deleteSelf(){
		level.removeEntity(this);
	}
	
	public void checkBoundary(){
		// Delete Self if it reaches out of bounds
		if(x < -20 || x > level.getGame().getWidth() + 20 || y < -20 || y > level.getGame().getHeight() + 20)
			deleteSelf();
	}
	
	public int getRadius(){
		return 0;
	}
	
	public double getCenterX(){
		return 1;
	}
	
	public double getCenterY(){
		return 0;
	}
	
	public Byte getPlayer(){
		return player;
	}

}
