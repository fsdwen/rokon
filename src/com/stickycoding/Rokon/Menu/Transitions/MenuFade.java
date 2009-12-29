package com.stickycoding.Rokon.Menu.Transitions;

import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Sprite;
import com.stickycoding.Rokon.Menu.Menu;
import com.stickycoding.Rokon.Menu.MenuLayers;
import com.stickycoding.Rokon.Menu.MenuTransition;

public class MenuFade extends MenuTransition {
	
	private int _time;
	private long _startTime;
	private Sprite _sprite;
	private Menu _menu;
	
	public MenuFade(int time) {
		_sprite = new Sprite(0, 0, Rokon.getRokon().getWidth(), Rokon.getRokon().getHeight());
		_sprite.setColor(0, 0, 0, 1);
		_time = time;
	}
	
	public void begin(Menu menu) {
		Debug.print("STARTING TO DO FADE");
		super.begin(menu);
		Rokon.getRokon().addSprite(_sprite, MenuLayers.OVERLAY);
		_startTime = Rokon.time;
		_menu = menu;
	}
	
	private float position;
	public void loop() {
		if(running) {
			position = ((float)(_startTime + _time) - (float)Rokon.time) / (float)_time;
			if(position < 0) {
				running = false;
				Rokon.getRokon().removeSprite(_sprite, MenuLayers.OVERLAY);
				Debug.print("FADE OVER");
				_menu.onStartTransitionComplete();
			} else
				_sprite.setAlpha(position);
		}
	}

}
