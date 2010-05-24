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
	
	public Layer(Scene parentScene, int maximumDrawableObjects) {
		this.parentScene = parentScene;
		this.maximumDrawableObjects = maximumDrawableObjects;
		drawQueue = new DrawQueue();
		drawableObjects = new FixedSizeArray<DrawableObject>(maximumDrawableObjects);
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
		drawableObject.onAdd();
	}
	
	protected void onDraw(GL10 gl) {
		for(int i = 0; i < drawableObjects.getCapacity(); i++) {
			if(drawableObjects.get(i) != null) {
				drawableObjects.get(i).onDraw(gl);
			}
		}
	}
	
}
