package com.stickycoding.rokon;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.microedition.khronos.opengles.GL10;

import android.view.KeyEvent;
import android.view.MotionEvent;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.stickycoding.rokon.device.Graphics;

/**
 * Scene.java
 * A Scene holds and prepares drawable objects or object groups
 * 
 * @author Richard
 */
public class Scene {
	
	public static final int SCENE_TEXTURE_COUNT = 32;
	public static final int DEFAULT_LAYER_COUNT = 1;
	public static final int DEFAULT_LAYER_OBJECT_COUNT = 32;

	protected Layer[] layer;
	protected boolean loadedTextures;
	protected int layerCount;
	protected Window window = null;
	protected Texture[] texturesToLoad;
	protected Texture[] texturesOnHardware;
	protected boolean useInvoke;
	protected World world;
	protected boolean usePhysics = false;
	protected ContactListener contactListener;
	protected boolean useContactListener;
	protected Background background;

	public void onPreDraw(GL10 gl) { }
	public void onPostDraw(GL10 gl) { }
	
	public void onPause() { }
	public void onResume() { }
	
	public void onTouchDown(DrawableObject object, float x, float y, MotionEvent event) { }
	public void onTouchUp(DrawableObject object, float x, float y, MotionEvent event) { }
	public void onTouchMove(DrawableObject object, float x, float y, MotionEvent event) { }
	public void onTouch(DrawableObject object, float x, float y, MotionEvent event) { }
	public void onTouchDown(float x, float y, MotionEvent event) { }
	public void onTouchMove(float x, float y, MotionEvent event) { }
	public void onTouch(float x, float y, MotionEvent event) { }
	public void onTouchUp(float x, float y, MotionEvent event) { }
	
	public void onTouchDownReal(float x, float y, MotionEvent event) { }
	public void onTouchMoveReal(float x, float y, MotionEvent event) { }
	public void onTouchUpReal(float x, float y, MotionEvent event) { }
	public void onTouchReal(float x, float y, MotionEvent event) { }
	
	public void onKeyDown(int keyCode, KeyEvent event) { }
	
	/**
	 * Sets a World for the physics in this Scene
	 * Automatically flags usePhysics
	 * 
	 * @param world valid World object
	 */
	public void setWorld(World world) {
		this.world = world;
		Physics.world = world;
		usePhysics = true;
	}
	
	/**
	 * Returns the World associated with this Scene
	 * 
	 * @return NULL if no World set
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Flags to use physics in this Scene
	 */
	public void usePhysics() {
		usePhysics = true;
	}
	
	/**
	 * Flags to not use physics
	 */
	public void noPhysics() {
		usePhysics = false;
	}
	
	/**
	 * Removes the World from this Scene
	 */
	public void removeWorld() {
		this.world = null;
		Physics.world = null;
		usePhysics = false;
	}
	
	/**
	 * Triggers the Scene to begin invoking methods on certain events, this is not set by default.
	 * If the methods that are to be invoked don't exist, no exceptions will be raised.
	 */
	public void useInvoke() {
		useInvoke = true;
	}
	
	/**
	 * Stops the Scene from invoking methods on events, this is the default state
	 */
	public void stopInvoke() {
		useInvoke = false;
	}
	
	/**
	 * Invokes a method inside the Scene class, defined by given parameters.
	 * If no parameters exist, use the alternative invoke method
	 * 
	 * @param methodName String
	 * @param params Class[]
	 * @param paramValues Object[]
	 * 
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean invoke(String methodName, Class<?>[] params, Object[] paramValues) {
		for(Method m : this.getClass().getDeclaredMethods()) {
			if(m.getName().equals(methodName)) {
				if(Arrays.equals(params, m.getParameterTypes())) {
					try {
						m.invoke(this, paramValues);
						return true;
					} catch (IllegalArgumentException e) {
						Debug.error("Invoking, IllegalArgument");
						e.printStackTrace();
						return false;
					} catch (IllegalAccessException e) {
						Debug.error("Invoking, IllegalAccess");
						e.printStackTrace();
						return false;
					} catch (InvocationTargetException e) {
						Debug.error("Invoking, IllegalTarget");
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Invokes a method by parameters inside a Callback object
	 * 
	 * @param callback valid Callback object
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean invoke(Callback callback) {
		if(callback.parameters == null) {
			return invoke(callback.methodName);
		} 
		if(callback.parameterTypes == null) {
			return invoke(callback.methodName, callback.parameters);
		}
		return invoke(callback.methodName, callback.parameterTypes, callback.parameters);
	}
	
	/**
	 * USE AT YOUR OWN RISK
	 * Invokes a method inside the Scene class, it selects the first matching method name and tries to pass on given parameters
	 * An error will be raised if this isn't the correct method. This routine is simply for those too lazy (or wanting to save
	 * on a little processing time) and are 100% sure there are no name conflicts.
	 * 
	 * IllegalArgumentException may be passed to the Debug class, logcat will be notified - but there is no way to test at your end.
	 * 
	 * @param methodName String
	 * @param paramValues Object[]
	 * 
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean invoke(String methodName, Object[] paramValues) {
		for(Method m : this.getClass().getDeclaredMethods()) {
			if(m.getName().equals(methodName)) {
				try {
					m.invoke(this, paramValues);
					return true;
				} catch (IllegalArgumentException e) {
					Debug.error("Invoking, IllegalArgument");
					e.printStackTrace();
					return false;
				} catch (IllegalAccessException e) {
					Debug.error("Invoking, IllegalAccess");
					e.printStackTrace();
					return false;
				} catch (InvocationTargetException e) {
					Debug.error("Invoking, IllegalTarget");
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}
	
	/**
	 * Invokes a method inside the Scene class, assuming there are no parameters to pass
	 * 
	 * @param methodName String
	 * 
	 * @return TRUE if successful, FALSE otherwise
	 */
	public boolean invoke(String methodName) {
		for(Method m : this.getClass().getDeclaredMethods()) {
			if(m.getName().equals(methodName)) {
				if(m.getParameterTypes().length == 0) {
					try {
						m.invoke(this);
						return true;
					} catch (IllegalArgumentException e) {
						Debug.error("Invoking, IllegalArgument");
						e.printStackTrace();
						return false;
					} catch (IllegalAccessException e) {
						Debug.error("Invoking, IllegalAccess");
						e.printStackTrace();
						return false;
					} catch (InvocationTargetException e) {
						Debug.error("Invoking, IllegalTarget");
						e.printStackTrace();
						return false;
					}
				}
			}
		}
		return false;
	}
	
	protected void handleTouch(MotionEvent event) {
		float realX = event.getX() * (Graphics.getWidthPixels() / RokonActivity.gameWidth);
		float realY = event.getY() * (Graphics.getHeightPixels() / RokonActivity.gameHeight);
		if(window != null) {
			float xFraction = event.getX() / Graphics.getWidthPixels();
			float yFraction = event.getY() / Graphics.getHeightPixels();
			float gameX = window.x + (window.width * xFraction);
			float gameY = window.y + (window.height * yFraction);
			event.setLocation(gameX, gameY);
		} else {
			event.setLocation(event.getX() * (Graphics.getWidthPixels() / RokonActivity.gameWidth), event.getY() * (Graphics.getHeightPixels()  / RokonActivity.gameHeight));			
		}
		onTouch(event.getX(), event.getY(), event);
		onTouchReal(realX, realY, event);
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				onTouchDown(event.getX(), event.getY(), event);
				onTouchDownReal(realX, realY, event);
				break;
			case MotionEvent.ACTION_UP:
				onTouchUp(event.getX(), event.getY(), event);
				onTouchUpReal(realX, realY, event);
				break;
			case MotionEvent.ACTION_MOVE:
				onTouch(event.getX(), event.getY(), event);
				onTouchUp(realX, realY, event);
				break;
		}
		for(int i = 0; i < layerCount; i++) {
			for(int j = 0; j < layer[i].maximumDrawableObjects; j++) {
				float checkX, checkY;
				checkX = event.getX();
				checkY = event.getY();
				if(layer[i].ignoreWindow) {
					checkX = realX;
					checkY = realY;
				}
				GameObject object = layer[i].gameObjects.get(j);
				if(object != null && object.isTouchable) {
					if(MathHelper.pointInRect(checkX, checkY, object.x, object.y, object.width, object.height)) {
						onTouch(object, checkX, checkY, event);
						if(object.getName() != null) {
							invoke(object.getName() + "_onTouch", new Class[] { float.class, float.class, MotionEvent.class }, new Object[] { event.getX(), event.getY(), event });
						}
						switch(event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								onTouchDown(object, checkX, checkY, event);
								if(object.getName() != null) {
									invoke(object.getName() + "_onTouchDown", new Class[] { float.class, float.class, MotionEvent.class }, new Object[] { event.getX(), event.getY(), event });
								}
								break;
							case MotionEvent.ACTION_UP:
								onTouchUp(object, checkX, checkY, event);
								if(object.getName() != null) {
									invoke(object.getName() + "_onTouchUp", new Class[] { float.class, float.class, MotionEvent.class }, new Object[] { event.getX(), event.getY(), event });
								}
								break;
							case MotionEvent.ACTION_MOVE:
								onTouch(object, checkX, checkY, event);
								if(object.getName() != null) {
									invoke(object.getName() + "_onTouchMove", new Class[] { float.class, float.class, MotionEvent.class }, new Object[] { event.getX(), event.getY(), event });
								}
								break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Creates a new Scene with given layer count, and a corresponding maximum DrawableObject count 
	 * 
	 * @param layerCount maximum number of layers
	 * @param layerObjectCount maximum number of DrawableObjects per layer, the array length must match layerCount
	 */
	public Scene(int layerCount, int[] layerObjectCount) {
		this.layerCount = layerCount;
		layer = new Layer[layerCount];
		for(int i = 0; i < layerCount; i++) {
			layer[i] = new Layer(this, layerObjectCount[i]);
		}
		prepareNewScene();
	}
	
	/**
	 * Creates a new Scene with given layer count, all layers will have the same maximum number of DrawableObjects
	 * 
	 * @param layerCount maximum number of layers
	 * @param layerObjectCount maximum number of DrawableObjects per layer
	 */
	public Scene(int layerCount, int layerObjectCount) {
		this.layerCount = layerCount;
		layer = new Layer[layerCount];
		for(int i = 0; i < layerCount; i++) {
			layer[i] = new Layer(this, layerObjectCount);
		}
		prepareNewScene();
	}
	
	/**
	 * Creates a new Scene with given layer count, and a default maximum DrawableObject count of DEFAULT_LAYER_OBJECT_COUNT
	 * 
	 * @param layerCount maximum number of layers
	 */
	public Scene(int layerCount) {
		this(layerCount, DEFAULT_LAYER_OBJECT_COUNT);
	}
	
	/**
	 * Creates a new Scene with defaults, DEFAULT_LAYER_COUNT and DEFAULT_LAYER_OBJECT_COUNT
	 */
	public Scene() {
		this(DEFAULT_LAYER_COUNT, DEFAULT_LAYER_OBJECT_COUNT);
		
	}
	
	private void prepareNewScene() {
		texturesToLoad = new Texture[SCENE_TEXTURE_COUNT];
		texturesOnHardware = new Texture[SCENE_TEXTURE_COUNT];
	}
	
	/**
	 * Flags a Texture to be loaded into this Scene
	 * This must be called before RokonActivity.setScene
	 * 
	 * @param texture valid Texture object
	 */
	public void useTexture(Texture texture) {
		for(int i = 0; i < texturesToLoad.length; i++) {
			if(texturesToLoad[i] == texture) return;
			if(texturesToLoad[i] == null) {
				texturesToLoad[i] = texture;
				return;
			}
		}
		Debug.warning("Scene.useTexture", "Tried loading too many Textures onto the Scene, max is " + texturesToLoad.length);
	}
	
	/**
	 * Defines the active Window for this Scene
	 * If no Window is given, a default static view will be rendered 
	 * 
	 * @param window
	 */
	public void setWindow(Window window) {
		if(window == null) {
			Debug.warning("Scene.setWindow", "Tried setting a NULL Window");
			return;
		}
		this.window = window;
	}
	
	/**
	 * Removes the current active Window, returning it to NULL
	 */
	public void removeWindow() {
		window = null;
	}
	
	/**
	 * @return NULL if there is no Window associated with this Scene
	 */
	public Window getWindow() {
		if(window == null)
			return null;
		return window;
	}
	
	/**
	 * Fetches the Layer object associated with the given index
	 * 
	 * @param index the index of the Layer
	 * @return NULL if invalid index is given
	 */
	public Layer getLayer(int index) {
		if(index < 0 || index > layerCount) {
			Debug.warning("Scene.getLayer", "Tried fetching invalid layer (" + index + "), maximum is " + layerCount);
			return null;
		}
		return layer[index];
	}
	
	/**
	 * Clears the DrawableObjects from all Layers
	 */
	public void clear() {
		for(int i = 0; i < layerCount; i++) {
			layer[i].clear();
		}
	}
	
	/**
	 * Clears all the DrawableObjects from a specified Layer
	 * 
	 * @param index the index of the Layer
	 */
	public void clearLayer(int index) {
		if(index <= 0 || index > layerCount) {
			Debug.warning("Scene.clearLayer", "Tried clearing invalid layer (" + index + "), maximum is " + layerCount);
			return;
		}
		layer[index].clear();
	}
	
	/**
	 * Moves a Layer from one index to another, and shuffles the others up or down to accomodate
	 * 
	 * @param startIndex the current index of the Layer
	 * @param endIndex the desired final index of the Layer
	 */
	public void moveLayer(int startIndex, int endIndex) {
		if(startIndex == endIndex) {
			Debug.warning("Scene.moveLayer", "Tried moving a Layer to its own position, stupid");
			return;
		}
		if(startIndex <= 0 || startIndex > layerCount) {
			Debug.warning("Scene.moveLayer", "Tried moving an invalid Layer, startIndex=" + startIndex + ", maximum is " + layerCount);
			return;
		}
		if(endIndex <= 0 || endIndex > layerCount) {
			Debug.warning("Scene.moveLayer", "Tried moving an invalid Layer, endIndex=" + endIndex + ", maximum is " + layerCount);
			return;
		}
		Layer temporaryLayer = layer[startIndex];
		if(endIndex < startIndex) {
			for(int i = endIndex; i < startIndex; i++) {
				layer[i + 1] = layer[i];
			}
			layer[endIndex] = temporaryLayer;
		}
		if(endIndex > startIndex) { 
			for(int i = startIndex; i < endIndex; i++) {
				layer[i] = layer[i + 1];
			}
			layer[endIndex] = temporaryLayer;
		}
	}
	
	/**
	 * Switches the position of one Layer with another
	 * 
	 * @param layer1 the index of the first Layer
	 * @param layer2 the index of the second Layer
	 */
	public void switchLayers(int layer1, int layer2) {
		if(layer1 == layer2) {
			Debug.warning("Scene.switchLayers", "Tried switching the same Layer");
			return;
		}
		if(layer1 < 0 || layer1 > layerCount) {
			Debug.warning("Scene.switchLayers", "Tried switch an invalid Layer, layer1=" + layer1 + ", maximum is " + layerCount);
			return;
		}
		if(layer2 < 0 || layer2 > layerCount) {
			Debug.warning("Scene.switchLayers", "Tried switch an invalid Layer, layer2=" + layer2 + ", maximum is " + layerCount);
			return;
		}
		Layer temporaryLayer = layer[layer1];
		layer[layer1] = layer[layer2];
		layer[layer2] = temporaryLayer;
	}
	
	/**
	 * Replaces a Layer object in this Scene
	 * 
	 * @param index a valid index for a Layer, less than getLayerCount
	 * @param layer a valid Layer object to replace the existing Layer
	 */
	public void setLayer(int index, Layer layer) {
		if(layer == null) {
			Debug.warning("Scene.setLayer", "Tried setting to a null Layer");
			return;
		}
		if(index < 0 || index > layerCount) {
			Debug.warning("Scene.setLayer", "Tried setting an invalid Layer, index=" + index + ", maximum is " + layerCount);
			return;
		}
		this.layer[index] = layer;
	}
	
	/**
	 * Adds a DrawableObject to the first (0th) Layer
	 * 
	 * @param drawableObject a valid DrawableObject
	 */
	public void add(GameObject drawableObject) {
		layer[0].add(drawableObject);
	}
	
	/**
	 * Adds a DrawableObject to a given Layer
	 * 
	 * @param layerIndex a valid index of a Layer
	 * @param drawableObject a valid DrawableObject
	 */
	public void add(int layerIndex, GameObject drawableObject) {
		if(layerIndex < 0 || layerIndex > layerCount) {
			Debug.warning("Scene.add", "Tried adding to an invalid Layer, layerIndex=" + layerIndex + ", maximum is " + layerCount);
			return;
		}
		if(drawableObject == null) {
			Debug.warning("Scene.add", "Tried adding a NULL DrawableObject");
			return;
		}
		layer[layerIndex].add(drawableObject);
	}
	
	/**
	 * Removes a DrawableObject from the Scene
	 * 
	 * @param drawableObject a valid DrawableObject
	 */
	public void remove(DrawableObject drawableObject) {
		drawableObject.remove();
	}
	
	protected void onUpdate() {
		
	}
	
	protected void onGameLoop() {
		
	}
	
	protected void onSetScene() {
		loadedTextures = false;
	}
	
	protected void onEndScene() {
		
	}
	
	protected void onLoadTextures(GL10 gl) {
		Debug.print("Loading textures onto the Scene");
		for(int i = 0; i < texturesToLoad.length; i++) {
			if(texturesToLoad[i] != null) {
				texturesToLoad[i].onLoadTexture(gl);
				boolean foundSpace = false;
				for(int j = 0; j < texturesOnHardware.length; j++) {
					if(texturesOnHardware[j] == null) {
						Debug.print("found room...");
						texturesOnHardware[j] = texturesToLoad[i];
						foundSpace = true;
						break;
					}
				}
				if(!foundSpace) {
					Debug.warning("Loading more textures than we can remember - will not be there if we onPause, may not be destroyed on Scene death");
				}
				texturesToLoad[i] = null;
			}
		}
		loadedTextures = true;
	}
	
	protected void onReloadTextures(GL10 gl) {
		texturesToLoad = texturesOnHardware;
		for(int i = 0; i < texturesOnHardware.length; i++) {
			texturesOnHardware[i] = null;
		}
		onLoadTextures(gl);
	}
	
	protected void onDraw(GL10 gl) {
		onPreDraw(gl);
		if(background != null) {
			background.onDraw(gl);
		}
		if(window != null) {
			window.onUpdate(gl);
		}
		if(usePhysics && !pausePhysics) {
			world.step(Time.getTicksFraction(), 10, 10);
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
		for(int i = 0; i < layerCount; i++) {
			layer[i].onDraw(gl);
		}
		onPostDraw(gl);
	}
	
	protected boolean pausePhysics = false;
	
	public void pausePhysics() {
		pausePhysics = true;
	}
	
	public void resumePhysics() {
		pausePhysics = false;
	}
	
	public void setBackground(Background background) {
		this.background = background;
		background.parentScene = this;
	}
	
	public Background getBackground() {
		return this.background;
	}
	
	
	
	
	
	
	
	
	protected class SceneContactListener implements ContactListener {

		public void beginContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}

		public void endContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void useContactListener() {
		if(contactListener == null) {
			contactListener = new SceneContactListener();
		}
		useContactListener = true;
	}
	
	public void stopContactListener() {
		useContactListener = false;
	}
	
	public void setContactListener(ContactListener contactListener) {
		this.contactListener = contactListener;
	}
	
	
	
}
