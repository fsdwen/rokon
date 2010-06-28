package com.stickycoding.rokon;

import com.badlogic.gdx.math.Vector2;
import com.stickycoding.rokon.vbo.ArrayVBO;
import com.stickycoding.rokon.vbo.VBO;


/**
 * Polygon.java
 * A polygon shape, vertices should be from 0 to 1
 * 
 * @author Richard
 */

public class Polygon {
	
	public static final int MAX_VERTEX_COUNT = 128;

	protected boolean building;
	protected Point[] vertex;
	protected int vertexCount = 0;
	
	protected Vector2[] edge;
	protected Vector2[] normal;
	
	protected BufferObject buffer;
	protected ArrayVBO vbo;
	
	public ArrayVBO getVBO() {
		if(vbo == null) {
			vbo = new ArrayVBO(buffer, VBO.STATIC);
		}
		return vbo;
	}
	
	protected Point[] rotated(float angle) {
		angle *= MathHelper.DEG_TO_RAD;
		Point[] rotatedPoint = new Point[vertexCount];
		for(int i = 0; i < vertexCount; i++) {
			Point newPoint = MathHelper.rotated(vertex[i], angle);
			rotatedPoint[i] = newPoint;
		}
		return rotatedPoint;
	}
	
	protected float[] rotatedX(float angle, float pivotX, float pivotY) {
		angle *= MathHelper.DEG_TO_RAD;
		float[] newX = new float[vertexCount];
		for(int i = 0; i < vertexCount; i++) {
			newX[i] = ((vertex[i].getX() - pivotX) * (float)(Math.cos(angle))) - ((vertex[i].getY() - pivotY) * (float)(Math.sin(angle))) + pivotX;
		}
		return newX;
	}

	
	protected float[] rotatedY(float angle, float pivotX, float pivotY) {
		angle *= MathHelper.DEG_TO_RAD;
		float[] newX = new float[vertexCount];
		for(int i = 0; i < vertexCount; i++) {
			newX[i] = ((vertex[i].getX() - pivotX) * (float)(Math.sin(angle))) + ((vertex[i].getY() - pivotY) * (float)(Math.cos(angle))) + pivotY;
		}
		return newX;
	}
	
	public void findEdges() {
		edge = new Vector2[vertexCount];
		normal = new Vector2[vertexCount];
		for(int i = 0; i < vertexCount; i++) {
			int nextEdge = i + 1;
			if(nextEdge == vertexCount) nextEdge = 0;
			edge[i] = new Vector2(vertex[nextEdge].getX() - vertex[i].getX(), vertex[nextEdge].getY() - vertex[i].getY());
			normal[i] = MathHelper.findNormal(edge[i]);
			Debug.print("Found edge = " + edge[i].x + " " + edge[i].y + " norm " + normal[i].x + " " + normal[i].y);
		}
		Debug.print("Edges found");
	}
	
	public BufferObject getBufferObject() {
		if(buffer == null) {
			buffer = new BufferObject(vertexCount * 2);
			float[] vertices = new float[vertexCount * 2];
			int c = 0;
			for(int i = 0; i < vertex.length; i++) {
				vertices[c++] = vertex[i].getX();
				vertices[c++] = vertex[i].getY();
			}
			buffer.updateRaw(vertices);
		}
		return buffer;
	}
	
	public Polygon(Point[] vertices) {
		this.vertex = vertices;
		vertexCount = vertices.length;
		findEdges();
	}
	
	public Polygon(float[] vertices) {
		if(vertices.length % 2 != 0) {
			Debug.error("Tried creating Polygon with odd number of vertices");
			Debug.forceExit();
			return;
		}
		vertex = new Point[vertices.length / 2];
		int c = 0;
		for(int i = 0; i < vertices.length / 2; i++) {
			vertex[i] = new Point(vertices[c++], vertices[c++]);
		}
		vertexCount = vertices.length / 2;
		findEdges();
	}
	
	public Polygon() {
		vertex = new Point[MAX_VERTEX_COUNT];
		vertexCount = 0;
	}
	
	public void addVertex(Point location) {
		vertex[vertexCount++] = location;
	}
	
	public void addVertex(float x, float y) {
		addVertex(new Point(x, y));
	}
	
	public Point getVertex(int index) {
		return vertex[index];
	}
	
	public void complete() {
		findEdges();
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	
}
