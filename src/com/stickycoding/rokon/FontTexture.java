package com.stickycoding.rokon;

/**
 * TextTexture.java
 * A texture specifically for bitmap fonts
 * 
 * @author Richard
 */
public class FontTexture extends Texture {
	
	public char[] chars;

	public FontTexture(String filename, String characters, int columns, int rows) {
		super(filename, columns, rows);
		chars = new char[characters.length()];
		for(int i = 0; i < characters.length(); i++) {
			chars[i] = characters.charAt(i);
		}
	}
	
	public FontTexture(String filename, String characters) {
		this(filename, characters, characters.length(), 1);
	}
	
	public int getCol(char ch) {
		return charPos(ch) % columns;
	}
	
	public int getRow(char ch) {
		int charPos = charPos(ch);
		return charPos - (charPos % columns) / columns;
	}
	
	public int charPos(char ch) {
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == ch) {
				return i;
			}
		}
		Debug.error("Character not found! " + ch);
		return -1;
	}

}
