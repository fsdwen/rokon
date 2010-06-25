package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.Font;
import com.stickycoding.rokon.LineSprite;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.PhysicalSprite;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Sprite;
import com.stickycoding.rokon.TextTexture;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;
import com.stickycoding.rokon.Window;
import com.stickycoding.rokon.background.FixedBackground;

public class Launcher extends RokonActivity {
	
	public PhysicalSprite sprite;
	public TextureAtlas atlas;
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
		atlas = new TextureAtlas();
		atlas.insert(texture = new Texture("circle.png"));
		atlas.insert(face = new Texture("face.png", 3, 2));
		Font font = new Font("fonts/angltrr.ttf");
		TextTexture textTexture = font.createTexture("This text should split into several lines, finger's crossed!", 600);
		atlas.insert(textTexture);
		atlas.complete();

		myScene.setWorld(world = new World(new Vector2(0f, 6f), true));
		
		FixedBackground fb = new FixedBackground(face);
		myScene.setBackground(fb);
		
		window = new Window(0, 0, 4.8f, 8f);
		
		sprite = new PhysicalSprite(0, 7.5f, 4.8f, 0.5f);
		sprite.setTexture(face);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10f;
		fixtureDef.friction = 0.2f;
		sprite.createStaticBox(fixtureDef);
		sprite.animate(new int[] { 0, 1 }, 500);
		myScene.add(sprite);
		myScene.setWindow(window);

		myScene.getLayer(1).ignoreWindow();
		
		sprite = new PhysicalSprite(0, 1, 3, 1);
		sprite.setAlpha(0.3f);
		myScene.add(1, sprite);
		
		myScene.setDefaultLineWidth(2f);	
		
		Sprite text = new Sprite(0, 0, 5, textTexture.getHeight(5));
		text.setTexture(textTexture);
		myScene.add(1, text);
		
		 lineSprite = new LineSprite(1, 3, 2, 1);
		myScene.add(1, lineSprite);
		lineSprite.setLineWidth(1);
		
		setScene(myScene);
	}
	
	LineSprite lineSprite;
	
	@Override
	public void onDestroy() {
		android.os.Debug.stopMethodTracing();
		super.onDestroy();
	}
	
	public Scene myScene = new Scene(2, 128) {
		
		int count = 1;		
		long nextAdd = 0;
		long nextCheck = 0;
		
		boolean addNew = false;
		float x, y;
		
		@Override
		public void onTouchDown(float x, float y, MotionEvent event, int pointerId) {
			this.x = x;
			this.y = y;
			addNew = true;
		}
		
		@Override
		public void onTouch(float x, float y, MotionEvent event, int pointerId) {
			if(pointerId == 0) {
				lineSprite.setLineStart(x, y);
			}
			if(pointerId == 1) {
				lineSprite.setLineEnd(x, y);
			}
		}
		
		boolean tracing = false;
		int traceCount = 0;
		@Override
		public void onKeyDown(int keyCode, KeyEvent event) {
			if(tracing) {
				tracing = false;
				//android.os.Debug.stopMethodTracing();
				//Debug.print("Stop Tracing");
			} else {
				//Debug.print("Start Tracing");
				//android.os.Debug.startMethodTracing("game-" + traceCount++);
				tracing = true;
			}
			//float newWidth = 1f + (float)Math.random() * 4.8f;
			//float newHeight = newWidth / window.getRatio();
			//window.move( (float)Math.random() * 4f, (float)Math.random() * 7f, newWidth, newHeight, 2500 );
		}
		
		public void onPreDraw(GL10 gl) {
			if(addNew) {
				createBox(x, y, 0.5f, 0.5f);
				addNew = false;
			}
			/*}
			if(count < 128) {
				if(Time.getTicks() > nextAdd) {
					nextAdd = Time.getTicks() + 70;
					if(Math.random() < 0.5d) {
						float size = 0.05f + (float)Math.random() * 0.4f;
						createCircle(((float)Math.random() * 4f), 0.1f, size);
					} else {
						createBox(((float)Math.random() * 4f), 0.1f, 0.1f + (float)Math.random() * 0.6f, 0.1f + (float)Math.random() * 0.6f);
					}
				}
			}
			if(Time.getTicks() > nextCheck) {
				for(int i = 0; i < 128; i++) {
					if(getLayer(0).getGameObject(i) != null) {
						if(getLayer(0).getGameObject(i).getY() > gameHeight) {
							getLayer(0).getGameObject(i).remove();
						}
					}
				}
				nextCheck = Time.getTicks() + 10;
			}*/
		}
		
		public void createBox(float x, float y, float width, float height) {
			sprite = new PhysicalSprite(x, y, width, height);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.friction = 0.2f;
			fixtureDef.density = 10f;
			//sprite.setTexture(face);
			sprite.border();
			sprite.createDynamicBox(fixtureDef);
			sprite.setRGB((float)Math.random(), (float)Math.random(), (float)Math.random());
			sprite.fade(0, 1, 250, Movement.SMOOTH);
			add(sprite);
		}
		
		public void createCircle(float x, float y, float radius) {
			sprite = new PhysicalSprite(x, y, radius, radius);
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