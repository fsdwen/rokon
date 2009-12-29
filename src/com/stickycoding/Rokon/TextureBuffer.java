package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A holder for the a Texture buffer, easier way of handling the ByteBuffer
 * @author Richard
 */
public class TextureBuffer {
	
	private ByteBuffer _buffer;
	private Texture _texture;
	private int _clipLeft = 0, _clipRight = 0, _clipTop = 0, _clipBottom = 0;
	 
	public TextureBuffer(Texture texture) {
		_texture = texture;
		_buffer = ByteBuffer.allocateDirect(8*4);
		_buffer.order(ByteOrder.nativeOrder());
		update();
	}
	
	public ByteBuffer getBuffer() {
		return _buffer;
	}
	
	public void clip(int left, int top, int right, int bottom) {
        _clipLeft = left;
        _clipTop = top;
        _clipRight = right;
        _clipBottom = bottom;
        update();
}
	
	public Texture getTexture() {
		return _texture;
	}
	
	private float _x1, _y1, _x2, _y2;
	public void update() {
		if(_texture.getTextureAtlas() == null)
			return;
		
		_x1 = _texture.getAtlasX() + _clipLeft;
		_y1 = _texture.getAtlasY() + _clipTop;
		_x2 = _texture.getAtlasX() + _texture.getWidth() - _clipRight;
		_y2 = _texture.getAtlasY() + _texture.getHeight() - _clipBottom;

		_x1 = _x1 / _texture.getTextureAtlas().getWidth();
		_y1 = _y1 / _texture.getTextureAtlas().getHeight();
		_x2 = _x2 / _texture.getTextureAtlas().getWidth();
		_y2 = _y2 / _texture.getTextureAtlas().getHeight();

		_buffer.position(0);		
		_buffer.putFloat(_x1); _buffer.putFloat(_y1);
		_buffer.putFloat(_x2); _buffer.putFloat(_y1);
		_buffer.putFloat(_x1); _buffer.putFloat(_y2);
		_buffer.putFloat(_x2); _buffer.putFloat(_y2);		
		_buffer.position(0);
	}

}
