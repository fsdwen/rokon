package com.stickycoding.rokon.audio;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.Rokon;

/**
 * RokonMusic.java
 * A class optimized for longer audio files - using MediaPlayer
 * Shouldn't be used for sound effects, use RokonAudio for that.
 * 
 * If wanting to use callbacks (for things like onCompletion
 * use the standard MediaPlayer listeners. See MediaPlayer for
 * information - use RokonMusic.getMediaPlayer() to get the object.
 * 
 * @author Richard
 */

public class RokonMusic {
	
	protected static MediaPlayer mediaPlayer;
	protected static boolean mustResume = false;
	
	private static void prepareMediaPlayer() {
		if(mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
		}
	}
	
	public static void play(String file) {
		play(file, false);
	}

	public static void play(String file, boolean loop) {
		prepareMediaPlayer();
		mediaPlayer.setLooping(loop);
		AssetFileDescriptor afd = null;
		try {
			afd = Rokon.getActivity().getAssets().openFd(file);
		} catch (Exception e) { 
			e.printStackTrace();
			Debug.error("Tried creating RokonMusic with missing asset ... " + file);
			Debug.forceExit();
			return;
		}
		try {
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			afd.close();
		} catch (Exception e) {
			e.printStackTrace();
			Debug.error("Error setting data source in RokonMusic.play");
			Debug.forceExit();
			return;
		}
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.prepare();	
		} catch (Exception e) { 
			e.printStackTrace();
			Debug.error("Error preparing MediaPlayer");
			Debug.forceExit();
			return;
		}
		mediaPlayer.start();
	}
	
	public static void play() {
		if(mediaPlayer == null) return;
		mediaPlayer.start();
	}
	
	public static void stop() {
		if(mediaPlayer == null) return;
		mediaPlayer.stop();
	}
	
	public static void pause() {
		if(mediaPlayer == null) return;
		mediaPlayer.pause();
	}
	
	public static void onPause() {
		if(mediaPlayer == null) return;
		if(mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			mustResume = true;
		}
	}
	
	public static void onResume() {
		if(mediaPlayer == null) return;
		if(mustResume) {
			mediaPlayer.start();
			mustResume = false;
		}
	}
	
	
	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	
}
