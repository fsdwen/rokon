package com.stickycoding.rokon;

/**
 * Time.java
 * Keeps track of the game time, frames, loops etc.
 * 
 * @author Richard
 */
public class Time {
	
	protected static long drawTicks, drawRealTicks, lastDrawTicks;
	protected static int ticksSinceLastFrame;
	protected static float drawTicksFraction;
	
	protected static long loopTicks, loopRealTicks, lastLoopTicks;
	protected static int ticksSinceLastLoop;
	protected static float loopTicksFraction;

	protected static long pauseStart, pauseTime;
	protected static boolean paused;
	
	protected static void update() {
		lastDrawTicks = drawTicks;
		drawRealTicks = System.currentTimeMillis();
		if(paused) return;
		drawTicks = drawRealTicks - pauseTime;
		if(lastDrawTicks == 0) return;
		ticksSinceLastFrame = (int)(drawTicks - lastDrawTicks);
		drawTicksFraction = ticksSinceLastFrame / 1000f;
	}
	
	protected static void updateLoop() {
		lastLoopTicks = loopTicks;
		loopRealTicks = System.currentTimeMillis();
		if(paused) return;
		loopTicks = loopRealTicks - pauseTime;
		if(lastLoopTicks == 0) return;
		ticksSinceLastLoop = (int)(loopTicks - lastLoopTicks);
		loopTicksFraction = ticksSinceLastLoop / 1000f;
	}
	
	public static long getLoopTicks() {
		return loopTicks;
	}
	
	public static int getTicksSinceLastLoop() {
		return ticksSinceLastLoop;
	}
	
	public static float getLoopTicksFraction() {
		return loopTicksFraction;
	}
	
	public static long getLastLoopTicks() {
		return lastLoopTicks;
	}
	
	/**
	 * Returns the ticks (in milliseconds) for the current frame
	 * 
	 * @return current ticks
	 */
	public static long getDrawTicks() {
		return drawTicks;
	}
	
	/**
	 * Returns the difference in ticks between this frame and the previous
	 * 
	 * @return the difference in ticks 
	 */
	public static int getTicksSinceLastFrame() {
		return ticksSinceLastFrame;
	}
	
	/**
	 * Returns the difference in ticks between this frame and the previous, as a fraction
	 * 
	 * @return a fraction, where 1f = 1000ms
	 */
	public static float getDrawTicksFraction() {
		return drawTicksFraction;
	}
	
	/**
	 * Returns the tick count for the previous frame
	 * 
	 * @return previous ticks
	 */
	public static long getLastDrawTicks() {
		return lastDrawTicks;
	}
	
	/**
	 * Pauses the tick count
	 */
	public static void pause() {
		pauseStart = drawTicks;
		paused = true;
	}
	
	/**
	 * Resumes the tick count
	 */
	public static void resume() {
		pauseTime = System.currentTimeMillis() - pauseStart;
		paused = false;
	}
	
	

}
