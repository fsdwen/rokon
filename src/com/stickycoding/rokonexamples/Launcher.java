package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.Time;

public class Launcher extends RokonActivity {
	
	public Sprite sprite;
	public Texture texture, face;
	public World world;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(48, 80);
		setDrawPriority(DrawPriority.PRIORITY_NORMAL);
		setGraphicsPath("textures/");
		createEngine();
	}
	
	public void onLoadComplete() {
		texture = new Texture("circle.png");
		face = new Texture("face.png");
		myScene.useTexture(texture);

		myScene.setWorld(world = new World(0f, 10f));
		
		sprite = new Sprite(0, 75, 48, 5);
		sprite.setTexture(face);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10f;
		fixtureDef.friction = 0.2f;
		sprite.createStaticBox(fixtureDef);
		myScene.add(sprite);
		
		setScene(myScene);
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		int count = 1;		
		long nextAdd = 0;
		long nextCheck = 0;
		
		public void onPreDraw(GL10 gl) {
			if(count < 128) {
				if(Time.getTicks() > nextAdd) {
					nextAdd = Time.getTicks() + 150;
					if(Math.random() < 0.5d) {
						float size = 5 + (float)Math.random() * 4;
						createCircle(((float)Math.random() * 40), 1, size);
					} else {
						createBox(((float)Math.random() * 40), 1, 1 + (float)Math.random() * 16, 1 + (float)Math.random() * 16);
					}
				}
			}
			if(Time.getTicks() > nextCheck) {
				for(int i = 0; i < 128; i++) {
					if(getLayer(0).getDrawableObject(i) != null && !getLayer(0).getDrawableObject(i).isOnScreen()) {
						if(getLayer(0).getDrawableObject(i).getY() > gameHeight) {
							getLayer(0).getDrawableObject(i).remove();
							count--;
						}
					}
				}
				nextCheck = Time.getTicks() + 1000;
			}
		}
		
		private void createBox(float x, float y, float width, float height) {
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
		
		private void createCircle(float x, float y, float radius) {
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