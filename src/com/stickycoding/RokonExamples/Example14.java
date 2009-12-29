package com.stickycoding.RokonExamples;

import android.view.KeyEvent;

import com.stickycoding.Rokon.RokonActivity;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.Backgrounds.FixedBackground;
import com.stickycoding.Rokon.Menu.Menu;
import com.stickycoding.Rokon.Menu.MenuObject;
import com.stickycoding.Rokon.Menu.Objects.MenuButton;

/**
 * @author Richard
 * A more advanced Menu example, has two Menu's that flow smoothly
 */
public class Example14 extends RokonActivity {

	public TextureAtlas atlas;
	public Texture backgroundTexture;
	public FixedBackground background;
	public MyMenu myMenu;
	public MyMenu2 myMenu2;

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
	}

	public void onLoadComplete() {
		rokon.showMenu(myMenu = new MyMenu());
	}
	
	public class MyMenu extends Menu {
		MenuButton button;
		
		public MyMenu() {
			setBackground(background);
			addMenuObject(button = new MenuButton(1, 150, 100, 100, 100, carTexture, car2Texture));
		}
		
		/**
		 * Any transition effects should be put in here, it is called once the Menu is actually visible
		 */
		public void onShow() {
			button.fadeIn(3000);			
		}
		
		public void onMenuObjectTouchUp(MenuObject menuObject) {
			button.fadeOut(1500);
			gotoMenu(new MyMenu2(), 1500);
		}
		
		public void onKey(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK)
				finish();
		}
	}
	
	public class MyMenu2 extends Menu {
		MenuButton button1, button2, button3, button4, button5;
		
		public MyMenu2() {
			setBackground(background);
			addMenuObject(button1 = new MenuButton(1, 75, 75, 75, 75, carTexture, car2Texture));
			addMenuObject(button2 = new MenuButton(2, 380, 75, 75, 75, carTexture, car2Texture));
			addMenuObject(button3 = new MenuButton(3, 380, 240, 75, 75, carTexture, car2Texture));
			addMenuObject(button4 = new MenuButton(4, 75, 240, 75, 75, carTexture, car2Texture));
			addMenuObject(button5 = new MenuButton(5, 220, 140, 50, 50, carTexture, car2Texture));
		}
	
		public void onShow() {
			button1.slideInTop(4000);
			button2.slideInRight(4000);
			button3.slideInBottom(4000);
			button4.slideInLeft(4000);
			button5.fadeIn(4000);
		}
		
		public void onMenuObjectTouchUp(MenuObject menuObject) {
			//You would normally check against menuObject.getId(), but for the purposes of this example, it isn't necessary
			goBack();
		}
		
		private void goBack() {
			button1.slideOutUp(200);
			button2.slideOutRight(200);
			button3.slideOutDown(200);
			button4.slideOutLeft(200);
			button5.fadeOut(2000);
			gotoMenu(new MyMenu(), 2000);
		}
		
		public void onKey(int keyCode, KeyEvent event) {
			//Because we are on the '2nd' Menu, we'll just go back to the last one rather than closing the example
			if(keyCode == KeyEvent.KEYCODE_BACK)
				goBack();
		}
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		rokon.unpause();
	}
}