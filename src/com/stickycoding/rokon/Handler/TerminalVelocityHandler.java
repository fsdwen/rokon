package com.stickycoding.rokon.Handler;

import com.stickycoding.rokon.DynamicObject;

/**
 * TerminalVelocityHandler.java
 * An interface which is used to handle a DynamicObjects arrival at terminal velocity
 * 
 * @author Richard
 */
public interface TerminalVelocityHandler {
	
	/**
	 * Called when a DynamicObject reaches its terminal velocity
	 * 
	 * @param dynamicObject the DyanmicObject in question
	 */
	void onTerminalVelocity(DynamicObject dynamicObject);

}
