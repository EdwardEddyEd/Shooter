package com.egame.src.main.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.sound.sampled.Clip;

import com.egame.src.main.KeyInputs;
import com.egame.src.main.displays.AmmoDisplay;
import com.egame.src.main.displays.HealthDisplay;
import com.egame.src.main.level.Level;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.statics.GameMethods;

public class Player extends Entity{

	private static final int DIAMETER = 20;
	private static final int BORDER_THICKNESS = 8;
	
	private Byte player;
	private Byte lastDirectionalKey = 1;	// 1 = Up; 2 = Down;
	private int health;
	private double speed = 3.0;	
	private Color color;
	
	private double deadX, deadY;
	private boolean dead;
	private int deathCounter = 300;
	private LinkedList<DeathParticle> particles;
	
	private HealthDisplay hDisplay;
	private AmmoDisplay aDisplay;
	
	private int ammo;
	private boolean full = false;
	
	private Level level;
	private KeyInputs keyInput;
	
	public Player(int x, int y, int color, Byte player, KeyInputs keyInput, Level level){
		super(x,y);
		this.color = new Color(color);
		this.player = player;
		this.keyInput = keyInput;
		this.level = level;
		
		this.health = 100;
		this.ammo = 250;
		dead = false;
		hDisplay = new HealthDisplay(this);
		aDisplay = new AmmoDisplay(this);
	}
	
	public void tick() {
		// For collisions against other player
		double previousX = x;
		double previousY = y;
		Player otherPlayer = null;
		
		if(!dead){
			// Input
			if(player == 1){
				if(keyInput.up.getPressed())	  {y -= speed; lastDirectionalKey = 1;}
				if(keyInput.down.getPressed())	  {y += speed; lastDirectionalKey = 2;}
				if(keyInput.left.getPressed())	  {x -= speed;}
				if(keyInput.right.getPressed())	  {x += speed;}
				if(keyInput.numpad1.getPressed()) {Attack1();}
				if(keyInput.numpad2.getPressed()) {Attack2();}
				if(keyInput.numpad3.getPressed()) {Attack3();}
				if(keyInput.numpad4.getPressed()) {Attack4();}
				if(keyInput.numpad5.getPressed()) {Attack5();}
				if(keyInput.numpad6.getPressed()) {Attack6();}
				otherPlayer = level.getPlayer2();
			}
			else if(player == 2){
				if(keyInput.w.getPressed())	{y -= speed; lastDirectionalKey = 1;}
				if(keyInput.s.getPressed())	{y += speed; lastDirectionalKey = 2;}
				if(keyInput.a.getPressed())	{x -= speed;}
				if(keyInput.d.getPressed())	{x += speed;}
				if(keyInput.f.getPressed()) {Attack1();}
				if(keyInput.g.getPressed()) {Attack2();}
				if(keyInput.h.getPressed()) {Attack3();}
				if(keyInput.j.getPressed()) {Attack4();}
				if(keyInput.t.getPressed()) {Attack5();}
				if(keyInput.y.getPressed()) {Attack6();}
				otherPlayer = level.getPlayer1();
			}
			
			// If players collide, do not let them cross over each other
			if(GameMethods.checkCollision(this, otherPlayer)){
				x = previousX;
				y = previousY;
			}
			
			// Boundary of Box
			if(y < BORDER_THICKNESS) y = BORDER_THICKNESS;
			if(y + DIAMETER > level.getGame().getHeight() - BORDER_THICKNESS) y = level.getGame().getHeight() - (BORDER_THICKNESS + DIAMETER);
			if(x < BORDER_THICKNESS) x = BORDER_THICKNESS;
			if(x + DIAMETER > level.getGame().getWidth() - BORDER_THICKNESS) x = level.getGame().getWidth() - (BORDER_THICKNESS + DIAMETER);
		}
		
		else{
			deathCounter--;
			x = deadX + ((Math.random() * 10) - 5);
			y = deadY + ((Math.random() * 10) - 5);
			
			if(deathCounter == 0) level.end();
			if(deathCounter > 0){
				for(int i = 0; i < 3; i++)
					particles.add(new DeathParticle(getCenterX(), getCenterY(), (int)(Math.random() * 30) + 30, color, this));
			}
			else if(deathCounter > -10 || (deathCounter < -20 && deathCounter > -30) || (deathCounter < -40 && deathCounter > -50) || (deathCounter < -60 && deathCounter > -70)){
				for(int i = 0; i < 80; i++)
					particles.add(new DeathParticle(getCenterX(), getCenterY(), (int)(Math.random() * 30) + 30, 3, color, this));
			}
			
			for(int i = 0; i < particles.size(); i++)
				particles.get(i).tick();
		}
		
		// Tick through display
		if(hDisplay.isVisible())
			hDisplay.tick();
		if(aDisplay.isVisible())
			aDisplay.tick();

		speed += 0.0001;		// Gradual Speed Increase
		
		if(ammo >= 1000){
			full = true;
		}
	}
	
	public void render(Graphics2D g2d){
		
		// Render Player
		g2d.setColor(color);
		g2d.fillOval((int)x, (int)y, DIAMETER, DIAMETER);
		
		if(dead){
			for(int i = 0; i < particles.size(); i++)
				particles.get(i).render(g2d);
		}
		
		// Render Displays
		if(hDisplay.isVisible())
			hDisplay.render(g2d);
		if(aDisplay.isVisible())
			aDisplay.render(g2d);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public void addAmmo(){
		aDisplay.triggerDisplay((byte) 0x1);
		ammo++;
	}
	
	public void damagePlayer(int damagePoints){
		health -= damagePoints;
		hDisplay.triggerDisplay((byte) 0x1);
		if(health <= 0){
			deadX = x;
			deadY = y;
			dead = true;
			particles = new LinkedList<DeathParticle>();
			SoundResources.DEATHBUBBLE.loop(Clip.LOOP_CONTINUOUSLY);
			
			level.initiateDeath(player);
		}
	}
	
	/*
	 * Shoots 8 bullets in 8 directions simultaneously
	 */
	public void Attack1(){
		if(ammo < 8)
			return;
		
		for(int i = 0; i < 360; i += 45){
			level.addEntity(new BasicProjectile(x + 5, y + 5, (double)5, (double)i, player, level));
		}
		SoundResources.SHOOT.play();		
		ammo -= 8;
		full = false;
		aDisplay.triggerDisplay((byte) 0x2);
	}
	
	public void Attack2(){
		if(ammo < 1)
			return;
		
		level.addEntity(new SpiralProjectile(x + 5, y + 5, lastDirectionalKey, player, level));
		ammo -= 1;
		full = false;
		aDisplay.triggerDisplay((byte) 0x2);
	}
	
	public void Attack3(){
		if(ammo < 5)
			return;
		
		level.addEntity(new HomingProjectile(x + 5, y + 5, player, level));
		ammo -= 5;
		full = false;
		aDisplay.triggerDisplay((byte) 0x2);
	}
	
	public void Attack4(){
		if(ammo < 7)
			return;
		
		level.addEntity(new CirclingProjectile(x + 5, y + 5, player, level));
		ammo -= 7;
		full = false;
		aDisplay.triggerDisplay((byte) 0x2);
	}
	
	public void Attack5(){
		if(ammo < 50)
			return;
		
		level.addEntity(new Turret(x + 5, y + 5, player, level));
		ammo -= 50;
		full = false;
		aDisplay.triggerDisplay((byte) 0x2);
	}
	
	public void Attack6(){
		if(ammo < 250)
			return;
		
		level.addEntity(new BlasterProjectile(this.getCenterX(), this.getCenterY(), player, level));
		ammo -= 250;
		full = false;
		aDisplay.triggerDisplay((byte) 0x2);
	}
	
	public void addParticle(DeathParticle block){
		particles.add(block);
	}
	
	public void removeParticle(DeathParticle block){
		particles.remove(block);
	}
	
	public int getRadius(){
		return DIAMETER/2;
	}
	
	public double getCenterX(){
		return x + DIAMETER/2;
	}
	
	public double getCenterY(){
		return y + DIAMETER/2;
	}
	
	public Byte getPlayer(){
		return player;
	}
	
	public boolean isFull(){
		return full;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getAmmo(){
		return ammo;
	}
}
