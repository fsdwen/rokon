package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.rokon.vbo.ArrayVBO;
import com.stickycoding.rokon.vbo.VBO;

/**
 * PolygonSprite.java
 * An extension of Sprite, that draws Polygon shapes rather than basic rectangles.
 * Cannot be textured (easily), as yet.
 * 
 * @author Richard
 */
public class PolygonSprite extends Sprite {

	protected Polygon polygon;
	protected BufferObject polygonBuffer;
	protected ArrayVBO polygonVBO;

	public float[] getVertex(int index) {
		if(rotation != 0) {
			float x = getX() + (getWidth() * polygon.vertex[index].getX());
			float y = getY() + (getHeight() * polygon.vertex[index].getY());
			float pivotX = getX() + (getWidth() * 0.5f);
			float pivotY = getY() + (getHeight() * 0.5f);
			float[] f = MathHelper.rotate(rotation, x, y, pivotX, pivotY);
			return f;
		} else {
			return new float[] { getX() + (getWidth() * polygon.vertex[index].getX()), getY() + (getHeight() * polygon.vertex[index].getY()) };
		}
	}
	
	public PolygonSprite(Polygon polygon, float x, float y, float width, float height) {
		super(x, y, width, height);
		setPolygon(polygon);
	}
	
	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
		polygonBuffer = polygon.getBufferObject();
		if(isVBO()) {
			polygonVBO = polygon.getVBO();
		}
	}
	
	@Override
	public void setTexture(Texture texture) {
		//Empty, no textures allowed
	}
	
	public Polygon getPolygon() {
		return polygon;
	}
	
	@Override
	protected void onDrawNormal(GL10 gl) {
		GLHelper.drawNormal(fill, red, green, blue, alpha, blendFunction, polygonBuffer, GL10.GL_TRIANGLE_FAN, getX(), getY(), width, height, rotation, rotateAboutPoint, rotationPivotX, rotationPivotY, border, polygonBuffer, borderRed, borderGreen, borderBlue, borderAlpha, lineWidth, false, null, 0);
	}

	@Override
	protected void onDrawVBO(GL10 gl) {
		GLHelper.drawVBO(fill, red, green, blue, alpha, blendFunction, polygonVBO, GL10.GL_TRIANGLE_FAN, getX(), getY(), width, height, rotation, rotateAboutPoint, rotationPivotX, rotationPivotY, border, polygonVBO, borderRed, borderGreen, borderBlue, borderAlpha, lineWidth, false, null, 0);
	}

}
