package com.stickycoding.Rokon.ParticleModifiers;

import com.stickycoding.Rokon.Particle;
import com.stickycoding.Rokon.ParticleModifier;

/**
 * Sets velocity of a particle on creation
 * @author Richard
 */
public class ParticleDynamics extends ParticleModifier {

	private float _minVelocityX, _maxVelocityX, _minVelocityY, _maxVelocityY;
	
	public ParticleDynamics(float velocityX, float velocityY) {
		this(velocityX, velocityX, velocityY, velocityY);
	}

	public ParticleDynamics(float minVelocityX, float maxVelocityX, float minVelocityY, float maxVelocityY) {
		_minVelocityX = minVelocityX;
		_maxVelocityX = maxVelocityX;
		_minVelocityY = minVelocityY;
		_maxVelocityY = maxVelocityY;
	}	
	
	public void onCreate(Particle particle) {
		particle.setVelocityX(_minVelocityX + ((float)Math.random() * (_maxVelocityX - _minVelocityX)));
		particle.setVelocityY(_minVelocityY + ((float)Math.random() * (_maxVelocityY - _minVelocityY)));
	}
}
