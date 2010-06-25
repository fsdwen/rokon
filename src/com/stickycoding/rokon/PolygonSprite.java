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

	protected Polygon polygon;
	protected BufferObject polygonBuffer;
	
	public PolygonSprite(Polygon polygon, float x, float y, float width, float height) {
		super(x, y, width, height);
		setPolygon(polygon);
	}
	
	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
		polygonBuffer = polygon.getBufferObject();
	}
	
	@Override
	public void setTexture(Texture texture) {
		//Empty, no textures allowed
	}
	
	@Override
	protected void onDrawNormal(GL10 gl) {

		GLHelper.color4f(red, green, blue, alpha);
		
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}
		
		gl.glPushMatrix();
		GLHelper.enableVertexArray();
		GLHelper.bindBuffer(0);
		GLHelper.vertexPointer(polygonBuffer, GL10.GL_FLOAT);			
		gl.glTranslatef(getX(), getY(), 0);
		
		if(rotation != 0) {
			if(!rotateAboutPoint) {
				gl.glTranslatef(width / 2, height / 2, 0);
				gl.glRotatef(rotation, 0, 0, 1);
				gl.glTranslatef(-width / 2, -height / 2, 0);
			} else {
				gl.glTranslatef(rotationPivotX, rotationPivotY, 0);
				gl.glRotatef(rotation, 0, 0, 1);
				gl.glTranslatef(-rotationPivotX, -rotationPivotY, 0);
			}
		}
		
		gl.glScalef(width, height, 0);
		
		GLHelper.disableTexCoordArray();
		GLHelper.disableTextures();
		if(fill) {
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		}
		if(border) {
			GLHelper.vertexPointer(polygonBuffer, GL10.GL_FLOAT);
			GLHelper.color4f(borderRed, borderGreen, borderBlue, alpha);
			if(lineWidth != -1) {
				GLHelper.lineWidth(lineWidth);
			} else {
				GLHelper.lineWidth(parentScene.defaultLineWidth);
			}
			gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
		}
		
		gl.glPopMatrix();	
	}

}
