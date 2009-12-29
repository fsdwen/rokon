package com.stickycoding.Rokon.Menu.Objects;

import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.Menu.MenuObject;

public class MenuButton extends MenuObject {
	
	private Texture _textureUp, _textureDown;
	
	public MenuButton(int id, int x, int y, Texture texture) {
		super(id, x, y, texture, true);
		_textureUp = texture;
		_textureDown = texture;
	}
	
	public MenuButton(int id, int x, int y, int width, int height) {
		super(id, x, y, width, height);
		_textureUp = null;
		_textureDown = null;
	}
	
	public MenuButton(int id, int x, int y, int width, int height, Texture texture) {
		super(id, x, y, width, height, texture, true);
		_textureUp = texture;
		_textureDown = texture;
	}
	
	public MenuButton(int id, int x, int y, Texture textureUp, Texture textureDown) {
		super(id, x, y, textureUp, true);
		_textureUp = textureUp;
		_textureDown = textureDown;
	}
	
	public MenuButton(int id, int x, int y, int width, int height, Texture textureUp, Texture textureDown) {
		super(id, x, y, width, height, textureUp, true);
		_textureUp = textureUp;
		_textureDown = textureDown;
	}
	
	public Texture getTextureUp() {
		return _textureUp;
	}
	
	public Texture getTextureDown() {
		return _textureDown;
	}
	
	public Texture getTexture() {
		return (depressed() ? _textureDown : _textureUp);
	}
	
	public void onTouchDown() {
		if(_textureDown != null)
			getSprite().setTexture(_textureDown);
	}
	
	public void onTouchUp() {
		if(_textureUp != null)
			getSprite().setTexture(_textureUp);
	}

}
