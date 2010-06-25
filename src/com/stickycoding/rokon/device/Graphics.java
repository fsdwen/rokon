package com.stickycoding.rokon.device;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.stickycoding.rokon.Debug;

/**
 * Device.java
 * Discovers and retrieves information about the devices hardware
 * 
 * @author Richard 
 */
public class Graphics {
	
	public static int NORMAL = 0, WIDE = 1, FULL_WIDTH = 2;
	
	public static final float WIDE_RATIO = 0.60037524f;
	
	private static DisplayMetrics displayMetrics;	
	private static int widthPixels, heightPixels, halfWidthPixels, halfHeightPixels;
	private static float aspectRatio;
	private static int screenType;
	private static boolean isOpenGL10;
	private static boolean supportsVBO;
	private static boolean supportsDrawTex;
	
	public static int getScreenType() {
		return screenType;
	}
	
	public static float getAspectRatio() {
		return aspectRatio;
	}
	
	public static boolean isNormalAspect() {
		return screenType == NORMAL;
	}
	
	public static boolean isWideAspect() {
		return screenType == WIDE;
	}
	
	public static boolean isFullWidthAspect() {
		return screenType == FULL_WIDTH;
	}
	
	/**
	 * Determines several characterstics of the physical device and stores for later usage
	 * This must be called before any other methods in this class are used
	 * 
	 * @param activity a currently active Activity
	 */
	public static void determine(Activity activity) {
		displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		widthPixels = displayMetrics.widthPixels;
		heightPixels = displayMetrics.heightPixels;
		halfWidthPixels = widthPixels / 2;
		halfHeightPixels = heightPixels / 2;
		aspectRatio = (float)widthPixels / (float)heightPixels;
		if(aspectRatio == WIDE_RATIO) {
			screenType = WIDE;
		}
		if(aspectRatio < WIDE_RATIO) {
			screenType = NORMAL;
		}
		if(aspectRatio > FULL_WIDTH) {
			screenType = FULL_WIDTH;
		}
	}
	
	/**
	 * Returns the DisplayMetrics object associated with the current screen
	 * 
	 * @return NULL if determine() has not been called previously
	 */
	public static DisplayMetrics getDisplayMetrics() {
		if(displayMetrics == null)
			return null;
		return displayMetrics;
	}
	
	/**
	 * Returns the width of the screen, in pixels
	 * 
	 * @return integer
	 */
	public static int getWidthPixels() {
		return widthPixels;
	}

	
	/**
	 * Returns the height of the screen, in pixels
	 * 
	 * @return integer
	 */
	public static int getHeightPixels() {
		return heightPixels;
	}

	
	/**
	 * Returns half the width of the screen, in pixels
	 * 
	 * @return integer
	 */
	public static int getHalfWidthPixels() {
		return halfWidthPixels;
	}

	
	/**
	 * Returns half the height of the screen, in pixels
	 * 
	 * @return integer
	 */
	public static int getHalfHeightPixel() {
		return halfHeightPixels;
	}

	/**
	 * @return TRUE if the device supports VBOs
	 */
	public static boolean supportsVBO() {
		return isSupportsVBO();
	}
	
	/**
	 * @return TRUE if the device supports the draw_tex extension
	 */
	public static boolean supportsDrawTex() {
		return isSupportsDrawTex();
	}

	public static void setSupportsDrawTex(boolean supportsDrawTex) {
		Graphics.supportsDrawTex = supportsDrawTex;
	}

	public static boolean isSupportsDrawTex() {
		return supportsDrawTex;
	}

	public static void setSupportsVBO(boolean supportsVBO) {
		Graphics.supportsVBO = supportsVBO;
	}

	public static boolean isSupportsVBO() {
		return supportsVBO;
	}

	public static void setOpenGL10(boolean isOpenGL10) {
		Graphics.isOpenGL10 = isOpenGL10;
	}

	public static boolean isOpenGL10() {
		return isOpenGL10;
	}
}
