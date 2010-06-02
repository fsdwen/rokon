package com.stickycoding.rokon;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
	
	public Body body;
	public BodyDef bodyDef;
	protected boolean usePhysics;

	public PhysicalObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void createBody(BodyDef bodyDef, FixtureDef fixture) {
		body = Physics.world.createBody(bodyDef);
		body.createFixture(fixture);
		//Debug.print("Prepare destroy");
		//Physics.world.destroyBody(body);
		//Debug.print("Prepare destroy 3");
		this.bodyDef = bodyDef;
		usePhysics = true;
	}
	
	public void createBody(BodyDef bodyDef, Shape shape) {
		body = Physics.world.createBody(bodyDef);
		body.createFixture(shape, 1f);
		this.bodyDef = bodyDef;
		usePhysics = true;
	}
	
	public CircleShape createCircleShape() {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		return circle;
	}
	
	public PolygonShape createBoxShape() {
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(width / 2f, height / 2f);
		return poly;
	}
	
	public void createDynamicBody(FixtureDef fixture) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = x + width / 2f;
		bodyDef.position.y = y + height / 2f;
		createBody(bodyDef, fixture);
	}
	
	public void createDynamicBody(Shape shape) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.x = x + width / 2f;
		bodyDef.position.y = y + height / 2f;
		createBody(bodyDef, shape);
	}
	
	public void createStaticBody(FixtureDef fixture) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.x = x + width / 2f;
		bodyDef.position.y = y + height / 2f;
		createBody(bodyDef, fixture);
	}
	
	public void createStaticBody(Shape shape) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.x = x + width / 2f;
		bodyDef.position.y = y + height / 2f;
		createBody(bodyDef, shape);
	}
	
	public void createKinematicBody(FixtureDef fixture) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.x = x + width / 2f;
		bodyDef.position.y = y + height / 2f;
		createBody(bodyDef, fixture);
	}
	
	public void createKinematicBody(Shape shape) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.x = x + width / 2f;
		bodyDef.position.y = y + height / 2f;
		createBody(bodyDef, shape);
	}
	
	public void createDynamicCircle(FixtureDef fixtureDef) {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		fixtureDef.shape = circle;
		createDynamicBody(fixtureDef);
		circle.dispose();
	}
	
	public void createDynamicCircle() {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		createDynamicBody(circle);
		circle.dispose();
	}
	
	public void createStaticCircle(FixtureDef fixtureDef) {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		fixtureDef.shape = circle;
		createStaticBody(fixtureDef);
		circle.dispose();
	}
	
	public void createStaticCircle() {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		createStaticBody(circle);
		circle.dispose();
	}
	
	public void createKinematicCircle(FixtureDef fixtureDef) {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		fixtureDef.shape = circle;
		createKinematicBody(fixtureDef);
		circle.dispose();
	}
	
	public void createKinematicCircle() {
		CircleShape circle = new CircleShape();
		circle.setRadius(width / 2f);
		createKinematicBody(circle);
		circle.dispose();
	}
	
	public void createDynamicBox(FixtureDef fixtureDef) {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2f, height / 2f);
		fixtureDef.shape = boxPoly;
		createDynamicBody(fixtureDef);
		boxPoly.dispose();
	}
	
	public void createDynamicBox() {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2f, height / 2f);
		createDynamicBody(boxPoly);
		boxPoly.dispose();
	}
	
	public void createStaticBox(FixtureDef fixtureDef) {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2f, height / 2f);
		fixtureDef.shape = boxPoly;
		createStaticBody(fixtureDef);
		boxPoly.dispose();
	}
	
	public void createStaticBox() {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2, height / 2f);
		createStaticBody(boxPoly);	
		boxPoly.dispose();	
	}
	
	public void createKinematicBox(FixtureDef fixtureDef) {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2, height / 2f);
		fixtureDef.shape = boxPoly;
		createKinematicBody(fixtureDef);
		boxPoly.dispose();
	}
	
	public void createKinematicBox() {
		PolygonShape boxPoly = new PolygonShape();
		boxPoly.setAsBox(width / 2, height / 2f);
		createKinematicBody(boxPoly);
		boxPoly.dispose();
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
		if(usePhysics) {
			x = body.getPosition().x - width / 2;
			y = body.getPosition().y - height / 2;
			rotation = body.getAngle() * 57.2957795f;
		} else {
			super.onUpdate();
		}
	}

}
