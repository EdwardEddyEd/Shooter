package com.egame.src.main.lib;

import java.awt.Font;

public class FontResources {

	public static Font title;
	public static Font titleOptions;
	public static Font instructionTitle;
	public static Font instruction;
	public static Font keyText;
	
	public static Font countDown;
	
	public static Font ammoFont;
	public static Font healthFont;
	
	public static void initializeFonts(){
		title = new Font(ResourceLoader.fontNames[1], Font.BOLD, 80);
		titleOptions = new Font(ResourceLoader.fontNames[1], Font.BOLD, 50);
		instructionTitle = new Font(ResourceLoader.fontNames[1], Font.BOLD, 55);
		instruction = new Font(ResourceLoader.fontNames[1], Font.PLAIN, 35);
		keyText = new Font(ResourceLoader.fontNames[1], Font.PLAIN, 33);
		
		countDown = new Font(ResourceLoader.fontNames[0], Font.BOLD, 90);
		
		ammoFont = new Font(ResourceLoader.fontNames[0], Font.BOLD, 30);
		healthFont = new Font(ResourceLoader.fontNames[0], Font.PLAIN + Font.ITALIC, 15);
	}
}
