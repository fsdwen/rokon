package com.stickycoding.rokon;

/**
 * BlendFunction.java
 * Various static methods and fields, used by the engine
 * 
 * @author Richard
 */

public class Rokon {
	
	protected static BlendFunction blendFunction;
	protected static BufferObject defaultVertexBuffer;
	protected static RokonActivity currentActivity;
	
	/**
	 * Returns the default (1x1 square) Buffer object for normal/VBO drawing
	 * 
	 * @return Buffer
	 */
	public static BufferObject defaultVertexBuffer() {
		return defaultVertexBuffer;
	}
	
	/**
	 * Sets the default BlendFunction to be used when rendering
	 * 
	 * @param blendFunction valid BlendFunction object
	 */
	public static void setBlendFunction(BlendFunction blendFunction) {
		Rokon.blendFunction = blendFunction;
	}
	
	/**
	 * Returns the default BlendFunction to be used when rendering
	 * When no specific BlendFunction is attached to a DrawableObject, this will be used
	 * A default will be used here if setBlendFunction is not used
	 * 
	 * @return the current BlendFunction
	 */
	public static BlendFunction getBlendFunction() {
		return blendFunction;
	}

}
