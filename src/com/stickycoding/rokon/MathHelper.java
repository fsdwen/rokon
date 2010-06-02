package com.stickycoding.rokon;

/**
 * MathHelper.java
 * Some basic functions for mathematics
 * 
 * @author Richard
 */

public class MathHelper {
	
	public static boolean pointInRect(float pointX, float pointY, float x, float y, float width, float height) {
		if(pointX < x || pointY < y || pointX > x + width || pointY > y + height) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean rectOverlap(float rect1X1, float rect1Y1, float rect1X2, float rect1Y2, float rect2X1, float rect2Y1, float rect2X2, float rect2Y2) {
		return rect1X1 < rect2X2 && rect1X2 > rect2X1 && rect1Y1 < rect2Y2 && rect1Y2 > rect2Y1;
	}

}
