package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * Texture.java
 * An object representing one texture (one image file)
 * Contains information linking the actual asset file for the texture
 * 
 * @author Richard
 */

public class Texture {
		
	protected int textureWidth, textureHeight;
	protected int width, height, columns, rows;
	protected String path;
	protected BufferObject buffer;
	protected int textureIndex;
	
	protected void setUnloaded() {
		textureIndex = -1;
	}
	
	/**
	 * Creates a texture, with a file from the assets
	 * 
	 * @param filename path in assets, relative to RokonActivity.getGraphicsPath
	 */
	public Texture(String filename) {
		this(filename, 1, 1);
	}
	
	/**
	 * Creates a texture, with a file from the assets
	 * 
	 * @param filename path in assets, relative to RokonActivity.getGraphicsPath
	 * @param columns number of columns in the texture
	 * @param rows number of rows in the texture
	 */
	public Texture(String filename, int columns, int rows) {
		path = RokonActivity.graphicsPath + filename;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeStream(Rokon.currentActivity.getAssets().open(path), null, opts);
		} catch (Exception e) {
			Debug.error("Tried creating a Texture, failed while decoding, " + path);
			return;
		}
		width = opts.outWidth;
		height = opts.outHeight;
		this.columns = columns;
		this.rows = rows;
		textureWidth = nextPowerOfTwo(width);
		textureHeight = nextPowerOfTwo(height);
	}
	
	protected void prepareBuffers() {
		buffer = new BufferObject();
		buffer.update(0, 0, (float)width / (float)textureWidth, (float)height / (float)textureHeight);
	}
	
	protected void freeBuffers() {
		buffer.free();
	}
	
	/**
	 * @param x integer
	 * @return TRUE if x is a power of two, FALSE otherwise
	 */
	public static boolean isPowerOfTwo(int x) {
		return (x != 0) && ((x & (x - 1)) == 0);
	}
	
	/**
	 * Finds the next power of two, from a given minimum
	 * 
	 * @param minimum integer
	 * @return the next (or same, if minimum is power-of-two) power-of-two
	 */
	public static int nextPowerOfTwo(int minimum) {
		if(isPowerOfTwo(minimum)) {
			return minimum;
		}
		int i = 0;
		while(true) {
			i++;
			if(Math.pow(2, i) >= minimum) {
				return (int)Math.pow(2, i);
			}
		}
	}
	
	protected void onLoadTexture(GL10 gl) {
		prepareBuffers();
		int[] nameArray = new int[1];
		GLHelper.enableTextures();
		gl.glGenTextures(1, nameArray, 0);
		textureIndex = nameArray[0];
		GLHelper.bindTexture(textureIndex);
		Bitmap bmp = Bitmap.createBitmap(textureWidth, textureHeight, Bitmap.Config.ARGB_8888);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, buffer.get());
        
        try {
        	bmp = BitmapFactory.decodeStream(Rokon.currentActivity.getAssets().open(path));
        } catch (Exception e) {
        	Debug.error("onLoadTexture error, bad asset?");
        	return;
        }
        GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, 0, 0, bmp);
        bmp.recycle();
        bmp = null;
	}

}
