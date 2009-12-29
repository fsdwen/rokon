package com.stickycoding.RokonExamples;

import android.view.KeyEvent;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.Menu.Menu;
import com.stickycoding.Rokon.Menu.MenuObject;
import com.stickycoding.Rokon.Menu.Objects.MenuButton;
import com.stickycoding.Rokon.Menu.Transitions.MenuFade;

/**
 * @author Richard
 * This is a basic Menu example, it has one button that prints something to the Debug window
 */
public class Example13 extends RokonActivity {

	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public FixedBackground background;
	public MyMenu myMenu;

	public Texture carTexture;
	public Texture car2Texture;
		
    public void onCreate() {
    	createEngine("graphics/loading.png", 480, 320, true);
    }

	@Override
	public void onLoad() {
		atlas = new TextureAtlas(512, 512);
		atlas.insert(backgroundTexture = new Texture("graphics/backgrounds/beach.png"));
		atlas.insert(carTexture = new Texture("graphics/sprites/car.png"));
		atlas.insert(car2Texture = new Texture("graphics/sprites/car2.png"));
		TextureManager.load(atlas);
		background = new FixedBackground(backgroundTexture);
		myMenu = new MyMenu();
	}

	public void onLoadComplete() {
		rokon.showMenu(myMenu);
	}
	
	/**
	 * Each menu has its own class, extend and build from there
	 */
	public class MyMenu extends Menu {
		
		/**
		 * A button is basic clickable object, which can be given a texture for either pressed and unpressed states
		 */
		MenuButton button;
		
		/**
		 * All objects should be created and added to the menu when the class is initialised
		 */
		public MyMenu() {
			setBackground(background);
			setStartTransition(new MenuFade(2000));
			addMenuObject(button = new MenuButton(1, 150, 100, carTexture, car2Texture));
		}
		
		public void onMenuObjectTouchDown(MenuObject menuObject) { 
			Debug.print("TOUCH DOWN");
		}
		
		public void onMenuObjectTouchUp(MenuObject menuObject) {
			Debug.print("TOUCH UP");
		}
		
		/** 
		 * All key presses are passed onto the Menu, you should always handle atleast the KEYCODE_BACK key
		 */
		public void onKey(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK)
				finish();
		}
		
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}