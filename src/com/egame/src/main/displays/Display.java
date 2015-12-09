package com.egame.src.main.displays;

import java.awt.Graphics2D;

import com.egame.src.main.entities.Player;

public abstract class Display {

	protected Player player;
	protected double x, y;

	protected int movementCounter = 0;
	protected int displayCounter = 0;
	
	protected boolean visible = false;
	protected boolean completedDisplay = false;
	protected boolean triggered = false;
	
	public Display(Player player){
		this.player = player;
	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	
	public void triggerDisplay(Byte event){
		visible = true;
		triggered = true;
		displayCounter = 0;
	}
	
	public boolean isVisible(){
		return visible;
	}
}