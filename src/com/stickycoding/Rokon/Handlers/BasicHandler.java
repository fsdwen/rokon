package com.stickycoding.Rokon.Handlers;

/**
 * The BasicHandler is a very simple handler, which is intended for SpriteModifiers
 * to use in order to trigger events in the game thread
 */
public class BasicHandler {
	
	public Object obj = null;
	
	public BasicHandler() {
		
	}
	
	public BasicHandler(Object obj) {
		this.obj = obj;
	}
	
	/**
	 * A generic event
	 */
	public void onFinished() {
		if(obj != null)
			onFinished(obj);
	}
	
	public void onFinished(Object obj) {
		
	}
	
	/**
	 * A generic event, passed with an Integer code
	 * @param code
	 */
	public void onTrigger(int code) {
		
	}

}
