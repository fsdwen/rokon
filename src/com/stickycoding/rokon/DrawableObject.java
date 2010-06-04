package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import com.stickycoding.rokon.device.Graphics;
import com.stickycoding.rokon.vbo.ArrayVBO;
import com.stickycoding.rokon.vbo.VBO;

public class DrawableObject extends BasicGameObject implements Drawable, Updateable {

	protected boolean killNextUpdate = false;
	
	protected BlendFunction blendFunction;
	protected int forceDrawType = DrawPriority.DEFAULT;
	protected float red = 1, green = 1, blue = 1, alpha = 1;
	protected BufferObject buffer;
	protected Texture texture;
	protected ArrayVBO arrayVBO;
	protected int textureTile = 0;
	
	protected boolean invisible;
	
	protected boolean isFading;
	protected int fadeTime, fadeType;
	protected long fadeStartTime;
	protected float fadeTo, fadeStart;
	protected boolean fadeUp;
	
	protected boolean animated, hasCustomAnimation, animationReturnToStart;
	protected int animationStartTile, animationEndTile, animationLoops, animationCustomPosition;
	protected int[] customAnimationSequence;
	private long animationFrameTicks, animationLastTicks;
	
	protected float lineWidth = -1;
	protected boolean fill = true;
	protected float borderRed = 0, borderGreen = 0, borderBlue = 0;
	
	protected boolean border;
	
	/**
	 * Removes the border
	 */
	public void noBorder() {
		fill = false;
	}
	
	/**
	 * Draws a border around the object
	 * This only applied to untextured objects
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void border(float red, float green, float blue) {
		fill = true;
		borderRed = red;
		borderGreen = green;
		borderBlue = blue;
	}
	
	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	public float getLineWidth() {
		return lineWidth;
	}
	
	public void useDefaultLineWidth() {
		lineWidth = -1;
	}
	
	public void border() {
		border = true;
	}
	
	public void hide() {
		invisible = true;
	}
	
	public void show() {
		invisible = false;
	}
	
	public boolean isVisible() {
		return !invisible;
	}
	
	public void fade(float alpha, int time, int movementType) {
		fade(this.alpha, alpha, time, movementType);
	}
	
	public void fade(float alpha, int time) {
		fade(this.alpha, alpha, time, Movement.LINEAR);
	}

	
	public void fade(float startAlpha, float alpha, int time, int movementType) {
		if(alpha == startAlpha) return;
		this.alpha = startAlpha;
		fadeType = movementType; 
		isFading = true;
		fadeTime = time;
		fadeStartTime = Time.ticks;
		fadeTo = alpha;
		fadeStart = this.alpha;
		fadeUp = alpha > this.alpha;
		
	}
	
	private void updateFadeTo() {
		if(!isFading) return;
		float position = (float)(Time.ticks - fadeStartTime) / (float)fadeTime;
		float factor = Movement.getPosition(position, fadeType);
		if(position >= 1) {
			this.alpha = fadeTo;
			isFading = false;
			return;
		}
		if(fadeUp) {
			this.alpha = fadeStart + ((fadeTo - fadeStart) * factor);
		} else {
			this.alpha = fadeStart - ((fadeStart - fadeTo) * factor);
		}
	}
	
	public DrawableObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public DrawableObject(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height);
		setTexture(texture);
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
	 * Uses a Texture rather than a blank square
	 * 
	 * @param texture valid Texture object
	 * @param tile the tile index to use
	 */
	public void setTextureTile(Texture texture, int tileIndex) {
		setTexture(texture);
		textureTile = tileIndex;
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
	
	protected void prepareVBO() {
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
	 * @param red float, 0 to 1f
	 * @param green float, 0 to 1f
	 * @param blue float, 0 to 1f
	 */
	public void setRGB(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * Sets the red, green, blue and alpha values
	 * 
	 * @param red float, 0 to 1f
	 * @param green float, 0 to 1f
	 * @param blue float, 0 to 1f
	 * @param alpha float, 0 to 1f
	 */
	public void setRGBA(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public void onDraw(GL10 gl) {
		if(invisible) return;
		switch(forceDrawType) {
			case DrawPriority.DEFAULT:
				switch(DrawPriority.drawPriority) {
					case DrawPriority.PRIORITY_VBO_DRAWTEX_NORMAL:
						if(Graphics.isSupportsVBO()) {
							onDrawVBO(gl);
							return;
						}
						if(Graphics.isSupportsDrawTex() && rotation == 0) {
							onDrawTex(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_VBO_NORMAL:
						if(Graphics.isSupportsVBO()) {
							onDrawVBO(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_DRAWTEX_VBO_NORMAL:
						if(Graphics.isSupportsDrawTex()) {
							onDrawTex(gl);
							return; 
						}
						if(Graphics.isSupportsVBO()) {
							onDrawVBO(gl);
							return;
						}
						onDrawNormal(gl);
						return;
					case DrawPriority.PRIORITY_DRAWTEX_NORMAL:
						if(Graphics.isSupportsDrawTex()) {
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
			GLHelper.texCoordPointer(texture.buffer[textureTile], GL10.GL_FLOAT);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
			if(fill) {
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			}
			if(border) {
				GLHelper.vertexPointer(Rokon.boxVertexBuffer, GL10.GL_FLOAT);
				GLHelper.color4f(borderRed, borderGreen, borderBlue, alpha);
				if(lineWidth != -1) {
					GLHelper.lineWidth(lineWidth);
				} else {
					GLHelper.lineWidth(parentScene.defaultLineWidth);
				}
				gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);
			}
		}
		
		gl.glPopMatrix();

	}
	
	protected void onDrawTex(GL10 gl) {
		if(texture == null) {
			return;
		}
		GLHelper.color4f(red, green, blue, alpha);
		GLHelper.bindTexture(texture.textureIndex);
		GLHelper.drawTexCrop(new float[] { 0, 0, texture.width, texture.height} );
		((GL11Ext)gl).glDrawTexfOES(x, Graphics.getHeightPixels() - y - height, 0, 100, 100);
		
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
			GLHelper.texCoordPointer(texture.buffer[textureTile], GL10.GL_FLOAT);
		} else {
			GLHelper.disableTexCoordArray();
			GLHelper.disableTextures();
		}

		GLHelper.bindElementBuffer(Rokon.defaultElementVBO().getBufferIndex());
		((GL11)gl).glDrawElements(GL11.GL_TRIANGLE_STRIP, 4, GL11.GL_FLOAT, 0);
		
		gl.glPopMatrix();
		
	}
	
	/**
	 * Determines whether this object is actually visible on screen
	 * 
	 * @return TRUE if on screen, FALSE otherwise
	 */
	public boolean isOnScreen() {
		float maxSize = width;
		if(height > width) maxSize = height;
		if(parentLayer.ignoreWindow || parentScene.window == null) {
			if (x - (maxSize / 2) < RokonActivity.gameWidth && x + maxSize + (maxSize / 2) > 0 && y - (maxSize / 2) < RokonActivity.gameHeight && y + maxSize + (maxSize / 2) > 0) {
				return true;
			}
		} else {
			if (x - (maxSize / 2) < parentScene.window.x + parentScene.window.width && x + maxSize + (maxSize / 2) > parentScene.window.x && y - (maxSize / 2) < parentScene.window.height + parentScene.window.y && y + maxSize + (maxSize / 2) > parentScene.window.y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets the tile inside the Texture that this DrawableObject should use
	 * 
	 * @param tileIndex integer, starting at 0
	 */
	public void setTextureTile(int tileIndex) {
		textureTile = tileIndex;
	}
	
	/**
	 * Sets the tile inside the Texture that this DrawableObject should use
	 * based on columsn and rows, not the index
	 * 
	 * @param column integer, starting at 0
	 * @param row integer, starting at 0
	 */
	public void setTextureTile(int column, int row) {
		textureTile = (row * texture.columns) + column;
	}
	
	/**
	 * Returns the current tile which the Texture is using
	 * 
	 * @return 0 as default
	 */
	public int getTextureTile() {
		return textureTile;
	}
	
	/**
	 * Returns the current row of the Texture which this DrawableObject is using
	 * 
	 * @return positive integer
	 */
	public int getTextureTileRow() {
		float col = textureTile % texture.columns;
		return (int)((textureTile - col) / texture.columns);
	}

	
	/**
	 * Returns the current row of the Texture which this DrawableObject is using
	 * 
	 * @return positive integer
	 */
	public int getTextureTileColumn() {
		return textureTile % texture.columns;
	}

	public void onUpdate() {
		super.onUpdate();
		updateFadeTo();
		updateAnimation();
	}

	public void onAdd(Layer layer) {
        killNextUpdate = false;
        if(forceDrawType == DrawPriority.VBO || DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO_DRAWTEX_NORMAL || DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO_NORMAL) {
        	prepareVBO();
        }
	}

	public void onRemove() {

	}

	/**
	 * Removes this DrawableObject from the Scene
	 */
	public void remove() {
		killNextUpdate = true;
	}
	
	public boolean isAlive() {
		if(killNextUpdate) {
			onRemove();
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Animates the Textures tile index, between minimum and maximum
	 * 
	 * @param startTile first tile index to animate from
	 * @param endTile the final tile index
	 * @param frameTime length of time to show one frame (in ms)
	 * @param loops number of animation loops before stopping
	 * @param returnToStart TRUE if the animation should return to the start after finished
	 */
	public void animate(int startTile, int endTile, long frameTime, int loops, boolean returnToStart) {
		animationStartTile = startTile;
		animationEndTile = endTile;
		animationFrameTicks = frameTime;
		animationLoops = loops;
		animationLastTicks = Time.ticks;
		textureTile = startTile;
		hasCustomAnimation = false;
		animationReturnToStart = returnToStart;
		animated = true;
	}
	
	/**
	 * Animates the Textures tile index, beween minimum and maximum
	 * 
	 * @param startTile first tile index to animate from
	 * @param endTile the final tile index
	 * @param frameTime length of time to show one frame (in ms)
	 */
	public void animate(int startTile, int endTile, long frameTime) {
		animate(startTile, endTile, frameTime, -1, false);
	}
	
	/**
	 * Animates the Texture tile index, through a custom array
	 * 
	 * @param animationTiles int array of texture tile indices
	 * @param frameTime length of time to show one frame (in ms)
	 * @param loops number of animation loops before stopping
	 * @param returnToStart TRUE if the animation should return to the start after finished
	 */
	public void animate(int[] animationTiles, long frameTime, int loops, boolean returnToStart) {
		hasCustomAnimation = true;
		textureTile = animationTiles[0];
		animationReturnToStart = returnToStart;
		animationLoops = loops;
		animationLastTicks = Time.ticks;
		animationFrameTicks = frameTime;
		customAnimationSequence = animationTiles;
		animated = true;
	}
	
	/**
	 * Animates the Texture tile index, through a custom array
	 * 
	 * @param animationTiles int array of texture tile indices
	 * @param frameTime length of time to show one frame (in ms)
	 */	
	public void animate(int[] animationTiles, long frameTime) {
		animate(animationTiles, frameTime, -1, false);
	}
	
	private void updateAnimation() {
		if(!animated) return;
		long tickDifference = Time.ticks - animationLastTicks - animationFrameTicks;
		if(tickDifference > 0) {
			int frameSkip = 0;
			while(tickDifference > 0) {
				tickDifference -= animationFrameTicks;
				frameSkip++;
			}
			if(hasCustomAnimation) {
				while(frameSkip > 0) {
					animationCustomPosition++;
					if(animationCustomPosition == customAnimationSequence.length) {
						animationCustomPosition = 0;
						if(animationLoops > 0) {
							animationLoops--;
							if(animationReturnToStart) {
								textureTile = customAnimationSequence[0];
							} else {
								animated = false;
								return;
							}
						}
					}
					textureTile = customAnimationSequence[animationCustomPosition];
					frameSkip--;
				}
			} else {
				while(frameSkip > 0) {
					textureTile++;
					if(textureTile > animationEndTile) {
						if(animationLoops > 0) {
							animationLoops--;
							if(animationLoops == 0) {
								if(animationReturnToStart) {
									textureTile = animationStartTile;
								} else {
									textureTile--;
									animated = false;
									return;
								}
							}
						}
						textureTile = animationStartTile;
					}
					frameSkip--;
				}
			}
			animationLastTicks -= tickDifference;
		}
	}
}
