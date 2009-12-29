package com.stickycoding.Rokon;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

/**
 * @author Richard
 * This interfaces between TextureAtlas's and the hardware, load/change/destroy events must be queued up so they can be processed on the next frame
 */
public class TextureManager {
	
	private static TextureAtlas[] _loadQueue, _changeQueue, _removeQueue, _active;	
	private static boolean _hasLoadQueue, _hasChangeQueue, _hasRemoveQueue;	
	private static int _currentTexture;
	private static int i, j;
	
	/**
	 * Prepares the variables for being used, may be needed to clear memory from a previous launch - called each time the engine is created
	 */
	public static void prepare() {
		_loadQueue = new TextureAtlas[Constants.MAX_TEXTURE_ATLAS_COUNT];
		_changeQueue = new TextureAtlas[Constants.MAX_TEXTURE_ATLAS_COUNT];
		_removeQueue = new TextureAtlas[Constants.MAX_TEXTURE_ATLAS_COUNT];
		_active = new TextureAtlas[Constants.MAX_TEXTURE_ATLAS_COUNT];
		_hasLoadQueue = false;
		_hasChangeQueue = false;
		_hasRemoveQueue = false;
	}
	
	/**
	 * @return the current texture index on the hardware
	 */
	public static int getCurrentTexture() {
		return _currentTexture;
	}
	
	/**
	 * Informs TextureManager that there has been a change in current texture index
	 * @param index
	 */
	public static void setCurrentTexture(int index) {
		_currentTexture = index;
	}
	
	/**
	 * @return an array of TextureAtlas's which need to be loaded into the hardware
	 */
	public static TextureAtlas[] getLoadQueue() {
		return _loadQueue;
	}
	
	/**
	 * @return an array of TextureAtlas's for which the options need to be changed
	 */
	public static TextureAtlas[] getChangeQueue() {
		return _changeQueue;
	}
	
	/**
	 * @return an array of TextureAtlas's which need to be removed from the hardware
	 */
	public static TextureAtlas[] getRemoveQueue() {
		return _removeQueue;
	}
	
	/**
	 * @return an array of TextureAtlas's which are loaded onto the hardware
	 */
	public static TextureAtlas[] getActiveAtlas() {
		return _active;
	}
	
	/**
	 * @return TRUE if there are textures in the load queue, FALSE otherwise
	 */
	public static boolean hasLoadQueue() {
		return _hasLoadQueue;
	}
	
	/**
	 * @return TRUE if there are textures which need options changing, FALSE otherwise
	 */
	public static boolean hasChangeQueue() {
		return _hasChangeQueue;
	}
	
	/**
	 * @return TRUE if there are textures which need removing from the hardware, FALSE otherwise
	 */
	public static boolean hasRemoveQueue() {
		return _hasRemoveQueue; 
	}
	
	/**
	 * Places a TextureAtlas into the loading queue, which will load it onto the hardware
	 * @param textureAtlas
	 */
	public static void load(TextureAtlas textureAtlas) {
		_hasLoadQueue = true;
		for(i = 0; i < _loadQueue.length; i++)
			if(_loadQueue[i] == null) {
				_loadQueue[i] = textureAtlas;
				return;
			}
		Debug.warning("TextureAtlas load queue is full max=" + Constants.MAX_TEXTURE_ATLAS_COUNT);
	}
	
	/**
	 * Removes a TextureAtlas from the hardware on the next frame
	 * @param textureAtlas
	 */
	public static void remove(TextureAtlas textureAtlas) {
		_hasRemoveQueue = true;
		for(i = 0; i < _removeQueue.length; i++)
			if(_removeQueue[i] == null)	{
				_removeQueue[i] = textureAtlas;
				return;
			}
		Debug.warning("TextureAtlas remove queue is full max=" + Constants.MAX_TEXTURE_ATLAS_COUNT);
	}
	
	/**
	 * Marks a TextureAtlas for change, in that the options must be reloaded onto the hardware - these are set by various methods in TextureAtlas, this must be called afterwards
	 * @param textureAtlas
	 */
	public static void markForChange(TextureAtlas textureAtlas) {
		_hasChangeQueue = true;
		for(i = 0; i < _changeQueue.length; i++)
			if(_changeQueue[i] == null) {
				_changeQueue[i] = textureAtlas;
				return;
			}
		Debug.warning("TextureAtlas change queue is full max=" + Constants.MAX_TEXTURE_ATLAS_COUNT);
	}
	
	/**
	 * Places a TextureAtlas into the active array, this represents all those who are on the hardware - and will be reloaded at onResume
	 * @param textureAtlas
	 */
	public static void setActive(TextureAtlas textureAtlas) {
		for(j = 0; j < _active.length; j++)
			if(_active[j] == null) {
				_active[j] = textureAtlas;
				return;
			}
		Debug.warning("TextureAtlas limit reached max=" + Constants.MAX_TEXTURE_ATLAS_COUNT);
	}
	
	public static void removeFromActive(TextureAtlas textureAtlas) {
		for(i = 0; i < _active.length; i++)
			if(_active[i] == textureAtlas) {
				_active[i] = null;
				return;
			}
	}
	
	/**
	 * Updates all the TextureAtlas's as necessary
	 * @param gl
	 */
	public static void updateTextures(GL10 gl) {
		if(_hasLoadQueue)
			loadTextures(gl);
		if(_hasChangeQueue)
			changeTextures(gl);
		if(_hasRemoveQueue)
			removeTextures(gl);
	}
	
	/**
	 * Loads any TextureAtlas's onto the hardware
	 * @param gl
	 */
	public static void loadTextures(GL10 gl) {
		for(i = 0; i < _loadQueue.length; i++) {
			if(_loadQueue[i] != null) {
				int[] textures = new int[1];
				gl.glGenTextures(1, textures, 0);
				Debug.print("Allocating new texture " + textures[0]);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
				setCurrentTexture(textures[0]);
				Bitmap bmp = Bitmap.createBitmap(_loadQueue[i].getWidth(), _loadQueue[i].getHeight(), Bitmap.Config.ARGB_8888);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
				bmp.recycle();
				bmp = null;
		        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, _loadQueue[i].getOptions().minFilter);
		        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, _loadQueue[i].getOptions().magFilter);
		        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, _loadQueue[i].getOptions().texEnv);
		        for(int j = 0; j < _loadQueue[i].getTextureArray().length; j++) {
		        	if(_loadQueue[i].getTextureArray()[j] != null) {
		        		if(_loadQueue[i].getTextureArray()[j].getType().getType() == TextureType.BYTEBUFFER) {
		        			Debug.print("Copying in raw ByteArray texture");
		        			gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, _loadQueue[i].getTextureArray()[j].getAtlasX(), _loadQueue[i].getTextureArray()[j].getAtlasY(), _loadQueue[i].getTextureArray()[j].getWidth(), _loadQueue[i].getTextureArray()[j].getHeight(), GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, _loadQueue[i].getTextureArray()[j].getType().getBuffer());
		        		} else {
			        		bmp = _loadQueue[i].getTextureArray()[j].getBitmap();
			        		GLUtils.texSubImage2D(GL10.GL_TEXTURE_2D, 0, _loadQueue[i].getTextureArray()[j].getAtlasX(), _loadQueue[i].getTextureArray()[j].getAtlasY(), bmp);
			        		bmp.recycle();
			        		bmp = null;
		        		}
		        	}
		        }
		        Debug.print("Copying Complete");
		        setActive(_loadQueue[i]);
		        _loadQueue[i].setOnHardware(true);
				_loadQueue[i].setTextureIndex(textures[0]);
				_loadQueue[i] = null;
				textures = null;	
			}
		}
		Debug.print("Texture Loading Complete");
		_hasLoadQueue = false;
		System.gc();
		Rokon.getRokon().textureLoadComplete();
	}
	
	/**
	 * Synchronises any changes in TextureAtlas settings with the hardware
	 * @param gl
	 */
	public static void changeTextures(GL10 gl) {
		for(i = 0; i < _changeQueue.length; i++) {
			if(_changeQueue[i] != null) {
				_changeQueue[i].select(gl);
		        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, _loadQueue[i].getOptions().minFilter);
		        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, _loadQueue[i].getOptions().magFilter);
		        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, _loadQueue[i].getOptions().texEnv);
		        _changeQueue[i] = null;
			}
		}
		_hasChangeQueue = false;
	}
	
	/**
	 * Removes any TextureAtlas's from the hardware that aren't wanted any more
	 * @param gl
	 */
	public static void removeTextures(GL10 gl) {
		for(i = 0; i < _removeQueue.length; i++) {
			if(_removeQueue[i] != null) {
				int[] textures = new int[] { _removeQueue[i].getTextureIndex() };
				gl.glDeleteTextures(1, textures, 0);
				_removeQueue[i].setOnHardware(false);
				removeFromActive(_removeQueue[i]);
				_removeQueue[i] = null;
			}
		}
		_hasRemoveQueue = false;
	}
	
	/**
	 * Moves all active TextureAtlas classes to the load queue (used in onResume)
	 */
	public static void reload() {
		for(i = 0; i < _active.length; i++)
			if(_active[i] != null) {
				load(_active[i]);
				_active[i] = null;
			}
	}

}
