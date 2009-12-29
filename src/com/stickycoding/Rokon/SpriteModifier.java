package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * SpriteModifier allows repetive changes to the Sprite to be applied across many Sprites with minimal code.
 * Several are included with the engine, but it is easy to make your own.
 * A SpriteModifier could be basic appearance changes, or it could contain a routine for your Sprite to follow.
 * 
 * @author Richard
 */
public class SpriteModifier {
	
	private boolean _expired = false;
	
	/**
	 * @param expired TRUE if the SpriteModifier is to remove itsself after the current frame
	 */
	public void setExpired(boolean expired) {
		_expired = expired;
	}
	
	/**
	 * @return TRUE if the SpriteModifier is to remove itsself after the current frame
	 */
	public boolean isExpired() {
		return _expired;
	}
	
	/**
	 * This is called on every SpriteModifier before the frame is drawn. Extend this to take control.
	 * @param sprite
	 */
	public void onUpdate(Sprite sprite) {
		
	}
	
	/**
	 * this is called on every SpriteModifier as it is drawn in the layer. Extend this to take control.
	 * @param sprite
	 * @param gl
	 */
	public void onDraw(Sprite sprite, GL10 gl) {
		
	}

}
