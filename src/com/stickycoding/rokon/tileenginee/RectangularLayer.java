package com.stickycoding.rokon.tileenginee;

import com.stickycoding.rokon.Scene;


/**
 * RectangularTiledLayer.java
 * A TiledLayer working with basic squares
 * 
 * @author Richard
 */

public class RectangularLayer extends TiledLayer {
	
	protected float width, height;

	public RectangularLayer(Scene parentScene, int maximumDrawableObjects, float width, float height) {
		super(parentScene, maximumDrawableObjects);
		this.width = width;
		this.height = height;
	}

	@Override
	public float getDrawX(int x, int y) { 
		return x * width;
	}

	@Override
	public float getDrawY(int x, int y) { 
		return y * height; 
	}

	@Override
	public float getDrawX(int x, int y, int targetX, int targetY, float offset) {
		return (x * width) + ((targetX - x) * offset * width);
	}

	@Override
	public float getDrawY(int x, int y, int targetX, int targetY, float offset) {
		return (y * height) + ((targetY - y) * offset * height);
	}

	@Override
	public int getTileX(float x, float y) { 
		return (int)((x - (x % width)) / width);
	}

	@Override
	public int getTileY(float x, float y) { 
		return (int)((y - (y % height)) / height);
	}

}
