package com.stickycoding.rokondonatefree;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.PhysicalSprite;
import com.stickycoding.rokon.Scene;
import com.stickycoding.rokon.Time;
import com.stickycoding.rokon.background.FixedBackground;

public class DonateScene extends Scene {
	
	private static final int BOX_COUNT = 36;
	
	private World world;
	private FixedBackground background;
	private Vector2 gravity;
	
	private PhysicalSprite[] wall, box;
	
	private long nextGravityShift = 0;
	
	public DonateScene() {
		super(3, new int[] { 4, BOX_COUNT, 1 });
		setBackground(background = new FixedBackground(Textures.background));
		setWorld(world = new World(gravity = new Vector2(0, 0), false));
		createWalls();
		createBoxes();
	}
	
	private void createWalls() {
		wall = new PhysicalSprite[4];
		
		wall[0] = new PhysicalSprite(0, -1, Donate.GAME_WIDTH, 1);
		wall[0].createStaticBox();
		add(0, wall[0]);
		
		wall[1] = new PhysicalSprite(Donate.GAME_WIDTH, 0, 1, Donate.GAME_HEIGHT);
		wall[1].createStaticBox();
		add(0, wall[1]);
		
		wall[2] = new PhysicalSprite(0, Donate.GAME_HEIGHT, Donate.GAME_WIDTH, 1);
		wall[2].createStaticBox();
		add(0, wall[2]);
		
		wall[3] = new PhysicalSprite(-1, 0, 1, Donate.GAME_HEIGHT);
		wall[3].createStaticBox();
		add(0, wall[3]);	
	}
	
	private void createBoxes() {
		box = new PhysicalSprite[BOX_COUNT];
		for(int i = 0; i < BOX_COUNT; i++) {
			box[i] = new PhysicalSprite( (float)Math.random() * Donate.GAME_WIDTH, (float)Math.random() * Donate.GAME_HEIGHT, 0.2f + (float)Math.random() * 0.5f, 0.2f + (float)Math.random() * 0.5f );
			box[i].setTexture(Textures.box);
			box[i].setRGBA((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
			box[i].createDynamicBox();
			box[i].setBorder(0, 0, 0, 1);
			add(1, box[i]);
		}
	}
	
	@Override
	public void onGameLoop() {
		if(Time.getTicks() > nextGravityShift) {
			nextGravityShift = Time.getTicks() + 1000;
			gravity.set(8 - (float)Math.random() * 16, 8 - (float)Math.random() * 16);
			world.setGravity(gravity);
		}
	}

}
