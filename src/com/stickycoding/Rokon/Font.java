package com.stickycoding.Rokon;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * @author Richard
 * TrueType Fonts can be loaded using Font. The TTF file is loaded, drawn
 * as tiles onto a texture and then loaded into the TextureAtlas.
 */
public class Font extends Texture {
	
	private static final String _pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklnopqrstuvwxyz0123456789¬!\"£$%^&*()_+-=[]{};'#:@~,./<>?\\|` ";
	private int[] _characterWidth;
	
	/**
	 * @param character a single character 
	 * @return position in the pattern string, zero based
	 */
	public static int getCharacterPosition(String character) {
		for(int i = 0; i < _pattern.length(); i++)
			if(_pattern.substring(i, i + 1).equals(character))
				return i;
		Debug.print("CHARACTER NOT FOUND " + character);
		return -1;
	}
	
	/**
	 * @param position position of the character in the pattern string
	 * @return character width, as defined by the font
	 */
	public int getCharacterWidth(int position) {
		return _characterWidth[position];
	}
	
	/**
	 * @param character the character, as it exists in the pattern string
	 * @return character width, as defined by the font
	 */
	public int getCharacterWidth(String character) {
		return getCharacterWidth(getCharacterPosition(character));
	}

	/**
	 * Creates a Font, it's respective texture, and adds it to the atlas.
	 * Note, this must be called before the TextureAtlas is prepared.
	 * @param filename as it is found in the assets folder of your APK
	 */
	public Font(String filename) {
		super();
		int fontSize = 20;
		Typeface typeface = Typeface.createFromAsset(Rokon.getRokon().getActivity().getAssets(), filename);
		Bitmap bmp = Bitmap.createBitmap(512, 256, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setTypeface(typeface);
		paint.setTextSize(fontSize);
		paint.setARGB(255, 255, 255, 255);
		
		_characterWidth = new int[_pattern.length()];
		int y = 0;
		int biggestHeight = 0;
		for(int i = 0; i < _pattern.length(); i++) {
			String character = _pattern.substring(i, i + 1);
			Rect bounds = new Rect();
			paint.getTextBounds(character, 0, 1, bounds);
			_characterWidth[i] = bounds.right;
			if(bounds.bottom - bounds.top > biggestHeight)
				biggestHeight = bounds.bottom - bounds.top;
			int x = (i % 16) * 32;
			x += 16;
			x -= (int)(_characterWidth[i] / 2);
			y = 0;
			if(i >= 16)
				y += 32;
			if(i >= 32)
				y += 32;
			if(i >= 48)
				y += 32;
			if(i >= 64)
				y += 32;
			if(i >= 96)
				y += 32;
			y += 32;
			y -= 8;
			canvas.drawText(character, x, y, paint);
		}
		Debug.print("i=" + _pattern.length() + " y=" + y);
		canvas.save();
		createFromBitmap(bmp);
		setTileCount(16, 8);
	}

}
