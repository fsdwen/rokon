package com.stickycoding.rokon;

/**
 * FPSCounter.java
 * Contains static methods used to count frame rate, and profile the performance of the engine
 * 
 * @author Richard
 */
public class FPSCounter {
	
	public static final int FPS_AVG = 5;
	
	protected static int frameCount = 0;
	protected static long secondStart = 0;
	
	protected static void onFrame() {
		if(secondStart == 0) {
			secondStart = Time.ticks;
			frameCount++;
			return;
		}
		frameCount++;
		if(Time.ticks - secondStart >= 1000 * FPS_AVG) {
			secondStart += 1000 * FPS_AVG;
			Debug.print("FPS " + (int)(frameCount / FPS_AVG));
			frameCount = 0;
			return;
		}
	}
	

}
