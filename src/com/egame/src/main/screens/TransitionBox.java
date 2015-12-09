package com.egame.src.main.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.Game;

public class TransitionBox {

	private double x,y;
	private int width, height;
	private Game game;
	
	private boolean coveringScreen = false;
	private boolean finishedMoving = true;
	
	private int frameCounter = 0;
	
	public TransitionBox(double x, double y, int width, int height, Game game){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
	}
	
	public void activateBox(){
		frameCounter = 30;
		finishedMoving = false;
	}
	
	public void tick(){
		if(finishedMoving) return;
		
		if(frameCounter > 0){
			frameCounter--;
			if(!coveringScreen) x = (game.getWidth()/2) * (Math.cos(Math.toRadians(frameCounter * 6)) - 1);
			else x = (game.getWidth()/2) * (Math.cos(Math.toRadians(frameCounter * 6)) + 1);
		}
		else if(frameCounter == 0){
			frameCounter--;
			coveringScreen = !coveringScreen;
			finishedMoving = true;
		}
	}
	
	public void render(Graphics2D g2d){
		g2d.setColor(Color.black);
		g2d.fillRect((int)x, (int)y, width, height);
	}
	
	public boolean isFinishedMoving(){
		return finishedMoving;
	}
	
	public boolean isCoveringScreen(){
		return coveringScreen;
	}
}
