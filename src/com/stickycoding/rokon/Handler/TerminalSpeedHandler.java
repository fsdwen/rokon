package com.stickycoding.rokon.Handler;

import com.stickycoding.rokon.DynamicObject;

/**
 * TerminalSpeedHandler.java
 * An interface which is used to handle a DynamicObjects arrival at terminal speed
 * 
 * @author Richard
 */
public interface TerminalSpeedHandler {
	
	public static final int X = 0, Y = 1;
	
	/**
	 * Called when terminal speed is reached in either the X or Y direction
	 * 
	 * @param dynamicObject the DynamicObject which reached its terminal speed
	 * @param axis 0 for x, 1 for y
	 */
	void onTerminalSpeed(DynamicObject dynamicObject, int axis);
}
