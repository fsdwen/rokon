package com.stickycoding.Rokon.ParticleModifiers;

import com.stickycoding.Rokon.Particle;
import com.stickycoding.Rokon.ParticleModifier;
import com.stickycoding.Rokon.Rokon;

/**
 * @author Richard
 * Causes a particle to expire after a certain time
 * This may be fixed, or between 2 random points
 */
public class ExpireParticle extends ParticleModifier {
	
	private int _minLife, _maxLife;
	
	/**
	 * @param life milliseconds to expiration after spawning
	 */
	public ExpireParticle(int life) {
		_minLife = life;
		_maxLife = life;
	}
	
	/**
	 * Causes a particle to expire randomly between two given times
	 * @param minLife milliseconds of minimum life
	 * @param maxLife milliseconds of maximum life
	 */
	public ExpireParticle(int minLife, int maxLife) {
		_minLife = minLife;
		_maxLife = maxLife;
	}
	
	public void onCreate(Particle particle) {
		particle.setDeathTime(Rokon.getTime() + (int)((Math.random() * (_maxLife - _minLife)) + _minLife));
	}

}
