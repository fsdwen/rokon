package com.stickycoding.rokon;

public class GameObject extends DrawableObject {

	protected boolean isTouchable = false;
	protected boolean isOnScene = false;
	
	
	public GameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return TRUE if the DrawableObject has been added to the current Scene
	 */
	public boolean isAdded() {
		return isOnScene;
	}
	
	@Override
	public void onRemove() {
		super.onRemove();
		isOnScene = false;
	}
	
	/**
	 * Sets the DrawableObject to a touchable, it will be checked when Scene handles input events 
	 */
	public void setTouchable() {
		isTouchable = true;
	}
	
	/**
	 * Sets the DrawableObject as un-touchable
	 */
	public void removeTouchable() {
		isTouchable = false;
	}
	
	/**
	 * @return TRUE if the object is touchable, FALSE otherwise
	 */
	public boolean isTouchable() {
		return isTouchable;
	}
	
	@Override
	public void onAdd(Layer layer) {
		super.onAdd(layer);
		killNextUpdate = false;
		isOnScene = true;
		parentScene = layer.parentScene;
		parentLayer = layer;
		if(texture != null && texture.textureIndex == -1) {
			if(layer.parentScene != null) {
				if(Rokon.verbose) Debug.verbose("DrawableObject.onAdd", "Scene does not already contain the this objects Texture, adding automatically."); 
				layer.parentScene.useTexture(texture);
			}
		}
	}

}
