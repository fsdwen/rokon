package com.stickycoding.Rokon;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.stickycoding.Rokon.Handlers.DynamicsHandler;

/**
 * @author Richard
 * Handles all positions, rotations and movement
 * Extended by Sprite, Emitter, Particle 
 * Created to bring together all dynamic methods for different types of object
 */
public class DynamicObject {

	private float _startX, _startY, _startWidth, _startHeight;
	private float _x, _y, _offsetX, _offsetY;
	private float _rotation, _rotationPivotX, _rotationPivotY;
	private boolean _rotationPivotRelative = true;
	private float _width, _height, _scaleX, _scaleY;
	
	private float _terminalVelocityX, _terminalVelocityY;
	private float _velocityX, _velocityY;
	private float _accelerationX, _accelerationY;
	private boolean _stopAtTerminalVelocity, _triggeredReachTerminalVelocityX, _triggeredReachTerminalVelocityY;
	private long _lastUpdate;
	
	private DynamicsHandler _dynamicsHandler;
	
	private long _timeDiff;
	private float _timeDiffModifier;

	private ByteBuffer _vertexBuffer;
	
	public DynamicObject(float x, float y, float width, float height) {
		_x = x;
		_y = y;
		_startX = x;
		_startY = y;
		_startWidth = width;
		_startHeight = height;
		_width = width;
		_height = height;
		_scaleX = 1;
		_scaleY = 1;
		_offsetX = 0;
		_offsetY = 0;
		_rotationPivotX = (_width / 2);
		_rotationPivotY = (_height / 2);		
		_vertexBuffer = ByteBuffer.allocateDirect(8*4);
		_vertexBuffer.order(ByteOrder.nativeOrder());
	}
	
	/**
	 * Sets the offset at which the sprite is drawn on screen
	 * @param offsetX
	 * @param offsetY
	 */
	public void setOffset(float offsetX, float offsetY) {
		_offsetX = offsetX;
		_offsetY = offsetY;
		updateVertexBuffer();
	}
	
	/**
	 * Updates the vertex buffer, should be done if you modify any variables manually
	 */
	public void updateVertexBuffer() {
		_vertexBuffer.position(0);
		
		_vertexBuffer.putFloat(_x + _offsetX);
		_vertexBuffer.putFloat(_y + _offsetY);

		_vertexBuffer.putFloat(_x + _offsetX + (_width * _scaleX));
		_vertexBuffer.putFloat(_y + _offsetY);

		_vertexBuffer.putFloat(_x + _offsetX);
		_vertexBuffer.putFloat(_y + _offsetX + (_height * _scaleY));

		_vertexBuffer.putFloat(_x + _offsetX + (_width * _scaleX));
		_vertexBuffer.putFloat(_y + _offsetX + (_height * _scaleY));
		
		_vertexBuffer.position(0);
	}
	/**
	 * @param rotation angle, in degrees, to rotate the Sprite relative to its current angle
	 */
	public void rotate(float rotation) {
		_rotation += rotation;
	}
	
	/**
	 * @param rotation angle, in degrees, to set the Sprite's rotation
	 */
	public void setRotation(float rotation) {
		_rotation = rotation;
	}
	
	/**
	 * @return the current angle, in degrees, at which the Sprite is at
	 */
	public float getRotation() {
		return _rotation;
	}
	
	/**
	 * Sets the rotation pivot x coordinate
	 * @param x
	 */
	public void setRotationPivotX(float x) {
		_rotationPivotX = x;
	}
	
	/**
	 * Get rotation pivot x coordinate
	 */
	public float getRotationPivotX() {
		return _rotationPivotX;
	}
	
	/**
	 * Sets the rotation pivot x coordinate
	 * @param x
	 */
	public void setRotationPivotY(float y) {
		_rotationPivotY = y;
	}
	
	/**
	 * Get rotation pivot y coordinate
	 */
	public float getRotationPivotY() {
		return _rotationPivotY;
	}
	
	/**
	 * Defines rotation pivot coordinates as relative to the sprite. 
	 * This does not change the actual pivot coordinates, but defines how the
	 * pivot coordinates are interpreted when rotating
	 */
	public void setRotationPivotRelative() {
		_rotationPivotRelative = true;
	}
	
	/**
	 * Defines rotation pivot coordinates as absolute/fixed (not relative to sprite). 
	 * This does not change the actual pivot coordinates, but defines how the
	 * pivot coordinates are interpreted when rotating
	 */
	public void setRotationPivotAbsolute() {
		_rotationPivotRelative = false;
	}
	
	/**
	 * @return TRUE if rotation pivot is relative, FALSE if absolute/fixed
	 */
	public boolean getRotationPivotRelative() {
		return _rotationPivotRelative;
	}
	
	/**
	 * @param scaleX a multiplier to scale your Sprite in the X direction when drawing
	 */
	public void setScaleX(float scaleX) {
		_scaleX = scaleX;
		updateVertexBuffer();
	}
	
	/**
	 * @return the current scale multiplier in X direction
	 */
	public float getScaleX() {
		return _scaleX;
	}
	
	/**
	 * @param scaleY a multiplier to scale your Sprite in the Y direction when drawing
	 */
	public void setScaleY(float scaleY) {
		_scaleY = scaleY;
		updateVertexBuffer();
	}
	
	/**
	 * @return the current scale multiplier in Y direction
	 */
	public float getScaleY() {
		return _scaleY;
	}
	
	/**
	 * Note that scale is not considered in collisions
	 * @param scaleX a multiplier to scale your Sprite in the X direction when drawing
	 * @param scaleY a multiplier to scale your Sprite in the Y direction when drawing
	 */
	public void setScale(float scaleX, float scaleY) {
		_scaleX = scaleX;
		_scaleY = scaleY;
		updateVertexBuffer();
	}

	/**
	 * @param x the top left position of your Sprite, in the X direction
	 */
	public void setX(float x) {
		_x = x;
		updateVertexBuffer();
	}
	
	/**
	 * @param y the top left position of your Sprite, in the Y direction
	 */
	public void setY(float y) {
		_y = y;
		updateVertexBuffer();
	}
	
	/**
	 * Sets the position of the Sprite, in pixels
	 * @param x 
	 * @param y
	 */
	public void setXY(float x, float y) {
		_x = x;
		_y = y;
		updateVertexBuffer();
	}
	
	/**
	 * @param x number of pixels to move the Sprite relative to its current position
	 */
	public void moveX(float x) {
		_x += x;
		updateVertexBuffer();
	}

	
	/**
	 * @param u number of pixels to move the Sprite relative to its current position
	 */
	public void moveY(float y) {
		_y += y;
		updateVertexBuffer();
	}
	
	/**
	 * Moves the Sprite relative to its current position
	 * @param x
	 * @param y
	 */
	public void move(float x, float y) {
		_x += x;
		_y += y;
		updateVertexBuffer();
	}
	
	/**
	 * @return the top left X position of the Sprite
	 */
	public float getX() {
		return _x;
	}
	
	/**
	 * @return the top left X position of the Sprite, rounded to the nearest integer
	 */
	public int getScreenX() {
		return (int)_x;
	}
	
	/**
	 * @return the top left X position of the Sprite
	 */
	public float getY() {
		return _y;
	}
	
	/**
	 * @return the top left Y position of the Sprite, rounded to the nearest integer
	 */
	public int getScreenY() {
		return (int)_y;
	}
	/**
	 * @param width width of the Sprite, used for collisions and multiplied by scale when drawn
	 */
	public void setWidth(float width) {
		_width = width;
	}
	
	/**
	 * @param width width of the Sprite, used for collisions and multiplied by scale when drawn
	 * @param start TRUE if startWidth should be set also
	 */
	public void setWidth(float width, boolean start) {
		_width = width;
		_startWidth = width;
	}

	/**
	 * @param height height of the Sprite, used for collisions and multiplied by scale when drawn
	 */
	public void setHeight(float height) {
		_height = height;
	}
	
	/**
	 * @param height height of the Sprite, used for collisions and multiplied by scale when drawn
	 * @param start TRUE if startHeight should be set also
	 */
	public void setHeight(float height, boolean start) {
		_height = height;
		_startHeight = _height;
	}
	
	/**
	 * @return current width of the Sprite
	 */
	public float getWidth() {
		return _width;
	}
	
	/**
	 * @return current height of the Sprite
	 */
	public float getHeight() {
		return _height;
	}
	
	/**
	 * @return current width of the Sprite, rounded to the nearest Integer
	 */
	public int getScreenWidth() {
		return (int)_width;
	}
	
	/**
	 * @return current height of the Sprite, rounded to the nearest Integer
	 */
	public int getScreenHeight() {
		return (int)_height;
	}
	
	/**
	 * @return the ByteBuffer for vertices
	 */
	public ByteBuffer getVertexBuffer() {
		return _vertexBuffer;
	}
	
	/**
	 * @return the time of the last movement update to the DynamicObject
	 */
	public long getLastUpdate() {
		return _lastUpdate;
	}
	
	/**
	 * Sets the time of the last update to the current frame time
	 */
	public void setLastUpdate() {
		_lastUpdate = Rokon.time;
	}
	
	/**
	 * Sets the time of the last update to an arbitrary value
	 * @param time
	 */
	public void setLastUpdate(long time) {
		_lastUpdate = time;
	}
	
	/**
	 * Updates movement
	 */
	public void updateMovement() {
		if(_accelerationX != 0 || _accelerationY != 0 || _velocityX != 0 || _velocityY != 0) {
			_timeDiff = Rokon.getTime() - _lastUpdate;
			_timeDiffModifier = (float)_timeDiff / 1000f;
			if(_accelerationX != 0 || _accelerationY != 0) {
				_velocityX += _accelerationX * _timeDiffModifier;
				_velocityY += _accelerationY * _timeDiffModifier;
				if(_stopAtTerminalVelocity) {
					if(!_triggeredReachTerminalVelocityX) {
						if((_accelerationX > 0.0f && _velocityX >= _terminalVelocityX)
						|| (_accelerationX < 0.0f && _velocityX <= _terminalVelocityX)) {
							if(_dynamicsHandler != null)
								_dynamicsHandler.reachedTerminalVelocityX();
							_accelerationX = 0;
							_velocityX = _terminalVelocityX;
							_triggeredReachTerminalVelocityX = true;
						}
					}
					if(!_triggeredReachTerminalVelocityY) {
						if((_accelerationY > 0.0f && _velocityY >= _terminalVelocityY)
						|| (_accelerationY < 0.0f && _velocityY <= _terminalVelocityY)) {
							if(_dynamicsHandler != null)
								_dynamicsHandler.reachedTerminalVelocityY();
							_accelerationY = 0;
							_velocityY = _terminalVelocityY;
							_triggeredReachTerminalVelocityY = true;
						}
					}
				}
			}
			_x += _velocityX * _timeDiffModifier;
			_y += _velocityY * _timeDiffModifier;
			updateVertexBuffer();
		}
		
		setLastUpdate();
	}

	/**
	 * @param dynamicsHandler sets a handler for the dynamics, this can track acceleration
	 */
	public void setDynamicsHandler(DynamicsHandler dynamicsHandler) {
		_dynamicsHandler = dynamicsHandler;
	}
	
	/**
	 * Removes the DynamicsHandler from the Sprite
	 */
	public void resetDynamicsHandler() {
		_dynamicsHandler = null;
	}
	
	/**
	 * Stops the Sprite, setting acceleration and velocities to zero
	 */
	public void stop() {
		resetDynamics();
	}
	
	public void resetDynamics() {
		_terminalVelocityX = 0;
		_terminalVelocityY = 0;
		_stopAtTerminalVelocity = false;
		_velocityX = 0;
		_velocityY = 0;
		_accelerationX = 0;
		_accelerationY = 0;
	}
	
	/**
	 * Accelerates a Sprite, note that this is relative to current Acceleration.
	 * @param accelerationX acceleration in X direction, pixels per second
	 * @param accelerationY acceleration in Y direction, pixels per second
	 * @param terminalVelocityX specifies a highest possible velocity in X direction, this will trigger reachedTerminalVelocityX
	 * @param terminalVelocityY specifies a highest possible velocity in Y direction, this will trigger reachedTerminalVelocityY
	 */
	public void accelerate(float accelerationX, float accelerationY, float terminalVelocityX, float terminalVelocityY) {
		_stopAtTerminalVelocity = true;
		_terminalVelocityX = terminalVelocityX;
		_terminalVelocityY = terminalVelocityY;
		_accelerationX += accelerationX;
		_accelerationY += accelerationY;
		_triggeredReachTerminalVelocityX = false;
		_triggeredReachTerminalVelocityY = false;
		_lastUpdate = 0;
	}
	
	/**
	 * Accelerates a Sprite, note that this is relative to current Acceleration. Terminal velocity restrictions are removed.
	 * @param accelerationX acceleration in X direction, pixels per second
	 * @param accelerationY acceleration in Y direction, pixels per second
	 */
	public void accelerate(float accelerationX, float accelerationY) {
		_stopAtTerminalVelocity = false;
		_accelerationX += accelerationX;
		_accelerationY += accelerationY;
		_lastUpdate = 0;
	}
	
	/**
	 * @return current acceleration in X direction, pixels per second
	 */
	public float getAccelerationX() {
		return _accelerationX;
	}
	/**
	 * @return current acceleration in Y direction, pixels per second
	 */
	public float getAccelerationY() {
		return _accelerationY;
	}
	
	/**
	 * @return current velocity in X direction, pixels per second
	 */
	public float getVelocityX() {
		return _velocityX;
	}
	
	/**
	 * @return current velocity in Y direction, pixels per second
	 */
	public float getVelocityY() {
		return _velocityY;
	}
	
	/**
	 * @param velocityX instantly sets the velocity of the Sprite in X direction, pixels per second
	 */
	public void setVelocityX(float velocityX) {
		_velocityX = velocityX;
	}
	
	/**
	 * @param velocityY instantly sets the velocity of the Sprite in Y direction, pixels per second
	 */
	public void setVelocityY(float velocityY) {
		_velocityY = velocityY;
	}
	
	/**
	 * Instantly sets the velocity of te Sprite in X and Y directions, pixels per second
	 * @param velocityX
	 * @param velocityY
	 */
	public void setVelocity(float velocityX, float velocityY) {
		_velocityX = velocityX;
		_velocityY = velocityY;
	}
	
	/**
	 * @return the current terminal velocity cap in X direction
	 */
	public float getTerminalVelocityX() {
		return _terminalVelocityX;
	}
	
	/**
	 * @return the current terminal velocity cap in Y direction
	 */
	public float getTerminalVelocityY() {
		return _terminalVelocityY;
	}
	
	/**
	 * @param stopAtTerminalVelocity TRUE if Sprite should stop at the terminal velocity, FALSE if it should continue accelerating
	 */
	public void setStopAtTerminalVelocity(boolean stopAtTerminalVelocity) {
		_stopAtTerminalVelocity = stopAtTerminalVelocity;
	}
	
	/**
	 * @return TRUE if the Sprite is going to stop when it reaches terminal velocity, FALSE if it will continue indefinately
	 */
	public boolean isStopAtTerminalVelocity() {
		return _stopAtTerminalVelocity;
	}
	
	/**
	 * Sets a terminal velocity at which the Sprite will stop accelerating, this will trigger reachedTerminalVelocityX and reachedTerminalVelocityY in your DynamicsHandler if set
	 * @param terminalVelocityX
	 * @param terminalVelocityY
	 */
	public void setTerminalVelocity(float terminalVelocityX, float terminalVelocityY) {
		_stopAtTerminalVelocity = true;
	}
	
	public void setTerminalVelocityX(float terminalVelocityX) {
		_terminalVelocityX = terminalVelocityX;
	}
	
	public void setTerminalVelocityY(float terminalVelocityY) {
		_terminalVelocityY = terminalVelocityY;
	}
	
	/**
	 * Increases the current velocity by a given value
	 * @param velocityX
	 * @param velocityY
	 */
	public void setVelocityRelative(float velocityX, float velocityY) {
		_velocityX += velocityX;
		_velocityY += velocityY;
	}
	
	/**
	 * Resets the X, Y, and dimenensions to their original value
	 */
	public void reset() {
		_x = _startX;
		_y = _startY;
		_width = _startWidth;
		_height = _startHeight;
	}
	
	/**
	 * @return TRUE if the DynamicObject does not appear to be on-screen
	 */
	public boolean notOnScreen() {
		if(Rokon.getRokon().isForceOffscreenRender())
			return false;
		if(_x + _width < 0 || _x > Rokon.getRokon().getWidth())
			return true;
		if(_y + _height < 0 || _y > Rokon.getRokon().getHeight())
			return true;
		return false;
	}
}
