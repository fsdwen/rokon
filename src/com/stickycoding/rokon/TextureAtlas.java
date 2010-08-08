package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

/**
 * TextureAtlas.java
 * Holds many textures, loaded onto the atlas rather than single textures. Good for reducing actual texture count on hardware, reducing state changes, improving speed.
 * 
 * @author Richard
 */

public class TextureAtlas extends Texture {

	/**
	 * The maximum number of textures able to be loaded onto one TextureAtlas, by default
	 */
	public static final int DEFAULT_MAX_TEXTURE_COUNT = 64;
	
	/**
	 * The default width of a TextureAtlas
	 */
	public static final int DEFAULT_TEXTURE_ATLAS_WIDTH = 1024;
	
	/**
	 * The default height of a TextureAtlas 
	 */
	public static final int DEFAULT_TEXTURE_ATLAS_HEIGHT = 1024;
	
	protected int atlasWidth, atlasHeight, maxTextureCount;	
	protected Texture[] texture;
	protected boolean complete;

	/**
	 * Creates a TextureAtlas with default configuration
	 */
	public TextureAtlas() {
		super();
		maxTextureCount = DEFAULT_MAX_TEXTURE_COUNT;
		texture = new Texture[this.maxTextureCount];
		atlasWidth = DEFAULT_TEXTURE_ATLAS_WIDTH;
		atlasHeight = DEFAULT_TEXTURE_ATLAS_HEIGHT;
		
	}
	
	/**
	 * Marks this TextureAtlas as complete. No more Textures can be added, and it can be loaded onto the hardware.
	 */
	public void complete() {
		complete = true;
	}
	
	/**
	 * Creates a TextureAtlas width default dimensions, but a given maximum number of Textures
	 * 
	 * @param maxTextureCount maximum number of Textures on this TextureAtlas
	 */
	public TextureAtlas(int maxTextureCount) {
		super();
		this.maxTextureCount = maxTextureCount;
		texture = new Texture[this.maxTextureCount];
		atlasWidth = DEFAULT_TEXTURE_ATLAS_WIDTH;
		atlasHeight = DEFAULT_TEXTURE_ATLAS_WIDTH;
	}
	
	/**
	 * Creates a TextureAtlas from a given configuration
	 * 
	 * @param maxTextureCount maximum number of Textures on this TextureAtlas
	 * @param textureAtlasWidth width of this TextureAtlas, in pixels
	 * @param textureAtlasHeight height of this TextureAtlas, in pixels
	 */
	public TextureAtlas(int maxTextureCount, int textureAtlasWidth, int textureAtlasHeight) {
		this(maxTextureCount);
		atlasWidth = textureAtlasWidth;
		atlasHeight = textureAtlasHeight;
	}
	
	/**
	 * Inserts a Texture onto the TextureAtlas, finds the next best spot.
	 * To optimize, this should be done from largest to smallest.
	 * 
	 * @param texture valid Texture object
	 */
	public void insert(Texture texture) {
		if(texture instanceof DynamicTexture) {
			Debug.error("Tried inserting DynamicTexture into TextureAtlas, not possible");
			Debug.forceExit();
			return;
		}
		if(complete) {
			Debug.error("Tried inserting Texture into TextureAtlas after complete()");
			Debug.forceExit();
			return;
		}
		int slot = getNextEmptySlot();
		if(slot == -1) {
			Debug.error("TextureAtlas is full");
			return;
		} else {
			this.texture[slot] = texture;
			texture.atlasIndex = slot;
			findRightSpot(texture);
			texture.parentAtlas = this;

		}
	}
	
	private void findRightSpot(Texture texture) {
		boolean foundSpot = false;
		int checkX = 0, checkY = 0;
		while(!foundSpot) {
			if(checkY + texture.height > atlasHeight) {
				Debug.error("Can't find a spot on TextureAtlas?!");
				return;
			}
			int textureAt = textureAt(checkX, checkY, texture);
			if(textureAt == -1) {
				texture.atlasX = checkX + 1;
				texture.atlasY = checkY + 1;
				foundSpot = true;
			} else {
				checkX = this.texture[textureAt].atlasX + this.texture[textureAt].width;
				if(checkX + texture.width > atlasWidth) {
					checkX = 0;
					checkY += 16;
				}
			}
		}
	}
	
	private int textureAt(int x, int y, Texture skip) {
		for(int i = 0; i < maxTextureCount; i++) {
			if(this.texture[i] != null && this.texture[i] != skip) {
				if(MathHelper.rectOverlap(x, y, x + skip.width + 2, y + skip.width + 2, texture[i].atlasX, texture[i].atlasY, texture[i].atlasX + texture[i].width, texture[i].atlasY + texture[i].height)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	private int getNextEmptySlot() {
		for(int i = 0; i < maxTextureCount; i++) {
			if(texture[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	/* (non-Javadoc)
	 * @see com.stickycoding.rokon.Texture#onLoadTexture(javax.microedition.khronos.opengles.GL10)
	 */
	protected void onLoadTexture(GL10 gl) {
		if(!reload) {
			if(!complete) {
				Debug.error("Tried loading TextureAtlas without calling complete() first");
				Debug.forceExit();
				return;
			}
			Debug.print("Loading TextureAtlas");
			System.gc();
			int[] nameArray = new int[1];
			GLHelper.enableTextures();
			gl.glGenTextures(1, nameArray, 0);
			setTextureIndex(nameArray[0]);
			GLHelper.bindTexture(getTextureIndex());
			Bitmap bmp = Bitmap.createBitmap(atlasWidth, atlasHeight, Bitmap.Config.ARGB_8888);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,  minFilter);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, magFilter);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, wrapS);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, wrapT);
	        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, envMode);
	        bmp.recycle();
	        bmp = null;
	        System.gc();
	        
	
			for(int i = 0; i < maxTextureCount; i++) {
				if(texture[i] != null) {
					//Debug.print("adding " + i + " at " + textureIndex);
	                texture[i].setTextureIndex(getTextureIndex());
	                //Debug.print("#1");
					texture[i].prepareBuffers();
					//Debug.print("#2");
					bmp = texture[i].getBitmap();
					//Debug.print("#3 " + bmp.getWidth() + " " + bmp.getHeight());
	                GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, texture[i].atlasX, texture[i].atlasY, bmp);
					//Debug.print("#4");
	                texture[i].clearBitmap();
					//Debug.print("#5");
	                bmp = null;
	            }
			}
			Debug.print("TextureAtlas.onLoadTexture, done");
			TextureManager.addToActive(this);
		} else {
			GLHelper.bindTexture(getTextureIndex());
			for(int i = 0; i < maxTextureCount; i++) {
				if(texture[i] != null && texture[i].reload) {
					bmp = texture[i].getBitmap();
	                GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, texture[i].atlasX, texture[i].atlasY, bmp);
	                texture[i].clearBitmap();
	                bmp = null;
	                texture[i].reload = false;
	            }
			}
			reload = false;
			TextureManager.addToActive(this);
			Debug.print("TextureAtlas.onLoadTexture, RELOADED");
		}
	}
	
	@Override
	public void setUnloaded() {
		setTextureIndex(-1);
		for(int i = 0; i < maxTextureCount; i++) {
			if(texture[i] != null) {
				texture[i].setUnloaded();
			}
		}
	}
	
	@Override
	public void reload() {
		super.reload = true;
		for(int i = 0; i < maxTextureCount; i++) {
			if(texture[i] != null) {
				texture[i].reload = true;
			}
		}
	}
	
	@Override
	protected void setReloaded() {
		this.reload = false;
	}
	
	public void replaceTexture(Texture original, Texture replacement) {
		texture[original.atlasIndex] = replacement;
		texture[original.atlasIndex].atlasX = original.atlasX;
		texture[original.atlasIndex].atlasY = original.atlasY;
		texture[original.atlasIndex].buffer = original.buffer;
		texture[original.atlasIndex].width = original.width;
		texture[original.atlasIndex].height = original.height;
		texture[original.atlasIndex].parentAtlas = this;
		replacement.setTextureIndex(original.getTextureIndex());
		original.parentAtlas = null;
		original.atlasIndex = -1;
		replacement.reload = true;
		reload();
	}
	
}
