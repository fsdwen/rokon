package com.stickycoding.rokon.tileenginee;

import com.stickycoding.rokon.Scene;

/**
 * SquareTiledLayer.java
 * A very basic square-based rectangular layer
 * 
 * @author Richard
 */
public class SquareLayer extends RectangularLayer {

	public SquareLayer(Scene parentScene, int maximumDrawableObjects, float size) { 
		super(parentScene, maximumDrawableObjects, size, size);
	}

}
