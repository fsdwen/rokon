package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

/**
 * GLHelper.java
 * Functions that help minimise and optimise OpenGL calls and state changes 
 * @author Richard
 */
public class GLHelper {
	
	private static GL10 gl;
	private static boolean glVertexArray, glTexCoordArray, glTexture2D;
	private static int textureIndex = -1, arrayBuffer = -1, elementBuffer = -1, srcBlendMode = -1, dstBlendMode = -1;
	private static float glColor4fRed = -1, glColor4fGreen = -1, glColor4fBlue = -1, glColor4fAlpha = -1;
	private static float glColorRed = -1, glColorGreen = -1, glColorBlue = -1, glColorAlpha = -1;
    private static float drawTexCrop0 = -1, drawTexCrop1 = -1, drawTexCrop2 = -1, drawTexCrop3 = -1;
    private static BufferObject lastVertexPointerBuffer;
    private static BufferObject lastTexCoordPointerBuffer;
    private static float lineWidth;
    
    public static void lineWidth(float lineWidth) {
    	if(lineWidth == GLHelper.lineWidth) return;
    	GLHelper.lineWidth = lineWidth;
    	gl.glLineWidth(lineWidth);
    }
	
	protected static void setGL(GL10 gl) {
		GLHelper.gl = gl;
	}
	
	public static void enableVertexArray() {
		if(!glVertexArray) {
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			glVertexArray = true;
		}
	}
	
	public static void disableVertexArray() {
		if(glVertexArray) {
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			glVertexArray = false;
		}
	}
	
	public static void enableTexCoordArray() {
		if(!glTexCoordArray) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			glTexCoordArray = true;
		}
	}
	
	public static void disableTexCoordArray() {
		if(glTexCoordArray) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			glTexCoordArray = false;
		}
	}
	
	public static void enableTextures() {
		if(!glTexture2D) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			glTexture2D = true;
		}
	}
	
	public static void disableTextures() {
		if(glTexture2D) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
			glTexture2D = false;
		}
	}
	
	public static void bindBuffer(int bufferIndex) {
		if(bufferIndex != arrayBuffer) {
			((GL11)gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferIndex);
			arrayBuffer = bufferIndex;
		}
	}
	
	public static void bindElementBuffer(int elementBufferIndex) {
		if(elementBufferIndex != elementBuffer) {
			((GL11)gl).glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, elementBufferIndex);
			elementBuffer = elementBufferIndex;
		}
	}
	
	public static void bindTexture(int textureIndex) {
		if(GLHelper.textureIndex != textureIndex) {
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex);
			GLHelper.textureIndex = textureIndex;
		}
	}
	
	public static void bindTexture(Texture texture) {
		if(texture.textureIndex == -1 && texture.parentAtlas == null) {
			texture.onLoadTexture(gl);
		} else {
			if(texture.textureIndex == -1) {
				texture.parentAtlas.onLoadTexture(gl);
			}
		}
		bindTexture(texture.textureIndex);
	}
	
	public static void blendMode(int srcBlendMode, int dstBlendMode) {
		if(GLHelper.srcBlendMode != srcBlendMode || GLHelper.dstBlendMode != dstBlendMode) {
			gl.glBlendFunc(srcBlendMode, dstBlendMode);
			GLHelper.srcBlendMode = srcBlendMode;
			GLHelper.dstBlendMode = dstBlendMode;
		}
	}
	
	public static void blendMode(BlendFunction blendFunction) {
		if(blendFunction.getSrc() != srcBlendMode || blendFunction.getDst() != dstBlendMode) {
			gl.glBlendFunc(blendFunction.getSrc(), blendFunction.getDst());
			srcBlendMode = blendFunction.getSrc();
			dstBlendMode = blendFunction.getDst();
		}
	}

    public static void color4f(float red, float green, float blue, float alpha) {
        if(alpha != glColor4fAlpha || red != glColor4fRed || green != glColor4fGreen || blue != glColor4fBlue) {
            gl.glColor4f(red, green, blue, alpha);
            glColor4fRed = red;
            glColor4fGreen = green;
            glColor4fBlue = blue;
            glColor4fAlpha = alpha;
        }
    }
    
    public static void color(int red, int green, int blue, int alpha) {
        if(alpha != glColorAlpha || red != glColorRed || green != glColorGreen || blue != glColorBlue) {
            gl.glColor4x(red, green, blue, alpha);
            glColorRed = red;
            glColorGreen = green;
            glColorBlue = blue;
            glColorAlpha = alpha;
        }
    }

    public static void texCoordPointer(BufferObject buffer, int type) {
        if(lastTexCoordPointerBuffer != buffer) {
        	gl.glTexCoordPointer(2, type, 0, buffer.get());
            lastTexCoordPointerBuffer = buffer;
        }
    }
    
    public static void vertexPointer(int type) {
    	lastVertexPointerBuffer = null;
    	((GL11)gl).glVertexPointer(2, type, 0, 0);
    }

    public static void vertexPointer(BufferObject buffer, int type) {
        if(lastVertexPointerBuffer != buffer) {
        	gl.glVertexPointer(2, type, 0, buffer.get());
            lastVertexPointerBuffer = buffer;
        }
    }
    
    public static void drawTexCrop(float[] buffer) {
		if(drawTexCrop0 != buffer[0] || drawTexCrop1 != buffer[1] || drawTexCrop2 != buffer[2] || drawTexCrop3 != buffer[3]) {
	        ((GL11Ext)gl).glTexParameterfv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, buffer, 0);
	        drawTexCrop0 = buffer[0];
	        drawTexCrop1 = buffer[1];
	        drawTexCrop2 = buffer[2];
	        drawTexCrop3 = buffer[3];
		}
    }
    
    public static void drawNormal(boolean fill, float red, float green, float blue, float alpha, BlendFunction blendFunction, BufferObject vertexBuffer, float x, float y, float width, float height, float rotation, boolean rotateAboutPivot, float rotationPivotX, float rotationPivotY, boolean border, float borderRed, float borderGreen, float borderBlue, float lineWidth, boolean hasTexture, Texture texture, int textureTile) {
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}
		gl.glPushMatrix();
		enableVertexArray();
		bindBuffer(0);
		vertexPointer(vertexBuffer, GL10.GL_FLOAT);
		if(x != 0 || y != 0) {
			gl.glTranslatef(x, y, 0);
		}
		if(rotation != 0) {
			if(!rotateAboutPivot) {
				gl.glTranslatef(width / 2, height / 2, 0);
				gl.glRotatef(rotation, 0, 0, 1);
				gl.glTranslatef(-width / 2, -height / 2, 0);
			} else {
				gl.glTranslatef(rotationPivotX, rotationPivotY, 0);
				gl.glRotatef(rotation, 0, 0, 1);
				gl.glTranslatef(-rotationPivotX, -rotationPivotY, 0);
			}
		}
		if(width != 1 || height != 1) {
			gl.glScalef(width, height, 0);
		}
		if(texture != null) {
			enableTextures();
			enableTexCoordArray();
			bindTexture(texture);
			texCoordPointer(texture.buffer[textureTile], GL10.GL_FLOAT);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		} else {
			disableTexCoordArray();
			disableTextures();
			if(fill) {
				color4f(red, green, blue, alpha);
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			}
			if(border) {
				color4f(borderRed, borderGreen, borderBlue, alpha);
				vertexPointer(vertexBuffer, GL10.GL_FLOAT);
				if(lineWidth != -1) {
					lineWidth(lineWidth);
				} else {
					lineWidth(RokonActivity.currentScene.defaultLineWidth);
				}
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
			}
		}
		gl.glPopMatrix();	
    }


}
