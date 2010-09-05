package com.stickycoding.rokon;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import com.stickycoding.rokon.RenderQueueManager.RenderElement;
import com.stickycoding.rokon.device.Graphics;
import com.stickycoding.rokon.device.OS;

/**
 * RokonRenderer.java
 * The GLSurfaceView.Renderer class for OpenGL
 * 
 * @author Richard
 */

public class RokonRenderer implements GLSurfaceView.Renderer {
	
	private RokonActivity rokonActivity;
	private ObjectManager drawQueue;
	
	private Object drawLock = new Object();
	
	private boolean drawQueueChanged;
	
	public static RokonRenderer singleton;
	
	public synchronized void setDrawQueue(ObjectManager queue) {
		this.drawQueue = queue;
		synchronized(drawLock) {
			drawQueueChanged = true;
			drawLock.notify();
		}
	}
	
	protected RokonRenderer(RokonActivity rokonActivity) {
		singleton = this;
		this.rokonActivity = rokonActivity;
		drawQueueChanged = false;
	}
	
	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.khronos.opengles.GL10)
	 */
	public void onDrawFrame(GL10 gl) {
		GLHelper.setGL(gl);
		
		Time.update();
		
		if(!RokonActivity.engineLoaded && RokonActivity.engineCreated) {
			RokonActivity.engineLoaded = true;
			System.gc();
			rokonActivity.onLoadComplete();
			rokonActivity.startThread();
			return;
		}
		
		FPSCounter.onFrame();

		final Scene scene = RokonActivity.currentScene;
		
		if(scene == null) return;
		
		synchronized(drawLock) {
			if(!drawQueueChanged) {
				while(!drawQueueChanged) {
					try {
						drawLock.wait();
					} catch (InterruptedException e) {
						
					}
				}
			}
			drawQueueChanged = false;
		}
		
		TextureManager.checkRefreshTextures();
		scene.checkForcedTextures();
		
		
		synchronized(this) {
			
			if(scene.useNewClearColor) {
				gl.glClearColor(scene.newClearColor[0], scene.newClearColor[1], scene.newClearColor[2], scene.newClearColor[3]);
				scene.useNewClearColor = false;
			}
			
			if(drawQueue != null && drawQueue.getObjects().getCount() > 0) {
				
				scene.onPreDraw(gl);
				
				final boolean hasWindow = scene.window != null;
		        boolean isWindow = false;
		        
				gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				
				if(scene.background != null) {
					scene.background.onDraw(gl);
				}
				
				if(hasWindow) {
					scene.window.onUpdate(gl);
					isWindow = true;
				}
				
				gl.glMatrixMode(GL10.GL_MODELVIEW);
		        gl.glLoadIdentity();
		        
				FixedSizeArray<BaseObject> objects = drawQueue.getObjects();
				Object[] objectArray = objects.getArray();
				final int count = drawQueue.getObjects().getCount();
				for(int i = 0; i < count; i++) {
					
					RenderElement element = (RenderElement)objectArray[i];
					final boolean useWindow = element.useWindow;
					
					if(hasWindow && !useWindow && isWindow) {
						Window.setDefault(gl);
						gl.glMatrixMode(GL10.GL_MODELVIEW);
				        gl.glLoadIdentity();
				        isWindow = false;
					}
					
					if(hasWindow && useWindow && !isWindow) {
						scene.window.onUpdate(gl);
						gl.glMatrixMode(GL10.GL_MODELVIEW);
				        gl.glLoadIdentity();
				        isWindow = true;
					}
					
					//element.drawable.onUpdate();
					element.drawable.onDraw(gl);
					
				}
				
				scene.onPostDraw(gl);
				
				GLHelper.setGL(null);
			} else {
				gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)
	 */
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		Debug.print("onSurfaceChanged() w=" + w + " h=" + h);
		//Debug.print("Surface Size Changed: " + w + " " + h);
		gl.glViewport(0, 0, w, h);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		Debug.print("gluOrtho2D : " + RokonActivity.gameWidth + "x" + RokonActivity.gameHeight);
        GLU.gluOrtho2D(gl, 0, RokonActivity.gameWidth, RokonActivity.gameHeight, 0);
	}

	/* (non-Javadoc)
	 * @see android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Debug.print("onSurfaceCreated()");
		
		GLHelper.reset();
		GLHelper.setGL(gl);
		
		gl.glClearColor(0, 0, 0, 1);
		
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		
		gl.glShadeModel(GL10.GL_FLAT);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_LIGHTING);

        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        String extensions = gl.glGetString(GL10.GL_EXTENSIONS);
        String version = gl.glGetString(GL10.GL_VERSION);
        Graphics.setSupportsVBO(!version.contains("1.0") || extensions.contains("vertex_buffer_object"));
        

		if(DrawPriority.drawPriority == DrawPriority.PRIORITY_VBO) {
			if(!Graphics.isSupportsVBO()) {
				Debug.warning("Device does not support VBO's, defaulting back to normal");
				DrawPriority.drawPriority = DrawPriority.PRIORITY_NORMAL;
			} else {
				Debug.print("## Device supports VBOs");
			}
		}

        OS.hackBrokenDevices();
        
        TextureManager.reloadActiveTextures(gl);

        
	}

	//@Override
	public void onSurfaceLost() {
		Debug.print("onSurfaceLost");
		GLHelper.reset();
		TextureManager.removeTextures();
		VBOManager.removeVBOs();
		if(RokonActivity.currentScene != null) {
			RokonActivity.currentScene.useNewClearColor = true;
		}
	}

}
