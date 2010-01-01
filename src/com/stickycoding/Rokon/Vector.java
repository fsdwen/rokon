package com.stickycoding.Rokon;

/**
 * @author Richard
 * A 2D vector
 */
public class Vector {
	
	private float _x, _y;
	
	/**
	 * Creates a 2D Vector object with given coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Vector(float x, float y) {
		_x = x;
		_y = y;
	}
	
	/**
	 * Returns the X coordinate
	 * @return X coordinate
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * Returns the Y coordinate
	 * @return Y coordinate
	 */
	public float getY() {
		return _y;
	}
	
	/**
	 * Sets both coordinates of the Vector
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void set(double x, double y) {
		setX((float)x);
		setY((float)y);
	}
	
	/**
	 * Sets both coordinates of the Vector
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public void set(float x, float y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Sets the X coordinate of the Vector
	 * @param x X coordinate
	 */
	public void setX(float x) {
		_x = x;
	}
	
	/**
	 * Sets the Y coordinate of the Vector
	 * @param y Y coordinate
	 */
	public void setY(float y) {
		_y = y;
	}

}
