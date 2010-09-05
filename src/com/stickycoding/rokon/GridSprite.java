package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

public class GridSprite extends Sprite {
	
	private int quadsAcross, quadsDown;
	private float quadWidth, quadHeight;
	private BufferObject indexBuffer;

	public GridSprite(int quadsAcross, int quadsDown, float x, float y, float quadWidth, float quadHeight) {
		super(x, y, quadWidth * quadsAcross, quadHeight * quadsDown);
		this.quadWidth = quadWidth;
		this.quadHeight = quadHeight;
		setQuads(quadsAcross, quadsDown);
	}
	
	public void setQuads(int quadsAcross, int quadsDown) {
		this.quadsAcross = quadsAcross;
		this.quadsDown = quadsDown;
		doBuffer();
	}
	
	@Override
	protected void doBuffer() {
		int quadCount = quadsAcross * quadsDown;
		int indexCount = quadCount * 8;
		buffer = new BufferObject(indexCount);
		
		for(int x = 0; x < quadsAcross; x++) {
			for(int y = 0; y < quadsDown; y++) {
				buffer.byteBuffer.putFloat(x * quadWidth);
				buffer.byteBuffer.putFloat(y * quadHeight);

				buffer.byteBuffer.putFloat((x + 1) * quadWidth);
				buffer.byteBuffer.putFloat(y * quadHeight);

				buffer.byteBuffer.putFloat(x * quadWidth);
				buffer.byteBuffer.putFloat((y + 1) * quadHeight);
				
				buffer.byteBuffer.putFloat((x + 1) * quadWidth);
				buffer.byteBuffer.putFloat((y + 1) * quadHeight);
			}
		}
	}
	
	@Override
	public void onDrawNormal(GL10 gl) {
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}
		gl.glPushMatrix();
		GLHelper.enableVertexArray();
		GLHelper.bindBuffer(0, false);
		if(x != 0 || y != 0) {
			gl.glTranslatef(x, y, 0);
		}
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
		if(texture != null) {
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.bindTexture(texture);
			GLHelper.color4f(red, green, blue, alpha);
			if(colourBuffer != null) {
				GLHelper.enableColourArray();
				GLHelper.colourPointer(colourBuffer);
			} else {
				GLHelper.disableColourArray();
			}
			GLHelper.texCoordPointer(texture.buffer[textureTile], GL10.GL_FLOAT);
			GLHelper.vertexPointer(buffer, GL10.GL_FLOAT);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, buffer.getSize() / 2);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
			GLHelper.color4f(red, green, blue, alpha);
			if(colourBuffer != null) {
				GLHelper.enableColourArray();
				GLHelper.colourPointer(colourBuffer);
			} else {
				GLHelper.disableColourArray();
			}
			GLHelper.vertexPointer(buffer, GL10.GL_FLOAT);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, buffer.getSize() / 2);
		}
		gl.glPopMatrix();
	}
	
	public int getQuadsAcross() {
		return quadsAcross;
	}
	
	public int getQuadsDown() {
		return quadsDown;
	}
	
	

	
	
}
