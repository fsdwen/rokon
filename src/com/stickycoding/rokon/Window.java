package com.stickycoding.rokon;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * Window.java
 * A way of controlling what part of a Scene is visible to the player
 * 
 * @author Richard
 */

public class Window {
	
	//TODO Update touch events to take into account Window
	//TODO Needs documentation
	
	public float x, y, width, height;
	protected boolean moving;
	protected float startX, startY, startWidth, startHeight;
	protected float finishX, finishY, finishWidth, finishHeight;
	protected long startTime;
	protected int moveTime, moveType;
	
	public Window(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public static void setDefault(GL10 gl) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
        GLU.gluOrtho2D(gl, 0, RokonActivity.gameWidth, RokonActivity.gameHeight, 0);
	}
	
	protected void onUpdate(GL10 gl) {
		if(moving) {
			float position = (float)(Time.ticks - startTime) / (float)moveTime;
			float factor = Movement.getPosition(position, moveType);
			if(position >= 1) {
				x = finishX;
				y = finishY;
				width = finishWidth;
				height = finishHeight;
				moving = false;
			} else {
				x = startX + ((finishX - startX) * factor);
				y = startY + ((finishY - startY) * factor);
				width = startWidth + ((finishWidth - startWidth) * factor);
				height = startHeight+ ((finishHeight - startHeight) * factor);
			}
		}
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
        GLU.gluOrtho2D(gl, x, x + width, y + height, y);
	}
	
	public float getRatio() {
		return width / height;
	}
	
	public void move(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void move(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void resize(float width) {
		float ratio = getRatio();
		this.width = width;
		this.height = width / ratio;
	}
	
	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void resize(float width, float height, int time) {
		move(x, y, width, height, time, Movement.SMOOTH);
	}
	
	public void resize(float width, float height, int time, int movementType) {
		move(x, y, width, height, time, movementType);
	}
	
	public void move(float x, float y, int time) {
		move(x, y, width, height, time, Movement.SMOOTH);
	}
	
	public void move(float x, float y, int time, int movementType) {
		move(x, y, width, height, time, movementType);
	}
	
	public void move(float x, float y, float width, float height, int time) {
		move(x, y, width, height, time, Movement.SMOOTH);
	}
	
	public void move(float x, float y, float width, float height, int time, int movementType) {
		startX = this.x;
		startY = this.y;
		startWidth = this.width;
		startHeight = this.height;
		startTime = Time.ticks;
		moveTime = time;
		moveType = movementType;
		finishX = x;
		finishY = y;
		finishWidth = width;
		finishHeight = height;
		moving = true;
	}
	
	public void centre(float x, float y) {
		this.x = x - width / 2;
		this.y = y - height / 2;
	}

}
