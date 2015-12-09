package com.egame.src.main.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.Game;
import com.egame.src.main.KeyInputs;
import com.egame.src.main.lib.FontResources;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.statics.GameMethods;

public class StartMenu {
	
	private Game game;
	private KeyInputs keyInput;
	
	private Byte transitionDirection;			// 0x1 = Right, 0x2 = Left;
	private int transitionCounter = 0;
	
	private int colorCounter = 16;
	private int color = 255;
	
	private double deltaX = 0;
	private double prevDeltaX = 0;
	
	private int titleChoices = 3;
	private int instructionChoices = 2;
	private int choiceSelected = 0;
	private String[] menuStrings = {"SHOOTER", "Play", "Instruction", "Quit", 
								    "INSTRUCTIONS", "Players must collect colorful bullets around the field to use as ammo to shoot at each other. The player to reduce the other's HP down to 0 wins the game.", 
								    "Back", "More",
								    "KEYS", "F/Numpad1 - ", "G/Numpad2 - ", "H/Numpad3 - ", 
								    	    "J/Numpad4 - ", "T/Numpad5 - ", "Y/Numpad6 - ",
								    "8 Basic Shot", "1 Spiral Shot", "5 Homing Shot",
								    "7 Orbit Shot", "50 Turret", "250 Blaster", 
								    "Back"};
	private String[] instructions;
	private int[] designatedX = new int[22];
	private int[] designatedY = {120, 240, 290, 340, 
								 70, 140, 470, 470,
								 70, 140, 470};
	
	private enum STATE{
		TITLE,
		INSTRUCTION,
		KEYS;
	}
	private STATE menuState;
	
	public StartMenu(Game game, KeyInputs keyInput){
		this.game = game;
		this.keyInput = keyInput;
	}
	
	public void init(Graphics2D g2d){
		// Set State for StartMenu
		menuState = STATE.TITLE;
		
		// Set X Coordinates for Fonts with "Title Font"
		g2d.setFont(FontResources.title);
		designatedX[0] = (int) (game.getWidth() - GameMethods.getStringLength(menuStrings[0], g2d))/2;		// Main Title

		// Set X Coordinates for Fonts with "TitleOptions Font"
		g2d.setFont(FontResources.titleOptions);
		designatedX[4] = (int) (game.getWidth() - GameMethods.getStringLength(menuStrings[4], g2d))/2;		// Instruction Title
		designatedX[8] = (int) (game.getWidth() - GameMethods.getStringLength(menuStrings[8], g2d))/2;		// Key Title
		
		for(int i = 0; i < 3; i++)
			designatedX[i + 1] = (int) (game.getWidth() - GameMethods.getStringLength(menuStrings[i + 1], g2d))/2;		// Main Title Options
		
		// Set X Coordinates for InstructionMenu Text
		designatedX[5] = 25;							// Instruction Text
		designatedX[6] = 25;							// "Back" Text
		designatedX[7] = 525;							// "More" Text
		instructions = GameMethods.getStringArray(g2d, menuStrings[5], FontResources.instruction, game.getWidth() - 40);		// Instruction Text with Line Wrapping
		
		// Set X Coordinates for Key Menu Text
		g2d.setFont(FontResources.keyText);
		for(int i = 0; i < 6; i++){
			designatedX[9 + i] = 25;																// Key Instruction
			designatedX[15 + i] = 25 + (int)GameMethods.getStringLength(menuStrings[9 + i], g2d);	// Resulting Bullet
		}
		
		designatedX[21] = 25;							// "Back" Text
		
		keyInput.up.setKeyHold(false);
		keyInput.down.setKeyHold(false);
		keyInput.left.setKeyHold(false);
		keyInput.right.setKeyHold(false);
		
		keyInput.w.setKeyHold(false);
		keyInput.s.setKeyHold(false);
		keyInput.a.setKeyHold(false);
		keyInput.d.setKeyHold(false);
	}
	
	public void tick(){
		if(menuState == STATE.TITLE && (deltaX%game.getWidth() == 0)){	
			// Navigating through Choices
			if(keyInput.w.getPressed() || keyInput.up.getPressed()) choiceSelected = (choiceSelected + titleChoices - 1)%titleChoices;
			if(keyInput.s.getPressed() || keyInput.down.getPressed()) choiceSelected = (choiceSelected + titleChoices + 1)%titleChoices;	
			
			// Selecting a Choice
			if(keyInput.enter.getPressed()){
				if(choiceSelected == 0) {
					game.startChangeState((byte) 0x1);
					SoundResources.SELECTED.setFramePosition(0);
					SoundResources.SELECTED.start();
				}
				else if(choiceSelected == 1) {
					menuState = STATE.INSTRUCTION;
					transitionCounter = 30;
					transitionDirection = 0x2;
					choiceSelected = 1;
					SoundResources.SELECTED.setFramePosition(0);
					SoundResources.SELECTED.start();
				}
				else if(choiceSelected == 2) {
					System.exit(0);
				}
			}
		}
		
		else if(menuState == STATE.INSTRUCTION && (deltaX%game.getWidth() == 0)){
			// Navigating through Choices
			if(keyInput.a.getPressed() || keyInput.left.getPressed() || keyInput.d.getPressed() || keyInput.right.getPressed()) 
				choiceSelected = (choiceSelected + instructionChoices - 1)%instructionChoices;
			
			// Selecting a Choice
			if(keyInput.enter.getPressed()) {
				if(choiceSelected == 0) {
					menuState = STATE.TITLE;
					transitionCounter = 30;
					transitionDirection = 0x1;
					choiceSelected = 1;
					SoundResources.SELECTED.setFramePosition(0);
					SoundResources.SELECTED.start();
				}
				else if(choiceSelected == 1) {
					menuState = STATE.KEYS;
					transitionCounter = 30;
					transitionDirection = 0x2;
					choiceSelected = 0;
					SoundResources.SELECTED.setFramePosition(0);
					SoundResources.SELECTED.start();
				}
			}
		}
		
		else if(menuState == STATE.KEYS && (deltaX%game.getWidth() == 0)){
			// Selecting a Choice
			if(keyInput.enter.getPressed()) {
				menuState = STATE.INSTRUCTION;
				transitionCounter = 30;
				transitionDirection = 0x1;
				choiceSelected = 0;
				SoundResources.SELECTED.setFramePosition(0);
				SoundResources.SELECTED.start();
			}
		}
		
		// COLOR TRANSITION
		if(colorCounter >= 9){
			color -= 20;
		}
		else if(colorCounter >= 1) {
			color += 20;
		}
		if(colorCounter-- <= 0) colorCounter = 16;
		
		// TRANSITION COUNTER
		if(transitionCounter-- > 0){
			if(transitionDirection == 0x2)
				deltaX = prevDeltaX + (game.getWidth()/2 * (Math.cos(Math.toRadians(transitionCounter * 6)) + 1));
			else if(transitionDirection == 0x1)
				deltaX = prevDeltaX - (game.getWidth()/2 * (Math.cos(Math.toRadians(transitionCounter * 6)) + 1));
			
			if(transitionCounter == 0)
				prevDeltaX = deltaX;
		}
	}
	
	public void render(Graphics2D g2d){
		
		// Render Title
		g2d.setColor(Color.white);
		g2d.setFont(FontResources.title);
		g2d.drawString(menuStrings[0], designatedX[0] - (int)deltaX, designatedY[0]);
		
		// Render Instruction and Key Menus' Titles
		g2d.setFont(FontResources.titleOptions);
		g2d.drawString(menuStrings[4], designatedX[4] + game.getWidth() - (int)deltaX, designatedY[4]);
		g2d.drawString(menuStrings[8], designatedX[8] + (2 * game.getWidth()) - (int)deltaX, designatedY[8]);
		
		// Render Title Options
		for(int i = 0; i < 3; i++){
			if(choiceSelected == i && menuState == STATE.TITLE){
				g2d.setColor(Color.yellow);
				g2d.drawString(menuStrings[i + 1], designatedX[i + 1] - (int)deltaX, designatedY[i + 1]);
				g2d.setColor(Color.white);
			}
			else
				g2d.drawString(menuStrings[i + 1], designatedX[i + 1] - (int)deltaX, designatedY[i + 1]);
		}
		
		// Render Instruction Options
		g2d.setFont(FontResources.instruction);
		int y = designatedY[5];
		for(String instruction: instructions){
			g2d.drawString(instruction, designatedX[5] + game.getWidth() - (int)deltaX, y);
			y += 35;
		}
		
		for(int i = 0; i < 2; i++){
			if(choiceSelected == i && menuState == STATE.INSTRUCTION){
				g2d.setColor(Color.yellow);
				g2d.drawString(menuStrings[i + 6], designatedX[i + 6] + game.getWidth() - (int)deltaX, designatedY[i + 6]);
				g2d.setColor(Color.white);
			}
			else
				g2d.drawString(menuStrings[i + 6], designatedX[i + 6] + game.getWidth() - (int)deltaX, designatedY[i + 6]);
		}
		
		// Render Keys Menu
		g2d.setColor(Color.white);
		g2d.setFont(FontResources.keyText);
		y = designatedY[9];
		for(int i = 0; i < 6; i++){
			g2d.drawString(menuStrings[i + 9], designatedX[i + 9] + (2 * game.getWidth()) - (int)deltaX, y);
			y += 50;
		}
		
		y = designatedY[9];
		y = setColorKeyText(new Color(color, color, 0x00), menuStrings[15], designatedX[15] + (2 * game.getWidth()) - (int)deltaX, y, g2d);		// Basic Shot Text
		y = setColorKeyText(new Color(0x00,  color, 0x00), menuStrings[16], designatedX[16] + (2 * game.getWidth()) - (int)deltaX, y, g2d);		// Spiral Shot Text
		y = setColorKeyText(new Color(color, 0x00,  0x00), menuStrings[17], designatedX[17] + (2 * game.getWidth()) - (int)deltaX, y, g2d);		// Homing Shot Text
		y = setColorKeyText(new Color(0x00,  0x00, color), menuStrings[18], designatedX[18] + (2 * game.getWidth()) - (int)deltaX, y, g2d);		// Orbit Shot Text
		y = setColorKeyText(new Color(color, 0x00, color), menuStrings[19], designatedX[19] + (2 * game.getWidth()) - (int)deltaX, y, g2d);		// Turret Shot Text
		
		GameMethods.drawTextOutline(g2d, menuStrings[20], new Color(color, color/2, 0x00), 10, designatedX[20] + (2 * game.getWidth()) - (int)deltaX, y, FontResources.keyText);
		setColorKeyText(Color.white, menuStrings[20], designatedX[20] + (2 * game.getWidth()) - (int)deltaX, y, g2d);							// Blaster Text
		
		g2d.setFont(FontResources.instruction);
		for(int i = 0; i < 1; i++){
			if(choiceSelected == i && menuState == STATE.KEYS){
				g2d.setColor(Color.yellow);
				g2d.drawString(menuStrings[i + 21], designatedX[i + 21] + (2* game.getWidth()) - (int)deltaX, designatedY[10]);
				g2d.setColor(Color.white);
			}
			else
				g2d.drawString(menuStrings[i + 21], designatedX[i + 21] + (2 * game.getWidth()) - (int)deltaX, designatedY[10]);
		}
	}
	
	public int setColorKeyText(Color color, String text, int x, int y, Graphics2D g2d){
		g2d.setColor(color);
		g2d.drawString(text, x, y);
		return y + 50;
	}
}
