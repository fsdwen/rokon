package com.stickycoding.rokon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * Font.java
 * Loads a TTF font, for creating TextTextures
 * 
 * @author Richard
 */

public class Font {
	
	public static final int MAX_LINES = 64;
	public static final float DEFAULT_FONT_SIZE = 64f;

	protected Rect bounds;
	protected FontMetrics fontMetrics;
	protected Typeface typeface;
	public Paint paint;
	
	public Font(Typeface typeface) {
		this.typeface = typeface;
		init();
	}
	
	public Font(String assetPath) {
		this.typeface = Typeface.createFromAsset(Rokon.currentActivity.getAssets(), assetPath);
		init();
	}
	
	private void init() {
		paint = new Paint();
		paint.setTypeface(typeface);
		paint.setTextSize(DEFAULT_FONT_SIZE);
		fontMetrics = paint.getFontMetrics();
		bounds = new Rect();
	}
	
	public void setFontSize(float fontSize) {
		paint.setTextSize(fontSize);
		fontMetrics = paint.getFontMetrics();
	}
	
	public TextTexture createTexture(String text) {
		int bitmapWidth, bitmapHeight;
		paint.getTextBounds(text, 0, text.length(), bounds);
		bitmapWidth = bounds.right - bounds.left;
		bitmapHeight = bounds.bottom - bounds.top;
		Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		canvas.drawText(text, 0 - bounds.left, 0 - bounds.top, paint);
		return new TextTexture(bmp);
	}
	
	public TextTexture createTexture(String text, int width) {
		int bitmapWidth, bitmapHeight;
		bitmapWidth = width;
		
		String[] lines = new String[MAX_LINES];
		int lineIndexStart = 0;
		int lineCount = 1;
		int currentLineWidth = 1;
		Rect testRect = new Rect();
		
		for(int index = 0; index < text.length(); index++) {
			paint.getTextBounds(text, index, index + 1, testRect);
			currentLineWidth += testRect.right;
			if(currentLineWidth + testRect.right > width) {
				currentLineWidth = testRect.right;
				lines[lineCount] = text.substring(lineIndexStart, index);
				lineIndexStart = index;
				lineCount++;
			} else {
				currentLineWidth += testRect.right;
			}
			if(index == text.length() - 1) {
				lines[lineCount] = text.substring(lineIndexStart, index);
			}
		}

		bitmapHeight = (int) (fontMetrics.bottom - fontMetrics.top) * lineCount;
		
		Bitmap bmp = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bmp);
		
		for(int i = 0; i <= lineCount; i++) {
			if(lines[i] != null) {
				Debug.print("##" + lines[i]);
				canvas.drawText(lines[i], 0, (int) (fontMetrics.bottom - fontMetrics.top) * i, paint);
			}
		}
		Debug.print("Created texture, " + bitmapWidth + "x" + bitmapHeight);
		return new TextTexture(bmp);
	}

}
