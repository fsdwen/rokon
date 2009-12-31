//	Polygon Collision
package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.ParallaxBackground;

/**
 * @author Richard
 * A basic 2 polygon collision test
 */
public class Example20 extends RokonActivity {
	
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
		rokon.setBackgroundColor(0, 0, 0);
	}

	@Override
	public void onLoadComplete() {

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