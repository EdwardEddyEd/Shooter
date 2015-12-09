package com.egame.src.main.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.egame.src.main.entities.Player;
import com.egame.src.main.lib.FontResources;

public class HealthDisplay extends Display{
	
	public static final int WIDTH = 70;
	public static final int HEIGHT = 14;
	
	public HealthDisplay(Player player) {
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
		
		x = player.getCenterX() - WIDTH/2;
		y = player.getCenterY()	- HEIGHT - (Math.sin((Math.PI/2)*((double)movementCounter/15f)) * 25) + 10;
		//y = player.getCenterY() - HEIGHT - movementCounter;			// Linear Movement
	}

	public void render(Graphics2D g2d) {
		// Determine Opacity
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, movementCounter/15f));
		
		// Color of the Background of the Health Bar
		g2d.setColor(Color.black);
		g2d.fillRect((int)x, (int)y, WIDTH, HEIGHT);
		
		// Color of the empty Health Bar
		g2d.setColor(Color.gray);
		g2d.fillRect((int)(x + 2), (int)(y + 2), WIDTH - 4, HEIGHT - 4);
		
		// Determining the Color of the filled Health Bar
		if(player.getHealth() >= 90) 	  g2d.setColor(Color.cyan);
		else if(player.getHealth() > 45) g2d.setColor(Color.green);
		else if(player.getHealth() > 15) g2d.setColor(Color.orange);
		else 							  g2d.setColor(Color.red);
		
		// Draw Health
		g2d.fillRect((int)(x + 2), (int)(y + 2), (int)(((double)player.getHealth()/100) * (WIDTH - 4)), HEIGHT - 4);
		
		// Display HP left in Player
		g2d.setFont(FontResources.healthFont);
		g2d.drawString(player.getHealth() + " HP", (int)x, (int)y - 1);
	}

}