package com.stickycoding.rokon;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Physics.java
 * Contains some static variables for the Box2D engine
 * (Kindly written by the libgdx project)
 * 
 * @author Richard
 */

public class Physics {
	
	public static PolygonShape polygonShape = new PolygonShape();
	public static World world;
	
	public static final float BOX2D_FACTOR = 10;
	
	public static void load() {

	}
	

}
