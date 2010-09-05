package com.stickycoding.rokon.background;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.rokon.Background;
import com.stickycoding.rokon.GLHelper;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.device.Graphics;

/**
 * FixedBackground.java
 * The most basic textured background, a fixed image stretched the size of the screen
 * 
 * @author Richard
 */
public class DrawTexBackground extends Background {
	
	private Texture texture;
	
	public DrawTexBackground(Texture texture) {
		this.texture = texture;
	}
	
	public void onDraw(GL10 gl) {
		super.onDraw(gl);
		GLHelper.drawTex(texture, 0, 0, Graphics.getWidthPixels(), Graphics.getHeightPixels());
		//bg.onDraw(gl);
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
		//bg.setTexture(texture);
	}

}
