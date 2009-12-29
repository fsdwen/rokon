package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;
import com.stickycoding.Rokon.Handlers.BasicHandler;

/**
 * FadeTo allows Sprite's to visibility fade in and out to a given value over a given timeframe
*/
public class FadeTo extends SpriteModifier {
	
	private BasicHandler _handler;
	private int _time;
	private long _startTime;
	private float _startAlpha;
	private float _targetAlpha;
	
	/**
	 * @param time time, in milliseconds, for FadeTo to take
	 * @param targetAlpha the alpha value at which to stop
	 * @param fadeOut TRUE if alpha is decreasing, FALSE if increasing
	 */
	public FadeTo(int time, float targetAlpha) {
		this(time, targetAlpha, null);
	}
	
	/**
	 * BasicHandler is used, and onFinished is triggered when all loops are done
	 * @param time time, in milliseconds, for one fade (from 1.0 to 0.0 or 0.0 to 1.0)
	 * @param targetAlpha the alpha value to stop at
	 * @param handler used to catch the onFinished() event
	 */
	public FadeTo(int time, float targetAlpha, BasicHandler handler) {
		if(_handler != null)
			_handler = handler;
		else
			_handler = null;
		_time = time;
		_targetAlpha = targetAlpha;
		_startAlpha = -1;
		_startTime = Rokon.time;
	}
	
	private long now, timeDiff;
	private float modifier, alpha;
	public void onUpdate(Sprite sprite) {
		now = Rokon.time;
		alpha = sprite.getAlpha();
		if(_startAlpha == -1) {
			_startAlpha = alpha;
			return;
		}
		
		if(alpha == _targetAlpha) {
			if(_handler != null)
				_handler.onFinished();
			setExpired(true);
			return;
		}

		timeDiff = now - _startTime;
		modifier = (float)timeDiff / (float)_time;
		
		if(_startAlpha > _targetAlpha) {
			alpha = (_startAlpha - _targetAlpha) * modifier;
			if(alpha < _targetAlpha)
				alpha = _targetAlpha;
			sprite.setAlpha(alpha);
		} else {
			alpha = (_targetAlpha - _startAlpha) * modifier;
			if(alpha > _targetAlpha)
				alpha = _targetAlpha;
			sprite.setAlpha(alpha);
		}
	}

}
