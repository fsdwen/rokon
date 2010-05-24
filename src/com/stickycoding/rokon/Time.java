package com.stickycoding.rokon;

/**
 * Time.java
 * Keeps track of the game time, frames, loops etc.
 * 
 * @author Richard
 */
public class Time {
	
	protected static long ticks;
	protected static int ticksSinceLastFrame;
	protected static float ticksFraction;
	protected static long lastTicks;
	
	protected static void update() {
		lastTicks = ticks;
		ticks = System.currentTimeMillis();
		if(lastTicks == 0) {
			return;
		}
		ticksSinceLastFrame = (int)(ticks - lastTicks);
		ticksFraction = ticksSinceLastFrame / 1000f;
	}
	
	public static long getTicks() {
		return ticks;
	}
	
	public static int getTicksSinceLastFrame() {
		return ticksSinceLastFrame;
	}
	
	public static float getTicksFraction() {
		return ticksFraction;
	}
	
	public static long getLastTicks() {
		return lastTicks;
	}

}
