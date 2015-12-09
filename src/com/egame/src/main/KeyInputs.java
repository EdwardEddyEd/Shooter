package com.egame.src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInputs extends KeyAdapter{
	
	public class Key{
		private boolean pressedDown;			// This variable determines whether the key being pressed or not.
		private boolean registered = false;		// This variable keeps track if the variable has already been registered by the program. Only used if allowKeyHold == false.
		private boolean allowKeyHold = true;	// If allowKeyHold == true, the key is allowed to be held.
		
		public Key(){
			keys.add(this);		// Add this key to the list of other keys.
		}
		
		/* If the key has just been pressed, pressedDown == true. 
		 * If the key has just been released, pressedDown == false. */
		public void toggle(boolean pressed){
			if(pressedDown != pressed){
				pressedDown = pressed;
			}
		}

		public boolean getPressed(){
			if(allowKeyHold)
				return pressedDown;
			
			return pressedDown && !registered;
		}

		public void tick(){
			if(allowKeyHold)
				return;
			
			if(pressedDown && !registered)
				registered = true;
			else if(!pressedDown && registered){
				registered = false;
			}
		}
		
		public void setKeyHold(boolean keyHold){
			allowKeyHold = keyHold;
		}
	}
	
	public ArrayList<Key> keys = new ArrayList<Key>();			// This holds all key actions.

	// Public access to any keys 
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key numpad1 = new Key();
	public Key numpad2 = new Key();
	public Key numpad3 = new Key();
	public Key numpad4 = new Key();
	public Key numpad5 = new Key();
	public Key numpad6 = new Key();
	
	public Key w = new Key();
	public Key s = new Key();
	public Key a = new Key();
	public Key d = new Key();
	public Key f = new Key();
	public Key g = new Key();
	public Key h = new Key();
	public Key j = new Key();
	public Key t = new Key();
	public Key y = new Key();
	
	public Key enter = new Key();
	public Key p = new Key();
	
	public KeyInputs(Game game){
		game.addKeyListener(this);
		
		numpad1.setKeyHold(false);
		numpad2.setKeyHold(false);
		numpad3.setKeyHold(false);
		numpad4.setKeyHold(false);
		numpad5.setKeyHold(false);
		numpad6.setKeyHold(false);
		
		f.setKeyHold(false);
		g.setKeyHold(false);
		h.setKeyHold(false);
		j.setKeyHold(false);
		t.setKeyHold(false);
		y.setKeyHold(false);
		
		enter.setKeyHold(false);
		p.setKeyHold(false);
	}
	
	public void releaseAll(){
		for(Key k: keys)
			k.pressedDown = false;
	}
	
	public void tick(){	
		for(Key key: keys){
			key.tick();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		toggle(e, true);						// Passing true because the key was pressed.
	}
	
	public void keyReleased(KeyEvent e) {
		toggle(e, false);						// Passing false because the key is no longer pressed.
	}

	/**
	 * This method will take care of assigning specific keys to do specific functions
	 */
	public void toggle(KeyEvent e, boolean pressed){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP) 	   up.toggle(pressed);
		if(key == KeyEvent.VK_DOWN)    down.toggle(pressed); 
		if(key == KeyEvent.VK_LEFT)    left.toggle(pressed);
		if(key == KeyEvent.VK_RIGHT)   right.toggle(pressed); 
		if(key == KeyEvent.VK_NUMPAD1) numpad1.toggle(pressed); 
		if(key == KeyEvent.VK_NUMPAD2) numpad2.toggle(pressed); 
		if(key == KeyEvent.VK_NUMPAD3) numpad3.toggle(pressed); 
		if(key == KeyEvent.VK_NUMPAD4) numpad4.toggle(pressed); 
		if(key == KeyEvent.VK_NUMPAD5) numpad5.toggle(pressed);
		if(key == KeyEvent.VK_NUMPAD6) numpad6.toggle(pressed);
		
		if(key == KeyEvent.VK_W) w.toggle(pressed);
		if(key == KeyEvent.VK_S) s.toggle(pressed); 
		if(key == KeyEvent.VK_A) a.toggle(pressed);
		if(key == KeyEvent.VK_D) d.toggle(pressed); 
		if(key == KeyEvent.VK_F) f.toggle(pressed);
		if(key == KeyEvent.VK_G) g.toggle(pressed); 
		if(key == KeyEvent.VK_H) h.toggle(pressed); 
		if(key == KeyEvent.VK_J) j.toggle(pressed); 
		if(key == KeyEvent.VK_T) t.toggle(pressed);
		if(key == KeyEvent.VK_Y) y.toggle(pressed);
		
		if(key == KeyEvent.VK_ENTER) enter.toggle(pressed);
		if(key == KeyEvent.VK_P) 	 p.toggle(pressed);
	}
}
