package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.Rokon;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;

public class Launcher extends RokonActivity {
	
	public Sprite sprite;
	public Texture texture;
	public World world;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(480, 800);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		setGraphicsPath("textures/");
		createEngine();
		Rokon.verboseMode();
	}
	
	public void onLoadComplete() {
		Debug.print("Loading is complete");

		texture = new Texture("face.png");

		myScene.setWorld(world = new World(0f, 300f));
		
		sprite = new Sprite(0, 750, 480, 50);
		sprite.setTexture(texture);
		myScene.add(sprite);
		sprite.createStaticBody();
		
		sprite = new Sprite(50, 50, 100, 100);
		sprite.setTexture(texture);
		myScene.add(sprite);
		sprite.createDynamicBody();
		
		myScene.useInvoke();	
		
		setScene(myScene);
		
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		boolean needsNew = false;
		float newX, newY;
		
		public void onPreDraw(GL10 gl) {
			if(needsNew) {
				needsNew = false;
				Debug.print("Creating!");
				sprite = new Sprite(newX, newY, 100, 100);
				sprite.setTexture(texture);
				add(sprite);
				sprite.createDynamicBody();
			}
		}
		
		public void onTouchDown(float x, float y, MotionEvent event) {
			needsNew = true;
			newX = x;
			newY = y;
		}
		
	};
	
}