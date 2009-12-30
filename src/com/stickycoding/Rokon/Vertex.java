package com.stickycoding.Rokon;

/**
 * @author Richard
 * A 2D vertex in a Polygon coordinate system 
 */
public class Vertex {
	
	private float _x, _y;
	
	/**
	 * Creates a Vertex object with given unit coordinates
	 * @param x X coordinate, 0 to 1
	 * @param y Y coordinate, 0 to 1
	 */
	public Vertex(float x, float y) {
		if(x < 0 || x > 1 || y < 0 || y > 1) {
			Debug.warning("Attempted to create vertex outside of unit coordinates (" + x + ", " + y + ")");
			return;
		}
		_x = x;
		_y = y;
	}
	
	/**
	 * Returns the X unit coordinate
	 * @return X coordinate, 0 to 1
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * Returns the Y unit coordinate
	 * @return Y coordinate, 0 to 1
	 */
	public float getY() {
		return _y;
	}
	
	/**
	 * Sets the X unit coordinate
	 * @param x X coordinate, 0 to 1
	 */
	public void setX(float x) {
		if(x < 0 || x > 1) {
			Debug.warning("Attempted to set invalid X coordinate (" + x + ")");
			return;
		}
		_x = x;
	}
	
	/**
	 * Sets the Y unit coordinate
	 * @param y Y coordinate, 0 to 1
	 */
	public void setY(float y) {
		if(y < 0 || y > 1) {
			Debug.warning("Attempted to set invalid X coordinate (" + y + ")");
			return;
		}
		_y = y;
	}
	

}
