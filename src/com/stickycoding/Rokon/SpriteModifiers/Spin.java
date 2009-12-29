package com.stickycoding.Rokon.SpriteModifiers;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.SpriteModifier;


/**
 * A very basic SpriteModifier that rotates a Sprite at a given frequency
 */
public class Spin extends SpriteModifier {

	private float _frequency;
	private float _angle;
	private long _lastUpdate;
	private long timeDiff;

	public Spin(float frequency) {
		_frequency = frequency;
		_angle = 0;
		_lastUpdate = Rokon.time;
	}
	
	public void onDraw(Sprite sprite, GL10 gl) {
		//timeDiff = Rokon.getTime() - _lastUpdate;
		//_angle += (float)((float)(_frequency * 360f) / 1000f * (float)timeDiff);
		//gl.glTranslatef(sprite.getX() + (sprite.getWidth() / 2), sprite.getY() + (sprite.getHeight() / 2), 0);
		//gl.glRotatef(_angle, 0, 0, 1);
		//gl.glTranslatef(-(sprite.getX() + (sprite.getWidth() / 2)), -(sprite.getY() + (sprite.getHeight() / 2)), 0);
		//_lastUpdate = Rokon.getTime();
	}
	
	public void onUpdate(Sprite sprite) {
		timeDiff = Rokon.time - _lastUpdate;
		_angle += (float)((float)(_frequency * 360f) / 1000f * (float)timeDiff);
		Debug.print("td=" + timeDiff + " Rotating at " + _angle);
		sprite.setRotation(_angle % 360);
		_lastUpdate = Rokon.time;
	}

}
