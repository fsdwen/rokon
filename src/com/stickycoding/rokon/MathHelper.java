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
	
	public static Vector2 findNormal(Vector2 vector) {
		return new Vector2(vector.y, -vector.x).nor();
	}
	
	public static boolean intersects(Sprite sprite1, Sprite sprite2) {
		if(sprite1 instanceof PolygonSprite && sprite2 instanceof PolygonSprite) {
			return polySpriteIntersects((PolygonSprite)sprite1, (PolygonSprite)sprite2);
		}
		
		return false;
	}
	
	public static boolean polySpriteIntersects(PolygonSprite polygon1, PolygonSprite polygon2) {
		float dotProduct = 0;
		float minA = 0;
		float maxA = 0;
		float minB = 0;
		float maxB = 0;
		
		for(int i = 0; i < polygon1.polygon.vertexCount; i++) {
			int startIndex = i;
			int endIndex = (i < polygon1.polygon.edge.length - 1 ? i + 1 : 0);
			float[] startVertex = polygon1.getVertex(startIndex);
			float[] endVertex = polygon1.getVertex(endIndex);
			float edgeX = endVertex[0] - startVertex[0];
			float edgeY = endVertex[1] - startVertex[1];
			
			float axisX = edgeY;
			float axisY = -edgeX;
			
			float[] vertex = polygon2.getVertex(0);
			dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
			minA = dotProduct;
			maxA = dotProduct;			
			for(int j = 1; j < polygon2.polygon.vertexCount; j++) {
				vertex = polygon2.getVertex(j);
				dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
				if(dotProduct < minA) {
					minA = dotProduct;
				} else if(dotProduct > maxA) {
					maxA = dotProduct;
				}
			}

			vertex = polygon1.getVertex(0);
			dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
			minB = dotProduct;
			maxB = dotProduct;			
			for(int j = 1; j < polygon1.polygon.vertexCount; j++) {
				vertex = polygon1.getVertex(j);
				dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
				if(dotProduct < minB) {
					minB = dotProduct;
				} else if(dotProduct > maxB) {
					maxB = dotProduct;
				}
			}
			
			if(intervalDistance(minB, maxB, minA, maxA) > 0) {
				return false;
			}
		}
		
		for(int i = 0; i < polygon2.polygon.vertexCount; i++) {
			int startIndex = i;
			int endIndex = (i < polygon2.polygon.edge.length - 1 ? i + 1 : 0);
			float[] startVertex = polygon2.getVertex(startIndex);
			float[] endVertex = polygon2.getVertex(endIndex);
			float edgeX = endVertex[0] - startVertex[0];
			float edgeY = endVertex[1] - startVertex[1];
			
			float axisX = edgeY;
			float axisY = -edgeX;

			float vertex[] = polygon2.getVertex(0);
			dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
			minA = dotProduct;
			maxA = dotProduct;			
			for(int j = 1; j < polygon2.polygon.vertexCount; j++) {
				vertex = polygon2.getVertex(j);
				dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
				if(dotProduct < minA) {
					minA = dotProduct;
				} else if(dotProduct > maxA) {
					maxA = dotProduct;
				}
			}

			vertex = polygon1.getVertex(0);
			dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
			minB = dotProduct;
			maxB = dotProduct;			
			for(int j = 1; j < polygon1.polygon.vertexCount; j++) {
				vertex = polygon1.getVertex(j);
				dotProduct = dot(axisX, axisY, vertex[0], vertex[1]);
				if(dotProduct < minB) {
					minB = dotProduct;
				} else if(dotProduct > maxB) {
					maxB = dotProduct;
				}
			}
			
			if(intervalDistance(minB, maxB, minA, maxA) > 0) {
				return false;
			}
		}
		return true;
	}
	
	public static float intervalDistance(float minA, float maxA, float minB, float maxB) {
	    if (minA < minB) {
	        return minB - maxA;
	    } else {
	        return minA - maxB;
	    }
	}
	
	public static float dot(Vector2 vector1, Vector2 vector2) {
		return vector1.dot(vector2);
	}
	
	public static float dot(Vector2 vector, float x, float y) {
		return vector.x * x + vector.y * y;
	}
	
	public static float dot(float x1, float y1, float x2, float y2) {
		return x1 * x2 + y1 * y2;
	}
	
	public static float[] rotate(float angle, float x, float y, float pivotX, float pivotY) {
		angle *= DEG_TO_RAD;
		float[] ret = new float[2];
		ret[0] = pivotX + ( (float)Math.cos(angle) * (x - pivotX) - (float)Math.sin(angle) * (y - pivotY));
		ret[1] = pivotY + ( (float)Math.sin(angle) * (x - pivotX) + (float)Math.cos(angle) * (y - pivotY));
		return ret;
	}
	

}
