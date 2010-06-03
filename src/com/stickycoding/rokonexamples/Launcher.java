package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.Time;
import com.stickycoding.rokon.Window;

public class Launcher extends RokonActivity {
	
	public Sprite sprite;
	public Texture texture, face;
	public World world;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(4.8f, 8.0f);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		setGraphicsPath("textures/");
		createEngine();
	}
	
	Window window;
	
	public void onLoadComplete() {
		texture = new Texture("circle.png");
		face = new Texture("face.png", 3, 2);
		myScene.useTexture(texture);

		myScene.setWorld(world = new World(new Vector2(0f, 10f), true));
		
		window = new Window(0, 0, 4.8f, 8f);
		
		sprite = new Sprite(0, 7.5f, 4.8f, 0.5f);
		sprite.setTexture(face);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10f;
		fixtureDef.friction = 0.2f;
		sprite.createStaticBox(fixtureDef);
		myScene.add(sprite);
		myScene.setWindow(window);

		sprite = new Sprite(2f, 0f, 0.5f, 0.5f);
		fixtureDef = new FixtureDef();
		fixtureDef.friction = 0.2f;
		fixtureDef.density = 10f;
		sprite.setTexture(face);
		sprite.createDynamicBox(fixtureDef);
		sprite.fade(0, 1, 250, Movement.SMOOTH);
		myScene.add(sprite);
		
		setScene(myScene);
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		int count = 1;		
		long nextAdd = 0;
		long nextCheck = 0;
		
		boolean addNew = false;
		float x, y;
		
		@Override
		public void onTouchDown(float x, float y, MotionEvent event) {
			this.x = x;
			this.y = y;
			addNew = true;
		}
		
		@Override
		public void onKeyDown(int keyCode, KeyEvent event) {
			float newWidth = 1f + (float)Math.random() * 4.8f;
			float newHeight = newWidth / window.getRatio();
			window.move( (float)Math.random() * 4f, (float)Math.random() * 7f, newWidth, newHeight, 2500 );
		}
		
		public void onPreDraw(GL10 gl) {
			if(addNew) {
				createCircle(x, y, 0.5f);
				addNew = false;
			}
			if(count < 128) {
				if(Time.getTicks() > nextAdd) {
					nextAdd = Time.getTicks() + 10000;
					if(Math.random() < 0.5d) {
						float size = 0.5f + (float)Math.random() * 0.4f;
						createCircle(((float)Math.random() * 4f), 0.1f, size);
					} else {
						createBox(((float)Math.random() * 4f), 0.1f, 0.1f + (float)Math.random() * 1.6f, 0.1f + (float)Math.random() * 1.6f);
					}
				}
			}
			if(Time.getTicks() > nextCheck) {
				for(int i = 0; i < 128; i++) {
					if(getLayer(0).getDrawableObject(i) != null) {
						if(getLayer(0).getDrawableObject(i).getY() > gameHeight) {
							getLayer(0).getDrawableObject(i).remove();
						}
					}
				}
				nextCheck = Time.getTicks() + 10;
			}
		}
		
		public void createBox(float x, float y, float width, float height) {
			sprite = new Sprite(x, y, width, height);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.friction = 0.2f;
			fixtureDef.density = 10f;
			sprite.setTexture(face);
			sprite.createDynamicBox(fixtureDef);
			sprite.setRGB((float)Math.random(), (float)Math.random(), (float)Math.random());
			sprite.fade(0, 1, 250, Movement.SMOOTH);
			add(sprite);
		}
		
		public void createCircle(float x, float y, float radius) {
			sprite = new Sprite(x, y, radius, radius);
			sprite.setTexture(texture);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.friction = 0.2f;
			fixtureDef.density = 10f;
			sprite.createDynamicCircle(fixtureDef);
			sprite.setRGB((float)Math.random(), (float)Math.random(), (float)Math.random());
			sprite.fade(0, 1, 250, Movement.SMOOTH);
			add(sprite);
		}
		
	};
	
}