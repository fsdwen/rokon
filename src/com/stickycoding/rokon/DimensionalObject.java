package com.stickycoding.rokon;

/**
 * Dimensional.java
 * An object with position and dimensions
 * 
 * @author Richard
 */

public class DimensionalObject extends Point {
	
	protected float width, height;

	public DimensionalObject(float x, float y, float width, float height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setSize(float width, float height) {
		this.width = width;
	}
	
	public void growWidth(float width) {
		this.width += width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void growHeight(float height) {
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.growHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void shrinkWidth(float width) {
		this.width -= width;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkWidth", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void shrinkHeight(float height) {
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrinkHeight", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void shrink(float width, float height) {
		this.width -= width;
		this.height -= height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.shrink", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void grow(float width, float height) {
		this.width += width;
		this.height += height;
		if(this.width < 0 || this.height < 0) {
			Debug.warning("StaticObject.grow", "Dimensions < 0");
			if(this.width < 0) this.width = 0;
			if(this.height < 0) this.height = 0;
		}
	}
	
	public void centre(float x, float y) {
		setX(x - width / 2);
		setY(y - height / 2);
	}

	protected boolean moving;
	protected float startX, startY, startWidth, startHeight;
	protected float finishX, finishY, finishWidth, finishHeight;
	protected long startTime;
	protected int moveTime, moveType;
	
	public void onUpdate() {
		
	}

}
