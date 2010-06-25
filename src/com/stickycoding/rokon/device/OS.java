package com.stickycoding.rokon.device;

import android.os.Build;

public class OS {
	
	public static int API_LEVEL  = 0;
	
	public static void determineAPI() {
		if(Build.VERSION.SDK == "1") {
			API_LEVEL = 1;
			return;
		}
		if(Build.VERSION.SDK == "2") {
			API_LEVEL = 2;
			return;
		}
		if(Build.VERSION.SDK == "3") {
			API_LEVEL = 3;
			return;
		}
		if(Build.VERSION.SDK == "4") {
			API_LEVEL = 4;
			return;
		}
		if(Build.VERSION.SDK == "5") {
			API_LEVEL = 5;
			return;
		}
		if(Build.VERSION.SDK == "6") {
			API_LEVEL = 6;
			return;
		}
		if(Build.VERSION.SDK == "7") {
			API_LEVEL = 7;
			return;
		}
		if(Build.VERSION.SDK == "8") {
			API_LEVEL = 8;
			return;
		}
		API_LEVEL = Integer.parseInt(Build.VERSION.SDK);
		Debug.warning("UNKNOWN API LEVEL " + Build.VERSION.SDK);
	}

}
