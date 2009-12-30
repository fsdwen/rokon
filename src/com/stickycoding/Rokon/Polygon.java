package com.stickycoding.Rokon;

/**
 * @author Richard
 * A finite sided vector object
 */
public class Polygon {
	
	private int _vertexCount, _position;
	private Vertex[] _vertex;
	
	/**
	 * Creates a Polygon with a given number of vertices
	 * @param vertexCount finite number of vertices, must be atleast 2
	 */
	public Polygon(int vertexCount) {
		if(vertexCount < 2) {
			Debug.warning("Attempted to create a Polygon with " + vertexCount + " vertices");
			_vertexCount = 0;
			return;
		}
		_vertexCount = vertexCount;
		_position = 0;
		_vertex = new Vertex[_vertexCount];
	}
	
	/**
	 * Returns the number of vertices
	 * @return number of vertices, will return 0 if the Polygon is not valid
	 */
	public int getVertexCount() {
		return _vertexCount;
	}
	
	/**
	 * Returns the current position in the vertex array, for defining coordinates
	 * @return the current position along the vertex array
	 */
	public int getPosition() {
		return _position;
	}
	
	/**
	 * Sets the current position in the vertex array
	 * @param position the vertex index
	 */
	public void position(int position) {
		if(position <= 0 || position > _vertexCount) {
			Debug.warning("Invalid vertex position (" + position + ") - is not within vertexCount");
			return;
		}
		_position = position;
	}
	
	/**
	 * Sets the active vertex, and moves position along by 1 (will remain at the end of array if full)
	 * @param x X unit coordinate, 0 to 1
	 * @param y Y unit coordinate, 0 to 1
	 */
	public void put(float x, float y) {
		put(new Vertex(x, y));
	}
	
	/**
	 * Sets the active vertex, and moves position along by 1 (will remain at the end of array if full)
	 * @param vertex a valid Vertex object
	 */
	public void put(Vertex vertex) {
		_vertex[_position] = vertex;
		if(_position < _vertexCount)
			_position++;
	}
	
	/**
	 * Sets a given vertex to X/Y unit coordinates
	 * @param x X coordinate, 0 to 1
	 * @param y Y coordinate, 0 to 1
	 * @param index the vertex index to set
	 */
	public void setVertex(float x, float y, int index) {
		setVertex(new Vertex(x, y), index);
	}
	
	/**
	 * Sets a given vertex coordinates by passing through Vertex object
	 * @param vertex a valid Vertex object
	 * @param index the vertex index to set
	 */
	public void setVertex(Vertex vertex, int index) {
		if(index < 0 || index >= _vertexCount) {
			Debug.warning("Attempted to set non-existant vertex [" + index + "]");
			return;
		}
		_vertex[index] = vertex;
	}
	
	/**
	 * Returns the current Vertex, moves along 1 if it hasn't reached the end
	 * @return Vertex object
	 */
	public Vertex get() {
		if(_position < _vertexCount)
			return _vertex[_position++];
		else
			return _vertex[_position];
	}
	
	/**
	 * Returns the Vertex at a given index
	 * @param index vertex array position
	 * @return Vertex object, NULL if invalid index is given
	 */
	public Vertex get(int index) {
		if(index < 0 || index >= _vertexCount) {
			Debug.warning("Attempted to fetch non-existant vertex [" + index + "]");
			return null;
		}
		return _vertex[index];
	}

}
