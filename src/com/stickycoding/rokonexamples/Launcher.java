package com.stickycoding.rokonexamples;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import android.view.MotionEvent;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.DrawableObject;
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
	}
	
	public void onLoadComplete() {
		Debug.print("Loading is complete");

		Texture texture = new Texture("face.png");
		
		myScene.useTexture(texture);
		
		sprite = new Sprite(50, 50, 100, 100);
		sprite.setTexture(texture);
		sprite.setName("sprite");
		sprite.setTouchable();
		myScene.add(sprite);
		
		 
		setScene(myScene);
		
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		public void sprite_onTouch(float x, float y, MotionEvent event) {
			Debug.print("@YESSS! x=" + x + " y=" + y);
		}
		
		@Override
		public void onTouchDown(DrawableObject object, float x, float y, MotionEvent event) {
			
		}
		
		@Override
		public void onTouch(float x, float y, MotionEvent event) {

		}
	};
	
}