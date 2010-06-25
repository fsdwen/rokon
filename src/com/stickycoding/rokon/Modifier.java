package com.stickycoding.rokon;

/**
 * Modifier.java
 * Modifies properties of drawable objects
 * 
 * @author Richard
 */

public class Modifier {
	
	private Sprite sprite;
	
	protected void onCreate(Sprite sprite) {
		this.sprite = sprite;
		onStart();
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void onStart() {
		
	}
	
	public void onUpdate(Sprite sprite) {
		
	}
	
	public void end() {
		sprite.removeModifier(this);
		onEnd();
	}
	
	public void onEnd() {
		
	}

}
