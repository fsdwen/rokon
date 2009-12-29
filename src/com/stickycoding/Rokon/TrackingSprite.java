package com.stickycoding.Rokon;

/**
 * A sprite which attaches itsself to a parent and follows that location with an offset
 * 
 * @author Richard
 */
public class TrackingSprite extends Sprite {
	
	private Sprite _parent;
	private float _offsetX;
	private float _offsetY;
	private float _scale;
	
	public boolean stopTracking = false;
	
	/**
	 * Creats the tracking sprite
	 * @param parent
	 * @param offsetX
	 * @param offsetY
	 * @param scale
	 * @param texture
	 */
	public TrackingSprite(Sprite parent, float offsetX, float offsetY, float scale, Texture texture) {
		super(parent.getX(), parent.getY(), texture);
		_parent = parent;
		_offsetX = offsetX;
		_offsetY = offsetY;
		_scale = scale;
	}
	
	/**
	 * Stops tracking the parent sprite, sticking it to the current location
	 */
	public void stopTracking() {
		stopTracking = true;
	}
	
	/* (non-Javadoc)
	 * @see com.stickycoding.Rokon.Sprite#updateMovement()
	 */
	public void updateMovement() {
		_parent.updateMovement();
		if(stopTracking)
			return;
		setTileIndex(_parent.getTileIndex());
		setWidth(_parent.getWidth() * _scale);
		setHeight(_parent.getHeight() * _scale);
		setScale(_parent.getScaleX(), _parent.getScaleY());
		setXY(_parent.getX() + _offsetX, _parent.getY() + _offsetY);
		setRotation(_parent.getRotation());
		setAlpha(_parent.getAlpha() / 3);
	}
	
	/**
	 * Sets the offset from the parent
	 */
	public void setTrackingOffset(float x, float y) {
		_offsetX = x;
		_offsetY = y;
	}
	
	/**
	 * @return tracking offset X position
	 */
	public float getTrackingOffsetX() {
		return _offsetX;
	}

	/**
	 * @return tracking offset Y position
	 */
	public float getTrackingOffsetY() {
		return _offsetY;
	}
	
}
