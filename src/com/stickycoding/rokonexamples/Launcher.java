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
			Debug.print("TOok " + (System.currentTimeMillis() - startTime));
			Debug.print("@YESSS! x=" + x + " y=" + y);
		}
		
		long startTime;
		
		public void sprite_onTouch() {
			Debug.print("NOOOOOOOaasdfasdfasdfaO");
		}
		
		public void sprite_onTouch(int a) {
			Debug.print("NOOOOOOOO!");
		}
		
		@Override
		public void onTouchDown(DrawableObject object, float x, float y, MotionEvent event) {
			invoke("sprite_onTouch", new Class[] { int.class }, new Object[] { 1 } );
			/*Debug.print("TOUCH " + object.getName());
			if(object.getName() != null) {
				Class<?> c = this.getClass();
				Object t = this;
				
				Method[] allMethods = c.getDeclaredMethods();
				for(Method m : allMethods) {
					if(m.getName().equals(object.getName() + "_onTouch")) {
						Debug.print("Method = " + m.getName());
						Class[] params = m.getParameterTypes();
						for(Class p : params) {
							Debug.print("Param = " + p.getName());
							if(p == float.class) { 
								Debug.print(" isfloat");
							}
							if(p == MotionEvent.class) {
								Debug.print(" isMotionEvent");
							}
						}
						for(int i = 0; i < )
						if(params == new Class[] { float.class, float.class, MotionEvent.class }) {
							Debug.print("  @@@Aaaaa");
							try {
								m.invoke(this, null);
							} catch (Exception e) {
								Debug.error("eh?");
								e.printStackTrace();
							}
						}
					}
				}
			}*/
		}
		
		@Override
		public void onTouch(float x, float y, MotionEvent event) {

		}
	};
	
}