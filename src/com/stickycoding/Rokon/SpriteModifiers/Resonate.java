package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;

public class Resonate extends SpriteModifier {
	
	public static final double PI = 3.14159265;
	
	public float minSize, maxSize;
	public int interval;
	public long startTime, timeDiff;
	public float modifier;
	public double pos;
	public float offx, offy, tw;

	public Resonate(int interval, float minSize, float maxSize) {
		this.interval = interval;
		this.minSize = minSize;
		this.maxSize = maxSize;
		startTime = Rokon.time;
	}
	
	public void onUpdate(Sprite sprite) {
		timeDiff = (Rokon.time - startTime) % interval;
		modifier = (float)timeDiff / (float)interval;
		pos = Math.cos(modifier * PI * 2 * maxSize);
		pos = minSize + (pos / 9);

		tw = (float)pos * sprite.getWidth();
		offx = (sprite.getWidth() - tw) / 2;
		tw = (float)pos * sprite.getHeight();
		offy = (sprite.getHeight() - tw) / 2;
		
		sprite.setScale((float)pos, (float)pos);
		sprite.setOffset(offx, offy);
	}
	
}
