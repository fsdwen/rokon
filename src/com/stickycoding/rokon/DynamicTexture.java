package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * DynamicTexture.java
 * A Texture which can be changed at runtime. A separate class was created to cut down on unecessary memory allocations,
 * since it is much more likely for a Texture to remain unchanged.
 * 
 * This cannot be used inside a TextureAtlas
 * 
 * @author Richard
 */

public class DynamicTexture extends Texture {
	
	protected Bitmap bmpCopy;
	protected Canvas canvas;

	public DynamicTexture(String filename) {
		super(filename);
	}
	
	public DynamicTexture(String filename, int columns, int rows) {
		super(filename, columns, rows);
	}
	
	@Override
	protected Bitmap getBitmap() {
		if(bmp == null) {
		        try {
		        	Bitmap tBitmap = BitmapFactory.decodeStream(Rokon.currentActivity.getAssets().open(path));
		        	bmp = tBitmap.copy(tBitmap.getConfig(), true);
		        	return bmp;
		        } catch (Exception e) {
		        	Debug.error("Texture.getBitmap() error, bad asset?");
		        	return null;
		        }
		}
		return bmp;
	}
	
	@Override
	protected void clearBitmap() {
		// Intentionally empty, Bitmap needs to be kept in memory
	}
	
	public Canvas getCanvas() {
		if(canvas == null) {
			//bmpCopy = bmp.copy(bmp.getConfig(), true);
			canvas = new Canvas(bmp);
		}
		return canvas;
	}
	
	public void reload() {
		//bmp = bmpCopy;
		TextureManager.refreshTexture(this);
	}
	
	protected void onRefreshTexture(GL10 gl) {
		GLHelper.removeTextures(new Texture[] { this });
		TextureManager.removeFromActiveTextures(this);
		TextureManager.load(this);
	}

}
