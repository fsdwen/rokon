package com.stickycoding.rokon.Handler;

import com.stickycoding.rokon.DynamicObject;

/**
 * MoveToHandler.java
 * An interface which handles events when a DynamicObject reaches its destination from a moveTo command
 *
 * @author Richard
 *
 */
public class ObjectHandler extends Handler {

	/**
	 * Triggered when the DynamicObject reaches its destination
	 * 
	 * @param dynamicObject
	 */
	public void onComplete(DynamicObject dynamicObject) {}
	
	/**
	 * Triggered when the DynamicObject reaches its destination
	 */
	public void onComplete() {}
	
	/**
	 * Triggered when the DynamicObject is subject to another moveTo
	 * This will not be triggered when basic (setVelocity etc.) methods are called
	 * because the move will still continue on the next frame in this situation
	 * 
	 * @param dynamicObject the DynamicObject attached to the handler
	 */
	public void onCancel(DynamicObject dynamicObject) {}

	/**
	 * Triggered when the DynamicObject is subject to another moveTo
	 * This will not be triggered when basic (setVelocity etc.) methods are called
	 * because the move will still continue on the next frame in this situation
	 */
	public void onCancel() {}

}
