package com.stickycoding.rokon;

import android.util.Log;

/**
 * Debug.java
 * Functions and helpers to aid debugging and engine reports
 * @author Richard
 */
public class Debug {
	
	private static String tag = "Rokon";
	
	public static String getDebugTag() {
		return tag;
	}
	
	/**
	 * Sets the tag to be used in LogCat debug messages
	 * If unset, it will default to "Rokon"
	 * 
	 * @param tag any valid String for LogCat tags
	 */
	public static void setDebugTag(String tag) {
		Debug.tag = tag;
	}
	
	/**
	 * Prints a warning to LogCat with information about the engine warning
	 * 
	 * @param source The source of the warning, such as function name
	 * @param message The message to be passed on
	 */
	public static void warning(String source, String message) {
		Log.w(tag, source + " - " + message);
		Exception e = new Exception(source + " - " + message);
		e.printStackTrace();
	}
	
	/**
	 * Prints a warning to LogCat with information about the engine warning
	 * 
	 * @param message The message to be passed on
	 */
	public static void warning(String message) {
		Log.w(tag, message);
	}
	
	/**
	 * Prints to the verbose stream of LogCat with information from the engine
	 * 
	 * @param message The message to be passed on
	 */
	public static void print(String message) {
		Log.v(tag, message);
	}
	
	/**
	 * Prints to the error stream of LogCat with information from the engine
	 * 
	 * @param message The message to be passed on
	 */
	public static void error(String message) {
		Log.e(tag, message);
		Exception e = new Exception(message);
		e.printStackTrace();
	}

}
