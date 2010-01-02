package com.stickycoding.Rokon;

/**
 * SoundFile contains sounds which have been loaded into the RokonAudio class.
 * 
 * @author Richard
 */
public class SoundFile {
	
	private int _id;
	private int result;
	private AudioStream audioStream;
	private boolean res;
	
	public SoundFile(int streamId) {
		_id = streamId;
	}
	
	public int getId() {
		return _id;
	}
	
	public void playFast() {
		if(RokonAudio.mute)
			return;
		RokonAudio.singleton.getSoundPool().play(_id, RokonAudio.singleton.getMasterVolume(), RokonAudio.singleton.getMasterVolume(), 1, 0, 1f);
	}
	
	/**
	 * @return the AudioStream through which the sound is playing
	 */
	public AudioStream play() {
		if(RokonAudio.mute)
			return null;
		result = RokonAudio.singleton.getSoundPool().play(_id, RokonAudio.singleton.getMasterVolume(), RokonAudio.singleton.getMasterVolume(), 1, 0, 1f);
		if(result != 0) {
			audioStream = new AudioStream(result, false, RokonAudio.singleton.getMasterVolume());
			return audioStream;
		} else
			return null;
	}
	
	public AudioStream play(float rate, float volume) {
		if(RokonAudio.mute)
			return null;
		result = RokonAudio.singleton.getSoundPool().play(_id, RokonAudio.singleton.getMasterVolume(), RokonAudio.singleton.getMasterVolume(), 1, 0, 1f);
		if(result != 0) {
			audioStream = new AudioStream(result, false, RokonAudio.singleton.getMasterVolume());
			audioStream.setRate(rate);
			audioStream.setVolume(volume);
			return audioStream;
		} else
			return null;
	}
	
	/**
	 * @return the AudioStream through which the sound is playing
	 */
	public AudioStream playContinuous() {
		if(RokonAudio.mute)
			return null;
		int result = RokonAudio.singleton.getSoundPool().play(_id, RokonAudio.singleton.getMasterVolume(), RokonAudio.singleton.getMasterVolume(), 0, -1, 1);
		if(result != 0) {
			audioStream = new AudioStream(result, true, RokonAudio.singleton.getMasterVolume());
			return audioStream;
		} else
			return null;
	}
	
	/**
	 * Removes this file from the memory. Should be used when this file is no longer needed.
	 */
	public void unload() {
		res = RokonAudio.singleton.getSoundPool().unload(_id);
		Debug.print("Unloading " + _id + " " + res);
	}
}
