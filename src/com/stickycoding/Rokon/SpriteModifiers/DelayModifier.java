package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;

public class DelayModifier extends SpriteModifier {
	
	private long time;
	private long timeout;
	private SpriteModifier spriteModifier;
	
	public DelayModifier(int delay, SpriteModifier spriteModifier) {
		this.spriteModifier = spriteModifier;
		this.time = delay;
		timeout = 0;
	}
	
	public void onUpdate(Sprite sprite) {
		if(timeout == 0) {
			timeout = Rokon.time + time;
			return;
		}
		if(Rokon.time > timeout) {
			setExpired(true);
			sprite.addModifier(spriteModifier);
		}
	}

}
