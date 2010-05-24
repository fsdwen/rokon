package com.stickycoding.rokon;

import android.view.MotionEvent;

/**
 * Touchable.java
 * An interface applied to objects to allow responses to screen touch events
 * @author Richard
 *
 */
public interface Touchable {
	
	void onTouchDown(StaticObject object, int x, int y, MotionEvent event);
	void onTouch(StaticObject object, int x, int y, MotionEvent event);
	void onTouchUp(StaticObject object, int x, int y, MotionEvent event);

}
