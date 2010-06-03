package com.stickycoding.rokon;

/**
 * StaticObject.java
 * StaticObjects have a position and 2D dimensions
 * Has id and state functions, to aid with game design somewhat
 * 
 * @author Richard
 *
 */
public class BasicGameObject extends RotationalObject {
	
	protected int id = -1;
	protected String name = null;
	protected Scene parentScene;
	protected Layer parentLayer;

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
	
	public BasicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		if(width < 0 || height < 0) {
			Debug.warning("StaticObject()", "Tried creating StaticObject with dimensions < 0");
			return;
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

}
