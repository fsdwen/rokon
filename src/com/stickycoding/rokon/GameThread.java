package com.stickycoding.rokon;

import com.stickycoding.rokon.device.OS;

import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * GameThread.java
 * Handles the game thread, should it be separated from the OpenGL
 * 
 * @author Richard
 */
public class GameThread implements Runnable {
	
	protected static final int MAX_TRIGGERS = 32; //This should be fine for now, any more and the game must be being very unresponsive anyway
	
	private static boolean finished = false, pauseGame = false;
	
	private static Object pauseLock = new Object();
	private static Object inputLock = new Object();
	
	private static MotionTrigger[] motionTrigger;
	private static KeyTrigger[] keyTrigger;
	protected static int motionTriggerCount;
	protected static boolean hasKeyTrigger;
	
	private long lastTime;
	
	protected static boolean hasRunnable;
	
	public static Object runnableLock = new Object();
	
	protected class MotionTrigger {
		public static final int MAX_POINTERS = 3;
		
		protected boolean isTouch;
		
		protected float[] x, y;
		protected int[] pointerId;
		protected int action;
		protected int pointerCount;
		
		protected MotionTrigger() {
			x = new float[MAX_POINTERS];
			y = new float[MAX_POINTERS];
			pointerId = new int[MAX_POINTERS];
		}
		
		protected void set(MotionEvent event, boolean isTouch) {
			if(OS.API_LEVEL >= 5) {
				if(OS.API_LEVEL >= 8) {
					action = event.getAction();
					pointerCount = Rokon.motionEvent8.getPointerCount(event);
					for(int i = 0; i < pointerCount; i++) {
						pointerId[i] = Rokon.motionEvent8.getPointerId(event, i);
						x[i] = Rokon.motionEvent8.getX(event, i);
						y[i] = Rokon.motionEvent8.getY(event, i);
					}
				} else {
					action = event.getAction();
					pointerCount = Rokon.motionEvent5.getPointerCount(event);
					for(int i = 0; i < pointerCount; i++) {
						pointerId[i] = Rokon.motionEvent5.getPointerId(event, i);
						x[i] = Rokon.motionEvent5.getX(event, i);
						y[i] = Rokon.motionEvent5.getY(event, i);
					}
				}
			} else {
				// No multitouch, life is simple
				x[0] = event.getX();
				y[0] = event.getY();
				action = event.getAction();
				pointerCount = motionTriggerCount++;
			}
			this.isTouch = isTouch;
		}
		
	}
	
	protected class KeyTrigger {
		protected KeyEvent keyEvent;
		protected boolean isDown;
		protected int keyCode;
		protected boolean isNull;
		
		protected KeyTrigger() {
			isNull = true;
		}
		
		protected void set(int keyCode, KeyEvent event, boolean isDown) {
			this.keyCode = keyCode;
			this.keyEvent = event;
			this.isDown = isDown;
			isNull = false;
		}
		
		protected void reset() {
			isNull = true;
			keyEvent = null;
		}
	}
	
	//@Override
	public void run() {
		Debug.print("Game thread begins");
		finished = false;
		pauseGame = false;
		hasRunnable = false;
		hasKeyTrigger = false;
		motionTrigger = new MotionTrigger[MAX_TRIGGERS];
		keyTrigger = new KeyTrigger[MAX_TRIGGERS];
		
		for(int i = 0; i < MAX_TRIGGERS; i++) {
			motionTrigger[i] = new MotionTrigger();
			keyTrigger[i] = new KeyTrigger();
		}
		
		while(!finished) {			
			final long startTime = SystemClock.uptimeMillis();
			final long deltaTime = startTime - lastTime;
			long finalDelta = deltaTime;
			
			//Just make sure we aren't going too fast, there's no rush here
			if(deltaTime > 12) {
				lastTime = startTime;
				Time.updateLoop();		
				Scene scene = RokonActivity.currentScene;
				
				if(scene != null) {
					// Before we do anything, make sure we're on a new buffer
					RokonActivity.renderQueueManager.swap(RokonRenderer.singleton);
					
					// First, see if there are any Runnables in the queue that you want me tod o
					synchronized(runnableLock) {
						if(hasRunnable) {
							for(int i = 0; i < Scene.MAX_RUNNABLE; i++) {
								if(Scene.gameRunnable[i] != null) {
									Scene.gameRunnable[i].run();
									Scene.gameRunnable[i] = null;
								}
							}
						}
						hasRunnable = false;
					}
					
					// Then see if there's any new input
					synchronized (inputLock) {
						if(motionTriggerCount > 0) {
							for(int i = 0; i < motionTriggerCount; i++) {
								if(motionTrigger[i].isTouch) {
									scene.handleTouch(motionTrigger[i].x, motionTrigger[i].y, motionTrigger[i].action, motionTrigger[i].pointerCount, motionTrigger[i].pointerId);
								} else {
									scene.onTrackballEvent(motionTrigger[i].x[0], motionTrigger[i].y[0], motionTrigger[i].action);
								}
							}
							motionTriggerCount = 0;
						}
						if(hasKeyTrigger) {
							for(int i = 0; i < MAX_TRIGGERS; i++) {
								if(!keyTrigger[i].isNull) {
									if(keyTrigger[i].isDown) {
										scene.onKeyDown(keyTrigger[i].keyCode, keyTrigger[i].keyEvent);
									} else {
										scene.onKeyUp(keyTrigger[i].keyCode, keyTrigger[i].keyEvent);
									}
									keyTrigger[i].reset();
								}
							}
							hasKeyTrigger = false;
						}
					}
					
					// Update the physics, if needs be
					if(scene.usePhysics) {
						if(scene.pausePhysics) {
							scene.world.step(0, 1, 1);
						} else {
							float timeStep = Time.getLoopTicksFraction();
							if(timeStep > 0.018f) timeStep = 0.018f;
							scene.world.step(timeStep, 10, 10);
						}
					}		
					
					// Run your game loop
					scene.onGameLoop();	
					
					// Stick everything onto the rendering buffer
					scene.render();					
				}
				final long endTime = SystemClock.uptimeMillis();
				finalDelta = endTime - startTime;
			}
			
			FPSCounter.onLoop();
			
			// If we're running above 60fps, chill out, let the other thread do some work
			if(finalDelta < 16) {
				try {
					Thread.sleep(16 - finalDelta);
				} catch (Exception e) {
					
				}
			}
			
			// If we're paused, hang on ...
			synchronized (pauseLock) {
				while(pauseGame) {
					try {
						pauseLock.wait();
					} catch (InterruptedException e) { }
				}
			}
			
		}
		
		RokonActivity.renderQueueManager.emptyQueues(RokonRenderer.singleton);
		
		Debug.print("Game thread over");
	}
	
	public static void pauseGame() {
		synchronized (pauseLock) {
			pauseGame = true;
		}
	}
	
	public static void resumeGame() {
		synchronized (pauseLock) {
			pauseGame = false;
			pauseLock.notifyAll();
		}
	}
	
	public static boolean getPaused() {
		return pauseGame;
	}
	
	public static void stopThread() {
		synchronized (pauseLock) {
			pauseGame = false;
			finished = true;
			pauseLock.notifyAll();
		}
	}
	
	public static void motionInput(boolean touch, MotionEvent event) {
		synchronized (inputLock) {
			if(motionTriggerCount < MAX_TRIGGERS) {
				motionTrigger[motionTriggerCount].set(event, touch);
				motionTriggerCount++;
			} else {
				Debug.warning("MotionInput queue is full, what's going on? Either I'm broken, or you need to buy a new phone");
			}
		}
	}
	
	public static void keyInput(boolean down, int keyCode, KeyEvent event) {
		synchronized (inputLock) {
			for(int i = 0; i < MAX_TRIGGERS; i++) {
				if(keyTrigger[i].isNull) {
					keyTrigger[i].set(keyCode, event, down);
					hasKeyTrigger = true;
					return;
				}
			}
			Debug.warning("Key input queue is full, what's goingo on?");
		}
	}

}
