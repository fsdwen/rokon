//	Post processing
package com.stickycoding.RokonExamples;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.SpriteModifiers.Resonate;

/**
 * @author Richard
 * This is an advanced tutorial, showing a possible implementation of the ByteBuffer Texture. The screen can be grabbed and pasted onto a new texture, this is used for a Sprite
 */
public class Example18 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public Texture carTexture;
	public FixedBackground background;
	public Sprite carSprite;
	
	TextureAtlas atlas2;
	Sprite screenSprite;
	boolean grabScreen = false;
	Texture screenTex;
	
    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 512);
		atlas.insert(backgroundTexture = new Texture("graphics/backgrounds/beach.png"));
		atlas.insert(carTexture = new Texture("graphics/sprites/car.png"));
		TextureManager.load(atlas);
		background = new FixedBackground(backgroundTexture);
		carSprite = new Sprite(80, 180, carTexture);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addSprite(carSprite);
	}
	
	@Override
	public void onTouchUp(int x, int y, boolean hotspot) {
		grabScreen = true;
	}

	@Override
	public void onAfterDraw(GL10 gl) {
		if(grabScreen) {
			ByteBuffer buffer = ByteBuffer.allocate(rokon.getWidth() * rokon.getHeight() * 4);
			gl.glReadPixels(0, 0, rokon.getWidth(), rokon.getHeight(), GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, buffer);
			atlas2 = new TextureAtlas(512, 512);
			atlas2.insert(screenTex = new Texture(buffer, rokon.getWidth(), rokon.getHeight(), true), 0, 0);
			TextureManager.load(atlas2);
			screenSprite = new Sprite((float)(Math.random() * 320) - 40, (float)(Math.random() * 480) - 60, 80, 120, screenTex);
			rokon.addSprite(screenSprite, 2);
			grabScreen = false;
		}
	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}