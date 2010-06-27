package com.stickycoding.rokon;

import android.R.dimen;

import com.badlogic.gdx.math.Vector2;

/**
 * MathHelper.java
 * Some basic functions for mathematics
 * 
 * @author Richard
 */

public class MathHelper {
	
	public static final float DEG_TO_RAD = 0.0174532925f;
	public static final float RAD_TO_DEG = 57.2957795f;
	
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
	
	public static Point rotated(Point point, float angle) {
		float pivotX = 0.5f;
		float pivotY = 0.5f;
		float cos = (float)(Math.cos(angle));
		float sin = (float)(Math.sin(angle));
		float x = point.getX() - pivotX;
		float y = point.getY() - pivotY;
		float newX = (x * cos) - (y * sin);
		float newY = (x * sin) + (y * cos);
		return new Point(newX + pivotX, newY + pivotY);
	}
	
	public static Vector2 findNormal(Vector2 vector) {
		return new Vector2(-vector.y, vector.x).nor();
	}
	
	/*public static boolean intersects(PolygonSprite polygon1, PolygonSprite polygon2) {
		boolean intersects = true;
		for(int i = 0; i < polygon1.polygon.edge.length; i++) {
			int posCount = 0, negCount = 0, zeroCount = 0;
			for(int j = 0; j < polygon2.polygon.vertexCount; j++) {
				//float dx = scaleX(polygon2.polygon.vertex[i].getX(), polygon2) - scaleX(polygon1.polygon.vertex[i].getX(), polygon1);
				//float dy = scaleY(polygon2.polygon.vertex[i].getY(), polygon2) - scaleX(polygon1.polygon.vertex[i].getY(), polygon1);
				
				float d1 = polygon1.polygon.normal[i].dot(new Vector2(scaleX(polygon1.polygon.vertex[i].getX(), polygon1), scaleY(polygon1.polygon.vertex[i].getY(), polygon1)));
				float d2 = polygon1.polygon.normal[i].dot(new Vector2(scaleX(polygon2.polygon.vertex[j].getX(), polygon2), scaleY(polygon2.polygon.vertex[j].getY(), polygon2)));
				float d = d2 - d1;
				
				if(d > 0)
					posCount++;
				else if(d < 0)
					negCount++;
				else
					zeroCount++;
				Debug.print("i=" + i + " j=" + j + " d=" + d);
			}
			Debug.print("pos=" + posCount + " neg=" + negCount + " zero=" + zeroCount);
			if((posCount > 0 && negCount > 0) || zeroCount > 0) {
				Debug.print("Intersects");
			} else {
				intersects = false;
				Debug.print("Does not intersect");
			}
		}
		Debug.print("############# " + intersects);
		return intersects;
	}*/
	
	
	public static boolean intersects(PolygonSprite polygon1, PolygonSprite polygon2) {//Polygon C0, DimensionalObject O0, Polygon C1, DimensionalObject O1) {
		Polygon C0 = polygon1.polygon;
		DimensionalObject O0 = polygon1;
		Polygon C1 = polygon2.polygon;
		DimensionalObject O1 = polygon2;
		
		int i1 = 0;
		
		Debug.print("Intersects?");
		
		for(int i0 = C0.vertexCount - 1; i1 < C0.vertexCount; i0 = i1++) {
			Point P = C0.vertex[i1];
			Vector2 D = C0.normal[i0];
			Debug.print("Testing v=" + i1 + " n=" + i0);
			if(whichSide(C1, O1, P, O0, D) > 0) {
				Debug.print("False at 1");
				return false;
			}
		}
		Debug.print("##");
		
		i1 = 0;
		for(int i0 = C1.vertexCount - 1; i1 < C1.vertexCount; i0 = i1++) {
			Point P = C1.vertex[i1];
			Vector2 D = C1.normal[i0];
			if(whichSide(C0, O0, P, O1, D) > 0) {
				Debug.print("False at 2");
				return false;
			}
		}
		
		Debug.print("True");
				
		return true;
	}
		
	private static Vector2 p = new Vector2(); // Saves destroying object constantly in collision detection
	
	private static int whichSide(Polygon C, DimensionalObject O0, Point P, DimensionalObject O1, Vector2 D) {
		int posCount = 0;
		int negCount = 0;
		int zeroCount = 0;
		
		for(int i = 0; i < C.vertexCount; i++) {
			p.x = scaleX(C.vertex[i].getX(), O0) - scaleY(P.getX(), O1);
			p.y = scaleY(C.vertex[i].getY(), O0) - scaleY(P.getY(), O1);
			float t = D.dot(p);
			Debug.print("Test points... " + D.x + " " + D.y + " -- " + p.x + " " + p.y + " ------ " + t);
			if(t > 0)
				posCount++;
			else if(t < 0)
				negCount++;
			else
				zeroCount++;
			
			if( (posCount > 0 && negCount > 0) || zeroCount > 0) {
				Debug.print("posCount=" + posCount + " negCount=" + negCount + " zeroCount=" + zeroCount + " return 0");
				return 0; 
			}
		}

		Debug.print("posCount=" + posCount + " negCount=" + negCount + " zeroCount=" + zeroCount + " return " + (posCount == 1 ? 1 : -1));
		return posCount > 0 ? 1 : -1;
	}
	
	public static float scaleX(float x, DimensionalObject dimensionalObject) {
		return dimensionalObject.getX() + (dimensionalObject.getWidth() * x);
	}
	
	public static float scaleY(float y, DimensionalObject dimensionalObject) {
		return dimensionalObject.getY() + (dimensionalObject.getHeight() * y);
	}

}
