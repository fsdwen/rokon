package com.stickycoding.rokon.pathfollower;

import com.stickycoding.rokon.Point;

/**
 * Path.java
 * A connected series of points (PositionalObjects), which PathFollower can use
 * 
 * @author Richard
 */

public class Path {
	
	public Point[] point;
	
	public Path(Point[] point) {
		this.point = point; 
	}
	
	public Point getPoint(int index) {
		return point[index];
	}
	
	public int getPathLength() {
		return point.length;
	}

}
