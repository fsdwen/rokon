package com.stickycoding.Rokon;

/**
 * Holds your choices for OpenGL blend function
 * See glBlendFunc parameters for details on each different setting
 * @author Richard
 */
/**
 * @author Richard
 *
 */
public class BlendFunction {
	
	private int _blendFunctionSrc, _blendFunctionDst;
	
	/**
	 * Creates a BlendFunction object with given parameters
	 * @param blendFunctionSrc blend function source parameter
	 * @param blendFunctionDst blend function destination parameter
	 */
	public BlendFunction(int blendFunctionSrc, int blendFunctionDst) {
		_blendFunctionSrc = blendFunctionSrc;
		_blendFunctionDst = blendFunctionDst;
	}

	/**
	 * Returns the choice of source blend function
	 * @return source parameter
	 */
	public int getSrc() {
		return _blendFunctionSrc;
	}
	
	/**
	 * Sets the choice of source blend function
	 * @param blendFunctionSrc source parameter
	 */
	public void setSrc(int blendFunctionSrc) {
		_blendFunctionSrc = blendFunctionSrc;
	}
	
	/**
	 * Returns the choice of destination blend function
	 * @return
	 */
	public int getDst() {
		return _blendFunctionDst;
	}
	
	/**
	 * Sets the choice of destination blend function
	 * @param blendFunctionDst destination parameter
	 */
	public void setDst(int blendFunctionDst) {
		_blendFunctionDst = blendFunctionDst;
	}

}
