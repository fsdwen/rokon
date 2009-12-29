package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.stickycoding.Rokon.OpenGL.RokonRenderer;


/**
 * The emitter is the spawning point for particles, much work is still needed to be done here
 * @author Richard
 */
public class Emitter extends DynamicObject {
	public static int MAX_PARTICLES = 100;
	public static int MAX_PARTICLE_MODIFIERS = 15;
	
	private Particle[] particleArr = new Particle[MAX_PARTICLES];
	
	private boolean _dead = false;
	private float _rate;
	private Texture _texture;
	private int i, j, k, w, x;
	private TextureBuffer _texBuffer;
	
	private ParticleModifier[] _particleModifier;

	/**
	 * Create's a point-emitter
	 * @param x
	 * @param y
	 * @param rate number of particles created per second
	 * @param texture texture of each particle
	 */
	public Emitter(float x, float y, float rate, Texture texture) {
		this(x, x, y, y, rate, texture);
	}
	
	/**
	 * Creates an emitter as a rectangle
	 * @param x1 Top left of spawn rect
	 * @param x2 Bottom right of spawn rect
	 * @param y1 Top left of spawn rect
	 * @param y2 Bottom right of spawn rect
	 * @param rate number of particles created per second
	 * @param texture texture of each particle
	 */
	public Emitter(float x1, float x2, float y1, float y2, float rate, Texture texture) {
		super(x1, y1, x2 - x1, y2 - y1);
		_rate = (1 / rate) * 1000;
		_texture = texture;
		_texBuffer = new TextureBuffer(texture);
		_particleModifier = new ParticleModifier[MAX_PARTICLE_MODIFIERS];
		setLastUpdate();
	}
	
	/**
	 * @return the number of particles emitted per second
	 */
	public float getRate() {
		return _rate;
	}
	
	/**
	 * Sets the rate at which Particle's are spawned from this Emitter
	 * @param rate Particle's per second
	 */
	public void setRate(float rate) {
		_rate = rate;
	}
	
	/**
	 * Lets the engine know to remove this emitter on the next loop
	 * @param mark
	 */
	public void markForDelete(boolean mark) {
		_dead = mark;
	}
	
	/**
	 * @return true if this Emitter is ready to be removed from the engine
	 */
	public boolean isDead() {
		return _dead;
	}
	
	private void _spawn() {
		spawnParticle(new Particle(this, getX() + ((float)Math.random() * getWidth()), getY() + ((float)Math.random() * getHeight()), 0, 0));
	}
	
	/**
	 * @param particle Particle object to be created
	 */
	public void spawnParticle(Particle particle) {
		j = -1;
		for(i = 0; i < MAX_PARTICLES; i++)
			if(particleArr[i] == null)
				j = i;
		if(j == -1) {
			Debug.print("TOO MANY PARTICLES");
			return;
		}
		particleArr[j] = particle;
		for(w = 0; w < _particleModifier.length; w++)
			_particleModifier[w].onCreate(particle);
	}
	
	private long _timeDiff;
	private int _count;
	private void _updateSpawns() {
		_timeDiff = Rokon.time - getLastUpdate();
		_count = Math.round(_timeDiff / _rate);
		if(_count > 0) {
			Debug.print("Spawning " + _count + " after " + _timeDiff);
			for(i = 0; i < _count; i++)
				_spawn();
			setLastUpdate();
		}
	}
	
	public void drawFrame(GL10 gl) {
		_updateSpawns();
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_DST_ALPHA);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texBuffer.getBuffer());
		gl.glVertexPointer(2, GL11.GL_FLOAT, 0, RokonRenderer.vertexBuffer);

		_texture.select(gl);
		
		for(i = 0; i < MAX_PARTICLES; i++) {
			if(particleArr[i] != null) {
				updateParticle(particleArr[i]);
				particleArr[i].updateMovement();
				if(particleArr[i].dead)
					particleArr[i] = null;
				else {
					if(particleArr[i].getX() + particleArr[i].getWidth() < 0 || particleArr[i].getX() > Rokon.getRokon().getWidth() || particleArr[i].getY() + particleArr[i].getHeight() < 0 || particleArr[i].getY() > Rokon.getRokon().getHeight()) {
						if(Rokon.getRokon().isForceOffscreenRender()) {
							gl.glLoadIdentity();
							gl.glTranslatef(particleArr[i].getX(), particleArr[i].getY(), 0);
							gl.glScalef(particleArr[i].getWidth(), particleArr[i].getHeight(), 0);
							gl.glColor4f(particleArr[i].red, particleArr[i].green, particleArr[i].blue, particleArr[i].alpha);
							gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
						}
					} else {
						gl.glLoadIdentity();
						gl.glTranslatef(particleArr[i].getX(), particleArr[i].getY(), 0);
						gl.glScalef(particleArr[i].getWidth(), particleArr[i].getHeight(), 0);
						gl.glColor4f(particleArr[i].red, particleArr[i].green, particleArr[i].blue, particleArr[i].alpha);
						gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
					}
				}
			}
		}
		
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Removes a particle from this Emitter and the memory
	 * @param particle
	 */
	public void removeParticle(Particle particle) {
		for(x = 0; x < particleArr.length; x++)
			if(particleArr[x] == particle) {
				particleArr[x] = null;
				return;
			}
	}
	
	/**
	 * @return the current count of Particle's in the Emitter
	 */
	public int particleCount() {
		k = 0;
		for(i = 0; i < MAX_PARTICLES; i++)
			if(particleArr[i] != null)
				k++;
		return k;
	}
	
	/**
	 * @return true if particle count is zero
	 */
	public boolean noParticles() {
		for(i = 0; i < MAX_PARTICLES; i++)
			if(particleArr[i] != null)
				return false;
		return true;
	}
	
	/**
	 * Updates a specific particle by applying all current ParticleModifier's
	 * @param particle
	 */
	public void updateParticle(Particle particle) {
		for(w = 0; w < _particleModifier.length; w++)
			_particleModifier[w].onUpdate(particle);
	}
	
	/**
	 * Add's a ParticleModifer to the current collection
	 * @param particleModifier
	 */
	public void addParticleModifier(ParticleModifier particleModifier) {
		for(w = 0; w < _particleModifier.length; w++)
			if(_particleModifier[w] == null) {
				_particleModifier[w] = particleModifier;
				return;
			}
	}
	
	/**
	 * Removes a ParticleModifier from the Emitter, found by value
	 * @param particleModifier
	 */
	public void removeParticleModifier(ParticleModifier particleModifier) {
		for(w = 0; w < _particleModifier.length; w++)
			if(_particleModifier[w] == particleModifier) {
				_particleModifier[w] = null;
				return;
			}
	}
	
	/**
	 * Sets the ParticleModifier's for the Emitter, can be used with any length of array
	 * @param particleModifier
	 */
	public void setParticleModifiers(ParticleModifier[] particleModifier) {
		_particleModifier = particleModifier;
	}
}
