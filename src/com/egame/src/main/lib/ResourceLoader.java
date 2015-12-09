package com.egame.src.main.lib;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class ResourceLoader {

	protected static final String[] fontNames = {"SquareFont", "Arcadepix"};
	protected static final String[] fontPaths = {"com/egame/src/main/lib/fonts/Square.ttf", 
										  		 "com/egame/src/main/lib/fonts/ARCADEPI.TTF"};
	protected static final String[] soundPaths = {"/sounds/SharionTitle.wav",
												  "/sounds/StreetLights.wav",
												  "/sounds/Blaster.wav",
												  "/sounds/Collect.wav",
												  "/sounds/CountDown.wav",
												  "/sounds/DeathBubble.wav",
												  "/sounds/FinalDeath.wav",
												  "/sounds/GO.wav",
												  "/sounds/Selected.wav",
												  "/sounds/Shoot.wav"};
	
	public static void loadResources(){
		// Loading Fonts
		for(int i = 0; i < fontPaths.length; i++)
			loadFonts(fontPaths[i]);
		
		// Initialize Fonts
		FontResources.initializeFonts();
		
		// Initialize Sounds
		SoundResources.initializeSounds();
	}
	
	public static void loadFonts(String path){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.class.getClassLoader().getResourceAsStream(path)));
		} catch (IOException|FontFormatException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
