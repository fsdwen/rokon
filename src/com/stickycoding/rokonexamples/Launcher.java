package com.stickycoding.rokonexamples;

import android.view.MotionEvent;

import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.DrawableObject;
import com.stickycoding.rokon.RokonActivity;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TileEngine.HexagonalLayer;
import com.stickycoding.rokon.TileEngine.TiledSprite;

public class Launcher extends RokonActivity {
	
	public TiledSprite sprite;
	public HexagonalLayer layer;

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

		Texture texture = new Texture("hex.png");
		layer = new HexagonalLayer(myScene, 64, 100, 73);
		myScene.setLayer(0, layer);
		
		myScene.useTexture(texture);
		
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 10; j++) {
				sprite = new TiledSprite(layer, 0, 0, 100, 100);
				sprite.setTexture(texture);
				sprite.setTile(i, j);
				myScene.add(sprite);
			}
		}
		
		setScene(myScene);
		
	}
	
	public Scene myScene = new Scene(1, 128) {
		
		@Override
		public void onTouchDown(DrawableObject object, float x, float y, MotionEvent event) {
			//sprite.setTileX(sprite.getTileX() + 1);
		}
		
		@Override
		public void onTouch(float x, float y, MotionEvent event) {
			HexagonalLayer layer = (HexagonalLayer)getLayer(0);
			sprite.setTile(layer.getTileX(x, y), layer.getTileY(x, y));
		}
	};
	
}