package com.egame.src.main.level;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.egame.src.main.Game;
import com.egame.src.main.entities.Ammo;
import com.egame.src.main.entities.BlasterProjectile;
import com.egame.src.main.entities.Player;
import com.egame.src.main.entities.Projectile;
import com.egame.src.main.entities.Turret;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.screens.Countdown;
import com.egame.src.main.screens.EndMenu;
import com.egame.src.main.screens.PauseMenu;
import com.egame.src.main.screens.WhiteFade;
import com.egame.src.main.statics.GameMethods;

public class Level {

	private Game game;
	
	private Player player;
	private Player player2;
	
	private BufferedImage background;
	private Countdown countdown;
	private PauseMenu pause;
	private EndMenu end;
	private WhiteFade white;
	
	private boolean deathInitiated;
	private int winner;
	
	private boolean pausable;
	
	private int ammoCounter = 3;
	private int maxAmmo = 300;
	
	private LinkedList<Ammo> ammo;
	private LinkedList<Projectile> projectiles;
	
	private static enum STATE{
		GAME,
		PAUSE,
		END;
	}
	private STATE state = STATE.GAME;
	
	public Level(Game game){
		this.game = game;
	}
	
	public void init(Graphics2D g2d){
		background = BackgroundInit.backgroundOne(game.getWidth(), game.getHeight());
		player = new Player(400, 300, 0xff0000ff, (byte) 0x1, game.getKeyInput(), this);
		player2 = new Player(200, 300, 0xffff0000, (byte) 0x2, game.getKeyInput(), this);
		ammo = new LinkedList<Ammo>();
		projectiles = new LinkedList<Projectile>();
		
		countdown = new Countdown(game);
		pause = new PauseMenu(game, this, game.getKeyInput(), g2d);
		
		pausable = true;
		
		deathInitiated = false;
		
		game.getKeyInput().w.setKeyHold(true);
		game.getKeyInput().s.setKeyHold(true);
		game.getKeyInput().a.setKeyHold(true);
		game.getKeyInput().d.setKeyHold(true);
		
		game.getKeyInput().up.setKeyHold(true);
		game.getKeyInput().down.setKeyHold(true);
		game.getKeyInput().left.setKeyHold(true);
		game.getKeyInput().right.setKeyHold(true);
	}
	
	public void generateAmmo(){
		if(ammo.size() < maxAmmo && ammoCounter <= 0){
			ammo.add(new Ammo(8 + (Math.random() * (game.getWidth() - 24)), 8 + (Math.random() * (game.getHeight() - 24)), this));
			ammoCounter = 3;
		}
		else if(ammoCounter > 0){
			ammoCounter--;
		}
	}
	
	public void backgroundRender(Graphics g){
		g.drawImage(background, 0, 0, game);
	}
	
	public void end(){
		end = new EndMenu(game, this, game.getKeyInput(), (Graphics2D)game.getGraphics(), winner);
		white = new WhiteFade(game, this);
		pausable = false;
		SoundResources.FINALDEATH.setFramePosition(0);
		SoundResources.FINALDEATH.start();
	}
	
	public void goToEndMenu(){
		state = STATE.END;
	}
	
	public void pause(){
		state = STATE.PAUSE;
		pause.turnPauseOn();
	}
	
	public void unpause(){
		state = STATE.GAME;
		pause.turnPauseOff();
	}
	
	public void restart(){
		game.startChangeState((byte) 0x1);
	}
	
	public void goToStartMenu(){
		game.startChangeState((byte) 0x2);
	}
	
	public void initiateDeath(Byte player){
		deathInitiated = true;
		if(player == 1) winner = 2;
		else			winner = 1;
	}
	
	public void tick(){
		if(!game.transition.isComplete()) return;
		if(countdown.isVisible()) countdown.tick();
		
		if(state == STATE.GAME) 	  gameTick();
		else if(state == STATE.PAUSE) pause.tick();
		else if(state == STATE.END)   end.tick();
		
		if(white != null) white.tick();

	}
	
	public void gameTick(){
		if(game.getKeyInput().p.getPressed() && pausable) pause();
		generateAmmo();
		
		// Go through ammo
		for(int i = 0; i < ammo.size(); i++){
			ammo.get(i).tick();
			if(GameMethods.checkCollision(player, ammo.get(i)) && !player.isFull()){
				player.addAmmo();
				SoundResources.COLLECT.play();
				ammo.get(i).deleteSelf();
			}
			else if(GameMethods.checkCollision(player2, ammo.get(i)) && !player2.isFull()){
				player2.addAmmo();
				SoundResources.COLLECT.play();
				ammo.get(i).deleteSelf();
			}
		}

		// Go through projectiles
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).tick();
			if(!deathInitiated){
				if(GameMethods.checkCollision(player, projectiles.get(i)) && GameMethods.isHit(player, projectiles.get(i))){
					if(projectiles.get(i) instanceof Turret){
						Turret turret = (Turret) projectiles.get(i);
						if(!turret.getWasHit()){
							turret.hit();
							player.damagePlayer(5);
							continue;
						}
					}
					else if(projectiles.get(i) instanceof BlasterProjectile){
						player.damagePlayer(1);
						continue;
					}
					else{
						player.damagePlayer(1);
						projectiles.get(i).deleteSelf();
						continue;
					}
				}
				else if(GameMethods.checkCollision(player2, projectiles.get(i)) && GameMethods.isHit(player2, projectiles.get(i))){
					if(projectiles.get(i) instanceof Turret){
						Turret turret = (Turret) projectiles.get(i);
						if(!turret.getWasHit()){
							turret.hit();
							player2.damagePlayer(5);
							continue;
						}
					}
					else if(projectiles.get(i) instanceof BlasterProjectile){
						player2.damagePlayer(1);
						continue;
					}
					else{
						player2.damagePlayer(1);
						projectiles.get(i).deleteSelf();
						continue;
					}
				}
			}
			projectiles.get(i).checkBoundary();
		}
		
		// Update Players
		player.tick();
		player2.tick();
	}
	
	public void render(Graphics g){
		if(state == STATE.GAME) 	  renderGame(g);
		else if(state == STATE.PAUSE) pause.render((Graphics2D)g);
		else if(state == STATE.END)	  end.render((Graphics2D)g);
		
		if(white != null) white.render((Graphics2D)g);
		if(countdown.isVisible()) countdown.render((Graphics2D)g);
	}
	
	public void renderGame(Graphics g){
		backgroundRender(g);
		for(int i = 0; i < ammo.size(); i++){
			ammo.get(i).render(g);
		}
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).render(g);
		}
		
		Graphics2D g2d = (Graphics2D) g;
		player.render(g2d);
		player2.render(g2d);
	}
	
	public void addEntity(Projectile block){
		projectiles.add(block);
	}

	public void removeEntity(Projectile block){
		projectiles.remove(block);
	}
	
	public void addEntity(Ammo block){
		ammo.add(block);
	}
	
	public void removeEntity(Ammo block){
		ammo.remove(block);
	}
	
	public Player getPlayer1(){
		return player;
	}
	
	public Player getPlayer2(){
		return player2;
	}
	
	public Game getGame(){
		return game;
	}
	
}
