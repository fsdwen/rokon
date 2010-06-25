package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * LineSprite.java
 * A very simple drawable straight line. For lines with many vertices, use PolygonSprite
 * 
 * @author Richard
 */

public class LineSprite extends Sprite {

	protected BufferObject lineBuffer;
	
	public LineSprite(float x1, float y1, float x2, float y2) {
		super(x1, y1, 1, 1);
		setLine(x1, y1, x2, y2);
	}
	
	public void setLine(float x1, float y1, float x2, float y2) {
		setXY(x1, y1);
		setSize(x2 - x1, y2 - y1);
		if(lineBuffer == null) {
			lineBuffer = new BufferObject();
		}
		lineBuffer.updateRaw(new float[] { 0, 0, x2 - x1, y2 - y1 });
	}

	@Override
	public void setTexture(Texture texture) {
		//Empty, no textures allowed
	}
	
	@Override
	protected void onDrawNormal(GL10 gl) {
		GLHelper.drawNormal(false, 0, 0, 0, alpha, blendFunction, lineBuffer, getX(), getY(), 1, 1, 0, false, 0, 0, true, red, green, blue, lineWidth, false, null, 0);
	}
	

}
