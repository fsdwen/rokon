package com.stickycoding.rokon.background;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.rokon.Scene;


/**
 * Background.java
 * A parent class for backgrounds, children will be kept in this package
 * 
 * @author Richard
 */

public class Background {

	public Scene parentScene;
	
	public Scene getParentScene() { 
		return parentScene;
	}
	
	public void onDraw(GL10 gl) { } 

}
