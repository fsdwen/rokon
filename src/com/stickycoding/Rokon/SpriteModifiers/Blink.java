package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;
import com.stickycoding.Rokon.Handlers.BasicHandler;

/**
 * Blink flashes a sprite between visible and invisible, indefinately, or for a specific amount of time
 */
public class Blink extends SpriteModifier {
	
	private long _timeout;
	private BasicHandler _handler;
	private int _time;
	private long _lastBlink;
	private boolean _visible;
	
	public Blink(int time) {
		this(time, -1, null);
	}
	
	/**
	 * @param time in milliseconds, how often the Sprite blinks from one state to another
	 * @param timeout in milliseconds, how long the Sprite will blink for
	 * @param handler triggers onFinished() when timeout is reached 
	 */
	public Blink(int time, long timeout, BasicHandler handler) {
		_lastBlink = Rokon.getTime();
		if(_timeout > 0)
			_timeout = _lastBlink + timeout;
		else
			_timeout = -1;
		_time = time;
		_visible = true;
		_handler = handler;
	}
	
	private long now;
	public void onUpdate(Sprite sprite) {
		now = Rokon.getTime();
		if(_timeout > 0 && now > _timeout) {
			if(_handler != null)
				_handler.onFinished();
			setExpired(true);
			return;
		}
		if(now - _lastBlink >= _time) {
			_visible = !_visible;
			sprite.setVisible(_visible);
			_lastBlink = now;
		}
	}

}
