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
	
	/**
	 * Returns the Sprite for which this Modifier was created for
	 * 
	 * @return Sprite object, NULL if invalid
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Called when the Modifier is added to a Sprite
	 */
	public void onStart() {
		
	}
	
	/**
	 * Called every time Modifier is updated in the rendering thread
	 * 
	 * @param sprite Sprite object which this Modifier is associated with
	 */
	public void onUpdate(Sprite sprite) {
		
	}
	
	/**
	 * Ends this Modifier, removing it from the Sprite to which it is attached
	 */
	public void end() {
		sprite.removeModifier(this);
		onEnd();
	}
	
	/**
	 * Called when the Modifier has ended, and removed from the Sprite
	 */
	public void onEnd() {
		
	}

}
