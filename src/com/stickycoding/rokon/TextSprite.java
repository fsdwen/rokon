package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * TextSprite.text
 * A Sprite optimized for use with TextTexture
 *
 * @author Richard
 */
public class TextSprite extends Sprite {
	
	protected float characterWidth;
	public String text;
	protected int textLength = 0;

	public TextSprite(float x, float y, float characterWidth, float height) {
		super(x, y, characterWidth, height);
		this.characterWidth = width;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
		textLength = this.text.length();
		width = characterWidth * textLength;
	}

	@Override
	public void setTexture(Texture texture) {
		if(!(texture instanceof TextTexture)) {
			Debug.error("Tried setting TextSprite to non-TextTexture");
			return;
		}
		super.setTexture(texture);
	}
	
	@Override
	protected void onDrawNormal(GL10 gl) {
		if(text.length() < 1)
			return;		
		GLHelper.color4f(red, green, blue, alpha);		
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}		
		GLHelper.enableTextures();
		GLHelper.enableTexCoordArray();
		GLHelper.bindTexture(texture);		
		gl.glPushMatrix();
		GLHelper.enableVertexArray();
		GLHelper.bindBuffer(0);
		GLHelper.vertexPointer(Rokon.defaultVertexBuffer, GL10.GL_FLOAT);			
		gl.glTranslatef(getX(), getY(), 0);	
		for(int i = 0; i < textLength; i++) {
			gl.glPushMatrix();
			gl.glTranslatef(i * characterWidth, 0, 0);
			gl.glScalef(characterWidth, height, 0);
			GLHelper.texCoordPointer(texture.buffer[((TextTexture)texture).charPos(text.charAt(i))], GL10.GL_FLOAT);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			gl.glPopMatrix();
		}		
		gl.glPopMatrix();	
	}
}
