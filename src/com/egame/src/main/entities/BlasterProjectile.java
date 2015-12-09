package com.egame.src.main.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.egame.src.main.Game;
import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.statics.GameMethods;

public class BlasterProjectile extends Projectile{

	private int counter = 90;
	private float opacity;
	private int centerBlastWidth, outerBlastWidth;
	private float theta;
	
	public BlasterProjectile(double x, double y, Byte player, Level level) {
		super(x, y, player, Color.orange, level);
		centerBlastWidth = 0;
		outerBlastWidth = 0;
		opacity = 0f;
		theta = 0;
	}

	public void tick(){
		super.tick();
		
		counter--;
		if(counter > 70){
		}
		else if(counter >= 60){
			if(counter == 69) {
				SoundResources.BLASTER.play();
			}
			opacity = ((counter - 70)/-10f);
			centerBlastWidth = (int) (35 * ((counter - 70)/-10f));
			outerBlastWidth = (int) (50 * ((counter - 70)/-10f));
		}
		else if(counter >= 10){
			theta = (theta + 11.25f)%360;
			centerBlastWidth = 35 + (int)(Math.sin(Math.toRadians(theta)) * 10f);
			outerBlastWidth = 50 + (int)(Math.sin(Math.toRadians(theta)) * 15f);
		}
		else if(counter >= 0){
			opacity = (counter)/10f;
			centerBlastWidth = (int) (35 * (counter/10f));
			outerBlastWidth = (int) (50 * (counter)/10f);
		}
		else{
			x = -200;
			y = -200;
		}
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		GameMethods.setOpacity(g2d, opacity);
		
		Game game = level.getGame();
		
		g2d.setColor(Color.orange);
		g2d.fillRect((int)x - outerBlastWidth, 0, outerBlastWidth * 2, game.getHeight());
		g2d.fillRect(0, (int)y - outerBlastWidth, game.getWidth(), outerBlastWidth * 2);
		
		g2d.setColor(Color.white);
		g2d.fillRect((int)x - centerBlastWidth, 0, centerBlastWidth * 2, game.getHeight());
		g2d.fillRect(0, (int)y - centerBlastWidth, game.getWidth(), centerBlastWidth * 2);
		
		GameMethods.setOpacity(g2d, 1f);
	}
	
	public boolean checkCollision(Player player){
		int centerX = (int) player.getCenterX();
		int centerY = (int) player.getCenterY();
		int radius = player.getRadius();
		
		int x[] = new int[4];
		int y[] = new int[4];
		
		x[0] = centerX - radius;
		x[1] = centerX - radius;
		x[2] = centerX + radius;
		x[3] = centerX + radius;
		
		y[0] = centerY - radius;
		y[1] = centerY + radius;
		y[2] = centerY - radius;
		y[3] = centerY + radius;
		
		for(int i = 0; i < x.length; i++){
			if(x[i] > (int)this.x - outerBlastWidth && x[i] < this.x + outerBlastWidth) return true;
			if(y[i] > (int)this.y - outerBlastWidth && y[i] < this.y + outerBlastWidth) return true;
		}
		return false;
	}
	
	public double getCenterX(){
		return x;
	}
	
	public double getCenterY(){
		return y;
	}
}
