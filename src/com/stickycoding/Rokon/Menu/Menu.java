package com.stickycoding.Rokon.Menu;

import android.view.KeyEvent;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Hotspot;
import com.stickycoding.Rokon.Rokon;

/**
 * A menu, currently full screen and not in-game only
 * @author Richard
 */
public class Menu {
	
	public static final int MAX_OBJECTS = 99;
	
	private Rokon _rokon;
	private Background _background;
	private MenuObject[] _menuObjects = new MenuObject[MAX_OBJECTS];
	private boolean _showing = false;
	
	private MenuTransition _startTransition = null, _exitTransition = null;
	
	private boolean _closingMenu = false;
	private long _closingMenuTimeout;
	private Menu _closingMenuNext;
	
	/**
	 * Called when the Menu is first shown
	 */
	public void onShow() { }
	
	/**
	 * Called once, straight after the Menu's start transition ends 
	 */
	public void onStartTransitionComplete() { }
	
	/**
	 * Triggered when a MenuObject is first touched
	 * @param menuObject
	 */
	public void onMenuObjectTouchDown(MenuObject menuObject) { }
	
	/**
	 * Triggered every time a touch on a MenuObject is detected
	 * @param menuObject
	 */
	public void onMenuObjectTouch(MenuObject menuObject) { }
	
	/**
	 * Triggered when a touch on a MenuObject ends
	 * @param menuObject
	 */
	public void onMenuObjectTouchUp(MenuObject menuObject) { }
	
	/**
	 * Called when the Menu's exit transition begins (currently inactive) 
	 */
	public void onExitTransitionBegin() { }
	
	/**
	 * Called when the Menu is closed 
	 */
	public void onExit() { }
	
	/**
	 * Triggered on all key events while the Menu is visible on screen
	 * @param keyCode
	 * @param event
	 */
	public void onKey(int keyCode, KeyEvent event) { }
	
	private int a;
	/**
	 * Used to manage animations and sprites, called every time frame renders 
	 */
	public void loop() {
		if(_closingMenu) {
			if(Rokon.time > _closingMenuTimeout) {
				if(_closingMenuNext == null)
					_rokon.removeMenu();
				else {
					_rokon.freeze();
					_rokon.showMenu(_closingMenuNext);
				}
				onExit();
			}
			return;
		}
		for(a = 0; a < _menuObjects.length; a++)
			if(_menuObjects[a] != null)
				_menuObjects[a].loop();
		if(_startTransition != null)
			_startTransition.loop();
		if(_exitTransition != null)
			_exitTransition.loop();
	}
	
	/**
	 * Begins exit transition (currently inactive)
	 */
	public void exit() {
		if(_exitTransition != null)
			_exitTransition.begin(this);
	}
	
	/**
	 * Removes all MenuObjects from the scene 
	 */
	public void end() {
		_rokon.clearScene();
	}
	
	/**
	 * Clears the scene, adds all MenuObject's, and begins the start transition if needed
	 */
	public void show() {
		_rokon = Rokon.getRokon();
		_rokon.pause();
		_rokon.freeze();
		_rokon.clearScene();
		Debug.print("Showing Menu");
		if(_background != null)
			_rokon.setBackground(_background);
		for(int i = 0; i < _menuObjects.length; i++)
			if(_menuObjects[i] != null)
				_menuObjects[i].addToScene(_rokon, this);
		if(_startTransition != null)
			_startTransition.begin(this);
		onShow();
		_rokon.unfreeze();
		_rokon.unpause();
		_showing = true;
	}
	
	/**
	 * Sets the Menu Background
	 * @param background
	 */
	public void setBackground(Background background) {
		_background = background;
		if(_showing)
			_rokon.setBackground(background);
	}
	
	/**
	 * @return NULL if none set
	 */
	public Background getBackground() {
		return _background;
	}
	
	/**
	 * @return NULL if none set
	 */
	public MenuTransition getStartTransition() {
		return _startTransition;
	}
	
	/**
	 * @return NULL if none set
	 */
	public MenuTransition getExitTransition() {
		return _exitTransition;
	}
	
	/**
	 * Sets the start transition
	 * @param menuTransition
	 */
	public void setStartTransition(MenuTransition menuTransition) {
		_startTransition = menuTransition;
	}
	
	/**
	 * Sets the exit transition
	 * @param menuTransition
	 */
	public void setExitTransition(MenuTransition menuTransition) {
		_exitTransition = menuTransition;
	}
	
	/**
	 * @param index
	 * @return NULL if not found
	 */
	public MenuObject getMenuObject(int index) {
		return _menuObjects[index];
	}
	
	/**
	 * @return the number of MenuObject's on this Menu
	 */
	public int getMenuObjectCount() {
		int j = 0;
		for(int i = 0; i < MAX_OBJECTS; i++)
			if(_menuObjects[i] != null)
				j++;
		return j;
	}
	
	private int _firstEmptyMenuObject() {
		for(int i = 0; i < MAX_OBJECTS; i++)
			if(_menuObjects[i] == null)
				return i;
		return -1;
	}
	
	/**
	 * Add's a MenuObject to this Menu
	 * @param menuObject
	 */
	public void addMenuObject(MenuObject menuObject) {
		if(getMenuObjectCount() < MAX_OBJECTS)
			_menuObjects[_firstEmptyMenuObject()] = menuObject;
		else {
			Debug.print("TOO MANY MENU OBJECTS");
		}
	}
	
	/**
	 * Remove's a MenuObject from this Menu
	 * @param menuObject
	 */
	public void removeMenuObject(MenuObject menuObject) {
		for(int i = 0; i < _menuObjects.length; i++)
			if(_menuObjects[i] != null && _menuObjects[i].equals(menuObject)) {
				_menuObjects[i] = null;
			}
	}
	
	private int b;
	private boolean _hotspotActive = false;
	public void onHotspot(Hotspot hotspot) {
		if(_closingMenu)
			return;
		for(b = 0; b < MAX_OBJECTS; b++)
			if(_menuObjects[b] != null && _menuObjects[b].getHotspot() != null && _menuObjects[b].getHotspot().equals(hotspot)) {
				_menuObjects[b].touch(_hotspotActive);
				_hotspotActive = true;
				break;
			}
	}
	
	public void resetActiveHotspot() {
		_hotspotActive = false;
	}

	/**
	 * Closes this Menu, and loads up another immediately
	 * @param menu
	 */
	public void gotoMenu(Menu menu) {
		gotoMenu(menu, 0);
	}
	
	/**
	 * Closes this Menu, and loads up another after a specified delay
	 * @param menu
	 * @param delay time to wait before the next Menu is shown, in milliseconds
	 */
	public void gotoMenu(Menu menu, int delay) {
		_closingMenuNext = menu;
		_closingMenu = true;
		_closingMenuTimeout = Rokon.time + delay;
	}
	
	/**
	 * Closes this Menu immediately
	 */
	public void closeMenu() {
		closeMenu(0);
	}
	
	/**
	 * Closes this Menu after a specified delay
	 * @param delay time to wait before the Menu is closed, in milliseconds
	 */
	public void closeMenu(int delay) {
		_closingMenuNext = null;
		_closingMenu = true;
		_closingMenuTimeout = Rokon.time + delay;
	}

}
