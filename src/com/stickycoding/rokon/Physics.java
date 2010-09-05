package com.stickycoding.rokon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Physics.java
 * Contains some static variables for the Box2D engine
 * (Kindly written by the libgdx project)
 * 
 * @author Richard
 */

public class Physics {
	
	/**
	 * The Box2D World which is in use
	 */
	public static World world;
	
	/**
	 * Creates a Body from a .json file
	 * 
	 * @param filename
	 * @param obj DimensionalObject to map this to
	 * @return
	 */
	public static PhysicsDef fromFile(String filename, DimensionalObject obj) {
		PhysicsDef physicsDef = null;
		try {
			InputStream stream = Rokon.currentActivity.getAssets().open(filename);
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader in = new BufferedReader(reader);
			String content = "";
			String line;
			line = in.readLine();
			while(line != null) {
				content += line + "\n";
				line = in.readLine();
			}
			stream.close();
			
			JSONObject jsonObj = new JSONObject(content);			
			String type = jsonObj.getString("type");
			if(!jsonObj.has("type")) {
				throw new Exception("Missing type");
			}
			JSONArray fixtures = jsonObj.getJSONArray("fixtures");
			if(fixtures == null) {
				throw new Exception("No fixtures definition, what's the point?");
			}
			
			physicsDef = new PhysicsDef();
			
			BodyDef bodyDef = new BodyDef();
			if(type.equals("dynamic")) bodyDef.type = BodyDef.BodyType.DynamicBody;
			if(type.equals("kinematic")) bodyDef.type = BodyDef.BodyType.KinematicBody;
			if(type.equals("static")) bodyDef.type = BodyDef.BodyType.StaticBody;
			if(bodyDef.type == null) throw new Exception("Unknown type=" + type);
		
			physicsDef.bodyDef = bodyDef;
			
			
			for(int i = 0; i < fixtures.length(); i++) {
				FixtureDef fixtureDef = new FixtureDef();
				Shape shape = null;
				
				JSONObject jsonFixture = fixtures.getJSONObject(i);
				JSONObject jsonShape = jsonFixture.getJSONObject("shape");
				
				if(jsonShape.getString("type").equals("circle")) {
					shape = new CircleShape();
					CircleShape circleShape = (CircleShape)shape;
					circleShape.setPosition(new Vector2((float)jsonShape.getDouble("x") * obj.getWidth(), (float)jsonShape.getDouble("y") * obj.getHeight()));
					circleShape.setRadius((float)jsonShape.getDouble("radius") * obj.getHeight());
				}
				
				if(jsonShape.getString("type").equals("polygon")) {
					JSONArray jsonX = jsonShape.getJSONArray("x");
					JSONArray jsonY = jsonShape.getJSONArray("y");
					shape = new PolygonShape();
					PolygonShape polyShape = (PolygonShape)shape;
					Vector2[] vertices = new Vector2[jsonX.length()];
					for(int j = 0; j < vertices.length; j++) {
						vertices[j] = new Vector2((float)jsonX.getDouble(j) * obj.getWidth(), (float)jsonY.getDouble(j) * obj.getHeight());
						Debug.print("added vertex x=" + vertices[j].x + " y=" +  vertices[j].y);
					}
					polyShape.set(vertices);
				}

				if(jsonFixture.has("friction")) fixtureDef.friction = (float)jsonFixture.getDouble("friction");
				if(jsonFixture.has("restitution")) fixtureDef.restitution = (float)jsonFixture.getDouble("restitution");
				if(jsonFixture.has("density")) fixtureDef.density = (float)jsonFixture.getDouble("density");
				
				fixtureDef.shape = shape;
				
				physicsDef.add(shape, fixtureDef);
			}
			
			
		} catch (Exception e) {
			Debug.error("Error in bodyFromFile " + filename);
			e.printStackTrace();
		}
		return physicsDef;
	}
	
	/**
	 * Creates an active Body from a PhysicsDef
	 * 
	 * @param physicsDef a filled PhysicsDef object
	 * @param obj the DimensionalObject to map this to
	 * @return the created Body
	 */
	public static Body bodyFromPhysicsDef(PhysicsDef physicsDef, DimensionalObject obj) {
		Body body = null;

		physicsDef.bodyDef.position.x = obj.getX() + obj.getHeight() / 2;
		physicsDef.bodyDef.position.y = obj.getY() + obj.getWidth() / 2;
		
		body = world.createBody(physicsDef.bodyDef);
		
		for(int i = 0; i < physicsDef.fixtureCount; i++) {
			body.createFixture(physicsDef.fixtureDefs[i]);
		}		
		
		return body;
	}
	
	public static boolean bodyContact(Contact contact, Body body1, Body body2) {
		if(body1.getFixtureList().contains(contact.getFixtureA()) && body2.getFixtureList().contains(contact.getFixtureB())) {
			return true;
		} else if(body1.getFixtureList().contains(contact.getFixtureB()) && body2.getFixtureList().contains(contact.getFixtureA())) {
			return true;
		}
		return false;
	}

}
