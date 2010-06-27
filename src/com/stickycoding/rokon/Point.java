package com.stickycoding.rokon;

/**
 * PositionalObject.java
 * An point which has basic 2D positional coordinates, and methods to move
 * 
 * @author Richard
 */

public class Point {

	private float x, y;
	private Point parent;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}	
	
	public void setParent(Point point) {
		parent = point;
	}
	
	public void removeParent() {
		parent = null;
	}
	
	public Point getParent() {
		return parent;
	}
	
	public float getX() {
		return parent == null ? x : parent.getX() + x;
	}
	
	public float getY() {
		return parent == null ? y : parent.getX() + y;
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
	
	public static Point subtract(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}

}
