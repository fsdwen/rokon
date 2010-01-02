package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.os.Build;

/**
 * A holder for the a Texture buffer, easier way of handling the ByteBuffer
 * @author Richard
 */
public class TextureBuffer {
	
	private ByteBuffer _buffer;
	private Texture _texture;
	private int _clipLeft = 0, _clipRight = 0, _clipTop = 0, _clipBottom = 0;
	private int _tileX, _tileY;
	 
	public TextureBuffer(Texture texture) {
		_texture = texture;
		if(Build.VERSION.SDK_INT == 3)
			_buffer = ByteBuffer.allocate(8*4);
		else
			_buffer = ByteBuffer.allocateDirect(8*4);
		_buffer.order(ByteOrder.nativeOrder());
		_tileX = 1;
		_tileY = 1;
		update();
	}
	
	public TextureBuffer(Texture texture, int tileIndex) {
		_texture = texture;
		if(Build.VERSION.SDK_INT == 3)
			_buffer = ByteBuffer.allocate(8*4);
		else
			_buffer = ByteBuffer.allocateDirect(8*4);
		_buffer.order(ByteOrder.nativeOrder());
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
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
	
	private float _x1, _y1, _x2, _y2, xs, ys;
	public void update() {
		if(_texture.getTextureAtlas() == null)
			return;

		_x1 = _texture.getAtlasX();
		_y1 = _texture.getAtlasY();
		_x2 = _texture.getAtlasX() + _texture.getWidth();
		_y2 = _texture.getAtlasY() + _texture.getHeight();
		
		xs = (_x2 - _x1) / _texture.getTileColumnCount();
		ys = (_y2 - _y1) / _texture.getTileRowCount();

		_x1 = _texture.getAtlasX() + (xs * (_tileX - 1)) + _clipLeft;
		_y1 = _texture.getAtlasY() + (ys * (_tileY - 1)) + _clipTop;
		_x2 = _texture.getAtlasX() + (xs * (_tileX - 1)) + xs - _clipBottom; 
		_y2 = _texture.getAtlasY() + (ys * (_tileY - 1)) + ys - _clipRight;

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
