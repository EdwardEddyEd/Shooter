package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.egame.src.main.level.Level;
import com.egame.src.main.statics.GameMethods;

public class Ammo extends Entity{
	
	private Level level;
	private Color color;
	private int diameter;
	public Ammo(double x, double y, Level level) {
		super(x, y);
		diameter = GameMethods.getRandomDiameter();
		color = GameMethods.getRandomColor();
		this.level = level;
	}
	
	public void deleteSelf(){
		level.removeEntity(this);
	}

	public void tick() {
		
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
