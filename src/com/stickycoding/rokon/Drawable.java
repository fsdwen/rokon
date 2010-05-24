package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;


/**
 * Drawable.java
 * An interface for objects which are to be in the render queue 
 * Confusion with Androids own Drawable class may be a potential issue, though this being an interface they cannot easily be used accidentally
 *  
 * @author Richard
 */

public interface Drawable {
	
	void onDraw(GL10 gl);

}
