package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;
import com.stickycoding.Rokon.Handlers.BasicHandler;

/**
 * Shrink does exactly that, shrinks a sprite to zero size in a given timeframe
 */
public class Grow extends SpriteModifier {
	
	private long _startTime;
	public float _startScale;
	private int _time;
	private BasicHandler _handler;
	
	/**
	 * Shrinks a sprite in a given timeframe, without a BasicHandler
	 * @param time milliseconds, time to reach 0x0 pixels
	 */
	public Grow(int time, float startScale) {
		this(time, null);
		_startScale = startScale;
	}
	
	/**
	 * Shrinks a sprite in a given timeframe, and triggers BasicHandler.onFinished() when complete
	 * @param time milliseconds, time to reach 0x0 pixels
	 * @param handler onFinished() will be triggered when the Sprite reaches 0x0 pixels
	 */
	public Grow(int time, BasicHandler handler) {
		if(handler != null)
			_handler = handler;
		_time = time;
		_startTime = Rokon.getTime();
	}
	
	private long timeDiff;
	private float scale, width, height, offsetX, offsetY;
	public void onUpdate(Sprite sprite) {
		timeDiff = Rokon.getTime() - _startTime;
		scale = _startScale + ((float)timeDiff / (float)_time);
		if(timeDiff > _time) {
			setExpired(true);
			if(_handler != null)
				_handler.onFinished();
		}
		sprite.setScale(scale, scale);
		width = sprite.getWidth();
		height = sprite.getHeight();
		offsetX = (width / 2f) - (scale * (width / 2f));
		offsetY = (height / 2f)- (scale * (height / 2f));
		sprite.setOffset(offsetX, offsetY);
	}

}
