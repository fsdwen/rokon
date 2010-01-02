package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.os.Build;


/**
 * A more simplistic (less memory usage) object than a Sprite
 * @author Richard
 */
public class BasicSprite extends DynamicObject {
	public static final int MAX_MODIFIERS = 5;
	
	private int i, j, k, r;
	
	private SpriteModifier[] _modifierArr = new SpriteModifier[MAX_MODIFIERS];

	private boolean _killMe;
	
	private Texture _texture;
	private int _tileX;
	private int _tileY;
	private ByteBuffer _texBuffer;
	
	public BasicSprite(float x, float y, float width, float height) {
		this(x, y, width, height, null);
	}
		
	public BasicSprite(float x, float y, float width, float height, Texture texture) {
		super(x, y, width, height);
		_killMe = false;

		if(Build.VERSION.SDK_INT == 3)
			_texBuffer = ByteBuffer.allocate(8*4);
		else
			_texBuffer = ByteBuffer.allocateDirect(8*4);
		_texBuffer.order(ByteOrder.nativeOrder());
		
		if(texture != null)
			setTexture(texture);
		resetDynamics();
		updateVertexBuffer();
	}
	
	public BasicSprite(float x, float y, Texture texture) {
		this(x, y, 0, 0, texture);
		setWidth(texture.getWidth() / texture.getTileColumnCount(), true);
		setHeight(texture.getHeight() / texture.getTileRowCount(), true);
		updateVertexBuffer();
	}
	
	public BasicSprite(Texture texture) {
		this(0, 0, 0, 0, texture);
		setWidth(texture.getWidth() / texture.getTileColumnCount(), true);
		setHeight(texture.getHeight() / texture.getTileRowCount(), true);
		updateVertexBuffer();
	}
	
	/**
	 * @return TRUE if the Sprite is marked for removal from the Layer after the next frame.
	 */
	public boolean isDead() {
		return _killMe;
	}
	
	/**
	 * Marks the Sprite for removal, it will be taken off the Layer at the end of the current frame 
	 */
	public void markForRemoval() {
		_killMe = true;
	}
	
	/**
	 * @param texture applies a Texture to the Sprite
	 */
	public void setTexture(Texture texture) {
		_texture = texture;
		_tileX = 1;
		_tileY = 1;
		_updateTextureBuffer();
	}
	
	/**
	 * Removes the Texture that has been applied to the Sprite
	 */
	public void resetTexture() {
		_texture = null;
	}
	
	/**
	 * @param tileIndex the index of the Texture tile to be used by the Sprite, 1-based
	 */
	public void setTileIndex(int tileIndex) {
		if(_texture == null) {
			Debug.print("Error - Tried setting tileIndex of null texture");
			return;			
		}
		tileIndex -= 1;
		_tileX = (tileIndex % _texture.getTileColumnCount()) + 1;
		_tileY = ((tileIndex - (_tileX - 1)) / _texture.getTileColumnCount()) + 1;
		tileIndex += 1;
		//Debug.print("Updating tile index idx=" + tileIndex + " x=" + _tileX + " y=" + _tileY);
		_updateTextureBuffer();
	}
	
	/**
	 * @return the current Texture tile index that is being used by the Sprite
	 */
	public int getTileIndex() {
		int tileIndex = 0;
		tileIndex += _tileX;
		tileIndex += (_tileY - 1) * _texture.getTileColumnCount();
		return tileIndex;
	}	
	
	/**
	 * Sets the Texture tile index to be used by the Sprite by columns and rows, rather than index
	 * @param tileX column
	 * @param tileY row
	 */
	public void setTile(int tileX, int tileY) {
		_tileX = tileX;
		_tileY = tileY;
		_updateTextureBuffer();
	}
	
	/**
	 * @return the current Texture applied to the Sprite
	 */
	public Texture getTexture() {
		return _texture;
	}
	
	private float x1, y1, x2, y2, xs, ys, fx1, fx2, fy1, fy2;
	private void _updateTextureBuffer() {		
		if(_texture == null)
			return;
		
		if(_texture.getTextureAtlas() == null)
			return;
		
		x1 = _texture.getAtlasX();
		y1 = _texture.getAtlasY();
		x2 = _texture.getAtlasX() + _texture.getWidth();
		y2 = _texture.getAtlasY() + _texture.getHeight();

		xs = (x2 - x1) / _texture.getTileColumnCount();
		ys = (y2 - y1) / _texture.getTileRowCount();

		x1 = _texture.getAtlasX() + (xs * (_tileX - 1));
		x2 = _texture.getAtlasX() + (xs * (_tileX - 1)) + xs; 
		y1 = _texture.getAtlasY() + (ys * (_tileY - 1));
		y2 = _texture.getAtlasY() + (ys * (_tileY - 1)) + ys; 
		
		fx1 = x1 / (float)_texture.getTextureAtlas().getWidth();
		fx2 = x2 / (float)_texture.getTextureAtlas().getWidth();
		fy1 = y1 / (float)_texture.getTextureAtlas().getHeight();
		fy2 = y2 / (float)_texture.getTextureAtlas().getHeight();
		
		if(!_texture.isFlipped()) {
			_texBuffer.position(0);		
			_texBuffer.putFloat(fx1); _texBuffer.putFloat(fy1);
			_texBuffer.putFloat(fx2); _texBuffer.putFloat(fy1);
			_texBuffer.putFloat(fx1); _texBuffer.putFloat(fy2);
			_texBuffer.putFloat(fx2); _texBuffer.putFloat(fy2);		
			_texBuffer.position(0);
		} else {
			_texBuffer.position(0);		
			_texBuffer.putFloat(fx1); _texBuffer.putFloat(fy2);
			_texBuffer.putFloat(fx2); _texBuffer.putFloat(fy2);	
			_texBuffer.putFloat(fx1); _texBuffer.putFloat(fy1);
			_texBuffer.putFloat(fx2); _texBuffer.putFloat(fy1);	
			_texBuffer.position(0);
		}
	}
	
	/**
	 * Updates the texture buffers used by OpenGL, there should be no need to call this
	 */
	public void updateBuffers() {
		_updateTextureBuffer();
	}

	/**
	 * Draws the Sprite to the OpenGL object, should be no need to call this
	 * @param gl
	 */
	private boolean hasTexture;
	public void drawFrame(GL10 gl) {
		
		if(notOnScreen())
			return;
		
		if(_texture == null)
			hasTexture = false;
		else
			hasTexture = true;
		
		if(!hasTexture) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		} else {
			_texture.select(gl);
		}
		
		gl.glLoadIdentity();
		gl.glVertexPointer(2, GL11.GL_FLOAT, 0, getVertexBuffer());
		
		for(i = 0; i < MAX_MODIFIERS; i++)
			if(_modifierArr[i] != null)
				_modifierArr[i].onDraw(this, gl);

		if(getRotation() != 0) {
			if (getRotationPivotRelative()) {
				gl.glTranslatef(getX() + (getScaleX() * getRotationPivotX()), getY() + (getScaleY() * getRotationPivotY()), 0);
				gl.glRotatef(getRotation(), 0, 0, 1);
				gl.glTranslatef(-1 * (getX() + (getScaleX() * getRotationPivotX())), -1 * (getY() + (getScaleY() * getRotationPivotY())), 0);
			} else {
				gl.glTranslatef(getRotationPivotX(), getRotationPivotY(), 0);
				gl.glRotatef(getRotation(), 0, 0, 1);
				gl.glTranslatef(-1 * getRotationPivotX(), -1 * getRotationPivotY(), 0);
			}
		}
		if(hasTexture)
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _texBuffer);	

		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		if(!hasTexture) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnable(GL10.GL_TEXTURE_2D);
		}
	}
	
	/**
	 * Note, this is very basic and does represents only the rectangular Sprite
	 * @param x
	 * @param y
	 * @return TRUE if the Sprite is colliding with the given coordinates
	 */
	public boolean isAt(int x, int y) {
		if(x < getX() || x > getX() + getWidth())
			return false;
		if(y < getY() || y > getY() + getHeight())
			return false;
		return true;
	}
	
	/**
	 * @param spriteModifier a SpriteModifier to add the Sprite 
	 */
	public void addModifier(SpriteModifier spriteModifier) {
		j = -1;
		for(i = 0; i < MAX_MODIFIERS; i++)
			if(_modifierArr[i] == null)
				j = i;
		if(j == -1) {
			Debug.print("TOO MANY SPRITE MODIFIERS");
			return;
		}
		_modifierArr[j] = spriteModifier;
	}
	
	/**
	 * @param spriteModifier a SpriteModifier to remove from the Sprite
	 */
	public void removeModifier(SpriteModifier spriteModifier) {
		for(i = 0; i < MAX_MODIFIERS; i++)
			if(_modifierArr[i].equals(spriteModifier))
				_modifierArr[i] = null;
	}
	
	/**
	 * Updates the movement, animation and modifiers. This is called automatically, no need to use this.
	 */
	private long timeDiff;
	private int nextTile;
	public void updateMovement() {
		//	if this is the first update, forget about it
		if(getLastUpdate() == 0) {
			setLastUpdate();
			return;
		}
		
		super.updateMovement();
		
		for(r = 0; r < MAX_MODIFIERS; r++)
			if(_modifierArr[r] != null) {
				_modifierArr[r].onUpdate(this);
				if(_modifierArr[r].isExpired())
					_modifierArr[r] = null;
			}
	}
	
	/**
	 * Removes all SpriteModifier's from the Sprite
	 */
	public void resetModifiers() {
			for(k = 0; k < MAX_MODIFIERS; k++) {
				try {
					_modifierArr[k] = null;
				} catch (Exception e) { }
			}
	}
	
	/**
	 * Resets the sprite to the initial conditions
	 */
	public void reset() {
		reset(true);
	}
	
	/**
	 * Resets the sprite to the initial conditions
	 * @param resetTexture TRUE if the texture is to be reset to the first tile
	 */
	public void reset(boolean resetTexture) {
		super.reset();
		stop();
		if(resetTexture)
			setTileIndex(1);
	}
	
	/**
	 * Revives the Sprite, so that it can be used again.
	 */
	public void revive() {
		_killMe = false;
	}
}