package com.stickycoding.rokon.vbo;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.stickycoding.rokon.BufferObject;
import com.stickycoding.rokon.Debug;
import com.stickycoding.rokon.GLHelper;

public class ElementVBO extends VBO {
	
	//public ElementVBO(int drawType) {
		//super(drawType);
	//}

	public ElementVBO(BufferObject bufferObject, int drawType) {
		super(bufferObject, drawType);
		// TODO Auto-generated constructor stub
	}

	public void load(GL10 gl) {
		GL11 gl11 = (GL11)gl;
		int[] nameArray = new int[1];
		gl11.glGenBuffers(1, nameArray, 0);
		bufferIndex = nameArray[0];
		Debug.print("Adding new element VBO idx=" + bufferIndex);
		GLHelper.bindElementBuffer(bufferIndex);
		gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferObject.get().capacity(), bufferObject.get(), drawType);
		Debug.print("Element VBO Added");
	}

}
