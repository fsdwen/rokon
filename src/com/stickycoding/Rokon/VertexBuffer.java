package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An easier way of managing a ByteBuffer for vertices
 * @author Richard
 */
public class VertexBuffer {

	public ByteBuffer buffer;

	/**
	 * Creates the ByteBuffer object and sets up with initial coordinates
	 * @param x1 Top Left X
	 * @param y1 Top Left Y
	 * @param x2 Bottom Right X
	 * @param y2 Bottom Right Y
	 */
	public VertexBuffer(int x1, int y1, int x2, int y2) {
		buffer = ByteBuffer.allocateDirect(8*4);
		buffer.order(ByteOrder.nativeOrder());
		update(x1, y1, x2, y2);
	}
	
	/**
	 * Updates the current ByteBuffer with new coordinates
	 * @param x1 Top Left X
	 * @param y1 Top Left Y
	 * @param x2 Bottom Right X
	 * @param y2 Bottom Right Y
	 */
	public void update(int x1, int y1, int x2, int y2) {
		buffer.position(0);
		buffer.putFloat(x1);
		buffer.putFloat(y1);
		buffer.putFloat(x2);
		buffer.putFloat(y1);
		buffer.putFloat(x1);
		buffer.putFloat(y2);
		buffer.putFloat(x2);
		buffer.putFloat(y2);
		buffer.position(0);
	}
	
	/**
	 * Removes the ByteBuffer from the memory
	 */
	public void destroy() {
		buffer = null;
	}

}
