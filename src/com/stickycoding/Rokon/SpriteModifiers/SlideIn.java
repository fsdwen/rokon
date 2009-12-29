package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;

public class SlideIn extends SpriteModifier {
	
	public static int RIGHT = 0;
	public static int TOP = 1;
	public static int LEFT = 2;
	public static int BOTTOM = 3;

	private long startTime;
	private int direction;
	private long time;
	
	private float startX, startY;
	private float destinationX, destinationY;
	private float distance;
	private float timeDiff;
	
	public SlideIn(Sprite sprite, long time, int direction) {
		this(sprite, time, direction, false);
	}
	
	public SlideIn(Sprite sprite, long time, int direction, boolean relative) {
		this.time = time;
		startTime = Rokon.time;
		this.direction = direction;
		destinationX = sprite.getX();
		destinationY = sprite.getY();
		if(direction == RIGHT) {
			startX = Rokon.getRokon().getWidth();
			if(relative)
				startX += destinationX;
			distance = startX - destinationX;
			sprite.setX(startX);
		}
		if(direction == TOP) {
			startY = 0 - sprite.getHeight();
			if(relative)
				startY += destinationY;
			distance = destinationY - startY;
			sprite.setY(startY);
		}
		if(direction == LEFT) {
			startX = 0 - sprite.getWidth();
			if(relative)
				startX += destinationX;
			distance = destinationX - startX;
			sprite.setX(startX);
		}
		if(direction == BOTTOM) {
			startY = Rokon.getRokon().getHeight();
			if(relative)
				startY += destinationY;
			distance = startY - destinationY;
			sprite.setY(startY);
		}
	}

	public void onUpdate(Sprite sprite) {
		if(Rokon.time > startTime + time) {
			sprite.setX(destinationX);
			setExpired(true);
			return;
		}
		timeDiff = Rokon.time - startTime;
		timeDiff = (float)((float)timeDiff / (float)time);
		if(direction == RIGHT) {
			sprite.setX(startX - ((-distance*(float)Math.pow(timeDiff - 1,2)) + distance ));
			if(sprite.getX() <= destinationX) {
				sprite.setX(destinationX);
				setExpired(true);
				return;
			}
		}
		if(direction == TOP) {
			sprite.setY(startY + ((-distance*(float)Math.pow(timeDiff - 1,2)) + distance ));
			if(sprite.getY() >= destinationY) {
				sprite.setY(destinationY);
				setExpired(true);
				return;
			}
		}
		if(direction == BOTTOM) {
			sprite.setY(startY - ((-distance*(float)Math.pow(timeDiff - 1,2)) + distance ));
			if(sprite.getY() <= destinationY) {
				sprite.setY(destinationY);
				setExpired(true);
				return;
			}
		}
		if(direction == LEFT) {
			sprite.setX(startX + ((-distance*(float)Math.pow(timeDiff - 1,2)) + distance ));
			if(sprite.getX() >= destinationX) {
				sprite.setX(destinationX);
				setExpired(true);
				return;
			}
		}
	}
	
}
