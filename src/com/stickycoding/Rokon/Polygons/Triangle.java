package com.stickycoding.Rokon.Polygons;

import com.stickycoding.Rokon.Polygon;

/**
 * @author Richard
 * A basic triangular Polygon
 */
public class Triangle extends Polygon {

	public Triangle() {
		super(3);
		put(0, 1);
		put(1, 0);
		put(1, 1);
	}
	
}
