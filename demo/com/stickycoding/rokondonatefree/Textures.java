package com.stickycoding.rokondonatefree;

import com.stickycoding.rokon.Texture;
import com.stickycoding.rokon.TextureAtlas;

public class Textures {

	public static TextureAtlas atlas;
	public static Texture background, box;
	
	public static void load() {
		atlas = new TextureAtlas();
		atlas.insert(background = new Texture("background.png"));
		atlas.insert(box = new Texture("box.png"));
		atlas.complete();
	}
}
