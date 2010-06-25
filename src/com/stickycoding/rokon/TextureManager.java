package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;


/**
 * TextureManager.java
 * Handles queues for loading/unloading textures from the hardware, does not interfere with actual Texture objects
 * 
 * @author Richard
 */
public class TextureManager {
	
	public static final int MAX_TEXTURE_COUNT = 256;
	
	protected static Texture[] activeTexture = new Texture[MAX_TEXTURE_COUNT];
	protected static int activeTextureCount = 0;
	
	protected static Texture[] unloadQueue = new Texture[MAX_TEXTURE_COUNT];
	protected static int unloadQueueCount = 0;
	
	protected static Texture[] loadQueue = new Texture[MAX_TEXTURE_COUNT];
	protected static int loadQueueCount = 0;
	
	public static void reloadTextures() {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(activeTexture[i] != null) {
				Texture texture = activeTexture[i];
				activeTexture[i] = null;
				activeTextureCount--;
				load(texture);
			}
		}
	}
	
	public static void load(Texture texture) {
		if(isLoading(texture) || isActive(texture)) {
			return;
		}
		if(isUnloading(texture)) {
			removeFromActiveTextures(texture);
			return;
		}
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(loadQueue[i] == null) {
				loadQueue[i] = texture;
				loadQueueCount++;
				return;
			}
		}
		Debug.error("Unable to load texture into the queue, queue is full");
	}
	
	public static boolean isLoading(Texture texture) {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(loadQueue[i] != null && loadQueue[i].textureIndex == texture.textureIndex) {
				return true;
			}
		}
		return false;
	}
	
	public static void unloadActiveTextures() {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(activeTexture[i] != null) {
				unload(activeTexture[i]);
				activeTexture[i] = null;
			}
		}
		activeTextureCount = 0;
	}
	
	protected static void execute(GL10 gl) {
		if(unloadQueueCount > 0) {
			GLHelper.removeTextures(unloadQueue);
			clearUnloadQueue();
		}
		if(loadQueueCount > 0) {
			for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
				if(loadQueue[i] != null) {
					loadQueue[i].onLoadTexture(gl);
					loadQueue[i] = null;
				}
			}
			loadQueueCount = 0;
			Debug.print("LOADED TEXTURES");
		}
	}
	
	public static void clearUnloadQueue() {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			unloadQueue[i] = null;
		}
		unloadQueueCount = 0;
	}
	
	public static void addToActive(Texture textureId) {
		if(isActive(textureId)) {
			return;
		}
		for(int i = 0 ; i < MAX_TEXTURE_COUNT; i++) {
			if(activeTexture[i] == null) {
				activeTexture[i] = textureId;
				activeTextureCount++;
			}
		}
	}
	
	public static void unload(Texture textureId) {
		if(isUnloading(textureId)) {
			return;
		}
		removeFromActiveTextures(textureId);
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(unloadQueue[i] == null) {
				unloadQueue[i] = textureId;
				unloadQueueCount++;
				return;
			}
		}
		Debug.warning("TRIED ADDING TO UNLOAD QUEUE, BUT FULL");
	}
	
	public static boolean isActive(Texture texture) {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(activeTexture[i] != null && activeTexture[i].textureIndex == texture.textureIndex) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isUnloading(Texture texture) {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(unloadQueue[i] != null && unloadQueue[i].textureIndex == texture.textureIndex) {
				return true;
			}
		}
		return false;
	}
	
	public static void removeFromUnloading(Texture texture) {
		for(int i = 0 ; i < MAX_TEXTURE_COUNT; i++) {
			if(unloadQueue[i] != null && unloadQueue[i].textureIndex == texture.textureIndex) {
				unloadQueue[i] = null;
				activeTextureCount--;
			}
		}
	}
	
	public static void removeFromActiveTextures(Texture texture) {
		for(int i = 0; i < MAX_TEXTURE_COUNT; i++) {
			if(activeTexture[i] != null && activeTexture[i].textureIndex == texture.textureIndex) {
				activeTexture[i] = null;
				activeTextureCount--;
			}
		}
	}
	
	

}
