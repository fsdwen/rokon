package com.stickycoding.Rokon.SpriteModifiers;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;

/** 
 * Colorize creates a random cycle through coloring for a Sprite
 */
public class Colorize extends SpriteModifier {
	
	private float _red;
	private boolean _redUp;
	private float _green;
	private boolean _greenUp;
	private float _blue;
	private boolean _blueUp;
	private float _frequency;
	private long _lastUpdate;
	
	public Colorize(float frequency) {
		_red = (float)Math.random();
		if(_red > 0.5f)
			_redUp = false;
		else
			_redUp = true;
		_green = (float)Math.random();
		if(_green > 0.5f)
			_greenUp = false;
		else
			_greenUp = true;
		_blue = (float)Math.random();
		if(_blue > 0.5f)
			_blueUp = false;
		else
			_blueUp = true;
		_frequency = frequency;
		_lastUpdate = Rokon.getTime();
	}
	
	private long now, timeDiff;
	private float modifier;
	public void onUpdate(Sprite sprite) {
		now = Rokon.getTime();
		timeDiff = now - _lastUpdate;
		modifier = _frequency / 1000 * timeDiff;
		
		if(_redUp)
			_red += modifier;
		else
			_red -= modifier;
		if(_red > 1) {
			_red = 1;
			_redUp = false;
		}
		if(_red < 0) {
			_red = 0;
			_redUp = true;
		}
		
		if(_greenUp)
			_green += modifier;
		else
			_green -= modifier;
		if(_green > 1) {
			_green = 1;
			_greenUp = false;
		}
		if(_red < 0) {
			_green = 0;
			_greenUp = true;
		}
		
		if(_blueUp)
			_blue += modifier;
		else
			_blue -= modifier;
		if(_blue > 1) {
			_blue = 1;
			_blueUp = false;
		}
		if(_blue < 0) {
			_blue = 0;
			_blueUp = true;
		}
		
		sprite.setRed(_red);
		sprite.setGreen(_green);
		sprite.setBlue(_blue);
		
		_lastUpdate = now;
	}
	
}
