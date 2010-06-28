package com.stickycoding.rokon;

/**
 * DrawQueue.java
 * 
 * Stores the type and list of draw queue for each layer
 * @author Richard
 */
public class DrawQueue {
	
	//TODO Implement this for 2.0.0
	
	/**
	 * The method for choosing render order
	 */
	public static final int FASTEST = 0, X_ASCENDING = 1, X_DESCENDING = 2, Y_ASCENDING = 3, Y_DESCENDING = 4;
	
	protected int method = FASTEST;

}
