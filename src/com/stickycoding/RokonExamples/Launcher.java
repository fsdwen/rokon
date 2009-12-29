package com.stickycoding.RokonExamples;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Launcher extends ListActivity {

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		String[] examples = new String[] {
			"Loading the engine",
			"Textures and backgrounds",
			"Sprites",
			"Touch and Keyboard Input",
			"Movement",
			"Sprite Modifiers",
			"Animation",
			"TTF Text Rendering",
			"Audio",
			"Vibration",
			"Advanced Texture Methods",
			"Pausing / Freezing",
			"Basic Menu",
			"Advanced Menu System",
			"Accelerometer",
			"Basic Particle System",
			"Custom Sprite Animation",
			"Post Processing",
			"Parallax Background"
		};
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent;
		switch(position) {
			default:
			case 0: // Loading the Engine
				intent = new Intent(this, Example1.class);
				startActivity(intent);
				break;
			case 1: // Textures and Backgrounds
				intent = new Intent(this, Example2.class);
				startActivity(intent);
				break;
			case 2: // Sprites
				intent = new Intent(this, Example3.class);
				startActivity(intent);
				break;
			case 3: // Touch and Keyboard Input
				intent = new Intent(this, Example4.class);
				startActivity(intent);
				break;
			case 4: // Movement
				intent = new Intent(this, Example5.class);
				startActivity(intent);
				break;
			case 5: // Sprite Modifiers
				intent = new Intent(this, Example6.class);
				startActivity(intent);
				break;
			case 6: // Animation
				intent = new Intent(this, Example7.class);
				startActivity(intent);
				break;
			case 7: // TTF Font Rendering
				intent = new Intent(this, Example8.class);
				startActivity(intent);
				break;
			case 8: // Audio
				intent = new Intent(this, Example9.class);
				startActivity(intent);
				break;
			case 9: // Vibration
				intent = new Intent(this, Example10.class);
				startActivity(intent);
				break;
			case 10: // Advanced Texture Methods
				intent = new Intent(this, Example11.class);
				startActivity(intent);
				break;
			case 11: // Pausing / Freezing
				intent = new Intent(this, Example12.class);
				startActivity(intent);
				break;
			case 12: // Basic Menu
				intent = new Intent(this, Example13.class);
				startActivity(intent);
				break;
			case 13: // Advanced Menu
				intent = new Intent(this, Example14.class);
				startActivity(intent);
				break;
			case 14: // Accelerometer
				intent = new Intent(this, Example15.class);
				startActivity(intent);
				break;
			case 15: // Basic Particle System
				intent = new Intent(this, Example16.class);
				startActivity(intent);
				break;
			case 16: // Custom Sprite Animation
				intent = new Intent(this, Example17.class);
				startActivity(intent);
				break;
			case 17: // Post Processing
				intent = new Intent(this, Example18.class);
				startActivity(intent);
				break;
			case 18: // Post Processing
				intent = new Intent(this, Example19.class);
				startActivity(intent);
				break;
		}
	}
	
}
