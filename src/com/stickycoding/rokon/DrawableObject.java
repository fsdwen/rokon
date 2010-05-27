package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import com.stickycoding.rokon.VBO.ArrayVBO;
import com.stickycoding.rokon.VBO.VBO;

/**
 * DrawableObject.java
 * An extension of DynamicObject, for objects which are to be drawn 
 * Confusion with Androids own Drawable class may be a potential issue, though this being an interface they cannot easily be used accidentally
 *  
 * @author Richard
 */

public class DrawableObject extends DynamicObject {

	protected boolean isTouchable = false;
	protected BlendFunction blendFunction;
	protected int forceDrawType = DrawPriority.DEFAULT;
	protected Layer parentLayer;
	protected boolean isOnScene = false;
	protected boolean killNextUpdate = false;
	protected float red, green, blue, alpha;
	protected BufferObject buffer;
	protected Texture texture;
	protected ArrayVBO arrayVBO;
	
	public DrawableObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		onCreate();
	}
	
	public DrawableObject(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height);
		onCreate();
		setTexture(texture);
	}
	
	/**
	 * Sets the DrawableObject to a touchable, it will be checked when Scene handles input events 
	 */
	public void setTouchable() {
		isTouchable = true;
	}
	
	/**
	 * Sets the DrawableObject as un-touchable
	 */
	public void removeTouchable() {
		isTouchable = false;
	}
	
	/**
	 * @return TRUE if the object is touchable, FALSE otherwise
	 */
	public boolean isTouchable() {
		return isTouchable;
	}
	
	/**
	 * Uses a Texture rather than a blank square
	 * 
	 * @param texture valid Texture object
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * Sets a specific BlendFunction to be applied to this DrawableObject
	 * If no BlendFunction is passed, the default will be used
	 * 
	 * @param blendFunction a valid BlendFunction
	 */
	public void setBlendFunction(BlendFunction blendFunction) {
		this.blendFunction = blendFunction;
	}
	
	/**
	 * Returns the specific BlendFunction associated with this DrawableObject
	 * 
	 * @return NULL if no specific BlendFunction has been given
	 */
	public BlendFunction getBlendFunction() {
		return blendFunction;
	}
	
	protected void onCreate() {
		red = 1f;
		green = 1f;
		blue = 1f;
		alpha = 1f;
		isOnScene = false;
		killNextUpdate = false;
		if(forceDrawType == DrawPriority.VBO || DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO_DRAWTEX_NORMAL || DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO_NORMAL) {
			prepareVBO();
		}
	}
	
	private void prepareVBO() {
		arrayVBO = new ArrayVBO(VBO.STATIC);
		arrayVBO.update(x, y, width, height);
	}

	public void forceDrawType(int drawType) {
		forceDrawType = drawType;
		onDrawType();
	}
	
	protected void onDrawType() {
		if(forceDrawType == DrawPriority.VBO || DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO_DRAWTEX_NORMAL || DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO_NORMAL) {
			prepareVBO();
		} else {
			if(arrayVBO != null) {
				Debug.print("Destroying VBO, onDrawType change");
				arrayVBO.destroy();
				arrayVBO = null;
			}
		}
	}
	
	protected void onAdd(Layer layer) {
		killNextUpdate = false;
		isOnScene = true;
		if(texture != null && texture.textureIndex == -1) {
			if(layer.parentScene != null) {
				if(Rokon.verbose) Debug.verbose("DrawableObject.onAdd", "Scene does not already contain the this objects Texture, adding automatically."); 
				layer.parentScene.useTexture(texture);
			}
		}
	}
	
	protected void onRemove() {
		isOnScene = false;
	}
	
	/**
	 * Sets the red value
	 * 
	 * @param red fixed point integer, 0 to ONE
	 */
	public void setRed(int red) {
		this.red = red;
	}
	
	/**
	 * @return current red value
	 */
	public float getRed() {
		return red;
	}
	
	/**
	 * Sets the green value
	 * 
	 * @param green float, 0 to 1
	 */
	public void setGreen(float green) {
		this.green = green;
	}
	
	/**
	 * @return current green value
	 */
	public float getGreen() {
		return green;
	}
	
	/**
	 * Sets the blue value
	 * 
	 * @param blue float, 0 to 1
	 */
	public void setBlue(float blue) {
		this.blue = blue;
	}
	
	/**
	 * @return blue value
	 */
	public float getBlue() {
		return blue;
	}
	
	/**
	 * Sets the alpha value
	 * 
	 * @param alpha float, 0 to 1
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	/**
	 * @return alpha value
	 */
	public float getAlpha() {
		return alpha;
	}
	
	/**
	 * Sets the red, green and blue values
	 * 
	 * @param red float, 0 to 1
	 * @param green float, 0 to 1
	 * @param blue float, 0 to 1
	 */
	public void setRGB(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Sets the red, green, blue and alpha values
	 * 
	 * @param red fixed point integer, 0 to ONE
	 * @param green fixed point integer, 0 to ONE
	 * @param blue fixed point integer, 0 to ONE
	 * @param alpha fixed point integer, 0 to ONE
	 */
	public void setRGBA(int red, int green, int blue, int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	/**
	 * Removes this DrawableObject from the Scene
	 */
	public void remove() {
		killNextUpdate = true;
	}
	
	/**
	 * @return TRUE if the DrawableObject has been added to the current Scene
	 */
	public boolean isAdded() {
		return isOnScene;
	}
	
	protected void onDraw(GL10 gl) {
		onUpdate();
		switch(forceDrawType) {
			case DrawPriority.DEFAULT:
				switch(DrawPriority.drawPriority) {
					case DrawPriority.PRIORITY_VBO_DRAWTEX_NORMAL:
						if(Device.supportsVBO) {
							onDrawVBO(gl);
							return;
						}
						if(Device.supportsDrawTex && rotation == 0) {
							onDrawTex(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_VBO_NORMAL:
						if(Device.supportsVBO) {
							onDrawVBO(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_DRAWTEX_VBO_NORMAL:
						if(Device.supportsDrawTex) {
							onDrawTex(gl);
							return; 
						}
						if(Device.supportsVBO) {
							onDrawVBO(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_DRAWTEX_NORMAL:
						if(Device.supportsDrawTex) {
							onDrawTex(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_NORMAL:
						onDrawNormal(gl);
						return;
					default:
						Debug.warning("DrawableObject.onDraw", "Invalid draw priority on DrawableObject");
						return;
				}
			case DrawPriority.NORMAL:
				onDrawNormal(gl);
				return;
			case DrawPriority.VBO:
				onDrawVBO(gl);
				return;
			case DrawPriority.DRAWTEX:
				onDrawTex(gl);
				return;
			default:
				Debug.warning("DrawableObject.onDraw", "Invalid forced draw priority");
				return;
		}
	}
	
	protected void onDrawNormal(GL10 gl) {
		GLHelper.color4f(red, green, blue, alpha);
		
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}
		
		gl.glPushMatrix();
		GLHelper.enableVertexArray();
		
		GLHelper.vertexPointer(Rokon.defaultVertexBuffer, GL10.GL_FLOAT);
		gl.glTranslatef(x, y, 0);
		
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
		
		if(texture != null) {
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.bindTexture(texture.textureIndex);
			GLHelper.texCoordPointer(texture.buffer, GL10.GL_FLOAT);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
		}
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		gl.glPopMatrix();

	}
	
	protected void onDrawTex(GL10 gl) {
		if(texture == null) {
			return;
		}
		GLHelper.color4f(red, green, blue, alpha);
		GLHelper.bindTexture(texture.textureIndex);
		GLHelper.drawTexCrop(new float[] { 0, 0, texture.width, texture.height} );
		((GL11Ext)gl).glDrawTexfOES(x, Device.heightPixels - y - height, 0, 100, 100);
		
	}
	
	protected void onDrawVBO(GL10 gl) {
		if(!arrayVBO.isLoaded()) {
			Debug.print("VBO isn't loaded");
			arrayVBO.load(gl);
		}		

		GLHelper.color4f(red, green, blue, alpha);
		
		if(blendFunction != null) {
			GLHelper.blendMode(blendFunction);
		} else {
			GLHelper.blendMode(Rokon.blendFunction);
		}
		
		gl.glPushMatrix();
		GLHelper.enableVertexArray();
		
		GLHelper.vertexPointer(GL10.GL_FLOAT);
		gl.glTranslatef(x, y, 0);
		
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
		
		if(texture != null) {
			GLHelper.enableTextures();
			GLHelper.enableTexCoordArray();
			GLHelper.bindTexture(texture.textureIndex);
			GLHelper.texCoordPointer(texture.buffer, GL10.GL_FLOAT);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
		}

		GLHelper.bindElementBuffer(Rokon.defaultElementVBO().getBufferIndex());
		((GL11)gl).glDrawElements(GL11.GL_TRIANGLE_STRIP, 4, GL11.GL_FLOAT, 0);
		
		gl.glPopMatrix();
		
	}

}
