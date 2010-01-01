//	Polygon Collision
package com.stickycoding.RokonExamples;

import com.stickycoding.Rokon.Collider;
import com.stickycoding.Rokon.Polygon;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Shape;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.Backgrounds.ParallaxBackground;

/**
 * @author Richard
 * A basic 2 polygon collision test
 */
public class Example20 extends RokonActivity {
	
	public TextureAtlas atlas;
	public Texture skyTexture, redTexture, greenTexture, blackTexture;
	public ParallaxBackground background;

	int rate = 50;
	long lastUpdate = 0;
	float timeDiff;
	float modifier;
	
    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		rokon.setBackgroundColor(0, 0, 0);
	}

	@Override
	public void onLoadComplete() {
		Polygon polygon = new Polygon(4);
		polygon.put(0.5f, 0);
		polygon.put(1, 0.5f);
		polygon.put(0.5f, 1);
		polygon.put(0, 0.5f);
		Shape shape = new Shape(polygon, 50, 50, 50, 50);

		Polygon polygon2 = new Polygon(3);
		polygon2.put(0, 0);
		polygon2.put(1, 0);
		polygon2.put(0, 1);
		Shape shape2 = new Shape(polygon2, 110, 50, 50, 50);
		
		Collider.check(shape, shape2);
	}

	@Override
	public void onGameLoop() {

	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}