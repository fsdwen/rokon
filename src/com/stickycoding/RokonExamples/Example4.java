package com.stickycoding.RokonExamples;

import android.view.KeyEvent;

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
 * This example handles basic touch and key input
 */
public class Example4 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public Texture carTexture;
	
	public FixedBackground background;
	
	public Sprite carSprite;
	public Hotspot carHotspot;
	
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
		carHotspot = new Hotspot(carSprite);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addSprite(carSprite);
		rokon.addHotspot(carHotspot);
	}

	@Override
	public void onGameLoop() {

	} 
	
	
	public void onTouchDown(int x, int y, boolean hotspot) { 
		Debug.print("TOUCH DOWN X=" + x + " Y=" + y);
	}

	public void onTouchUp(int x, int y, boolean hotspot) { 
		Debug.print("TOUCH UP X=" + x + " Y=" + y);
	}
	
	public void onTouch(int x,int y, boolean hotspot) { 
		Debug.print("TOUCH X=" + x + " Y=" + y);
		carSprite.setXY(x, y);
	}
	
	public void onHotspotTouch(Hotspot hotspot) { 
		Debug.print("HOTSPOT");
	}
	
	public void onHotspotTouchUp(Hotspot hotspot) { 
		Debug.print("HOTSPOT UP");
	}
	
	public void onHotspotTouchDown(Hotspot hotspot) { 
		Debug.print("HOTSPOT DOWN");
	}
	
    /**
     * You can handle key events just as you would in a normal Activity, but be aware you should call the superclass method's, they are handled in RokonActivity for passing on to any active Menu's
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	super.onKeyDown(keyCode, event);
    	Debug.print("KEYBOARD DOWN - " + keyCode);
    	return true;
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}