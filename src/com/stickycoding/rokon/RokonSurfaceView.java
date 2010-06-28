package com.stickycoding.rokon;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * RokonSurfaceView.java
 * The SurfaceView used for OpenGL rendering
 * 
 * @author Richard
 */
public class RokonSurfaceView extends GLSurfaceView {

	private RokonActivity rokonActivity;
	private RokonRenderer renderer;
	
	/**
	 * Creates RokonSurfaceView
	 * 
	 * @param rokonActivity valid RokonActivity
	 */
	public RokonSurfaceView(RokonActivity rokonActivity) {
	    super(rokonActivity);
	    this.rokonActivity = rokonActivity;
	    renderer = new RokonRenderer(rokonActivity);
	    setRenderer(renderer);
	    setKeepScreenOn(true);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	rokonActivity.onTouchEvent(event);
        try {
        	Thread.sleep(16);
        } catch (Exception e) { }
        return true;
    }

}
