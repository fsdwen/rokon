package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.SpriteModifiers.Resonate;

/**
 * @author Richard
 * Pausing and Freezing the engine is useful for a variety of situations, explained here
 */
public class Example12 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture carTexture;
	public Sprite carSprite;
	
	public int state = 0;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 512);
		atlas.insert(carTexture = new Texture("graphics/sprites/car.png"));
		TextureManager.load(atlas);
		carSprite = new Sprite(50, 100, carTexture.getWidth() * 3, carTexture.getHeight() * 3, carTexture);
		carSprite.addModifier(new Resonate(3000, 0.4f, 2f));
	}

	@Override
	public void onLoadComplete() {
		rokon.addSprite(carSprite);
	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onTouchUp(int x, int y, boolean hotspot) {
		Debug.print("PAUSING ---- " + rokon.isPaused());
		if(rokon.isPaused()) {
			rokon.unpause();
		} else {
			rokon.pause();
		}
		// pause() & unpause() are different to freeze() and unfreeze()
		// pause is used to break gameplay, drawing still continues, but any time-based will fail (unless you do them yourself via the render hooks)
		// freeze is used to completely freeze anything from happening, but does not stop the time.
		// This is useful if you have seperate threads handling game logic to the drawing, and helps synchronize.
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
	
}