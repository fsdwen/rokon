package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

import com.stickycoding.Rokon.Handlers.AccelerometerHandler;
import com.stickycoding.Rokon.Handlers.InputHandler;
import com.stickycoding.Rokon.Menu.Menu;

/**
 * @version 1.0.2
 * @author Richard
 * 
 * This class does not have to be used, but provides a very simple way of handling events.
 */
/**
 * @author Richard
 *
 */
public class RokonActivity extends Activity {
	
	public final static int TOUCH_THRESHOLD = 100;
	
	public Rokon rokon;
	public static RokonActivity singleton;
	
	private boolean _touchDown = false;
	private long _lastTouchTime = 0;
	private int _touchX, _touchY;
	private Hotspot _lastHotspot;
	
	private boolean _hasLoadingScreen;
    private PowerManager _pm;
    private PowerManager.WakeLock _wl;

	/**
	 * Called when the RokonActivity is first created
	 */
	public void onCreate() { }
	
	/**
	 * Called when the engine is ready for Texture's to be loaded
	 */
	public void onLoad() { }
	
	/**
	 * Called when the engine has loaded all Texture's, and the loading screen has been removed
	 */
	public void onLoadComplete() { }
	
	/**
	 * Called before each render 
	 */
	public void onGameLoop() { }
	
	/**
	 * Called each time the engine recognizes a touch down on screen
	 * @param x
	 * @param y
	 * @param hotspot true if a hotspot has also been triggered
	 */
	public void onTouchDown(int x, int y, boolean hotspot) { }
	
	/**
	 * Called each time the engine recognizes a touch up on screen
	 * @param x
	 * @param y
	 * @param hotspot true if a hotspot has also been triggered
	 */
	public void onTouchUp(int x, int y, boolean hotspot) { }
	
	/**
	 * Called each time the engine recognizes any touch on screen
	 * @param x
	 * @param y
	 * @param hotspot true if a hotspot has also been triggered
	 */
	public void onTouch(int x,int y, boolean hotspot) { }
	
	/**
	 * Called each time a hotspot is touched
	 * @param hotspot
	 */
	public void onHotspotTouch(Hotspot hotspot) { }
	
	/**
	 * Called when a hotspot is first touched
	 * @param hotspot
	 */
	public void onHotspotTouchUp(Hotspot hotspot) { }
	
	/**
	 * Called when a hotspot was previously touched, but nothing is touching the screen any longer
	 * @param hotspot
	 */
	public void onHotspotTouchDown(Hotspot hotspot) { }
	
	/**
	 * Called just after the background is rendered
	 * @param gl
	 */
	public void onDrawBackground(GL10 gl) { }
	
	/**
	 * Called just after each layer is rendered	 
	 * @param gl
	 * @param layer
	 */
	public void onDraw(GL10 gl, int layer) { }
	
	/**
	 * Called at the end of each render
	 * @param gl
	 */
	public void onAfterDraw(GL10 gl) { }
	
	/**
	 * Called when the phone notifies the engine of an incoming call 
	 */
	public void onIncomingCall() { }
	
	/**
	 * Triggered when the Accelerometer coordinates change (only if startAccelerometer has been called)
	 * @param x
	 * @param y
	 * @param z
	 */
	public void onAccelerometerChanged(float x, float y, float z) { }
	
	/**
	 * Called when the Accelerometer detects a shake above the shakeThreshold (only if startAccelerometer has been called)
	 * @param intensity
	 */
	public void onAccelerometerShake(float intensity) { }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    public void onDestroy() {
    	super.onDestroy();
    	rokon.end();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    public void onResume() {
    	super.onResume();
    	MyPhoneStateListener phoneListener = new MyPhoneStateListener(); 
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE); 
    	_pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
		_wl = _pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Rokon");
		_wl.acquire();
    	rokon.onResume();
    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	public void onPause() {
		super.onPause();
    	_wl.release();
    	rokon.onPause();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle b) {
		super.onCreate(b);
    	singleton = this;
    	onCreate();
	}
	
	/**
	 * Starts the engine without a loading screen
	 * @param width the width to calibrate the coordaintes to 
	 * @param height the height to calibrate coordinates to
	 * @param landscape TRUE if screen is to be locked landscape, FALSE if locked portrait
	 */
	public void createEngine(int width, int height, boolean landscape) {
		createEngine(null, width, height, landscape);
	}
	
	/**
	 * Starts the engine with a loading screen
	 * @param loadingScreen path to the loading screen, from the /assets/ folder
	 * @param width the width to calibrate the coordinates to
	 * @param height the height to calibrate coordinates to 
	 * @param landscape TRUE if the screen is to be locked landscape, FALSE if locked portrait
	 */
	public void createEngine(String loadingScreen, int width, int height, final boolean landscape) {
		if(loadingScreen == null)
			rokon = Rokon.createEngine(this, width, height);
		else
			rokon = Rokon.createEngine(this, loadingScreen, width, height);
		_hasLoadingScreen = (loadingScreen != null);
		rokon.setFullscreen();
		if(landscape) 
			rokon.fixLandscape();
		else
			rokon.fixPortrait();
		rokon.init();

		if(!_hasLoadingScreen)
			doLoading();
	}
	
	/**
	 * Runs the RokonActivity loading procedure
	 */
	public void doLoading() {
    	rokon.setBackgroundColor(0.5f, 0.2f, 0.2f);
    	rokon.setInputHandler(touchHandler);
    	rokon.setRenderHook(_renderHook);
    	onLoad();
        System.gc();
		rokon.setLoading(false);
		rokon.updateTime();
		onLoadComplete();
	}
	
	private RenderHook _renderHook = new RenderHook() {
		public void onGameLoop() {
			if(_touchDown && Rokon.realTime >= _lastTouchTime + TOUCH_THRESHOLD) {
				if(_lastHotspot != null) {
					_touchDown = false;
					onHotspotTouchUp(_lastHotspot);
					onTouchUp(_touchX, _touchY, true);
					_lastHotspot = null;
				} else {
					_touchDown = false;
					onTouchUp(_touchX, _touchY, false);
				}
			}
			singleton.onGameLoop();
		}
		
		public void onDrawBackground(GL10 gl) {
			singleton.onDrawBackground(gl);
		}
		
		public void onDraw(GL10 gl, int layer) {
			singleton.onDraw(gl, layer);
		}
		
		public void onAfterDraw(GL10 gl) {
			singleton.onAfterDraw(gl);
		}
	};
    
    private InputHandler touchHandler = new InputHandler() {
    	public void onTouchEvent(int x, int y, boolean hotspot) {
    		_lastTouchTime = Rokon.realTime;
    		if(!hotspot) {
	    		if(!_touchDown) {
	    			_touchDown = true;
	        		onTouchDown(x, y, hotspot);
	    		}
    		}
    		onTouch(x, y, hotspot);
    	}
    	
    	private int i;
    	public void onHotspotTouched(Hotspot hotspot) {
    		_lastTouchTime = Rokon.realTime;
    		if(!_touchDown) {
    			_lastHotspot = hotspot;
    			if(!_touchDown) {
    				_touchDown = true;
    				if(rokon.getActiveMenu() != null)
    					for(i = 0; i < Menu.MAX_OBJECTS; i++)
    						if(rokon.getActiveMenu().getMenuObject(i) != null)
    							if(rokon.getActiveMenu().getMenuObject(i).getHotspot() != null)
        							if(rokon.getActiveMenu().getMenuObject(i).getHotspot().equals(hotspot)) {
        								rokon.getActiveMenu().onMenuObjectTouchDown(rokon.getActiveMenu().getMenuObject(i));
        								rokon.getActiveMenu().onMenuObjectTouch(rokon.getActiveMenu().getMenuObject(i));
        								break;
        							}
	        		onHotspotTouchDown(hotspot);
    			}
    			onHotspotTouch(hotspot);
    		}
    	}
    };

    public class MyPhoneStateListener extends PhoneStateListener { 
        /* (non-Javadoc)
         * @see android.telephony.PhoneStateListener#onCallStateChanged(int, java.lang.String)
         */
        public void onCallStateChanged(int state,String incomingNumber){ 
        	if(state == TelephonyManager.CALL_STATE_RINGING) {
        		Debug.print("PHONE IS RINGING");
        		onIncomingCall();
        	}
        } 
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(rokon.getActiveMenu() != null)
			rokon.getActiveMenu().onKey(keyCode, event);
		else
			super.onKeyDown(keyCode, event);
		return false;
	} 
    
    private AccelerometerHandler _accelerometerHandler = new AccelerometerHandler() {
    	public void onChanged(float x, float y, float z) {
    		onAccelerometerChanged(x, y, z);
    	}
    	
    	public void onShake(float intensity) {
    		onAccelerometerShake(intensity);
    	}    		
    };
    
    /**
     * Starts the Accelerometer class, and will trigger the onAccelerometerChanged and onAccelerometerShake events
     * Anything that needs to be changed with the accelerometer can be done through the static Accelerometer class
     */
    public void startAccelerometer() {
    	Accelerometer.startListening(_accelerometerHandler);
    }
    
    /**
     * Stops the Accelerometer from listening, this should be done when it is not needed, to save processing time
     */
    public void stopAccelerometer() {
    	Accelerometer.stopListening();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override 
    public void onConfigurationChanged(Configuration newConfig) { 
    	super.onConfigurationChanged(newConfig); 
    } 
}
