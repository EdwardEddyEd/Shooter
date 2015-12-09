package com.egame.src.main.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.Game;
import com.egame.src.main.lib.FontResources;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.statics.GameMethods;

public class Countdown {

	private Game game;
	
	private boolean visible;				// If the countdown animation is completed and no longer visible, set to false.
	private float opacity = 1f;
	private int counter = 270;
	
	private int[] rectX = {0 ,0 ,0};
	private int[] rectY = {100, 130, 160};
	private int[] rectWidth = new int[3];
	private int[] rectHeight = {30, 30, 30};
	
	private int textY = 175;
	
	private String countdownString;
	
	public Countdown(Game game){
		this.game = game;
		
		visible = true;
		for(int i = 0; i < rectWidth.length; i++)
			rectWidth[i] = game.getWidth();
		game.removeKeyListener(game.getKeyInput());
	}
	
	public void tick(){
		counter--;
		if(counter >= 240) return;
		
		if(counter >= 180){
			if(counter == 239) SoundResources.COUNTDOWN.play();
			countdownString = "3";
			rectX[0] = (int) (Math.sin(Math.toRadians((counter - 240) * (-3f/2))) * game.getWidth());
			
			if(counter < 210)
				opacity = (counter - 180f)/30f;
			else
				opacity = 1f;
		}
		else if(counter >= 120){
			if(counter == 179) SoundResources.COUNTDOWN.play();
			countdownString = "2";
			rectX[1] = (int) (Math.sin(Math.toRadians((counter - 180) * (-3f/2))) * game.getWidth());
			
			if(counter < 150)
				opacity = (counter - 120f)/30f;
			else
				opacity = 1f;
		}
		else if(counter >= 60){
			if(counter == 119) SoundResources.COUNTDOWN.play();
			countdownString = "1";
			rectX[2] = (int) (Math.sin(Math.toRadians((counter - 120) * (-3f/2))) * game.getWidth());
			
			if(counter < 90)
				opacity = (counter - 60f)/30f;
			else
				opacity = 1f;
		}
		else if(counter >= 0){
			if(counter == 59) {
				game.addKeyListener(game.getKeyInput());
				SoundResources.GO.setFramePosition(0);
				SoundResources.GO.start();
			}
			countdownString = "GO";
			
			if(counter < 30){
				opacity = (counter)/30f;
				textY = 175 - (int) ((Math.sin(Math.toRadians((counter - 30) * -3))) * 20);
			}
			else
				opacity = 1f;
			
		}
		else{
			visible = false;
		}
	}
	
	public void render(Graphics2D g2d){
		GameMethods.setOpacity(g2d, .4f);
		g2d.setColor(Color.black);
		for(int i = 0; i < rectX.length; i++)
			g2d.fillRect(rectX[i], rectY[i], rectWidth[i], rectHeight[i]);
		
		if(counter < 240){
			g2d.setFont(FontResources.countDown);
			GameMethods.setOpacity(g2d, opacity);
			g2d.drawString(countdownString, (int)(game.getWidth()/2 - GameMethods.getStringLength(countdownString, g2d)/2), textY);
		}
	}
	
	public boolean isVisible(){
		return visible;
	}
}
