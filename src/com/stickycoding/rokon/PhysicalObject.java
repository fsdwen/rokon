package com.stickycoding.rokon;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * PhysicalObject.java
 * An object which can undergo basic collisions
 * 
 * @author Richard
 *
 */
public class PhysicalObject extends DynamicObject {
	
	protected Body body;
	protected BodyDef bodyDef;
	protected boolean usePhysics;

	public PhysicalObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public Body createBody(BodyDef bodyDef, Shape shape) {
		body = parentScene.world.createBody(bodyDef);
		body.createFixture(shape, 10);
		this.bodyDef = bodyDef;
		usePhysics = true;
		return body;
	}
	
	public Body createDynamicBody(Shape shape) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = x;
		bodyDef.position.y = y;
		return createBody(bodyDef, shape);
	}
	
	public Body createStaticBody(Shape shape) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.x = x;
		bodyDef.position.y = y;
		return createBody(bodyDef, shape);
	}
	
	public Body createKinematicBody(Shape shape) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.x = x;
		bodyDef.position.y = y;
		return createBody(bodyDef, shape);
	}
	
	public Body createDynamicBody() {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2, height / 2);
		return createDynamicBody(boxPoly);
	}
	
	public Body createStaticBody() {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width, height);
		return createStaticBody(boxPoly);		
	}
	
	public Body createKinematicBody() {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2, height / 2);
		return createKinematicBody(boxPoly);	
	}
	
	/**
	 * Returns the Body associated with this PhysicalObject
	 * 
	 * @return NULL if none set
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * Sets the Body for this PhysicalObject
	 * Automatically flags usePhysics as TRUE
	 * 
	 * @param body valid Body object
	 */
	public void setBody(Body body) {
		this.body = body;
		usePhysics = true;
	}
	
	/**
	 * Flags the PhysicalObject to use the Box2D physics engine
	 * If the Body object is not set, this will raise an exception on next redraw
	 */
	public void usePhysics() {
		usePhysics = true;
	}
	
	/**
	 * Flags the PhysicalObject to ignore the physics engine for now
	 */
	public void noPhysics() {
		usePhysics = false;
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		
		if(usePhysics) {
			x = body.getPosition().x;
			y = body.getPosition().y;
			rotation = body.getAngle() * 57.2957795f;
		}
	}

}
