package com.egame.src.main.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.entities.Player;
import com.egame.src.main.lib.FontResources;
import com.egame.src.main.statics.GameMethods;

public class AmmoDisplay extends Display{
	
	private int addCounter = 0;
	private int lossCounter = 0;
	
	public AmmoDisplay(Player player) {
		super(player);
	}

	public void tick() {
		// Determining when the display should be rendered
		if(triggered && movementCounter < 15){				// Moving display to position
			movementCounter++;
		}
		else if(displayCounter < 120){						// Showing display at designated position
			displayCounter++;
			if(displayCounter >= 120)
				completedDisplay = true;
		}
		else if(completedDisplay && movementCounter > 0){	// Removing display from position
			triggered = false;
			movementCounter--;
			if(movementCounter == 0)
				visible = false;
		}
		
		// X-coordinate is determined in the render() method
		y = player.getCenterY()	+ (Math.sin((Math.PI/2)*((double)movementCounter/15f)) * 25) + 8;
		
		// Decrement Color of text
		if(addCounter > 0)
			addCounter--;					// TODO: Make colorCounter slower
		else if(lossCounter > 0)			// TODO: Add outline to text
			lossCounter--;
	}
	
	public void triggerDisplay(Byte event){
		super.triggerDisplay(event);
		
		// If Ammo is being added, change color of text to green
		if(event == 1){
			addCounter = 15;
			lossCounter = 0;
		}
		
		// If Ammo is being added, change color of text to red
		else if(event == 2){
			addCounter = 0;
			lossCounter = 15;
		}
	}

	public void render(Graphics2D g2d) {
		// Determining Opacity
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, movementCounter/15f));
		
		// Determining X-coordinate using the center of the player and length of the string
		String ammo = player.getAmmo() + "";
		g2d.setFont(FontResources.ammoFont);													// Set Font	
		int stringLength = (int) GameMethods.getStringLength(ammo, g2d);						// Get the String Length for a specific Font
		x = player.getCenterX() - stringLength/2;												// Find the starting point to draw the string for the x-coordinate		
		
		// Color text
		if(addCounter > 0) 		 g2d.setColor(new Color(0x00, 17 * addCounter, 0x00));
		else if(lossCounter > 0) g2d.setColor(new Color(17 * lossCounter, 0x00, 0x00));
		else				 	 g2d.setColor(Color.black);
		
		GameMethods.turnOffAntiAlias(g2d);
		g2d.drawString(ammo, (int)x, (int)y);
		GameMethods.turnOnAntiAlias(g2d);

	}
}
