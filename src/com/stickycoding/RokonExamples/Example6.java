package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.SpriteModifiers.Resonate;

/**
 * @author Richard
 * Utilises some of the SpriteModifiers - these are great ways of organising any actions or changes to a large number of Sprite's
 */
public class Example6 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture carTexture;
	public Sprite carSprite;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 512);
		atlas.insert(backgroundTexture = new Texture("graphics/backgrounds/beach.png"));
		atlas.insert(carTexture = new Texture("graphics/sprites/car.png"));
		TextureManager.load(atlas);
		
		background = new FixedBackground(backgroundTexture);
		carSprite = new Sprite(80, 180, carTexture);
		carSprite.addModifier(new Resonate(2000, 2f, 3f));
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