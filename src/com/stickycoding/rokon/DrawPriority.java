package com.stickycoding.rokon;

/**
 * DrawPriority.java
 * Contains constants and details on the draw priority system
 * @author Richard
 *
 */
public class DrawPriority {
	
	public static final int PRIORITY_VBO_DRAWTEX_NORMAL = 0;
	public static final int PRIORITY_VBO_NORMAL = 1;
	public static final int PRIORITY_DRAWTEX_VBO_NORMAL = 2;
	public static final int PRIORITY_DRAWTEX_NORMAL = 3;
	public static final int PRIORITY_NORMAL = 4;
	
	public static final int DEFAULT = 0, VBO = 1, DRAWTEX = 2, NORMAL = 3;
	
	protected static int drawPriority = 0;

}
