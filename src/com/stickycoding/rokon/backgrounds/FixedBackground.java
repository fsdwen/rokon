package com.stickycoding.rokon.backgrounds;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.rokon.Background;
import com.stickycoding.rokon.GLHelper;
import com.stickycoding.rokon.Rokon;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.Window;

/**
 * FixedBackground.java
 * The most basic textured background, a fixed image stretched the size of the screen
 * 
 * @author Richard
 */
public class FixedBackground extends Background {
	
	private Texture texture;
	
	public FixedBackground(Texture texture) {
		this.texture = texture;
	}
	
	public void onDraw(GL10 gl) {
		//Debug.print("DRAWING");
		//gl.glPopMatrix();
	}

}
