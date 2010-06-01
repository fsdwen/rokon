package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
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
		fixtureDef.friction = 0.8f;
		sprite.createStaticBox(fixtureDef);
		myScene.add(sprite);
		
		setScene(myScene);
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		int count = 1;		
		long nextAdd = 0;
		
		public void onPreDraw(GL10 gl) {
			if(count >= 80)
				return;
			if(Time.getTicks() > nextAdd) {
				nextAdd = Time.getTicks() + 150;
				float size = 5 + (float)Math.random() * 4;
				createSprite(((float)Math.random() * 40), 1, size, size);
				Debug.print("Now " + count++ + " circles");
			}
		}
		
		private void createSprite(float x, float y, float width, float height) {
			sprite = new Sprite(x, y, width, height);
			sprite.setTexture(texture);
			sprite.createDynamicCircle();
			sprite.setRGB((float)Math.random(), (float)Math.random(), (float)Math.random());
			add(sprite);
		}
		
	};
	
}