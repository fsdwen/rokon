package com.stickycoding.Rokon;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * RokonMusic plays one sound file, continuously or otherwise.
 * It is suitable for background music, and should not be used for
 * sound effects in game.
 * 
 * MediaPlayer is used.
 * 
 * @author Richard
 */
public class RokonMusic {
	
	private static MediaPlayer _mediaPlayer;
	
	/**
	 * Begins playing a sound file in the background
	 * @param filename path to a sound file in the /assets/ folder of your APK
	 * @param continuous TRUE if the sound is to loop indefinately
	 */
	public static void play(String filename, boolean continuous) {
		if(_mediaPlayer != null)
			_mediaPlayer.release();
		try {
			_mediaPlayer = new MediaPlayer();
			AssetFileDescriptor afd = Rokon.getRokon().getActivity().getAssets().openFd(filename);
			_mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			_mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			_mediaPlayer.prepare();
			_mediaPlayer.start();
		} catch (IOException e) {
			Debug.print("CANNOT FIND " + filename + " IN ASSETS");
			e.printStackTrace();
		}
	}
	
	public static boolean isContinuous() {
		return _mediaPlayer.isLooping();
	}
	
	public static boolean isPlaying() {
		return _mediaPlayer.isPlaying();
	}
	
	public static void setContinuous(boolean continuous) {
		_mediaPlayer.setLooping(continuous);
	}
	
	public static void setVolume(float volume) {
		_mediaPlayer.setVolume(volume, volume);
	}
	
	public static void pause() {
		_mediaPlayer.pause();
	}
	
	public static void restart() {
		_mediaPlayer.seekTo(0);
	}
	
	public static void stop() {
		_mediaPlayer.stop();
	}
	
	public static MediaPlayer getMediaPlayer() {
		return _mediaPlayer;
	}
	
	/**
	 * Stops the music, and cleans up the MediaPlayer. Should be called when you are finished with music.
	 */
	public static void end() {
		if(_mediaPlayer.isPlaying())
			_mediaPlayer.stop();
		_mediaPlayer.release();
		_mediaPlayer = null;
	}

}
