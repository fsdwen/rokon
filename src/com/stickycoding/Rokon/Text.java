package com.stickycoding.Rokon;

import javax.microedition.khronos.opengles.GL10;

/**
 * Text is an object which can be added to Layer's that will draw text using TTF fonts loaded into the Font class.
 * Essentially, each letter is drawn as its own Sprite, and textured independantly. For this reason, Text should
 * be used for frequently changing text (such as game score's)
 * 
 * Another library is planned which will be optimized for longer or non-changing text, using fixed textures.
 * 
 * @author Richard
 */
public class Text {

	private boolean _killMe;
	private boolean _visible;
	private String _text;
	private int _x;
	private int _y;
	private Font _font;
	private Sprite[] _sprite;
	private int _scale;
	
	private float _red;
	private float _green;
	private float _blue;
	private float _alpha;
	
	public Text(String text, Font font, int x, int y, int scale) {
		_text = text;
		_x = x;
		_y = y;
		_visible = true;
		_font = font;
		_scale = scale;
		_killMe = false;
		_red = 1;
		_green = 1;
		_blue = 1;
		_alpha = 1;
		_updateSprites();
	}
	
	public void setRed(float red) {
		_red = red;
	}
	
	public void setGreen(float green) {
		_green = green;
	}
	
	public void setBlue(float blue) {
		_blue = blue;
	}
	
	public void setAlpha(float alpha) {
		_alpha = alpha;
	}
	
	public void setRedInt(int red) {
		_red = (float)red / 255f;
	}
	
	public void setGreenInt(int green) {
		_green = (float)green / 255f;
	}
	
	public void setBlueInt(int blue) {
		_blue = (float)blue / 255f;
	}
	
	public void setAlphaInt(int alpha) {
		_alpha = (float)alpha / 255f;
	}
	
	public float getAlpha() {
		return _alpha;
	}
	
	public float getRed() {
		return _red;
	}
	
	public float getGreen() {
		return _green;
	}
	
	public float getBlue() {
		return _blue;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	public int getRedInt() {
		return Math.round(_red * 255);
	}
	
	public int getGreenInt() {
		return Math.round(_green * 255);
	}
	
	public int getBlueInt() {
		return Math.round(_blue * 255);
	}
	
	public int getAlphaInt() {
		return Math.round(_alpha * 255);
	}
	
	public boolean isDead() {
		return _killMe;
	}
	
	public void markForRemoval() {
		_killMe = true;
	}
	
	public void setScale(int scale) {
		_scale = scale;
	}
	
	public int getScale() {
		return _scale;
	}
	
	public boolean isVisible() {
		return _visible;
	}
	
	public void setVisible(boolean visible) {
		_visible = visible;
	}
	
	public void setText(String text) {
		_text = text;
		_updateSprites();
	}
	
	public String getText() {
		return _text;
	}
	
	public void setX(int x) {
		_x = x;
	}
	
	public void setY(int y) {
		_y = y;
	}
	
	public void setXY(int x, int y) {
		_x = x;
		_y = y;
	}
	
	private int length, x, i, characterIndex;
	private float width;
	private String character;
	private Sprite[] newSprites;
	private void _updateSprites() {
		length = _text.length();
		x = _x;
		newSprites = new Sprite[length];
		for(i = 0; i < length; i++) {
			character = _text.substring(i, i + 1);
			characterIndex = Font.getCharacterPosition(character);
			width = (float)_font.getCharacterWidth(characterIndex) / 32f * (float)_scale;
			newSprites[i] = new Sprite(x, _y, _scale, _scale, _font);
			Debug.print("Drawing " + character + " at " + x + " " + _y);
			newSprites[i].setTexture(_font);
			newSprites[i].setTileIndex(characterIndex + 1);
			newSprites[i].setRed(_red);
			newSprites[i].setGreen(_green);
			newSprites[i].setBlue(_blue);
			newSprites[i].setAlpha(_alpha);
			x += width + 3;
		}
		_sprite = newSprites;
	}
	
	public void drawFrame(GL10 gl) {
		for(int i = 0; i < _sprite.length; i++)
			_sprite[i].drawFrame(gl);
	}

}
