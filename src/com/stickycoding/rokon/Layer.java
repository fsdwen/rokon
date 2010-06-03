package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * Layer.java
 * A Layer is contained inside a Scene, and are drawn in ascending order
 * Each Layer has a many DrawableObjects and one DrawQueue
 * @author Richard
 *
 */
public class Layer {
	
	protected Scene parentScene;
	protected FixedSizeArray<DrawableObject> drawableObjects;	
	protected int maximumDrawableObjects;
	protected DrawQueue drawQueue;
	protected boolean ignoreWindow;
	
	public Layer(Scene parentScene, int maximumDrawableObjects) {
		this.parentScene = parentScene;
		this.maximumDrawableObjects = maximumDrawableObjects;
		drawQueue = new DrawQueue();
		drawableObjects = new FixedSizeArray<DrawableObject>(maximumDrawableObjects);
	}
	
	/**
	 * Tells this layer to ignore the current Scenes Window (if any) and draw straight to the device
	 * Useful for interface layers
	 */
	public void ignoreWindow() {
		ignoreWindow = true;
	}
	
	/**
	 * Tells this layer to use the current Scenes Window (if any)
	 * By default, this is on
	 */
	public void useWindow() {
		ignoreWindow = false;
	}
	
	/**
	 * @return TRUE if this Layer is drawing with respect to the current Scenes Window (if any)
	 */
	public boolean isUsingWindow() {
		return !ignoreWindow;
	}
	
	/**
	 * Returns a DrawableObject from this layer
	 * 
	 * @param index position in the array
	 * @return NULL if not found
	 */
	public DrawableObject getDrawableObject(int index) {
		return drawableObjects.get(index);
	}
	
	/**
	 * @return the current DrawQueue object for this Scene
	 */
	public DrawQueue getDrawQueue() {
		return drawQueue;
	}
	
	/**
	 * Sets the DrawQueue method
	 * Defaults to DrawQueue.FASTEST if unset
	 * @param type Taken from the constants in DrawQueue
	 */
	public void setDrawQueueType(int type) {
		if(type < 0 || type > 4) {
			Debug.warning("Scene.setDrawQueueType", "Tried setting DrawQueue type to " + type + ", defaulting to 0");
			drawQueue.method = DrawQueue.FASTEST;
			return;
		}
		drawQueue.method = type;
	}
	
	/**
	 * Clears all the DrawableObjects off this Layer
	 */
	public void clear() {
		drawableObjects.clear();
	}
	
	/**
	 * Adds a DrawableObject to this Layer
	 * 
	 * @param drawableObject a valid DrawableObject
	 */
	public void add(DrawableObject drawableObject) {
		if(drawableObject == null) {
			Debug.warning("Layer.add", "Tried adding an invalid DrawableObject");
			return;
		}
		if(drawableObjects.getCount() == drawableObjects.getCapacity()) {
			Debug.warning("Layer.add", "Tried adding to a Layer which is full, maximum=" + maximumDrawableObjects);
			return;
		}
		drawableObjects.add(drawableObject);
		drawableObject.onAdd(this);
	}
	
	protected void onDraw(GL10 gl) {
		if(ignoreWindow && parentScene.window != null) {
			Window.setDefault(gl);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
	        gl.glLoadIdentity();
		}
		for(int i = 0; i < drawableObjects.getCapacity(); i++) {
			if(drawableObjects.get(i) != null) {
				try {
					while(drawableObjects.get(i) != null && drawableObjects.get(i).killNextUpdate) {
						if(drawableObjects.get(i).body != null) {
							parentScene.world.destroyBody(drawableObjects.get(i).body);
							drawableObjects.get(i).body = null;
						}
						drawableObjects.remove(i);
					}
				} catch (Exception e) { 
					Debug.warning("Exception onDraw!");
					e.printStackTrace();
				}
				if(drawableObjects.get(i) != null) {
					drawableObjects.get(i).onUpdate();
					if(drawableObjects.get(i).isOnScreen()) {
						drawableObjects.get(i).onDraw(gl);
					}
				}
			}
		}
		if(ignoreWindow && parentScene.window != null) {
			parentScene.window.onUpdate(gl);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
	        gl.glLoadIdentity();
		}
	}
	
}
