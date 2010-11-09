package com.stickycoding.rokondonatefree;

import com.stickycoding.rokon.DrawPriority;
import com.stickycoding.rokon.RokonActivity;


public class Donate extends RokonActivity {
	
	public static final float GAME_WIDTH = 8f;
	public static final float GAME_HEIGHT = 4.8f;
		
	private DonateScene scene;

	public void onCreate() {
		debugMode();
		forceFullscreen();
		forceLandscape();
		setGameSize(GAME_WIDTH, GAME_HEIGHT);
		setDrawPriority(DrawPriority.PRIORITY_VBO);
		setGraphicsPath("textures/");
		createEngine();
	}
	
	public void onLoadComplete() {
		Textures.load();
		setScene(scene = new DonateScene());	
	}
	
	
}