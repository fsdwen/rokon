package com.stickycoding.Rokon;


/**
 * Represents a Particle inside an Emitter system
 * @author Richard
 */

public class Particle extends DynamicObject {
	public static final int MAX_PROPERTIES = 20;
	
	public boolean dead = false;
	
	private Emitter _emitter;
	public float alpha;
	public float red;
	public float green;
	public float blue;
	private long _spawnTime;
	
	private int i;
	private int[] _propertyId;
	private Object[] _propertyValue;
	
	private long _deathTime;
	
	/**
	 * Creates a particle with basic coordinates
	 * @param emitter
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Particle(Emitter emitter, float x, float y, float width, float height) {
		super(x, y, width, height);
		_emitter = emitter;
		this.alpha = 1;
		red = 1;
		green = 1;
		blue = 1;
		_spawnTime = Rokon.time;
		_propertyId = new int[MAX_PROPERTIES];
		_propertyValue = new Object[MAX_PROPERTIES];
	}
	
	/**
	 * @return the game time that the particle was spawned
	 */
	public long getSpawnTime() {
		return _spawnTime;
	}
	
	/**
	 * @return milliseconds since spawning
	 */
	public long getAge() {
		return Rokon.time - _spawnTime;
	}
	
	/**
	 * Sets a property to any Object
	 * @param key
	 * @param value
	 */
	public void setProperty(int key, Object value) {
		for(i = 0; i < MAX_PROPERTIES; i++)
			if(_propertyValue[i] == null) {
				_propertyId[i] = key;
				_propertyValue[i] = value;
				return;
			}
	}
	
	/**
	 * Retrieves a property value Object
	 * @param key
	 * @return NULL if key is not found
	 */
	public Object getProperty(int key) {
		for(i = 0; i < MAX_PROPERTIES; i++)
			if(_propertyId[i] == key)
				return _propertyValue[i];
		return null;
	}
	
	/**
	 * Retrieves a property value as an Integer
	 * @param key
	 * @return -1 if key is not found
	 */
	public int getPropertyInt(int key) {
		for(i = 0; i < MAX_PROPERTIES; i++)
			if(_propertyId[i] == key)
				return (Integer)_propertyValue[i];
		return -1;
	}
	
	/**
	 * @return the Emitter object that spawned this Particle
	 */
	public Emitter getEmitter() {
		return _emitter;
	}
	
	/**
	 * Flags a particle as dead, it will be removed from memory on the next loop 
	 */
	public void kill() {
		dead = true;
	}
	
	public long getDeathTime() {
		return _deathTime;
	}
	
	public void setDeathTime(long deathTime) {
		_deathTime = deathTime;
	}
	
}
