package com.stickycoding.rokon;

/**
 * TextTexture.java
 * A texture specifically for bitmap fonts
 * 
 * @author Richard
 */
public class TextTexture extends Texture {

	public TextTexture(String filename, String characters, int columns) {
		super(filename, columns, columns);
	}
	
	public TextTexture(String filename, String characters) {
		this(filename, characters, 1);
	}

}
