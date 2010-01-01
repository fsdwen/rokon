package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.stickycoding.Rokon.Handlers.InputHandler;
import com.stickycoding.Rokon.Menu.Menu;
import com.stickycoding.Rokon.OpenGL.RokonSurfaceView;

/**
 * @version 1.0.3
 * @author Richard Taylor - Sticky Coding
 * 
 * See LICENSE for information about copyright.
 * 
 * This engine was built to be open source, you arefree to use this in your projects,
 * commercial or otherwise, as you wish. If you make any improvements, please send them 
 * on to me so everybody can benefit from them.
 * 
 * Please report any bugs through the Issue tracker on google projects
 * 
 * http://code.google.com/p/rokon/
 * http://stickycoding.com/rokon/
 * 
 * If this code has been useful, please spread the word, and consider donating to keep
 * working on this worthwhile!
 */
public class Rokon {
	public static int MAX_HOTSPOTS = 25;
	public static int MAX_LAYERS = 16;
	
	private Vibrator _vibrator;
	private Hotspot[] hotspotArr = new Hotspot[MAX_HOTSPOTS];
	private int i, j, k, l, u;
	
	private Transition _transition;
	
	private static Rokon _rokon;
	private RenderHook _renderHook;
	private boolean _hasRenderHook = false;
	
	private Background _background;

	private Layer[] _layer;
	
	private Activity _activity;
	private InputHandler _inputHandler;
	
	private int _frameCount;
	private int _frameRate;
	private long _frameTimer;
	
	private int _width;
	private int _height;
	
	private float[] _setBackgroundColor;

	public static long time = 0;
	public static long realTime = 0;
	
	private boolean _frozen = false;	
	private boolean _loading = false;
	private String _loadPath;
	
	private boolean _paused = false;
	private long _pauseTime = 0;
	private long _pausedOn;
	
	private boolean _showFps = false;
	
	public int currentTexture = -1;
	
	public static int fixedWidth, fixedHeight, screenWidth, screenHeight;
	private boolean _landscape = false;
	private boolean _isLoadingScreen = false;
	private boolean _letterBoxMode = false;
	public VertexBuffer letterBoxBuffer1, letterBoxBuffer2;
	
	private boolean _forceTextureRefresh = false;
	private boolean _freezeUntilTexturesReloaded = false;
	private boolean _forceOffscreenRender = false;
	
	private Menu _menu = null;
	
	/**
	 * Sets the active Menu to NULL
	 */
	public void removeMenu() {
		if(_menu != null)
			_menu.end();
		_menu = null;
	}
	
	/**
	 * Sets an active Menu in the game
	 * @param menu
	 */
	public void showMenu(Menu menu) {
		_menu = menu;
		menu.show();
	}
	
	/**
	 * @return the active Menu, NULL if none set
	 */
	public Menu getActiveMenu() {
		return _menu;
	}
	
	/**
	 * @return true if the engine is rendering offscreen objects
	 */
	public boolean isForceOffscreenRender() {
		return _forceOffscreenRender;
	}
	
	/**
	 * Forces the engine to render objects which appear to be offscreen (therefore invisible)
	 */
	public void forceOffscreenRender() {
		_forceOffscreenRender = true;		
	}
	
	/**
	 * Defines whether or not the engine renders objects which appear to be offscreen
	 * @param value
	 */
	public void forceOffscreenRender(boolean value) {
		_forceOffscreenRender = value;
	}
	
	/**
	 * Forces the engine to recreate the atlas rather than loading an old configuration
	 */
	public void forceTexttureRefresh() {
		_forceTextureRefresh = true;
	}
	
	/**
	 * Defines whether or not the engine recreates the atlas rather than using an older configuration
	 * @param value
	 */
	public void forceTextureRefresh(boolean value) {
		_forceTextureRefresh = value;
	}
	
	/**
	 * @return true if the engine is being forced to create a new atlas
	 */
	public boolean isForceTextureRefresh() {
		return _forceTextureRefresh;
	}
	
	/**
	 * Forces the engine to enter letterbox mode (blacking out all objects that aren't fully on screen, for when aspect ratios differ)
	 */
	public void forceLetterBox() {
		_letterBoxMode = true;
		letterBoxBuffer1 = new VertexBuffer(getWidth(), 0, getWidth() + 200, getHeight() + 200);
		letterBoxBuffer2 = new VertexBuffer(0, getHeight(), getWidth() + 200, getHeight() + 200);
	}
	
	/**
	 * Defines whether or not to force letterbox mode
	 * @param value
	 */
	public void forceLetterBox(boolean value) {
		if(value)
			forceLetterBox();
		_letterBoxMode = false;
	}
	
	/**
	 * @return true if the engine has in letterbox mode
	 */
	public boolean isLetterBox() {
		return _letterBoxMode;
	}
	
	/**
	 * Lets the engine know that the loading screen is currently visible - you should not need to use this
	 * @param value
	 */
	public void setIsLoadingScreen(boolean value) {
		_isLoadingScreen = value;
	}
	
	/**
	 * @return true if the loading screen is still visible
	 */
	public boolean isLoadingScreen() {
		return _isLoadingScreen;
	}
	
	/**
	 * Toggles whether to output FPS through Debug
	 * @param showFps
	 */
	public void fps(boolean showFps) {
		_showFps = showFps;
	}
	
	/**
	 * Pauses time, but continues rendering
	 */
	public void pause() {
		_pausedOn = time;
		_paused = true;
	}
	
	/**
	 * Unpauses time, offsetting it with the duration of the pause
	 */
	public void unpause() {
		_pauseTime = (System.currentTimeMillis() - _pausedOn);
		_paused = false;
	}
	
	/**
	 * @return true if the engine is currently paused
	 */
	public boolean isPaused() {
		return _paused;
	}
	
	/**
	 * You do not need this
	 * @param tf
	 */
	public void setLoading(boolean tf) {
		_loading = tf;
	}
	
	/**
	 * You do not need this
	 * @param path
	 */
	public void setLoadPath(String path) {
		_loadPath = path;
	}
	
	/**
	 * @return true if the engine is still loading
	 */
	public boolean isLoading() {
		return _loading;
	}
	
	/**
	 * @return the path of the load screen
	 */
	public String getLoadingImage() {
		return _loadPath;
	}
	
	/**
	 * Sets a RenderHook to access OpenGL directly. This is currently inactive.
	 * @param renderHook
	 */
	public void setRenderHook(RenderHook renderHook) {
		_renderHook = renderHook;
		_hasRenderHook = true;
	}
	
	/**
	 * @return the current RenderHook, NULL if unset
	 */
	public RenderHook getRenderHook() {
		return _renderHook;
	}
	
	/**
	 * Pass through your InputHandler to manage screen touches and Hotspot's
	 * @param inputHandler 
	 */
	public void setInputHandler(InputHandler inputHandler) {
		_inputHandler = inputHandler;
	}
	
	/**
	 * @return the active InputHandler, NULL is no handler set
	 */
	public InputHandler getInputHandler() {
		return _inputHandler;
	}
	
	/**
	 * Rokon operates as a singleton, and can be retrieved statically
	 * @return the current Rokon engine
	 */
	public static Rokon getRokon() {
		if(_rokon == null) {
			Debug.print("Rokon has not been created");
			System.exit(0);
		}
		return _rokon;
	}
	
	/**
	 * Creates the engine instance
	 * @param activity the current activity
	 * @param fWidth the width to calibrate the engine's coordinates to
	 * @param fHeight the height to calibrate the engine's coordinates to
	 * @return the engine reference
	 */
	public static Rokon createEngine(Activity activity, int fWidth, int fHeight) {
		fixedWidth = fWidth;
		fixedHeight = fHeight;
		Debug.print("Rokon engine created");
		_rokon = new Rokon(activity);
		_rokon.setIsLoadingScreen(false);
		return _rokon;		
	}
	
	/**
	 * Creates the engine instance
	 * @param activity the current activity
	 * @param path the path of the loading screen
	 * @param fWidth the width to calibrate the engine's coordinates to
	 * @param fHeight the height to calibrate the engine's coordinates to
	 * @return the engine reference
	 */
	public static Rokon createEngine(Activity activity, String path, int fWidth, int fHeight) {
		fixedWidth = fWidth;
		fixedHeight = fHeight;
		Debug.print("Rokon engine created");
		_rokon = new Rokon(activity);
		_rokon.setLoading(true);
		_rokon.setIsLoadingScreen(true);
		_rokon.setLoadPath(path);
		
		return _rokon;
	}

	/**
	 * Creates the Rokon instance, but does not set up loading/coordinates
	 * @param activity
	 */
	public Rokon(Activity activity) {
		_activity = activity;
		_layer = new Layer[MAX_LAYERS];
		for(int i = 0; i < MAX_LAYERS; i++)
			_layer[i] = new Layer();
		_frameRate = 0;
		_frameCount = 0;
		_frameTimer = 0;
		TextureManager.prepare();
		time = System.currentTimeMillis();
	}
	
	/**
	 * Updates Rokon.time, offset by the length of game pause so far
	 */
	public void updateTime() {
		time = System.currentTimeMillis() - _pauseTime;
	}
	
	/**
	 * Adds a Hotspot to be checked each time a screen touch is registered
	 * @param hotspot
	 */
	public void addHotspot(Hotspot hotspot) {
		j = -1;
		for(k = 0; k < MAX_HOTSPOTS; k++)
			if(hotspotArr[k] == null)
				j = k;
		if(j == -1) {
			Debug.print("TOO MANY HOTSPOTS");
			return;
		}
		hotspotArr[j] = hotspot;
	}
	
	/**
	 * Removes a Hotspot from the list to be checked
	 * @param hotspot
	 */
	public void removeHotspot(Hotspot hotspot) {
		for(l = 0; l < MAX_HOTSPOTS; l++)
			if(hotspotArr[l] != null)
				if(hotspotArr[l].equals(hotspot))
					hotspotArr[l] = null;
	}
	
	/**
	 * @return a HashSet of all active Hotspot's
	 */
	public Hotspot[] getHotspots() {
		return hotspotArr;
	}
	
	/**
	 * Initializes the engine, sets the content view and prepares for the game
	 */
	private float _fixedAspect, _realAspect;
	private RokonSurfaceView _rokonSurfaceView;
	public void init() {
		DisplayMetrics dm = new DisplayMetrics();
		_activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		_width = fixedWidth;
		_height = fixedHeight;
		
		_fixedAspect = (float)screenWidth / (float)screenHeight;
		_realAspect = (float)fixedWidth / (float)fixedHeight;
		
		Debug.print("FA=" + _fixedAspect + " RA=" + _realAspect);
		
		if(_fixedAspect != _realAspect)
			forceLetterBox();

		Runtime r = Runtime.getRuntime();
		r.gc();
	
		_rokonSurfaceView = new RokonSurfaceView(_activity);
		_activity.setContentView(_rokonSurfaceView);
		
	}
	
	/**
	 * @return the width of the screen, in pixels
	 */
	public int getWidth() {
		return _width;
	}
	
	/**
	 * @return the height of the screen, in pixels
	 */
	public int getHeight() {
		return _height;
	}
	
	/**
	 * @return the current Activity
	 */
	public Activity getActivity() {
		return _activity;
	}
	
	/**
	 * Fixes the orientation through hardware, if left to be defined physically this will cause problems
	 * @param orientation an orientation constant as used in ActivityInfo.screenOrientation
	 */
	public void setOrientation(int orientation) {
		//Debug.print("Orientation changed");
		_activity.setRequestedOrientation(orientation);
	}
	
	/**
	 * Removes the title bar and application name from view
	 */
	public void setFullscreen() {
		//Debug.print("Set to fullscreen");
		_activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        _activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 * Fixes the screen in landscape mode
	 */
	public void fixLandscape() {
		//Debug.print("Fixed in landscape mode");
		_landscape = true;
		_activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
	/**
	 * @return true if the screen is set to landscape
	 */
	public boolean isLandscape() {
		return _landscape;
	}
	
	/**
	 * Fixes the screen in portrait mode
	 */
	public void fixPortrait() {
		//Debug.print("Fixed in portrait mode");
		_landscape = false;
		_activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	/**
	 * @param index the index of the layer, zero based
	 * @return the Layer at index
	 */
	public Layer getLayer(int index) {
		return _layer[index];
	}
	
	/**
	 * Draws your graphics in OpenGL, no need to call this. 
	 * @param gl
	 */
	public void drawFrame(GL10 gl) {
		try {
			while(_frozen);
			
			if(_freezeUntilTexturesReloaded)
				return;
			
			if(!_paused)
				time = System.currentTimeMillis() - _pauseTime;
			
			realTime = System.currentTimeMillis();
			
			if(_setBackgroundColor != null) {
				gl.glClearColor(_setBackgroundColor[0], _setBackgroundColor[1], _setBackgroundColor[2], 1);
				_setBackgroundColor = null;
			}
			
			if(!_paused)
				for(int m = 0; m < MAX_LAYERS; m++)
					_layer[m].updateMovement();
			
			
			if(_menu != null)
				_menu.loop();

			if(_hasRenderHook)
				_renderHook.onGameLoop();
	
			if(_background != null)
				_background.drawFrame(gl);
	
			if(_hasRenderHook)
				_renderHook.onDrawBackground(gl);
			
			for(int m = 0; m < MAX_LAYERS; m++) {
				_layer[m].drawFrame(gl);
				if(_hasRenderHook)
					_renderHook.onDraw(gl, m);
			}
			
			if(_hasRenderHook)
				_renderHook.onAfterDraw(gl);
			
			if(_letterBoxMode) {
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glDisable(GL10.GL_TEXTURE_2D);
				
				gl.glColor4f(0, 0, 0, 1);

				gl.glLoadIdentity();
				gl.glVertexPointer(2, GL11.GL_FLOAT, 0, letterBoxBuffer1.buffer);		
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
				gl.glLoadIdentity();
				gl.glVertexPointer(2, GL11.GL_FLOAT, 0, letterBoxBuffer2.buffer);		
				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
				
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glEnable(GL10.GL_TEXTURE_2D);
			}
			
			if(time > _frameTimer) {
				_frameRate = _frameCount;
				_frameCount = 0;
				_frameTimer = time + 1000;
				if(_showFps)
					Debug.print("FPS=" + _frameRate);
			}
			_frameCount++;
				
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}	
	
	/**
	 * Adds a sprite to the bottom Layer (zero)
	 * @param sprite
	 */
	public void addSprite(Sprite sprite) {
		getLayer(0).addSprite(sprite);
	}
	
	/**
	 * Adds a sprite to a Layer
	 * @param sprite
	 * @param layer
	 */
	public void addSprite(Sprite sprite, int layer) {
		getLayer(layer).addSprite(sprite);
	}
	
	/**
	 * Removes a sprite from a Layer
	 * @param sprite
	 * @param layer
	 */
	public void removeSprite(Sprite sprite, int layer) {
		getLayer(layer).removeSprite(sprite);
	}
	
	/**
	 * Removes a sprite from the bottom Layer (zero)
	 * @param sprite
	 */
	public void removeSprite(Sprite sprite) {
		getLayer(0).removeSprite(sprite);
	}
	
	/**
	 * Adds Text to the bottom Layer (zero)
	 * @param text
	 */
	public void addText(Text text) {
		getLayer(0).addText(text);
	}
	
	/**
	 * Adds Text to a Layer
	 * @param text
	 * @param layer
	 */
	public void addText(Text text, int layer) {
		getLayer(layer).addText(text);
	}
	
	/**
	 * Adds Emitter to layer zero
	 * @param emitter
	 */
	public void addEmitter(Emitter emitter) {
		getLayer(0).addEmitter(emitter);
	}
	
	/**
	 * Adds Emitter to a Layer
	 * @param emitter
	 * @param layer
	 */
	public void addEmitter(Emitter emitter, int layer) {
		getLayer(layer).addEmitter(emitter);
	}
	
	/**
	 * Removes Text from the bottom Layer (zero)
	 * @param text
	 */
	public void removeText(Text text) {
		getLayer(0).removeText(text);
	}
	
	/**
	 * Removes Text from a Layer
	 * @param text
	 * @param layer
	 */
	public void removeText(Text text, int layer) {
		getLayer(layer).removeText(text);
	}
		
	/**
	 * Sets the current background. Currently inactive.
	 * @param background
	 */
	public void setBackground(Background background) {
		_background = background;
	}
	
	/**
	 * Gets the current background. Currently inactive.
	 * @return
	 */
	public Background getBackground() {
		return _background;
	}
	
	/**
	 * Initializes the vibrator object, this is done automatically when calling vibrate() so you should not need this
	 */
	public void initVibrator() {
		_vibrator = (Vibrator)Rokon.getRokon().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	/**
	 * Vibrates the device
	 * @param milliseconds length of time to vibrate
	 */
	public void vibrate(long milliseconds) {
		if(_vibrator == null)
			initVibrator();
		_vibrator.vibrate(milliseconds);
	}
	
	/**
	 * Vibrates the device once, according to a pattern
	 * @param pattern an array of vibrations
	 */
	public void vibrate(long[] pattern) {
		if(_vibrator == null)
			initVibrator();
		_vibrator.vibrate(pattern, 1);
	}
	
	/**
	 * Vibrates the device according to a pattern, over a set number of loops
	 * @param pattern an array of vibration
	 * @param loops number of loops
	 */
	public void vibrate(long[] pattern, int loops) {
		if(_vibrator == null)
			initVibrator();
		_vibrator.vibrate(pattern, loops);
	}
	
	/**
	 * Sets the background color of the OpenGL surface
	 * @param red 0.0 to 1.0
	 * @param green 0.0 to 1.0
	 * @param blue 0.0 to 1.0
	 */
	public void setBackgroundColor(float red, float green, float blue) {
		_setBackgroundColor = new float[3];
		_setBackgroundColor[0] = red;
		_setBackgroundColor[1] = green;
		_setBackgroundColor[2] = blue;
	}
	
	/**
	 * @return the system time, in milliseconds, for the current visible frame
	 */
	public static long getTime() {
		if(time == 0)
			time = System.currentTimeMillis();
		return time;
	}
	
	/**
	 * @param transition creates a transition effect as defined by an extension of Transition
	 */
	public void setTransition(Transition transition) {
		_transition = transition;
	}
	
	/**
	 * @return the current Transition applied to the scene
	 */
	public Transition getTransition() {
		return _transition;
	}
	
	/**
	 * Clears all active hotspots from the game
	 */
	public void resetHotspots() {
		for(u = 0; u < MAX_HOTSPOTS; u++)
			hotspotArr[u] = null;
	}
	
	/**
	 * Removes the active RenderHook from the engine
	 */
	public void resetRenderHook() {
		_renderHook = null;
		_hasRenderHook = false;
	}
	
	/**
	 * Removes the active Background from the scene
	 */
	public void resetBackground() {
		_background = null;
	}
	
	/**
	 * Removes EVERYTHING from the scene
	 */
	public void clearScene() {
		clearScene(false);
	}
	
	/**
	 * Removes EVERYTHING from the scene
	 */
	public void clearScene(boolean keepBackground) {
		resetHotspots();
		if(!keepBackground)
			resetBackground();
		for(u = 0; u < MAX_LAYERS; u++) {
			_layer[u].clearLayer();
		}
    	System.gc();
	}
	
	/**
	 * Freeze's rendering, but does not pause the game time
	 */
	public void freeze() {
		_frozen = true;
	}
	
	/**
	 * Unfreeze's rendering 
	 */
	public void unfreeze() {
		_frozen = false;
	}
	
	/**
	 * @return true if rendering is frozen
	 */
	public boolean isFrozen() {
		return _frozen;
	}
	
	/**
	 * This must be called through the Activity's onPause
	 */
	public void onPause() {
		pause();
		_rokonSurfaceView.onPause();
	}
	
	/**
	 * This must be called through Activity's onResume, it will trigger textures to be reloaded
	 */
	public void onResume() {
		_rokonSurfaceView.onResume();
		_freezeUntilTexturesReloaded = true;
		TextureManager.reload();
	}
	
	/**
	 * Notifies the engine that it needs to shut down
	 */
	public void end() {
		Debug.print("############## REQUEST END");
    	Accelerometer.stopListening();
		(new Exception()).printStackTrace();
	}
	
	/**
	 * Freezes the engine until all textures have been reloaded onto the hardware
	 */
	public void freezeUntilTexturesReloaded() {
		_freezeUntilTexturesReloaded = true;
	}
	
	/**
	 * Informs the engine that all textures have been loaded, so unfreeze
	 */
	public void textureLoadComplete() { 
		_freezeUntilTexturesReloaded = false;
	}
	
	/**
	 * Triggered from GLSurfaceView each time a touch is recongized
	 * @param event
	 */
	private int _newTouchX, _newTouchY;
	private boolean hit;
	public void onTouchEvent(MotionEvent event) {
		 if(getInputHandler() != null) {
             hit = false;
             hotspotArr = getHotspots();

             _newTouchX = (int)((event.getX() / screenWidth) * fixedWidth);
             _newTouchY = (int)((event.getY() / screenHeight) * fixedHeight);
             
             if(_menu != null) {
                 for(i = 0; i < Rokon.MAX_HOTSPOTS; i++) {
    		         if(hotspotArr[i] != null)
    	                 if(hotspotArr[i].sprite != null) {
    	                	 if(_newTouchX >= hotspotArr[i].sprite.getX() && _newTouchX <= hotspotArr[i].sprite.getX() + hotspotArr[i].sprite.getWidth() && _newTouchY >= hotspotArr[i].sprite.getY() && _newTouchY <= hotspotArr[i].sprite.getY() + hotspotArr[i].sprite.getHeight()) {
	    	                     _menu.onHotspot(hotspotArr[i]);
	    	                	 hit = true;
	    	                	 break;
                             }
    	                 }
                 }
                 getInputHandler().onTouchEvent(_newTouchX, _newTouchY, hit);
                 getInputHandler().onTouchEvent(_newTouchX, _newTouchY, event.getAction(), hit);
                 return;
             }
             
             for(i = 0; i < Rokon.MAX_HOTSPOTS; i++) {
		         if(hotspotArr[i] != null)
	                 if(hotspotArr[i].sprite != null) {
	                	 if(_newTouchX >= hotspotArr[i].sprite.getX() && _newTouchX <= hotspotArr[i].sprite.getX() + hotspotArr[i].sprite.getWidth() && _newTouchY >= hotspotArr[i].sprite.getY() && _newTouchY <= hotspotArr[i].sprite.getY() + hotspotArr[i].sprite.getHeight()) {
	                         Rokon.getRokon().getInputHandler().onHotspotTouched(hotspotArr[i]);
	                         Rokon.getRokon().getInputHandler().onHotspotTouched(hotspotArr[i], _newTouchX, _newTouchY);
		                     Rokon.getRokon().getInputHandler().onHotspotTouched(hotspotArr[i], _newTouchX, _newTouchY, event.getAction());
	                         hit = true;
		                     break;
                         } 
	                 } else if(_newTouchX >= hotspotArr[i].x && _newTouchX <= hotspotArr[i].x + hotspotArr[i].width && _newTouchY >= hotspotArr[i].y && _newTouchY <= hotspotArr[i].y + hotspotArr[i].height) {
	                	 Rokon.getRokon().getInputHandler().onHotspotTouched(hotspotArr[i]);
	                     Rokon.getRokon().getInputHandler().onHotspotTouched(hotspotArr[i], _newTouchX, _newTouchY);
	                     Rokon.getRokon().getInputHandler().onHotspotTouched(hotspotArr[i], _newTouchX, _newTouchY, event.getAction());
	                     hit = true;
	                     break;
	                 }
             }
             getInputHandler().onTouchEvent(_newTouchX, _newTouchY, hit);
             getInputHandler().onTouchEvent(_newTouchX, _newTouchY, event.getAction(), hit);
		 }
	}
	
}
	