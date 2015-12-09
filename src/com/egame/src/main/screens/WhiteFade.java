package com.egame.src.main.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.Game;
import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.statics.GameMethods;

public class WhiteFade {

	private Game game;
	private Level level;
	private float opacity = 1f;
	private int counter = 151;
	
	public WhiteFade(Game game, Level level){
		this.game = game;
		this.level = level;
	}
	
	public void tick(){
		if(counter <= 0) return;
		
		counter--;
		if(counter > 145)	   opacity = 1f;
		else if(counter > 140) opacity = 0f;
		else if(counter > 135) opacity = 1f;
		else if(counter > 120) opacity = 0f;
		else if(counter > 80)  opacity = (-1*(counter - 120))/40f;
		else if(counter > 40){
			if(counter == 70){
				level.goToEndMenu();
				SoundResources.DEATHBUBBLE.stop();
			}
			opacity = 1f;
		}
		else if(counter > 0)  opacity = counter/40f;
	}
	
	public void render(Graphics2D g2d){
		g2d.setColor(Color.white);
		GameMethods.setOpacity(g2d, opacity);
		g2d.fillRect(0, 0, game.getWidth(), game.getHeight());
	}
	
}
