//	Parallax Background
package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.ParallaxBackground;

/**
 * @author Richard
 * Demonstrates a simple parallax background, moving along at a set speed
 */
public class Example19 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture skyTexture, redTexture, greenTexture, blackTexture;
	public ParallaxBackground background;

	int rate = 50;
	long lastUpdate = 0;
	float timeDiff;
	float modifier;
	
    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 1024);
		atlas.insert(skyTexture = new Texture("graphics/backgrounds/parallax/sky.png"));
		atlas.insert(redTexture = new Texture("graphics/backgrounds/parallax/red.png"));
		atlas.insert(greenTexture = new Texture("graphics/backgrounds/parallax/green.png"));
		atlas.insert(blackTexture = new Texture("graphics/backgrounds/parallax/black.png"));
		TextureManager.load(atlas);
		background = new ParallaxBackground(4);
		background.addLayer(skyTexture, 0, 0.1f, 0);
		background.addLayer(redTexture, 1, 0.3f, 96);
		background.addLayer(greenTexture, 2, 0.7f, 200);
		background.addLayer(blackTexture, 3, 2f, 280);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
	}

	@Override
	public void onGameLoop() {
		if(lastUpdate == 0)
			lastUpdate = Rokon.time;
		else {
			timeDiff = Rokon.time - lastUpdate;
			modifier = timeDiff * rate / 1000;
			background.setScroll(background.getScroll() + modifier);
			lastUpdate = Rokon.time;
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}