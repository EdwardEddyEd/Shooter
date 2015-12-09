package com.egame.src.main.lib;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MultiSoundClip {
	private ArrayList<Clip> clips;
	private int currentClip;
	
	public MultiSoundClip(String path, float volumeAdjustment, int numOfClips){
		clips = new ArrayList<Clip>();
		
		try {
			for(int i = 0; i < numOfClips; i++){
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundResources.class.getResource(path));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);

				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volumeAdjustment);
				clips.add(clip);
				
				audioInputStream.close();
			}
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
		currentClip = 0;
	}
	
	public void play(){
		Clip clip = clips.get(currentClip);
		clip.stop();
		clip.setFramePosition(0);
		clip.start();
		
		currentClip = (currentClip + 1 + clips.size()) % clips.size();
	}
	
	public void stopAll(){
		for(Clip clip: clips){
			clip.stop();
			clip.setFramePosition(0);
		}
	}
}
