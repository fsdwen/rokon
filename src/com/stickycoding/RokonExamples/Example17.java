//	Custom Tile Animation
package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

public class Example17 extends RokonActivity {
	
	public TextureAtlas atlas;
	public FixedBackground background;
	
	public Texture [] narutoTexture = new Texture[2];
	public Sprite [] narutoSprite = new Sprite[5];
	
    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 1024);
		atlas.insert(narutoTexture[0] = new Texture("graphics/sprites/naruto.png"));
		atlas.insert(narutoTexture[1] = new Texture("graphics/backgrounds/beach.png"));
		narutoTexture[0].setTileSize(40, 40);
		TextureManager.load(atlas);

		narutoSprite[0] = new Sprite(100, 100, 40, 40, narutoTexture[0]);
    	narutoSprite[1] = new Sprite(140, 100, 40, 40, narutoTexture[0]);
    	narutoSprite[2] = new Sprite(100, 140, 40, 40, narutoTexture[0]);
    	narutoSprite[3] = new Sprite(140, 140, 40, 40, narutoTexture[0]);
    	narutoSprite[4] = new Sprite(180, 140, 40, 40, narutoTexture[0]);
        rokon.addSprite(narutoSprite[0]);
        rokon.addSprite(narutoSprite[1]);
        rokon.addSprite(narutoSprite[2]);
        rokon.addSprite(narutoSprite[3]);
        rokon.addSprite(narutoSprite[4]);
        int animeNaruto0[] = {2,13,2,24};
        int animeNaruto1[] = {6,17};
        int animeNaruto2[] = {37,48,37,59};
        int animeNaruto3[] = {9,20,9,31};
        int animeNaruto4[] = {41,42,43,44};
        narutoSprite[0].animateCustom(animeNaruto3, 200);
        narutoSprite[1].animateCustom(animeNaruto4, 200);
        narutoSprite[2].animateCustom(animeNaruto0, 200);
        narutoSprite[3].animateCustom(animeNaruto1, 200);
        narutoSprite[4].animateCustom(animeNaruto2, 200);
		background = new FixedBackground(narutoTexture[1]);
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