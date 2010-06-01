package com.stickycoding.rokon;


/**
 * Sprite.java
 * This contains some of the fancier things that you can do.
 * A-la, SpriteModifiers from the past
 * 
 * @author Richard
 *
 */
public class Sprite extends DrawableObject {

	public Sprite(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	
	protected boolean isFading;
	protected int fadeTime, fadeType;
	protected long fadeStartTime;
	protected float fadeTo, fadeStart;
	protected boolean fadeUp;
	
	public void fade(float alpha, int time, int movementType) {
		fade(this.alpha, alpha, time, movementType);
	}
	
	public void fade(float alpha, int time) {
		fade(this.alpha, alpha, time, Movement.LINEAR);
	}
	
	public void fade(float startAlpha, float alpha, int time, int movementType) {
		if(alpha == startAlpha) return;
		this.alpha = startAlpha;
		fadeType = movementType; 
		isFading = true;
		fadeTime = time;
		fadeStartTime = Time.ticks;
		fadeTo = alpha;
		fadeStart = this.alpha;
		fadeUp = alpha > this.alpha;
		
	}
	
	private void updateFadeTo() {
		if(!isFading) return;
		float position = (float)(Time.ticks - fadeStartTime) / (float)fadeTime;
		float factor = Movement.getPosition(position, fadeType);
		if(position >= 1) {
			this.alpha = fadeTo;
			isFading = false;
			return;
		}
		if(fadeUp) {
			this.alpha = fadeStart + ((fadeTo - fadeStart) * factor);
		} else {
			this.alpha = fadeStart - ((fadeStart - fadeTo) * factor);
		}
	}
	
	@Override
	protected void onUpdate() {
		updateFadeTo();
		super.onUpdate();
	}
	
	

}
