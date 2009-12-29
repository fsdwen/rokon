package com.stickycoding.Rokon.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.stickycoding.Rokon.Rokon;

public class RokonSurfaceView extends GLSurfaceView {
	
	RokonRenderer renderer;
	
	public RokonSurfaceView(Context context) {
		super(context);
		renderer  = new RokonRenderer();
		setRenderer(renderer);
	}
	
	public boolean onTouchEvent(final MotionEvent event) {
		queueEvent(new Runnable() {
			public void run() {
				Rokon.getRokon().onTouchEvent(event);
			}
		});
		return true;
	}

}
