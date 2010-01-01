package com.stickycoding.Rokon;

/**
 * @author Richard
 * Handles all collision checking routines
 */
public class Collider {
	
	private static Vector axis = new Vector(0, 0);
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
	
	public static boolean check(Shape shape1, Shape shape2) {
		calculateAxis(shape1.getRealX(0), shape1.getRealY(0), shape1.getRealX(1), shape1.getRealY(1));
		Debug.print("Found axis, @=" + angle + " V=[" + axis.getX() + ", " + axis.getY() + "]");
		return false;
	}
	
	private static void calculateAxis(float x1, float y1, float x2, float y2) {
		angle = getNormalAngle(x1, y1, x2, y2);
		axis.set(Math.sin(angle), -1 * Math.cos(angle));
	}
	
	private static double getNormalAngle(float x1, float y1, float x2, float y2) {
		if(x2 <= x1 && y2 >= y1)
			return Math.PI - Math.atan((y2 - y1) / (x1 - x2));
		if(x2 >= x1 && y2 <= y1)
			return Math.PI + (Math.PI / 2) + Math.atan((x2 - x1) / (y1 - y2));
		if(x2 <= x1 && y2 <= y1)
			return Math.PI + Math.atan((y1 - y2) / (x1 - x2));
		if(x2 >= x1 && y2 >= y1)
			return (Math.PI / 2) - Math.atan((x2 - x1) / (y2 - y1));
		Debug.warning("UNABLE TO FIND ANGLE : " + x1 + "," + y1 + " " + x2 + "," + y2);
		return 0;
	}
	
}
