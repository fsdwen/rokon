package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Emitter;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.ParticleModifiers.AccelerateParticle;
import com.stickycoding.Rokon.ParticleModifiers.Expire;
import com.stickycoding.Rokon.ParticleModifiers.ParticleDimensions;

/**
 * @author Richard
 * Basic usage of the Emitter and Particle system
 */
public class Example16 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public FixedBackground background;
	
	public Texture carTexture;
	public Emitter carEmitter;
	
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
		carEmitter = new Emitter(150, 150, 1, carTexture);
		carEmitter.addParticleModifier(new ParticleDimensions(50, 50, 50, 50));
		carEmitter.addParticleModifier(new Expire(600, 1200));
		carEmitter.addParticleModifier(new AccelerateParticle(0, 0, -100, -200));
	}

	@Override
	public void onLoadComplete() {
		rokon.setBackground(background);
		rokon.addEmitter(carEmitter);
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