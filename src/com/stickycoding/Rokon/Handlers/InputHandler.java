package com.stickycoding.Rokon.Handlers;

import android.view.MotionEvent;

import com.stickycoding.Rokon.Hotspot;

/**
 * InputHandler allows you to be notified of screen touches, and alerted when specific hotspots are touched
 */
public class InputHandler {
	
	public void onTouchEvent(int x, int y, boolean hotspot) { }

	public void onHotspotTouched(Hotspot hotspot) { }
	
	public void onHotspotTouched(Hotspot hotspot, int x, int y) { }

}
