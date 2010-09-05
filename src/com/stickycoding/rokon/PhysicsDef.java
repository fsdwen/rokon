package com.stickycoding.rokon;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Holds the information needed to create a Body. Stores all info in the memory, to speed up creation time.
 * Used with Physics.fromFile()
 * 
 * @author Richard
 */
public class PhysicsDef {
	
	public static final int MAX_FIXTURE_DEF_COUNT = 64;
	
	public BodyDef bodyDef;
	
	public FixtureDef fixtureDefs[];
	public Shape shapes[];
	
	protected int fixtureCount;
	
	public PhysicsDef() {
		fixtureDefs = new FixtureDef[MAX_FIXTURE_DEF_COUNT];
		shapes = new Shape[MAX_FIXTURE_DEF_COUNT];
		fixtureCount = 0;
	}
	
	public void add(Shape shape, FixtureDef fixtureDef) {
		fixtureDefs[fixtureCount] = fixtureDef;
		shapes[fixtureCount] = shape;
		fixtureCount++;
	}
	
	public int getFixtureCount() {
		return fixtureCount;
	}

}
