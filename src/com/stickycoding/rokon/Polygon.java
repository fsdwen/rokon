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
	
	public Polygon(Point[] vertices) {
		this.vertex = vertices;
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
