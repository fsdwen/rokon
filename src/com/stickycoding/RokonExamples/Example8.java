package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Font;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Text;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;

/**
 * @author Richard
 * Renders TTF fonts
 * 
 * (currently broken)
 */
public class Example8 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Font font;
	
    public void onCreate() {
        createEngine(480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 512);
		atlas.insert(backgroundTexture = new Texture("graphics/backgrounds/beach.png"));
		atlas.insert(font = new Font("fonts/256BYTES.TTF"));
		TextureManager.load(atlas);
		background = new FixedBackground(backgroundTexture);
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		Text text = new Text("Hello world!", font, 50, 50, 32);
		rokon.addText(text);
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