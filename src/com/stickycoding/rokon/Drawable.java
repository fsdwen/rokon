package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * Drawable.java
 * An interface for objects which are to be in the render queue 
 * Confusion with Androids own Drawable class may be a potential issue, though this being an interface they cannot easily be used accidentally
 *  
 * @author Richard
 */
abstract interface Drawable {
	
	/**
	 * Called each time the Drawable must be rendered
	 * @param gl valid GL10 object in the rendering thread
	 */
	void onDraw(GL10 gl);
	
	/**
	 * Called when the Drawable is added to a Layer
	 * @param layer the Layer which the Drawable has just been added to
	 */
	void onAdd(Layer layer);
	
	/**
	 * Called when the Drawable is removed from a Layer
	 */
	void onRemove();
	
	/**
	 * Called on each loop, before rendering calls
	 */
	void onUpdate();
	
	/**
	 * Determines whether the object is active on a Scene, or has been removed and should be ignored
	 * 
	 * @return TRUE if the object is alive, FALSE otherwise
	 */
	boolean isAlive();
	
	
	/**
	 * Determines whether the object is visible in the current screen
	 * 
	 * @return TRUE if visible, FALSE otherwise
	 */
	boolean isOnScreen();
	
	/**
	 * Determines whether the object is touchable, and therefore should raise an event
	 * 
	 * @return TRUE if touchable, FALSE otherwise
	 */
	boolean isTouchable();
	
	/**
	 * Fetches the name for this Drawable
	 * 
	 * @return NULL if not set
	 */
	String getName();
	
	/**
	 * Fetches the X coordinate of a Drawable
	 * 
	 * @return x-coordinate of Drawable
	 */
	float getX();
	
	/**
	 * Fetches the Y coordinate of a Drawable
	 * 
	 * @return y-coordinate of Drawable
	 */
	float getY();
	
	/**
	 * Fetches the width of a Drawable
	 * 
	 * @return width of Drawable
	 */
	float getWidth();
	
	/**
	 * Fetches the height of a Drawable
	 * 
	 * @return height of Drawable
	 */
	float getHeight();

}
