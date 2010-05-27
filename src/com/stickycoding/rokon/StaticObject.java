package com.stickycoding.rokon;

/**
 * StaticObject.java
 * StaticObjects have a position and a dimension, but no functions for movement
 * Has id and state functions, to aid with game design somewhat
 * 
 * @author Richard
 *
 */
public class StaticObject {
	
	protected int id = -1, state = -1;
	protected String name = null;
	protected float x, y, width, height, rotation, rotationPivotX, rotationPivotY;
	protected boolean rotateAboutPoint;
	protected Scene parentScene;

	/**
	 * Attempts to invoke a method in this objects parentScene
	 * Finds a method with no parameters
	 * 
	 * @param Callback a Callback reference
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean attemptInvoke(Callback callback) {
		if(name != null && parentScene != null) {
			return parentScene.invoke(callback);
		}
		return false;
	}
	
	/**
	 * Attempts to invoke a method in this objects parentScene
	 * Finds a method with no parameters
	 * 
	 * @param methodSuffix method name to invoke, [name]_[methodSuffix]
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean attemptInvoke(String methodSuffix) {
		if(name != null && parentScene != null) {
			return parentScene.invoke(name + "_" + methodSuffix);
		}
		return false;
	}
	
	/**
	 * USE AT YOUR OWN RISK
	 * 
	 * Attempts to invoke a method in this objects parentScene
	 * Finds the first method with matching name, very risky if you aren't careful
	 * 
	 * @param methodSuffix method name to invoke, [name]_[methodSuffix]
	 * @param parameters an array of Objects to pass as parameters
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean attemptInvoke(String methodSuffix, Object[] parameters) {
		if(name != null && parentScene != null) {
			return parentScene.invoke(name + "_" + methodSuffix, parameters);
		}
		return false;
	}
	
	/**
	 * Attempts to invoke a method in this objects parentScene
	 * Matches parameter name and parameter class types
	 * 
	 * @param methodSuffix method name to invoke, [name]_[methodSuffix]
	 * @param parameterTypes an array of Class objects which represent the type of parameter needed
	 * @param parameters an array of Objects to pass as parameters
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean attemptInvoke(String methodSuffix, Class<?>[] parameterTypes, Object[] parameters) {
		if(name != null && parentScene != null) {
			return parentScene.invoke(name + "_" + methodSuffix, parameterTypes, parameters);
		}
		return false;
	}
	
	public Scene getParentScene() {
		return parentScene;
	}
	
	public StaticObject(float x, float y, float width, float height) {
		if(width < 0 || height < 0) {
			Debug.warning("StaticObject()", "Tried creating StaticObject with dimensions < 0");
			return;
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	protected void onChange() {

	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setX(float x) {
		this.x = x;
		onChange();
	}
	
	public void setY(float y) {
		this.y = y;
		onChange();
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
		onChange();
	}
	
	public void moveX(float x) {
		this.x += x;
		onChange();
	}
	
	public void moveY(float y) {
		this.y += y;
		onChange();
	}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
		onChange();
	}
	
	public void setWidth(float width) {
		this.width = width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void setHeight(float height) {
		this.height = height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.setSize", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void growWidth(float width) {
		this.width += width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void growHeight(float height) {
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void shrinkWidth(float width) {
		this.width -= width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void shrinkHeight(float height) {
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void shrink(float width, float height) {
		this.width -= width;
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrink", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void grow(float width, float height) {
		this.width += width;
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.grow", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
		onChange();
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(float rotation, float rotationPivotX, float rotationPivotY) {
		this.rotation = rotation;
		this.rotationPivotX = rotationPivotX;
		this.rotationPivotY = rotationPivotY;
		rotateAboutPoint = true;
	}
	
	public boolean isRotateAboutPoint() {
		return false;
	}
	
	public float getRotationPivotX() {
		return rotationPivotX;
	}
	
	public float getRotationPivotY() {
		return rotationPivotY;
	}
	
	public void rotateAboutCentre() {
		rotateAboutPoint = false;
	}
	
	public void rotateAboutPoint(float rotationPivotX, float rotationPivotY) {
		this.rotationPivotX = rotationPivotX;
		this.rotationPivotY = rotationPivotY;
	}
	
	public void rotate(float rotation) {
		this.rotation += rotation;
	}
	
	/**
	 * @param distance the modulus of the distance to move
	 * @param angle the angle, in radians, relative to north 
	 */
	public void moveVector(float distance, float angle) {
		this.x += distance * (float)Math.sin(angle);
		this.y += distance * (float)Math.cos(angle);
		onChange();
	}

}
