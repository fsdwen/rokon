package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Richard
 * Holds a collection of Texture's, organised so they can be stored as one texture on the hardware
 */
public class TextureAtlas {

	private TextureOptions _textureOptions;
	private Texture[] _texture;
	private int _index, _width, _height;
	private boolean _onHardware;
	
	private Texture _tempTexture;
	private int a, i, j, k;
	
	/**
	 * Creates a TextureAtlas with specified width/height
	 * @param width pixel width of the TextureAtlas, must be power of 2
	 * @param height pixel height of the TextureAtlas, must be power of 2
	 */
	public TextureAtlas(int width, int height) {
		this(width, height, new TextureOptions());
	}
	
	public boolean isPowerOfTwo(int n) {
		return ((n!=0)&&(n&(n-1))==0);
	}
	
	/**
	 * Creates a TextureAtlas with specified width/height and non-standard TextureOptions
	 * @param width pixel width of the TextureAtlas, must be power of 2
	 * @param height pixel height of the TextureAtlas, must be power of 2
	 * @param textureOptions
	 */
	public TextureAtlas(int width, int height, TextureOptions textureOptions) {
		_index = -1;
		_width = width;
		_height = height;
		if(!isPowerOfTwo(_width) || !isPowerOfTwo(_height)) {
			Debug.warning("TextureAtlas width and height must both be powers of two (2, 4, 8, 16, 32, 64, 128, 256, 512, 1024)");
		}
		_texture = new Texture[Constants.MAX_TEXTURES_PER_ATLAS];
		setOptions(textureOptions);
		_onHardware = false;
	}
	
	/**
	 * @return TRUE if this Atlas should be loaded onto the hardware
	 */
	public boolean isOnHardware() {
		return _onHardware;
	}
	
	/**
	 * Flags whether this Atlas is currently loaded onto the hardware
	 * @param value
	 */
	public void setOnHardware(boolean value) {
		_onHardware = value;
	}
	
	/**
	 * @return current TextureOptions applied to this 
	 */
	public TextureOptions getOptions() {
		return _textureOptions;
	}
	
	/**
	 * Gives the TextureAtlas specific TextureOptions, it will default to a standard mode
	 * If you are calling this, you must also use TextureManager.change to update the hardware
	 * @param textureOptions
	 */
	public void setOptions(TextureOptions textureOptions) {
		_textureOptions = textureOptions;
	}
	
	/**
	 * @return the width, in pixels, of this TextureAtlas
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * @return the height, in pixels, of this TextureAtlas
	 */
	public int getHeight() {
		return _height;
	}
	
	/**
	 * Selects this TextureAtlas on the hardware, does not call anything if it is already active
	 */
	public void select(GL10 gl) {
		if(TextureManager.getCurrentTexture() != _index) {
			TextureManager.setCurrentTexture(_index);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, _index);
		}
	}
	
	/**
	 * @return the index at which the TextureAtlas is on the hardware, -1 if not set
	 */
	public int getTextureIndex() {
		return _index;
	}
	
	/**
	 * Informs the TextureAtlas of what it's texture index on the hardware is
	 * @param index
	 */
	public void setTextureIndex(int index) {
		_index = index;
	}
	
	/**
	 * @return the array of Texture's inside this atlas
	 */
	public Texture[] getTextureArray() {
		return _texture;
	}
	
	/**
	 * Places a Texture onto the TextureAtlas at given XY coordinates, it does not check whether this overlaps with any existing Texture's
	 * @param texture
	 * @param x
	 * @param y
	 */
	public void insert(Texture texture, int x, int y) {
		for(i = 0; i < _texture.length; i++)
			if(_texture[i] == null) {
				_texture[i] = texture;
				_texture[i].setAtlasX(x);
				_texture[i].setAtlasY(y);
				_texture[i].setTextureAtlas(this);
				return;
			}
		Debug.warning("TextureAtlas is full, attempted forced insertion max=" + Constants.MAX_TEXTURES_PER_ATLAS);
	}
	
	/**
	 * Places a Texture onto the TextureAtlas, calculating the first place it will fit (starting from top left)
	 * If not used correctly (largest textures first) this is not the optimal method, you should precalculate and pass on coordinates
	 * It is recommended that you only use this for development, and then in your public release pass through all XY coordinates - speeding up greatly 
	 * @param texture
	 */
	public void insert(Texture texture) {
		for(i = 0; i < _texture.length; i++) {
			if(_texture[i] == null) {
				for(j = 0; j <= _width - texture.getWidth(); j++)
					for(k = 0; k <= _height - texture.getHeight(); k++) {
						if((_tempTexture = textureAt(j, k)) == null)
							if(j + texture.getWidth() <= _width && k + texture.getHeight() <= _height) {
								_texture[i] = texture;
								_texture[i].setAtlasX(j);
								_texture[i].setAtlasY(k);
								_texture[i].setTextureAtlas(this);
								return;
							}
					}
				Debug.warning("TextureAtlas appears to have no room for your Texture?!");
				return;
			}
		}
		Debug.warning("TextureAtlas is full, attempted calculated insertion max=" + Constants.MAX_TEXTURES_PER_ATLAS);
	}
	
	/**
	 * @param x
	 * @param y
	 * @return the Texture currently occupying the given coordinates, NULL if empty
	 */
	public Texture textureAt(int x, int y) {
		for(a = 0; a < _texture.length; a++)
			if(_texture[a] != null)
				if(_texture[a].getAtlasX() <= x && _texture[a].getAtlasX() +_texture[a].getWidth() > x)
					if(_texture[a].getAtlasY() <= y && _texture[a].getAtlasY() + _texture[a].getHeight() > y)
						return _texture[a];
		return null;
	}
}
