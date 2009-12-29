package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Applying a Texture to a Sprite
 */
public class Example3 extends RokonActivity {

	public FixedBackground background;
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;	
	public Texture carTexture;
	
	public Sprite carSprite;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 1024);
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
	public void onGameLoop() {

	} 
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}