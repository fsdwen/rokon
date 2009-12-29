package com.stickycoding.Rokon.Backgrounds;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Texture;

/**
 * A basic, horizontally scrolling parallax background
 * @author Rokon
 */
public class ParallaxBackground extends Background {
	
	private int _layers;
	private ScrollingBackground[] _background;
	private float[] _scrollFactor;
	
	private float _scrollX;
	private int i;
	
	/**
	 * Creates a ParallaxBackground, define the number of layers to be used - these must be filled before the background is used
	 * @param layers
	 */
	public ParallaxBackground(int layers) {
		_layers = layers;
		_background = new ScrollingBackground[_layers];
		_scrollFactor = new float[_layers];
		_scrollX = 0;
	}
	
	/**
	 * Adds a layer to the ParallaxBackground, defaulting to an offset of 0
	 * @param texture the Texture of the background
	 * @param index the layer index of this Texture, 0 is the background
	 * @param scrollFactor the amount to scroll this layer by for every 1 unit
	 */
	public void addLayer(Texture texture, int index, float scrollFactor) {
		_background[index] = new ScrollingBackground(texture);
		_scrollFactor[index] = scrollFactor;
	}
	
	/**
	 * Adds a layer to the ParallaxBackground
	 * @param texture the Texture of the background
	 * @param index the layer index of this Texture, 0 is the background
	 * @param scrollFactor the amount to scroll this layer by for every 1 unit
	 * @param offset the Y offset for which this layer begins
	 */
	public void addLayer(Texture texture, int index, float scrollFactor, int offset) {
		_background[index] = new ScrollingBackground(texture, offset);
		_scrollFactor[index] = scrollFactor;
	}
	
	/**
	 * Returns the current scroll position of the ParallaxBackground
	 * @return scroll position
	 */
	public float getScroll() {
		return _scrollX;
	}	
	
	/**
	 * Sets the scroll position
	 * @param x scroll position X value
	 */
	public void setScroll(float x) {
		_scrollX = x;
		for(i = 0; i < _layers; i++)
			_background[i].setScroll(x * _scrollFactor[i], 0);
	}
	
	/* (non-Javadoc)
	 * @see com.stickycoding.Rokon.Background#drawFrame(javax.microedition.khronos.opengles.GL10)
	 */
	public void drawFrame(GL10 gl) {
		for(i = 0; i < _layers; i++)
			_background[i].drawFrame(gl);
	}

}
