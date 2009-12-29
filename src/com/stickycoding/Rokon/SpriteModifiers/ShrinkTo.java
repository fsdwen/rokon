package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;
import com.stickycoding.Rokon.Handlers.BasicHandler;

/**
 * Shrink does exactly that, shrinks a sprite to zero size in a given timeframe
 */
public class ShrinkTo extends SpriteModifier {
	
	private float _destinationScale;
	private long _startTime;
	private int _time;
	private BasicHandler _handler;
	

	public ShrinkTo(int time, float destinationScale) {
		this(time, destinationScale, null);
	}
	
	public ShrinkTo(int time, float destinationScale, BasicHandler handler) {
		if(handler != null)
			_handler = handler;
		_destinationScale = destinationScale;
		_time = time;
		_startTime = Rokon.getTime();
	}
	
	private long timeDiff;
	private float scale, width, height, offsetX, offsetY;
	public void onUpdate(Sprite sprite) {
		timeDiff = Rokon.getTime() - _startTime;
		scale = 1f - ((float)timeDiff / (float)_time);
		if(scale <= _destinationScale) {
			setExpired(true);
			if(_handler != null)
				_handler.onFinished();
			return;
		}
		sprite.setScale(scale, scale);
		width = sprite.getWidth();
		height = sprite.getHeight();
		offsetX = (width / 4f) - (scale * (width / 4));
		offsetY = (height / 4f) - (scale * (height / 4));
		sprite.setOffset(offsetX, offsetY);
	}

}
