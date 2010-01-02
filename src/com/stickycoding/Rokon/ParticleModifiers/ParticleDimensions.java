package com.stickycoding.Rokon.ParticleModifiers;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Particle;
import com.stickycoding.Rokon.ParticleModifier;

/**
 * @author Richard
 * Set's a particles dimensions on creation
 * Can be either fixed or random sizes
 */
public class ParticleDimensions extends ParticleModifier {
	
	private float _minWidth, _maxWidth;
	private float _minHeight, _maxHeight;

	public ParticleDimensions(float width, float height) {
		this(width, width, height, height);
	}
	
	public ParticleDimensions(float minWidth, float maxWidth, float minHeight, float maxHeight) {
		_minWidth = minWidth;
		_maxWidth = maxWidth;
		_minHeight = minHeight;
		_maxHeight = maxHeight;
	}
	
	public void onCreate(Particle particle) {
		particle.setWidth(_minWidth + ((float)Math.random() * (_maxWidth - _minWidth)));
		particle.setHeight(_minHeight + ((float)Math.random() * (_maxHeight - _minHeight)));
	}
	
}
