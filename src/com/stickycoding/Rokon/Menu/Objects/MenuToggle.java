package com.stickycoding.Rokon.Menu.Objects;

import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Menu.MenuObject;

public class MenuToggle extends MenuObject {
	
	private Texture _textureOn, _textureOff;
	private boolean _state = false;
	
	public MenuToggle(int id, int x, int y, Texture textureOn, Texture textureOff) {
		super(id, x, y, textureOn, true);
		_textureOn = textureOn;
		_textureOff = textureOff;
	}
	
	public MenuToggle(int id, int x, int y, int width, int height, Texture textureOn, Texture textureOff) {
		super(id, x, y, width, height, textureOn, true);
		_textureOn = textureOn;
		_textureOff = textureOff;
	}
	
	public void setState(boolean value) {
		_state = value;
	}
	
	public boolean getState() {
		return _state;
	}
	
	public Texture getTextureOn() {
		return _textureOn;
	}
	
	public Texture getTextureOff() {
		return _textureOff;
	}
	
	public Texture getTexture() {
		return (depressed() ? _textureOff : _textureOn);
	}
	
	public void onTouchUp() {
		getSprite().setTexture(_textureOn);
		_state = !_state;
		if(_state)
			getSprite().setTexture(_textureOn);
		else
			getSprite().setTexture(_textureOff);
	}

}
