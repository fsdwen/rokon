package com.stickycoding.Rokon.Menu;

import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.SpriteModifiers.Fade;
import com.stickycoding.Rokon.SpriteModifiers.FadeTo;
import com.stickycoding.Rokon.SpriteModifiers.SlideIn;

/**
 * The extendable class for which all objects added to Menu's are made from. Has a Sprite, Texture and Hotspot, the rest is done by extensions.
 * @author Richard
 */
public class MenuObject {

	private static final int TOUCH_THRESHOLD_TIME = 100;
	
	private Menu _menu;
	private Sprite _sprite;
	private Texture _texture;
	private Hotspot _hotspot;
	private boolean _depressed;
	private long _lastTouch = 0;
	private int _id;
	
	/**
	 * Triggered when the object is no longer being touched
	 */
	public void onTouchUp() { }
	
	/**
	 * Triggered the first time the object is touched 
	 */
	public void onTouchDown() { }
	
	/**
	 * Triggered every time a touch is recognized
	 * 
	 */
	public void onTouch() { }
	
	/**
	 * Called every render, handles touch events
	 */
	public void loop() {
		if(_depressed && Rokon.time > _lastTouch + TOUCH_THRESHOLD_TIME) {
			_depressed = false;
			onTouchUp();
			_menu.onMenuObjectTouchUp(this);
			_menu.resetActiveHotspot();
			return;
		}
	}
	
	/**
	 * @return the integer ID number, -1 or NULL if not set
	 */
	public int getId() {
		return _id;
	}
	
	public MenuObject(int id, int x, int y, Texture texture, boolean createHotspot) {
		_id = id;
		_texture = texture;
		_sprite = new Sprite(x, y, texture);
		if(createHotspot)
			_hotspot = new Hotspot(_sprite);
	}
	
	public MenuObject(int id, int x, int y, int width, int height) {
		_id = id;
		_sprite = new Sprite(x, y, width, height);
		_sprite.setAlpha(0);
		_hotspot = new Hotspot(_sprite);
	}
	
	public MenuObject(int id, int x, int y, int width, int height, Texture texture, boolean createHotspot) {
		_id = id;
		_sprite = new Sprite(x, y, width, height, texture);
		_texture = texture;
		if(createHotspot)
			_hotspot = new Hotspot(_sprite);
	}
	
	public void addToScene(Rokon rokon, Menu menu) {
		_menu = menu;
		if(_sprite != null)
			rokon.addSprite(_sprite, MenuLayers.LAYER_1);
		if(_hotspot != null)
			rokon.addHotspot(_hotspot);
	}
	
	public Sprite getSprite() {
		return _sprite;
	}
	
	public Menu getMenu() {
		return _menu;
	}
	
	public Texture getTexture() {
		return _texture;
	}

	public void setTexture(Texture texture) {
		_texture = texture;
		_sprite.setTexture(texture);
	}
	
	public void touch(boolean hasActive) {
		if(hasActive && !_depressed)
			return;
		_lastTouch = Rokon.time;
		if(!_depressed) {
			onTouchDown();
			_menu.onMenuObjectTouchDown(this);
			_menu.onMenuObjectTouch(this);
			_depressed = true;
		}
		_menu.onMenuObjectTouch(this);
		onTouch();
	}
	
	public boolean depressed() {
		return _depressed;
	}
	
	public Hotspot getHotspot() {
		return _hotspot;
	}
	
	public void fadeOut(int time) {
		_sprite.resetModifiers();
		_sprite.addModifier(new Fade(time, 1, true));
	}
	
	public void slideOutRight(int acceleration) {
		_sprite.accelerate(acceleration, 0);
	}
	
	public void slideOutLeft(int acceleration) {
		_sprite.accelerate(-acceleration, 0);
	}
	
	public void slideOutDown(int acceleration) {
		_sprite.accelerate(0, acceleration);
	}
	
	public void slideOutUp(int acceleration) {
		_sprite.accelerate(0, -acceleration);
	}
	
	public void fadeIn(int time) {
		_sprite.resetModifiers();
		_sprite.setAlpha(0.0f);
		_sprite.addModifier(new Fade(time, 1, false));
	}
	
	public void fadeIn(int time, float target) {
		_sprite.resetModifiers();
		_sprite.setAlpha(0.0f);
		_sprite.addModifier(new FadeTo(time, target));
	}
	
	public void slideInRight(int time) {
		_sprite.reset();
		_sprite.addModifier(new SlideIn(_sprite, time, SlideIn.RIGHT));
	}
	
	public void slideInBottom(int time) {
		_sprite.reset();
		_sprite.addModifier(new SlideIn(_sprite, time, SlideIn.BOTTOM));
	}
	
	public void slideInLeft(int time) {
		_sprite.reset();
		_sprite.addModifier(new SlideIn(_sprite, time, SlideIn.LEFT));
	}
	
	public void slideInTop(int time) {
		_sprite.reset();
		_sprite.addModifier(new SlideIn(_sprite, time, SlideIn.TOP));
	}

}
