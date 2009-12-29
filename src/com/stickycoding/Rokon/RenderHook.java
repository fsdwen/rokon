package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * RenderHook's give you the ability to access, and modify OpenGL directly.
 * Currently this is not active
 */
public abstract class RenderHook {
	
	public void onGameLoop() {
		
	}
	
	public void onDrawBackground(GL10 gl) {
		
	}
	
	public void onBeforeDraw(GL10 gl) {
		
	}
	
	public void onDraw(GL10 gl, int layer) {
		
	}
	
	public void onAfterDraw(GL10 gl) {
		
	}
	
}
