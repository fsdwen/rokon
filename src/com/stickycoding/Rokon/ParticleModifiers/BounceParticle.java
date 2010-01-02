package com.stickycoding.Rokon.ParticleModifiers;

import com.stickycoding.Rokon.Particle;
import com.stickycoding.Rokon.ParticleModifier;

/**
 * Bounces the particle upwards from a horizontal floor
 * @author Richard
 */
public class BounceParticle extends ParticleModifier {
	
	private float _floor, _bounceFactor;
	
	public BounceParticle(float floor, float bounceFactor) {
		_floor = floor;
		_bounceFactor = bounceFactor;
	}

	public void onUpdate(Particle particle) {
		if(particle.getVelocityY() > 0)
			if(particle.getY() >= _floor)
				particle.setVelocityY(-1 * particle.getVelocityY() * _bounceFactor);
	}
}
