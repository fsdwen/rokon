package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * TextureAtlas.java
 * Holds many textures, loaded onto the atlas rather than single textures. Good for reducing actual texture count on hardware, reducing state changes, improving speed.
 * 
 * @author Richard
 */

public class TextureAtlas extends Texture {

	public static final int DEFAULT_MAX_TEXTURE_COUNT = 32;
	public static final int DEFAULT_TEXTURE_ATLAS_WIDTH = 1024;
	public static final int DEFAULT_TEXTURE_ATLAS_HEIGHT = 1024;
	
	protected int atlasWidth, atlasHeight, maxTextureCount;	
	protected Texture[] texture;
	protected boolean complete;

	public TextureAtlas() {
		super();
		maxTextureCount = DEFAULT_MAX_TEXTURE_COUNT;
		texture = new Texture[this.maxTextureCount];
		atlasWidth = DEFAULT_TEXTURE_ATLAS_WIDTH;
		atlasHeight = DEFAULT_TEXTURE_ATLAS_HEIGHT;
		
	}
	
	public void complete() {
		complete = true;
	}
	
	public TextureAtlas(int maxTextureCount) {
		super();
		this.maxTextureCount = maxTextureCount;
		texture = new Texture[this.maxTextureCount];
		atlasWidth = DEFAULT_TEXTURE_ATLAS_WIDTH;
		atlasHeight = DEFAULT_TEXTURE_ATLAS_WIDTH;
	}
	
	public TextureAtlas(int maxTextureCount, int textureAtlasWidth, int textureAtlasHeight) {
		this(maxTextureCount);
		atlasWidth = textureAtlasWidth;
		atlasHeight = textureAtlasHeight;
	}
	
	public void insert(Texture texture) {
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
			findRightSpot(texture);
			texture.parentAtlas = this;

		}
	}
	
	private void findRightSpot(Texture texture) {
		//TODO Must be a better way of doing this!
		boolean foundSpot = false;
		int checkX = 0, checkY = 0;
		while(!foundSpot) {
			if(checkY + texture.height > atlasHeight) {
				Debug.error("Can't find a spot on TextureAtlas?!");
				return;
			}
			int textureAt = textureAt(checkX, checkY, texture);
			if(textureAt == -1) {
				texture.atlasX = checkX;
				texture.atlasY = checkY;
				foundSpot = true;
			} else {
				checkX = this.texture[textureAt].atlasX + this.texture[textureAt].width + 1;
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
				if(MathHelper.pointInRect(x, y, texture[i].atlasX, texture[i].atlasY, texture[i].width, texture[i].height)) {
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
	
	protected void onLoadTexture(GL10 gl) {
		if(!complete) {
			Debug.error("Tried loading TextureAtlas without calling complete() first");
			Debug.forceExit();
			return;
		}
		
		int[] nameArray = new int[1];
		GLHelper.enableTextures();
		gl.glGenTextures(1, nameArray, 0);
		textureIndex = nameArray[0];
		GLHelper.bindTexture(textureIndex);
		Bitmap bmp = Bitmap.createBitmap(atlasWidth, atlasHeight, Bitmap.Config.ARGB_8888);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);


		for(int i = 0; i < maxTextureCount; i++) {
			if(texture[i] != null) {
                texture[i].textureIndex = textureIndex;
				texture[i].prepareBuffers();
				bmp = texture[i].getBitmap();
                GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, texture[i].atlasX, texture[i].atlasY, bmp);
                texture[i].clearBitmap();
                bmp = null;
            }
		}

	}

}
