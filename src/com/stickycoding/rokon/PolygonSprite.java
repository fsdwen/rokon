package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * PolygonSprite.java
 * An extension of Sprite, that draws Polygon shapes rather than basic rectangles.
 * Cannot be textured (easily), as yet.
 * 
 * @author Richard
 */
public class PolygonSprite extends Sprite {
	
	//TODO PolygonSprite's with borders

	protected Polygon polygon;
	protected BufferObject polygonBuffer;
	protected BufferObject borderBuffer;
	
	public PolygonSprite(Polygon polygon, float x, float y, float width, float height) {
		super(x, y, width, height);
		setPolygon(polygon);
	}
	
	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
		polygonBuffer = polygon.getBufferObject();
		borderBuffer = polygon.getBufferObject();
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
		GLHelper.drawNormal(fill, red, green, blue, alpha, blendFunction, Rokon.triangleStripBoxBuffer, getX(), getY(), width, height, rotation, rotateAboutPoint, rotationPivotX, rotationPivotY, border, borderBuffer, borderRed, borderGreen, borderBlue, lineWidth, false, null, 0);
		//GLHelper.drawNormal(true, 1, 1, 1, 1, blendFunction, Rokon.triangleStripBoxBuffer, getX(), getY(), width, height, rotation, rotateAboutPoint, rotationPivotX, rotationPivotY, border, borderBuffer, borderRed, borderGreen, borderBlue, lineWidth, false, null, 0);
	}

}
