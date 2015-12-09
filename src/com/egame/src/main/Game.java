package com.egame.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import com.egame.src.main.level.Level;
import com.egame.src.main.lib.ResourceLoader;
import com.egame.src.main.lib.SoundResources;
import com.egame.src.main.screens.SlideTransition;
import com.egame.src.main.screens.StartMenu;
import com.egame.src.main.statics.GameMethods;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH * 9 / 12;	// 4:3 frame ratio
	public static final String TITLE = "Shooter";
	
	public SlideTransition transition;
	private StartMenu sMenu;
	private Level level;
	private KeyInputs keyInputs;
	
	private boolean running = false;
	private Byte changeState;				// 1 = From StartMenu to Game; 2 = From Game to StartMenu
	private int transitionCounter = 0;
	
	public static enum STATE{
		STARTMENU,
		INGAME;
	}
	private STATE state = STATE.STARTMENU;
	
	
	public void start(){
		running = true;
		new Thread(this).start();
	}

	public void stop(){
		running = false;
		System.exit(1);
	}
	
	private void init(){
		ResourceLoader.loadResources();
		keyInputs = new KeyInputs(this);
		
		SoundResources.TITLESONG.loop(Clip.LOOP_CONTINUOUSLY);
		
		sMenu = new StartMenu(this, keyInputs);
		sMenu.init((Graphics2D) this.getGraphics());
		
		transition = new SlideTransition(this, 15, 2);
		
		this.requestFocus();
	}
	
	public void startChangeState(Byte changeTo){
		changeState = changeTo;
		removeKeyListener(keyInputs);
		transition.activateTransition();
	}
	
	public void enterGame(){
		level = new Level(this);
		level.init((Graphics2D) this.getGraphics());
		sMenu = null;
		state = STATE.INGAME;
				
		SoundResources.TITLESONG.stop();
		SoundResources.TITLESONG.setFramePosition(0);
		if(!SoundResources.GAMESONG.isActive())
			SoundResources.GAMESONG.loop(Clip.LOOP_CONTINUOUSLY);
		
		transition.activateSecondTransition();
	}
	
	public void enterStartMenu(){
		sMenu = new StartMenu(this, keyInputs);
		sMenu.init((Graphics2D) this.getGraphics());
		level = null;
		state = STATE.STARTMENU;
		addKeyListener(keyInputs);
				
		SoundResources.GAMESONG.stop();
		SoundResources.GAMESONG.setFramePosition(0);
		SoundResources.TITLESONG.loop(Clip.LOOP_CONTINUOUSLY);
		
		transition.activateSecondTransition();
	}
	
	public void run() {

		init();										// Grab our resources, such as sprite sheets
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;			// "Frames per second"
		double ns = 1000000000 / amountOfTicks;		// Number of nanoseconds spent in each frame
		double delta = 0;							// Delta serves as a counter. It's keeping track of when enough time has passed to constitute a tick, or a frame. Once it is surpassed, then the world is updated.
		int updates = 0;							// Keeps track of how many ticks have passed
		int frames = 0;								// Keeps track of how many times the loop has been ran for a second.
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			// If a frame has passed, update the game environment.
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			// If a second has passed by, then print to the console some info.
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames + ", " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000000 + "MB memory used");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	private void tick(){
		if(state == STATE.INGAME)
			level.tick();
		else if(state == STATE.STARTMENU)
			sMenu.tick();
		
		if(!transition.isComplete()){
			transition.tick();
			if(transition.isFinishedMoving() && changeState == 1){
				if(transitionCounter == 0){
					enterGame();
					transitionCounter = 1;
				}

			}
			else if(transition.isFinishedMoving() && changeState == 2){
				if(transitionCounter == 0){
					enterStartMenu();
					transitionCounter = 1;
				}
			}
		}
		else{
			transitionCounter = 0;
		}

		keyInputs.tick();
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		
		// Create out new BufferStrategy if we start the game
		if(bs == null){	
			createBufferStrategy(3);		// This creates 3 Buffers - Triple Buffering
			return;	
		}
		
		Graphics g = bs.getDrawGraphics();	// Creates a graphics context in order to draw out Buffers
		Graphics2D g2d = (Graphics2D) g;
		GameMethods.turnOnAntiAlias(g2d);
		
		// Graphics
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if(state == STATE.INGAME)
			level.render(g);
		else if(state == STATE.STARTMENU)
			sMenu.render(g2d);
		
		if(!transition.isComplete()){
			transition.render(g2d);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		JFrame frame = new JFrame(Game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start(); 			// Starts the whole game loop
	}
	
	public KeyInputs getKeyInput(){
		return keyInputs;
		
	}
}
