package com.stickycoding.rokon.device;

import android.os.Build;

public class OS {
	
	public static int API_LEVEL  = 0;
	
	public static void determineAPI() {
		if(Build.VERSION.SDK == "1") {
			API_LEVEL = 1;
		}
		if(Build.VERSION.SDK == "2") {
			API_LEVEL = 2;
		}
		if(Build.VERSION.SDK == "3") {
			API_LEVEL = 3;
		}
		if(Build.VERSION.SDK == "4") {
			API_LEVEL = 4;
		}
		if(Build.VERSION.SDK == "5") {
			API_LEVEL = 5;
		}
		if(Build.VERSION.SDK == "6") {
			API_LEVEL = 6;
		}
		if(Build.VERSION.SDK == "7") {
			API_LEVEL = 7;
		}
		if(Build.VERSION.SDK == "8") {
			API_LEVEL = 8;
		}
	}

}
