package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;
import com.stickycoding.Rokon.Handlers.BasicHandler;

/**
 * Fade allows Sprite's to visibly fade in and out, and has the option to remove the
 * sprite from the layer once the fade is completed.
 * 
 * Fade will start by fading to 0.0 alpha, unless the Sprite is already at 0.0 alpha.
 */
public class Fade extends SpriteModifier {
	
	private BasicHandler _handler;
	private int _loops;
	private int _time;
	private boolean _fadingOut;
	private long _lastUpdate;
	
	/**
	 * No handler is used
	 * @param time time, in milliseconds, for one fade (from 1.0 to 0.0 or 0.0 to 1.0)
	 * @param loops -1 for continuous fading, odd numbers will end on 0.0, even numbers on 1.0
	 */
	public Fade(int time, int loops, boolean fadeOut) {
		this(time, loops, fadeOut, null);
	}
	
	/**
	 * BasicHandler is used, and onFinished is triggered when all loops are done
	 * @param time time, in milliseconds, for one fade (from 1.0 to 0.0 or 0.0 to 1.0)
	 * @param loops -1 for continuous fading, odd numbers will end on 0.0, even numbers on 1.0
	 * @param handler used to catch the onFinished() event
	 */
	public Fade(int time, int loops, boolean fadeOut, BasicHandler handler) {
		if(handler != null)
			_handler = handler;
		else
			_handler = null;
		_time = time;
		_loops = loops;
		_fadingOut = fadeOut;
		_lastUpdate = Rokon.getTime();
	}
	
	private long now, timeDiff;
	private float modifier, alpha;
	public void onUpdate(Sprite sprite) {
		if(_loops == 0) {
			if(_handler != null)
				_handler.onFinished();
			setExpired(true);
			return;
		}
		now = Rokon.getTime();
		timeDiff = now - _lastUpdate;
		modifier = (float)timeDiff / (float)_time;
		alpha = sprite.getAlpha();
		if(_fadingOut)
			alpha -= modifier;
		else
			alpha += modifier;
		if(alpha <= 0) {
			alpha = 0;
			_fadingOut = false;
			if(_loops > -1)
				_loops--;
		}
		if(alpha >= 1) {
			alpha = 1;
			_fadingOut = true;
			if(_loops > -1)
				_loops--;
		}
		sprite.setAlpha(alpha);
		_lastUpdate = now;
	}

}
