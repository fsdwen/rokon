package com.stickycoding.Rokon;

import java.nio.ByteBuffer;

import android.graphics.Bitmap;

/**
 * @author Richard
 * Holds information, and handles loading, for each texture 'type' - ie, asset, bitmap, resource etc.
 */
public class TextureType {
	
	public static final int UNSET = -1, ASSET = 0, BITMAP = 1, RESOURCE = 2, BYTEBUFFER = 3;
	private int _type;
	private String _assetPath;
	private Bitmap _bitmap;
	private ByteBuffer _byteBuffer;
	private int _resourceId;
	
	/**
	 * @return the texture 'type'
	 */
	public int getType() {
		return _type;
	}
	
	/**
	 * Returns the path to the texture file in assets
	 * @return NULL if not set
	 */
	public String getAssetPath() {
		if(_type == ASSET)
			return _assetPath;
		return null;
	}
	
	/**
	 * Returns the Bitmap of the texture
	 * @return NULL if not set
	 */
	public Bitmap getBitmap() {
		if(_type == BITMAP)
			return _bitmap;
		return null;
	}
	
	/**
	 * Returns the ByteBuffer object of the texture
	 * @return NULL if not set
	 */
	public ByteBuffer getBuffer() {
		if(_type == BYTEBUFFER)
			return _byteBuffer;
		return null;
	}
	
	/**
	 * @return the int resourceId of the texture, -1 if not set
	 */
	public int getResourceId() {
		if(_type == RESOURCE)
			return _resourceId;
		return -1;
	}
	
	/**
	 * Creates a TextureType object based from an asset path. The path will be stored, and the file will be loaded each time it is needed by the hardware
	 * @param assetPath
	 */
	public TextureType(String assetPath) {
		_type = ASSET;
		_assetPath = assetPath;
	}
	
	/**
	 * Creates a TextureType object based from a Bitmap. The Bitmap object will be stored in the memory for the entire time, it is wise to use only a few of these
	 * @param bitmap
	 */
	public TextureType(Bitmap bitmap) {
		_type = BITMAP;
		_bitmap = bitmap;
	}
	
	/**
	 * Creates a TextureType object based from a Resource. A pointer to the resource will be remembered, and will be loaded from there each time it's needed
	 * @param resourceId
	 */
	public TextureType(int resourceId) {
		_type = RESOURCE;
		_resourceId = resourceId;
	}
	
	/**
	 * Creates a TextureType object based on a ByteBuffer. This will be kept in memory for loading, useful for transitions
	 * @param byteBuffer
	 */
	public TextureType(ByteBuffer byteBuffer) {
		_type = BYTEBUFFER;
		_byteBuffer = byteBuffer;
	}

}
