package com.stickycoding.Rokon.ParticleModifiers;

import com.stickycoding.Rokon.Particle;
import com.stickycoding.Rokon.ParticleModifier;

/**
 * @author Richard
 * Causes a particle to expire after a certain time
 * This may be fixed, or between 2 random points
 */
public class Expire extends ParticleModifier {
	
	private int _minLife, _maxLife;
	
	/**
	 * @param life milliseconds to expiration after spawning
	 */
	public Expire(int life) {
		_minLife = life;
		_maxLife = life;
	}
	
	/**
	 * Causes a particle to expire randomly between two given times
	 * @param minLife milliseconds of minimum life
	 * @param maxLife milliseconds of maximum life
	 */
	public Expire(int minLife, int maxLife) {
		_minLife = minLife;
		_maxLife = maxLife;
	}
	
	public void onCreate(Particle particle) {
		particle.setProperty(1, (int)((Math.random() * (_maxLife - _minLife)) + _minLife));
	}
	
	public void onUpdate(Particle particle) {
		if(particle.getAge() > particle.getPropertyInt(1))
			particle.kill();
	}

}
