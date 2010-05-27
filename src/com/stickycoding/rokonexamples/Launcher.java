package com.stickycoding.rokonexamples;

import android.view.MotionEvent;

import com.stickycoding.rokon.Callback;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.DrawableObject;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.Rokon;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.Texture;

public class Launcher extends RokonActivity {
	
	public Sprite sprite;

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

		Texture texture = new Texture("face.png");
		
		//myScene.useTexture(texture);
		
		sprite = new Sprite(50, 50, 100, 100);
		sprite.setTexture(texture);
		sprite.setName("sprite");
		sprite.setTouchable();
		myScene.add(sprite);
		myScene.useInvoke();
		
		 
		setScene(myScene);
		
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		public void sprite_onTouchDown(float x, float y, MotionEvent event) {
			startMoving();
		}
		
		public void sprite_onMoveToComplete() {
			Debug.print("GOT THERE");
		}
		
		public void startMoving() {
			sprite.moveTo(300, 600, 2500, Movement.SMOOTH, new Callback("reverse"));
		}
		
		public void reverse() {
			sprite.moveTo(50, 50, 1000, Movement.SMOOTH, new Callback("startMoving"));
		}
		
	};
	
}