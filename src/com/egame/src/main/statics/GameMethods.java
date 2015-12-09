package com.egame.src.main.statics;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.Random;

import com.egame.src.main.entities.Ammo;
import com.egame.src.main.entities.BlasterProjectile;
import com.egame.src.main.entities.Player;
import com.egame.src.main.entities.Projectile;

public class GameMethods {

	private static Random r = new Random();
	
	public static Color getRandomColor(){
		int red = r.nextInt(256) << 16;
		int green = r.nextInt(256) << 8;
		int blue = r.nextInt(256);
		return (new Color(0xff000000 + red + green + blue));
	}
	
	public static int getRandomDiameter(){
		return r.nextInt(5) + 7;
	}
	
	public static boolean isHit(Player player, Projectile projectile){
		return player.getPlayer() != projectile.getPlayer();
	}
	
	public static String[] getStringArray(Graphics2D g2d, String message, Font font, int width){
		g2d.setFont(font);
		String[] allWords = message.split(" ");
		ArrayList<String> wrappedMessage = new ArrayList<String>();
		String previousLine = "";
		
		for(String word: allWords){
			if(width > getStringLength(previousLine + word + " ", g2d))
				previousLine = previousLine + word + " ";
			else{
				wrappedMessage.add(previousLine);
				previousLine = word + " ";
			}
		}
		wrappedMessage.add(previousLine);			// Add last line to String array
		String[] returns = new String[wrappedMessage.size()];
		
		return wrappedMessage.toArray(returns);
	}
	
	public static double getStringLength(String string, Graphics2D g2d){
		return g2d.getFontMetrics().getStringBounds(string, g2d).getWidth();
	}
	
	public static void turnOffAntiAlias(Graphics2D g2d){
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	public static void turnOnAntiAlias(Graphics2D g2d){
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public static void setOpacity(Graphics2D g2d, float opacity){
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}
	
	public static void drawTextOutline(Graphics2D g2d, String text, Color color, float strokeSize, int x, int y, Font font){
		GlyphVector gv = font.createGlyphVector(g2d.getFontRenderContext(), text);
		Shape shape = gv.getOutline();
		g2d.setStroke(new BasicStroke(strokeSize));
		g2d.setColor(color);
		g2d.translate(x, y);
		g2d.draw(shape);  
		g2d.translate(-(x), -(y));
	}
	
	public static boolean checkCollision(Player player, Ammo ammo){
		double minimumDistance = player.getRadius() + ammo.getRadius();
		double currentDistance = Math.sqrt(Math.pow(player.getCenterX() - ammo.getCenterX(), 2) + Math.pow(player.getCenterY() - ammo.getCenterY(), 2));
		if(minimumDistance > currentDistance)
			return true;
		
		return false;
	}
	
	public static boolean checkCollision(Player player, Projectile projectile){
		if(projectile instanceof BlasterProjectile)	return checkCollision(player, (BlasterProjectile) projectile);
		
		double minimumDistance = player.getRadius() + projectile.getRadius();
		double currentDistance = Math.sqrt(Math.pow(player.getCenterX() - projectile.getCenterX(), 2) + Math.pow(player.getCenterY() - projectile.getCenterY(), 2));
		if(minimumDistance > currentDistance)
			return true;
		
		return false;
	}
	
	public static boolean checkCollision(Player player, BlasterProjectile blaster){
		return blaster.checkCollision(player);
	}
	
	public static boolean checkCollision(Player player, Player otherPlayer){
		double minimumDistance = player.getRadius() + otherPlayer.getRadius();
		double currentDistance = Math.sqrt(Math.pow(player.getCenterX() - otherPlayer.getCenterX(), 2) + Math.pow(player.getCenterY() - otherPlayer.getCenterY(), 2));
		if(minimumDistance > currentDistance)
			return true;
		
		return false;
	}
}
