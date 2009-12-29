package com.stickycoding.Rokon;

import android.util.Log;


/**
 * Handles LogCat output, and timing events for testing
 * @author Richard
 */
public class Debug {
	
	public static final boolean DEBUG_MODE = true;
	
	/**
	 * @param msg string to be printed to LogCat
	 */
	public static void print(String msg) {
		if(DEBUG_MODE)
			Log.v("Rokon", msg);
	}
	
	private static long startTime = 0;
	private static long lastInterval = 0;
	/**
	 * Starts a timer to record how long it takes for certain events to take place
	 */
	public static void startTimer() {
		if(DEBUG_MODE) {
			startTime = System.currentTimeMillis();
			lastInterval = startTime;
		}
	}
	
	/**
	 * Debugs the timer, outputting time difference from startTimer()
	 * @param message prefix message for LogCat
	 */
	public static void debugTimer(String message) {
		if(DEBUG_MODE) {
			long diff = System.currentTimeMillis() - startTime;
			Debug.print(message + " took " + diff + "ms");
		}
	}
	
	/**
	 * Debugs the timer, outputting time difference from startTimer()
	 */
	public static void debugTimer() {
		if(DEBUG_MODE) {
			long diff = System.currentTimeMillis() - startTime;
			Debug.print("Took " + diff + "ms");
		}
	}
	
	/**
	 * Debugs the timer, outputting time difference from the last interval (startTimer() if none have been called before)
	 * @param message prefix message for LogCat
	 */
	public static void debugInterval(String message) {
		if(DEBUG_MODE) {
			long diff = System.currentTimeMillis() - lastInterval;
			Debug.print(message + " interval took " + diff + "ms");
			lastInterval = System.currentTimeMillis();
		}
	}
	
	/**
	 * Debugs the timer, outputting time difference from the last interval (startTimer() if none have been called before)
	 */
	public static void debugInterval() {
		if(DEBUG_MODE) {
			long diff = System.currentTimeMillis() - lastInterval;
			Debug.print("Interval took " + diff + "ms");
			lastInterval = System.currentTimeMillis();
		}
	}
	
	/**
	 * Prints a warning to LogCat, regardless of being in debug mode or not
	 * @param warning message to be passed on to LogCat
	 */
	public static void warning(String warning) {
		Log.e("Rokon", "ROKON WARNING : " + warning);
		(new Exception("ROKON WARNING : " + warning)).printStackTrace();
	}

}
