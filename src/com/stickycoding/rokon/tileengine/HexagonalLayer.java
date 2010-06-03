package com.stickycoding.rokon.tileengine;

import com.stickycoding.rokon.Scene;

/**
 * HexagonalLayer.java
 * A basic (regular) hexagonal TiledLayer
 *  
 * @author Richard
 */

public class HexagonalLayer extends TiledLayer {
	
	//TODO Support alternative hexagons (flat along horizontal)
	//TODO Impprove getTile routines, very basic right now
	
	protected int size, halfSize;
	protected int separation;

	public HexagonalLayer(Scene parentScene, int maximumDrawableObjects, int size, int separation) {
		super(parentScene, maximumDrawableObjects);
		this.size = size;
		this.halfSize = (int)(size / 2);
		this.separation = separation;
	}

	@Override
	public float getDrawX(int x, int y) {
		return (y % 2 == 0) ? x * size : (x * size) + halfSize;
	}

	@Override
	public float getDrawY(int x, int y) { 
		return y * separation; 
	}

	@Override
	public float getDrawX(int x, int y, int targetX, int targetY, float offset) {
		return (x * size) + ((targetX - x) * offset * size);
	}

	@Override
	public float getDrawY(int x, int y, int targetX, int targetY, float offset) {
		return (y * size) + ((targetY - y) * offset * size);
	}

	@Override
	public int getTileX(float x, float y) { 
		return (getTileY(x, y) % 2 == 0) ? (int)((x - (x % size)) / size) : (int)((x - halfSize - ((x - halfSize) % size)) / size);
	}

	@Override
	public int getTileY(float x, float y) { 
		return (int)((y - (y % separation)) / separation);
	}


}
