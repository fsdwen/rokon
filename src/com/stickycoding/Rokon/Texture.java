package com.stickycoding.Rokon;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Representative of each image, can exist without being loaded onto the hardware
 * @author Richard
 */
public class Texture {
	
	private TextureType _textureType;
	private int _width, _height;
	private TextureBuffer _textureBuffer;
	private int _tileRows, _tileCols;
	private float _tileWidth, _tileHeight;
	private int _atlasX, _atlasY;
	private TextureAtlas _textureAtlas;
	private boolean _flipped;
	
	/**
	 * @return the pixel width of one tile
	 */
	public float getTileWidth() {
		return _tileWidth;
	}
	
	/**
	 * @return the pixel height of one tile
	 */
	public float getTileHeight() {
		return _tileHeight;
	}
	
	/**
	 * @return the TextureAtlas to which this Texture has been applied, null if not set
	 */
	public TextureAtlas getTextureAtlas() {
		return _textureAtlas;
	}
	
	/**
	 * @return the width, in pixels, of this texture
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * @return the height, in pixels, of this texture
	 */
	public int getHeight() {
		return _height;
	}
	
	/**
	 * @return an integer representative of the Type of Texture this is
	 */
	public TextureType getType() {
		return _textureType;
	}
	
	/**
	 * @return the TextureBuffer object for this Texture, on the current active tile
	 */
	public TextureBuffer getBuffer() {
		return _textureBuffer;
	}
	
	/**
	 * @return the number of tile rows in this Texture
	 */
	public int getTileRowCount() {
		return _tileRows;
	}
	
	/**
	 * @return the number of tile columns in this Texture
	 */
	public int getTileColumnCount() {
		return _tileCols;
	}
	
	/**
	 * @return the Texture's X coordinates on its TextureAtlas, -1 if not set
	 */
	public int getAtlasX() {
		return _atlasX;
	}
	
	/**
	 * @return the Texture's Y coordinates on its TextureAtlas, -1 if not set
	 */
	public int getAtlasY() {
		return _atlasY;
	}
	
	/**
	 * Selects this Texture's TextureAtlas for the hardware, if necessary
	 * @param gl
	 */
	public void select(GL10 gl) {
		_textureAtlas.select(gl);
	}
	
	/**
	 * Creates Bitmap object for loading from either its file, resource or original Bitmap (through TextureType)
	 * @return
	 */
	public Bitmap getBitmap() {
		Bitmap bmp = null;
		switch(_textureType.getType()) {
			case TextureType.ASSET:
				try {
					bmp = BitmapFactory.decodeStream(Rokon.getRokon().getActivity().getAssets().open(_textureType.getAssetPath()));
					return bmp;
				} catch (IOException e) {
					Debug.warning("Texture asset not found, " + _textureType.getAssetPath());
					return null;
				}				
			case TextureType.BITMAP:
				return _textureType.getBitmap();
			case TextureType.RESOURCE:
				bmp = BitmapFactory.decodeResource(Rokon.getRokon().getActivity().getResources(), _textureType.getResourceId());
				return bmp;
			default:
				Debug.warning("Unkown TextureType, fatal error!");
				return null;
		}
	}
	
	/**
	 * Sets the X coordinate of this Texture on its TextureAtlas
	 * @param x
	 */
	public void setAtlasX(int x) {
		_atlasX = x;
		_textureBuffer.update();
	}
	
	/**
	 * Sets the Y coordinate of this Texture on its TextureAtlas
	 * @param y
	 */
	public void setAtlasY(int y) {
		_atlasY = y;
		_textureBuffer.update();
	}
	
	/**
	 * Creates a Texture based on an asset file
	 * @param assetPath absolute path to your texture image from the /assets/ folder
	 */
	public Texture(String assetPath) {
		_textureType = new TextureType(assetPath);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeStream(Rokon.getRokon().getActivity().getAssets().open(_textureType.getAssetPath()), null, opts);
		} catch (IOException e) { 
			Debug.warning("Texture asset not found, " + assetPath);
		}
		_width = opts.outWidth;
		_height = opts.outHeight;
		_textureBuffer = new TextureBuffer(this);
		_tileCols = 1;
		_tileRows = 1;
		_flipped = false;
	}
	
	/**
	 * Creates a Texture based on a Bitmap object, it is recommended to have very few of these - as it takes a lot of memory
	 * @param bitmap
	 */
	public Texture(Bitmap bitmap) {
		_textureType = new TextureType(bitmap);
		_width = bitmap.getWidth();
		_height = bitmap.getHeight();
		_textureBuffer = new TextureBuffer(this);
		_tileCols = 1;
		_tileRows = 1;
		_flipped = false;
	}
	
	/**
	 * Creates a Texture based on a resource id
	 * @param resourceId
	 */
	public Texture(int resourceId) {
		_textureType = new TextureType(resourceId);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(Rokon.getRokon().getActivity().getResources(), resourceId, opts);
		_width = opts.outWidth;
		_height = opts.outHeight;
		_textureBuffer = new TextureBuffer(this);
		_tileCols = 1;
		_tileRows = 1;
		_flipped = false;
	}
	
	/**
	 * Creates a Texture from a ByteBuffer and given dimensions
	 * @param byteBuffer allocated ByteBuffer object containing RGBA pixel information
	 * @param width pixel width of Texture
	 * @param height pixel height of Texture
	 * @param flipped TRUE if the ByteBuffer needs to be flipped when rendered
	 */
	public Texture(ByteBuffer byteBuffer, int width, int height, boolean flipped) {
		_textureType = new TextureType(byteBuffer);
		_width = width;
		_height = height;
		_textureBuffer = new TextureBuffer(this);
		_tileCols = 1;
		_tileRows = 1;
		_flipped = flipped;
	}
	
	/**
	 * Creates a Texture with no set image or dimensions
	 */
	public Texture() {
		
	}
	
	/**
	 * Resets a Texture's properties to a given Bitmap object
	 * @param bitmap
	 */
	public void createFromBitmap(Bitmap bitmap) {
		_textureType = new TextureType(bitmap);
		_width = bitmap.getWidth();
		_height = bitmap.getHeight();
		_textureBuffer = new TextureBuffer(this);
		_tileCols = 1;
		_tileRows = 1;
		_flipped = false;
	}
	
	/**
	 * @return TRUE if the texture must be flipped on render, FALSE if normal
	 */
	public boolean isFlipped() {
		return _flipped;
	}
	
	/**
	 * Marks the Texture as containing tiled images of the same size, and seperates into rows/columns
	 * @param columns
	 * @param rows
	 */
	public void setTileCount(int columns, int rows) {
		_tileCols = columns;
		_tileRows = rows;
		_tileWidth = _width / columns;
		_tileHeight = _height / rows;
		_textureBuffer.update();
	}
	
	/**
	 * Sets the reference to the TextureAtlas that this Texture is placed into 
	 * @param textureAtlas
	 */
	public void setTextureAtlas(TextureAtlas textureAtlas) {
		_textureAtlas = textureAtlas;
		_textureBuffer.update();
	}

    /**
     * Sets the number of tile columns and rows based on given tile dimensions
     * @param tileWidth
     * @param tileHeight
     */
    public void setTileSize(int tileWidth, int tileHeight) {
    	_tileCols = _width / tileWidth;
	    _tileRows = _height / tileHeight;
		_textureBuffer.update();
    }
}
