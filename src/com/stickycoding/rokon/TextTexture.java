package com.stickycoding.rokon;

import android.graphics.Bitmap;

/**
 * TextTexture.java
 * 
 * 
 * @author Richard
 */
public class TextTexture extends Texture {
	
	protected Bitmap bmp;
	
	protected TextTexture(Bitmap bmp) {
		this.bmp = bmp;
		width = bmp.getWidth();
		height = bmp.getHeight();
		columns = 1;
		rows = 1;
		tileCount = 1;
		textureWidth = nextPowerOfTwo(width);
		textureHeight = nextPowerOfTwo(height);
	}
	
	protected Bitmap getBitmap() {
		return bmp;
	}
	
	protected void clearBitmap() {
		//Empty
	}

}
