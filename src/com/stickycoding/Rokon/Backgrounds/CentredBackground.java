package com.stickycoding.Rokon.Backgrounds;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureBuffer;
import com.stickycoding.Rokon.OpenGL.RokonRenderer;


/**
 * A very basic, static textured background image
 */
public class CentredBackground extends Background {
	
	public TextureBuffer _buffer;
	
	public CentredBackground(Texture texture) {
		_buffer = new TextureBuffer(texture);
		_buffer.clip(64, 0, 64, 0);
	}
	
	public void drawFrame(GL10 gl) {
		_buffer.getTexture().select(gl);
		
		gl.glVertexPointer(2, GL11.GL_FLOAT, 0, RokonRenderer.vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _buffer.getBuffer());
		gl.glLoadIdentity();
		gl.glScalef(Rokon.getRokon().getWidth(), Rokon.getRokon().getHeight(), 0);
		gl.glColor4f(1, 1, 1, 1);
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
}
