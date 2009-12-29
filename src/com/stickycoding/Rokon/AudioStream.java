package com.stickycoding.Rokon;

/**
 * Handles a stream of sound, launched through RokonAudio.
 * Each time a SoundFile is played it is given a unique AudioStream
 * @author Richard
 */
public class AudioStream {
	
	private float _volume;
	private int _id;
	private boolean _paused;
	private boolean _continuous;
	private float _rate;
	
	/**
	 * Creates the AudioStream
	 * @param id the streamId
	 * @param continuous TRUE if looping indefinately
	 * @param volume 0.0 to 1.0
	 */
	public AudioStream(int id, boolean continuous, float volume) {
		_id = id;
		_continuous = continuous;
		_volume = volume;
		_rate = 1;
	}
	
	/**
	 * @return TRUE if the AudioStream is paused
	 */
	public boolean isPaused() {
		return _paused;
	}
	
	/**
	 * @return The raw streamId from the SoundPool interface
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * Pauses the AudioStream, it must be started again with resume()
	 */
	public void pause() {
		_paused = true;
		RokonAudio.singleton.getSoundPool().pause(_id);
	}
	
	/**
	 * Resumes a paused AudioStream
	 */
	public void resume() {
		_paused = false;
		RokonAudio.singleton.getSoundPool().resume(_id);
	}
	
	/**
	 * Stops an AudioStream from being played, it cannot be restarted
	 */
	public void stop() {
		RokonAudio.singleton.getSoundPool().stop(_id);
	}
	
	/**
	 * Defines whether the AudioStream is looping indefinately
	 * @param continuous TRUE if indefinate looping
	 */
	public void setContinuous(boolean continuous) {
		if(continuous) 
			RokonAudio.singleton.getSoundPool().setLoop(_id, -1);
		else
			RokonAudio.singleton.getSoundPool().setLoop(_id, 0);
		_continuous = continuous;
	}
	
	/**
	 * @return TRUE if the AudioStream is looping indefinately
	 */
	public boolean isContinuous() {
		return _continuous;
	}
	
	/**
	 * Sets the volume of this particular AudioStream
	 * @param volume 0.0 to 1.0
	 */
	public void setVolume(float volume) {
		_volume = volume;
		RokonAudio.singleton.getSoundPool().setVolume(_id, _volume, _volume);
	}
	
	/**
	 * @return 0.0 to 1.0 the volume of this AudioStream
	 */
	public float getVolume() {
		return _volume;
	}
	
	/**
	 * Sets the rate at which the AudioStream is played, slow normal or fast.
	 * @param rate 0.5 to 2.0
	 */
	public void setRate(float rate) {
		_rate = rate;
		RokonAudio.singleton.getSoundPool().setRate(_id, _rate);
	}
	
	/**
	 * Returns the rate at which the AudioStream is playing
	 * @return 0.5 to 2.0
	 */
	public float getRate() {
		return _rate;
	}

}
