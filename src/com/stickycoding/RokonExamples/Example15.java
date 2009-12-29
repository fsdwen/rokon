package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.RokonAudio;
import com.stickycoding.Rokon.SoundFile;

/**
 * @author Richard
 * Outputs the Accelerometer coordinates through Debug, and plays a sound when a shake is detected
 */
public class Example15 extends RokonActivity {
	
	public RokonAudio audio;
	public SoundFile gunshot;
		
    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    	startAccelerometer();
    }

	public void onLoad() {
        audio = new RokonAudio();
		gunshot = audio.createSoundFile("audio/gunshot.wav");
	}
	
	public void onAccelerometerShake(float intensity) {
		Debug.print("SHAKE " + intensity);
		gunshot.play();
	}
	
	public void onAccelerometerChanged(float x, float y, float z) {
		Debug.print("X=" + x + " Y=" + y + " Z=" + z);
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}