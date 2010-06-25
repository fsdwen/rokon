package com.stickycoding.rokon.device;

import android.os.Build;

import com.stickycoding.rokon.Debug;

public class OS {
	
	public static int API_LEVEL  = 0;
	
	public static void determineAPI() {
		API_LEVEL = Integer.parseInt(Build.VERSION.SDK);
		Debug.warning("API LEVEL " + Build.VERSION.SDK + " int=" + API_LEVEL);
	}

}
