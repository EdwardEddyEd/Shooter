package com.egame.src.main.screens;

import java.awt.Graphics2D;

import com.egame.src.main.Game;
import com.egame.src.main.statics.GameMethods;

public class SlideTransition {

	private int numberOfBoxes;
	private int frameSpeed;
	private int frameSeparation;
	
	private boolean transitionComplete = true;
	private boolean finishedMoving = true;
	private boolean coveringScreen = false;
	private TransitionBox[] boxes;
	
	public SlideTransition(Game game, int numOfBoxes, int frameSpeed){
		this.numberOfBoxes = numOfBoxes;
		this.frameSpeed = frameSpeed;
		this.frameSeparation = (numOfBoxes) * frameSpeed;
		
		boxes = new TransitionBox[numberOfBoxes + 1];
		for(int i = 0; i < boxes.length; i++)
			boxes[i] = new TransitionBox(-game.getWidth(), (game.getHeight()/numberOfBoxes) * i, game.getWidth(), game.getHeight()/numberOfBoxes, game);
	}
	
	public void activateTransition(){
		if(!coveringScreen){
			transitionComplete = false;
			finishedMoving = false;
			frameSeparation = (numberOfBoxes) * frameSpeed;
		}
	}
	
	public void activateSecondTransition(){
		if(coveringScreen){
			frameSeparation = (numberOfBoxes) * frameSpeed;
			finishedMoving = false;
		}
	}
	
	public void checkFinishedMoving(){
		finishedMoving = true;
		for(TransitionBox box: boxes)
			finishedMoving = finishedMoving && box.isFinishedMoving();
	}
	
	public void checkScreenCovered(){
		coveringScreen = true;
		for(TransitionBox box: boxes)
			coveringScreen = coveringScreen && box.isCoveringScreen();
	}
	
	public void tick(){
		if(transitionComplete || finishedMoving) return;
		
		for(int i = 0; i < boxes.length; i++){
			if(frameSeparation == (frameSpeed * (numberOfBoxes - i))) boxes[i].activateBox();
			boxes[i].tick();
		}
		
		if(frameSeparation >= 0) frameSeparation--;
		checkFinishedMoving();
		checkScreenCovered();

		if(finishedMoving && !coveringScreen){
			transitionComplete = true;
		}
	}
	
	public void render(Graphics2D g2d){
		if(transitionComplete) return;
		
		GameMethods.setOpacity(g2d, 1f);
		for(TransitionBox box: boxes)
			box.render(g2d);
	}
	
	public boolean isFinishedMoving(){
		return finishedMoving;
	}
	
	public boolean isComplete(){
		return transitionComplete;
	}
}
