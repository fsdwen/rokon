package com.stickycoding.Rokon;

/**
 * @author Richard
 * Handles all collision checking routines
 */
public class Collider {
	
	private static Vector axis;
	private static float min, max;
	private static double angle;
	
	/**
	 * Checks whether 2 static Shapes are overlapping
	 * @param shape1 valid Shape object
	 * @param shape2 valid Shape object
	 * @return TRUE if overlapping, FALSE otherwise
	 */
	public static boolean overlap(Shape shape1, Shape shape2) {
		
		return false;
	}
	
	/**
	 * Checks wether a Shape is intersecting, by the seperating axis theorem
	 * @param shape1
	 * @param shape2
	 * @param vertexFrom
	 * @return
	 */
	public static boolean checkProjection(Shape shape1, Shape shape2, int vertexFrom) {
		angle = Math.atan( (shape1.getPolygon().get(vertexFrom + 1).getX() - shape1.getPolygon().get(vertexFrom).getX()) - (shape1.getPolygon().get(vertexFrom + 1).getY() - shape1.getPolygon().get(vertexFrom).getY()) );
		
		return false;
	}
	
}
