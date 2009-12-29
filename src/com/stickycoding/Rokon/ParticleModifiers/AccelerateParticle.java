package com.stickycoding.Rokon.ParticleModifiers;

import com.stickycoding.Rokon.Particle;
import com.stickycoding.Rokon.ParticleModifier;

/**
 * @author Richard
 * Accelerates a particle in any direction
 * This may be fixed, or between 2 random values
 */
public class AccelerateParticle extends ParticleModifier {
	
	private float _minAccelerationX, _maxAccelerationX;
	private float _minAccelerationY, _maxAccelerationY;
	
	public AccelerateParticle(float accelerationX, float accelerationY) {
		this(accelerationX, accelerationX, accelerationY, accelerationY);
	}
	
	public AccelerateParticle(float minAccelerationX, float maxAccelerationX, float minAccelerationY, float maxAccelerationY) {
		_minAccelerationX = minAccelerationX;
		_maxAccelerationX = maxAccelerationX;
		_minAccelerationY = minAccelerationY;
		_maxAccelerationY = maxAccelerationY;
	}

	public void onCreate(Particle particle) {
		particle.accelerate(((float)Math.random() * (_maxAccelerationX - _minAccelerationX)) + _minAccelerationX, ((float)Math.random() * (_maxAccelerationY - _minAccelerationY)) + _minAccelerationY);
	}
}
