package com.stickycoding.rokonexamples;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.DynamicTexture;
import com.stickycoding.rokon.Font;
import com.stickycoding.rokon.MathHelper;
import com.stickycoding.rokon.Movement;
import com.stickycoding.rokon.PhysicalSprite;
import com.stickycoding.rokon.Polygon;
import com.stickycoding.rokon.PolygonSprite;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.TextTexture;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;
import com.stickycoding.rokon.Window;
import com.stickycoding.rokon.audio.RokonAudio;
import com.stickycoding.rokon.audio.RokonMusic;
import com.stickycoding.rokon.audio.SoundFile;
import com.stickycoding.rokon.background.FixedBackground;

public class Launcher extends RokonActivity {
	
	public PhysicalSprite sprite;
	public TextureAtlas atlas;
	public Texture texture;
	public DynamicTexture face;
	public World world;

	public void onCreate() {
		forceFullscreen();
		forcePortrait();
		setGameSize(4.8f, 8.0f);
		setDrawPriority(DrawPriority.PRIORITY_VBO);
		setGraphicsPath("textures/");
		createEngine();
	}
	
	Window window;
	
	public void onLoadComplete() {
		atlas = new TextureAtlas();
		atlas.insert(texture = new Texture("circle.png"));
		
		face = new DynamicTexture("face.png", 3, 2);
		Font font = new Font("fonts/angltrr.ttf");
		TextTexture textTexture = font.createTexture("This text should split into several lines, finger's crossed!", 600);
		atlas.insert(textTexture);
		atlas.complete();

		myScene.setWorld(world = new World(new Vector2(0f, 6f), true));
		
		FixedBackground fb = new FixedBackground(face);
		myScene.setBackground(fb);
		
		sprite = new PhysicalSprite(0, 7.5f, 4.8f, 0.5f);
		sprite.setTexture(face);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10f;
		fixtureDef.friction = 0.2f;
		sprite.createStaticBox(fixtureDef);
		sprite.animate(new int[] { 0, 1 }, 500);
		myScene.add(sprite);
		
		Polygon polygon = new Polygon(new float[] {0, 0, 1, 0, 1, 1, 0, 1 });

		polySprite1 = new PolygonSprite(polygon, 1, 1, 1, 1);
		polySprite2 = new PolygonSprite(polygon, 3, 1, 1, 1);
		polySprite1.setBorder(0, 0, 0, 1);
		polySprite2.setBorder(0, 0, 0, 1);
		myScene.add(1, polySprite1);
		myScene.add(1, polySprite2);
		
		setScene(myScene);
		
		audio = new RokonAudio();
		soundFile = audio.createSoundFile("audio/effects/button.mp3");
		RokonMusic.play("audio/music/music.mp3");
	}
	
	PolygonSprite polySprite1, polySprite2;
	PhysicalSprite aSprite;

	SoundFile soundFile;
	RokonAudio audio;
	
	RokonMusic music;
	
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
		public void onTouch(float x, float y, MotionEvent event, int pointerId) {
			this.x = x;
			this.y = y;

			//soundFile.play();
			
			addNew = true;
		}

		
		boolean tracing = false;
		int traceCount = 0;
		
		@Override
		public void onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
				
				if(MathHelper.intersects(polySprite1, polySprite2)) {
					polySprite1.setRGBA(1, 0, 0, 1);
					polySprite2.setRGBA(1, 0, 0, 1);
				} else {
					polySprite1.setRGBA(1, 1, 1, 1);
					polySprite2.setRGBA(1, 1, 1, 1);
				}
				
			}
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
				createBox(x, y, 0.5f, 0.3f);
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
			sprite.setBorder(true);
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