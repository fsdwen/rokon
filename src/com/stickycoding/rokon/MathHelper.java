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

}
