package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Loading a Texture and applying it to a FixedBackground
 */
public class Example2 extends RokonActivity {
	
	public Texture backgroundTexture;
	public TextureAtlas atlas;
	
	public FixedBackground background;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		backgroundTexture = new Texture("graphics/backgrounds/beach.png");
		atlas = new TextureAtlas(512, 512);
		atlas.insert(backgroundTexture);
		TextureManager.load(atlas);
		background = new FixedBackground(backgroundTexture);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
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