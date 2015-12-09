package com.egame.src.main.lib;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundResources {

	public static Clip TITLESONG;
	public static Clip GAMESONG;

	public static MultiSoundClip BLASTER;
	public static MultiSoundClip COLLECT;
	public static MultiSoundClip COUNTDOWN;
	public static Clip 			 DEATHBUBBLE;
	public static Clip 			 FINALDEATH;
	public static Clip 			 GO;
	public static Clip 			 SELECTED;
	public static MultiSoundClip SHOOT;

	public static void initializeSounds() {
		int i = 0;
		TITLESONG = getClip(ResourceLoader.soundPaths[i++], -20.0f);
		GAMESONG = getClip(ResourceLoader.soundPaths[i++], -20.0f);

		BLASTER = getMultiSoundClip(ResourceLoader.soundPaths[i++], -7f, 5);
		COLLECT = getMultiSoundClip(ResourceLoader.soundPaths[i++], -15f, 30);
		COUNTDOWN = getMultiSoundClip(ResourceLoader.soundPaths[i++], -10.0f, 2);
		DEATHBUBBLE = getClip(ResourceLoader.soundPaths[i++], -15f);
		FINALDEATH = getClip(ResourceLoader.soundPaths[i++], -10.0f);
		GO = getClip(ResourceLoader.soundPaths[i++], -10.0f);
		SELECTED = getClip(ResourceLoader.soundPaths[i++], -5.0f);
		SHOOT = getMultiSoundClip(ResourceLoader.soundPaths[i++], -5f, 30);
	}

	public static Clip getClip(String path, float volumeAdjustment) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundResources.class.getResource(path));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volumeAdjustment);
			return clip;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static MultiSoundClip getMultiSoundClip(String path, float volumeAdjustment, int numOfClips){
		return new MultiSoundClip(path, volumeAdjustment, numOfClips);
	}
}
