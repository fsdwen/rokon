package com.stickycoding.rokon.Handler;

import com.stickycoding.rokon.DynamicObject;

/**
 * TerminalAngularVelocityHandler.java
 * An interface which handles a DynamicObjects arrival at its terminal angular velocity
 * 
 * @author Richard
 */
public interface TerminalAngularVelocityHandler {
	
	/**
	 * Called when a DynamicObject reaches its terminal angular velocity
	 * 
	 * @param dynamicObject
	 */
	void onTerminalAngularVelocity(DynamicObject dynamicObject);

}
