package com.stickycoding.Rokon.Polygons;

import com.stickycoding.Rokon.Polygon;

/**
 * @author Richard
 * A basic rectangular Polygon
 */
public class Rectangle extends Polygon {

	public Rectangle() {
		super(4);
		put(0, 0);
		put(1, 0);
		put(1, 1);
		put(0, 1);
	}	
	
}
