package com.stickycoding.rokon;

/**
 * PositionalObject.java
 * An point which has basic 2D positional coordinates, and methods to move
 * 
 * @author Richard
 */

public class Point {

	protected float x, y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}	
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveX(float x) {
		this.x += x;
	}
	
	public void moveY(float y) {
		this.y += y;
	}
	
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * @param distance the modulus of the distance to move
	 * @param angle the angle, in radians, relative to north 
	 */
	public void moveVector(float distance, float angle) {
		this.x += distance * (float)Math.sin(angle);
		this.y += distance * (float)Math.cos(angle);
	}

}
