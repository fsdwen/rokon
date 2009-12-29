package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;

/**
 * @author Richard
 * More advanced texture use, explaining how and when to use textureSplit to seperate your textures into different atlas's
 */
public class Example11 extends RokonActivity {
	
	public TextureAtlas atlas, atlas2;
	public Texture carTexture1, carTexture2;
	public Sprite carSprite1, carSprite2;
	
	public int state = 0;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		// Texture's on the hardware are limited to 1024x1024 pixels.
		// The number of textures you can have is limited by the devices hardware, usually 16mb
		// In order to draw from two texture atlas's at once, the engine has to switch between them each frame.
		// This severely limits your FPS, often bringing it below 20fps.
		// It makes sense therefore, to seperate out your texture atlases sensibly, in order to keep high FPS.
		// Having one atlas for your menu's, and seperate levels is a good idea.
		// By default, Rokon will split up into a new atlas when it is full.
		// You can specify points where you want it to split, as shown below.

		// It's unlikely you will see much difference in FPS in this example - the textures are very small and only 2 sprites.
		// You will just have to believe that it makes a difference when your game is much bigger ;)
	
		atlas = new TextureAtlas(512, 512);
		atlas.insert(carTexture1 = new Texture("graphics/sprites/car.png"));

		atlas2 = new TextureAtlas(512, 512);
		atlas2.insert(carTexture2 = new Texture("graphics/sprites/car.png"));
		
		TextureManager.load(atlas);
		TextureManager.load(atlas2);
		
		carSprite1 = new Sprite(50, 100, carTexture1);
		carSprite2 = new Sprite(250, 100, carTexture2);
		
		rokon.fps(true);
	}

	@Override
	public void onLoadComplete() {
		rokon.addSprite(carSprite1);
		rokon.addSprite(carSprite2);
	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onTouchUp(int x, int y, boolean hotspot) {
		state++;
		if(state > 2)
			state = 0;
		if(state == 0) {
			carSprite1.setVisible(true);
			carSprite2.setVisible(true);
		}
		if(state == 1) {
			carSprite1.setVisible(false);
			carSprite2.setVisible(true);
		}
		if(state == 2) {
			carSprite1.setVisible(true);
			carSprite2.setVisible(false);
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
	
}