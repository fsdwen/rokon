package com.stickycoding.rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import android.os.Build;

/**
 * BufferObject.java
 * Some functions for Buffers
 * 
 * @author Richard 
 */

public class BufferObject {

	protected ByteBuffer byteBuffer;
	protected IntBuffer intBuffer;
	
	public BufferObject() {
		if(Build.VERSION.SDK == "3")
			byteBuffer = ByteBuffer.allocate(8*4);
		else
			byteBuffer = ByteBuffer.allocateDirect(8*4);
		byteBuffer.order(ByteOrder.nativeOrder());		
	}
	
	public void free() {
		byteBuffer.clear();
		byteBuffer = null;
	}
	
	public void update(float x, float y, float width, float height) {
		byteBuffer.position(0);
		byteBuffer.putFloat(x);
		byteBuffer.putFloat(y);
		byteBuffer.putFloat(x + width);
		byteBuffer.putFloat(y);
		byteBuffer.putFloat(x);
		byteBuffer.putFloat(y + height);
		byteBuffer.putFloat(x + width);
		byteBuffer.putFloat(y + height);
		byteBuffer.position(0);
	}
	
	public IntBuffer getIntBuffer() {
		return intBuffer;
	}
	
	public ByteBuffer get() {
		return byteBuffer;
	}
	
}
