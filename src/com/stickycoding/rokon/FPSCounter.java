package com.stickycoding.rokon;

/**
 * FPSCounter.java
 * Contains protected static methods used to count frame rate, and profile the performance of the engine
 * No good for end-user
 * 
 * @author Richard
 */
public class FPSCounter {
	
	protected static final int FPS_AVG = 5;
	
	protected static int frameCount = 0;
	protected static long secondStart = 0;
	
	protected static int loopCount = 0;
	protected static long loopSecondStart = 0;
	
	protected static void onFrame() {
		if(secondStart == 0) {
			secondStart = Time.drawTicks;
			frameCount++;
			return;
		}
		frameCount++;
		if(Time.drawTicks - secondStart >= 1000 * FPS_AVG) {
			secondStart += 1000 * FPS_AVG;
			Debug.print("Render FPS " + (int)(frameCount / FPS_AVG));
			frameCount = 0;
			return;
		}
	}
	
	protected static void onLoop() {
		if(loopSecondStart == 0) {
			loopSecondStart = Time.loopTicks;
			loopCount++;
			return;
		}
		loopCount++;
		if(Time.loopTicks - loopSecondStart >= 1000 * FPS_AVG) {
			loopSecondStart += 1000 * FPS_AVG;
			Debug.print("Logic FPS " + (int)(loopCount / FPS_AVG));
			loopCount = 0;
			return;
		}
	}
	

}
