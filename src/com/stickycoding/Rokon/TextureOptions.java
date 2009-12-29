package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Richard
 * Holds a few variables regarding the texture options, if you aren't sure what these change, just expirement - they might improve things
 */
public class TextureOptions {
	
	public int magFilter, minFilter, texEnv;
	
	public TextureOptions() {
		magFilter = GL10.GL_NEAREST;
		minFilter = GL10.GL_NEAREST;
		texEnv = GL10.GL_MODULATE;
	}
	
	public TextureOptions(int minFilter, int magFilter, int texEnv) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		this.texEnv = texEnv;
	}
	
	public TextureOptions(int minFilter, int magFilter) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		texEnv = GL10.GL_MODULATE;
	}

}
