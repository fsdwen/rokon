// Audio

package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.AudioStream;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.RokonAudio;
import com.stickycoding.Rokon.SoundFile;

/**
 * @author Richard
 * Shows basic usage of the RokonAudio class, used for sound effects
 * RokonMusic should be used for BGM
 */
public class Example9 extends RokonActivity {
	
	public RokonAudio audio;
	public SoundFile gunshot;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
        audio = new RokonAudio();
        //audio.mute();
		gunshot = audio.createSoundFile("audio/gunshot.wav");
	}

	@Override
	public void onLoadComplete() {

	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onTouchUp(int x, int y, boolean hotspot) {
		AudioStream audioStream = gunshot.play();
		audioStream.setRate((float)(Math.random() * 1.5f) + 0.5f);
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
	
}