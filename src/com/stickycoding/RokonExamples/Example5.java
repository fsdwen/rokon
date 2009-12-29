package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Uses some of the basic dynamic methods in Sprite
 */
public class Example5 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public Texture carTexture;
	public Texture goTexture;
	
	public FixedBackground background;	
	
	public Sprite carSprite;	
	public Sprite goSprite;
	public Hotspot goHotspot;
	
    public void onCreate() {
    	Debug.warning("CREATED START AGAIN");
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 512);
		backgroundTexture = new Texture("graphics/backgrounds/beach.png");
		carTexture = new Texture("graphics/sprites/car.png");
		goTexture = new Texture("graphics/sprites/go.png");
		atlas.insert(backgroundTexture);
		atlas.insert(carTexture);
		atlas.insert(goTexture);
		TextureManager.load(atlas);
		
		background = new FixedBackground(backgroundTexture);
		carSprite = new Sprite(80, 180, carTexture);
		goSprite = new Sprite(20, 20, goTexture);
		goHotspot = new Hotspot(goSprite);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addSprite(carSprite);
		rokon.addSprite(goSprite);
		rokon.addHotspot(goHotspot);
	}

	@Override
	public void onGameLoop() {

	} 
	
	@Override
	public void onHotspotTouchUp(Hotspot hotspot) {
		if(hotspot.equals(goHotspot)) {
			carSprite.resetDynamics();
			carSprite.setXY(80, 180);
			carSprite.accelerate(5, 0);
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}