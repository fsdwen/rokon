package com.stickycoding.Rokon;

/**
 * Hotspot class is used as a way of simplying the detection of touches on
 * the screen, and triggers onHotspotTouched
 *
 */
public class Hotspot {
	
	public float x;
	public float y;
	public float width;
	public float height;
	private int _id;
	
	public Sprite sprite;
	
	/**
	 * Creates a Hotspot based on coodinates, with no ID
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Hotspot(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		_id = -1;
		sprite = null;
	}
	
	/**
	 * Creates a Hotspot based on coordinates, with a specified ID
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param id
	 */
	public Hotspot(float x, float y, float width, float height, int id) {
		this(x, y, width, height);
		_id = id;
	}
	
	/**
	 * Defines a hotspot by a sprite, with no ID number
	 * @param sprite
	 */
	public Hotspot(Sprite _sprite) {
		sprite = _sprite;
	}
	
	/**
	 * Defines a hotspot by a sprite, with a specified ID
	 * @param _sprite
	 * @param id
	 */
	public Hotspot(Sprite _sprite, int id) {
		this(_sprite);
		_id = id;
	}
	
	/**
	 * @return the ID of the Hotspot, for easier identification
	 */
	public int getId() {
		return _id;
	}
	
	/**
	 * Sets the ID of this particular Hotspot
	 * @param id
	 */
	public void setId(int id) {
		_id = id;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void update(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		sprite = null;
	}
	
	/**
	 * @param sprite
	 */
	public void update(Sprite _sprite) {
		sprite = _sprite;
	}
}
