package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.Rokon;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.Time;

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
	}
	
	public void onLoadComplete() {
		texture = new Texture("face.png");

		myScene.setWorld(world = new World(0f, 300f));
		
		sprite = new Sprite(0, 750, 480, 50);
		sprite.setTexture(texture);
		sprite.createStaticBody();
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
				createSprite(((float)Math.random() * 400), 10, 10 + (float)Math.random() * 40, 10 + (float)Math.random() * 40);
				Debug.print("Now " + count++ + " boxes");
			}
		}
		
		private void createSprite(float x, float y, float width, float height) {
			sprite = new Sprite(x, y, width, height);
			sprite.createDynamicBody();
			sprite.setRGB((float)Math.random(), (float)Math.random(), (float)Math.random());
			add(sprite);
		}
		
	};
	
}