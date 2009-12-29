package com.stickycoding.Rokon.Handlers;

import com.stickycoding.Rokon.Sprite;

/**
 * AnimationHandler allows you to be notified at the end of each animation loop, or final loop
 */
public class AnimationHandler {
	
	public void finished(Sprite sprite) { }
	
	public void endOfLoop(int remainingLoops) { }
	
}
