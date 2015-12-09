package com.egame.src.main.entities;

public abstract class Entity {
	protected double x, y;
	
	public Entity(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick();
}
