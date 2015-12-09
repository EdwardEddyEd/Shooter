package com.egame.src.main.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.Game;
import com.egame.src.main.KeyInputs;
import com.egame.src.main.level.Level;
import com.egame.src.main.lib.FontResources;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.statics.GameMethods;

public class EndMenu {

	private Level level;
	private KeyInputs keyInput;
	
	private String[] stringOptions = {"", "Play Again", "Exit"};
	private int[] x = new int[3];
	private int[] y = {120, 240, 300};
	
	private int choices = 2;
	private int selected = 0;
	
	public EndMenu(Game game, Level level, KeyInputs keyInput, Graphics2D g2d, int winner){
		this.level = level;
		this.keyInput = keyInput;
		
		if(winner == 1) stringOptions[0] = "Blue wins!";
		else			stringOptions[0] = "Red wins!";
		
		g2d.setFont(FontResources.title);
		x[0] = (int) (game.getWidth()/2 - GameMethods.getStringLength(stringOptions[0],g2d)/2);
		
		g2d.setFont(FontResources.titleOptions);
		for(int i = 1; i < x.length; i++)
			x[i] = (int) (game.getWidth()/2 - GameMethods.getStringLength(stringOptions[i],g2d)/2);
		
		keyInput.w.setKeyHold(false);
		keyInput.s.setKeyHold(false);
		keyInput.up.setKeyHold(false);
		keyInput.down.setKeyHold(false);
	}
	
	public void tick(){
		if(keyInput.w.getPressed() || keyInput.up.getPressed()) selected = (selected + choices - 1)%choices;
		if(keyInput.s.getPressed() || keyInput.down.getPressed()) selected = (selected + choices + 1)%choices;
		if(keyInput.enter.getPressed() && selected == 0) {
			level.restart();
			SoundResources.SELECTED.setFramePosition(0);
			SoundResources.SELECTED.start();
		}
		if(keyInput.enter.getPressed() && selected == 1) {
			level.goToStartMenu();
			SoundResources.SELECTED.setFramePosition(0);
			SoundResources.SELECTED.start();
		}
	}
	
	public void render(Graphics2D g2d){
		g2d.setFont(FontResources.title);
		g2d.setColor(Color.white);
		g2d.drawString(stringOptions[0], x[0], y[0]);
		
		g2d.setFont(FontResources.titleOptions);
		for(int i = 1; i < x.length; i++){
			if(selected + 1 == i) continue;
			g2d.drawString(stringOptions[i], x[i], y[i]);
		}
		
		g2d.setColor(Color.yellow);
		g2d.drawString(stringOptions[selected + 1], x[selected + 1], y[selected + 1]);
		
	}
}
