package com.stickycoding.rokon;


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
	
	protected BufferObject buffer;
	
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
		vertexCount = vertices.length;
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
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	
}
