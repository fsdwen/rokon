package com.stickycoding.Rokon;

/**
 * @author Richard
 * A geometric shape, basic on a Polygon, that has real coordinates and dimensions
 */
public class Shape extends DynamicObject {
	
	private Polygon _polygon;
	
	/**
	 * Creates a Shape with zero-length dimensions
	 * @param polygon a valid Polygon object
	 */
	public Shape(Polygon polygon) {
		this(polygon, 0, 0, 0, 0);
	}
	
	/**
	 * Creates a Shape width given coordinates and dimensions
	 * @param polygon a valid Polygon object
	 * @param x X coordinate in game-space
	 * @param y Y coordinate in game-space
	 * @param width game-space width
	 * @param height game-space height
	 */
	public Shape(Polygon polygon, float x, float y, float width, float height) {
		super(x, y, width, height);
		_polygon = polygon;
	}
	
	/**
	 * Returns the Polygon to which the Shape is representing
	 * @return NULL if not valid
	 */
	public Polygon getPolygon() {
		return _polygon;
	}
	
	/**
	 * Sets the Polygon which the Shape should represent
	 * @param polygon a valid Polygon object
	 */
	public void setPolygon(Polygon polygon) {
		_polygon = polygon;
	}

}
