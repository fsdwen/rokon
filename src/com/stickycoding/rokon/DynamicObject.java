package com.stickycoding.rokon;


/**
 * DynamicObject.java
 * A child of StaticObject, DynamicObject has functions for time dependent movement and rotation
 * 
 * There are two types of translational movement, one based on X and Y, and one based on a magnitude and angle
 * The two methods can be used together, though it is recommended to avoid this - it may be easy to get confused
 * 
 * @author Richard
 */

public class DynamicObject extends StaticObject {
	
	protected float accelerationX, accelerationY, speedX, speedY, terminalSpeedX, terminalSpeedY;
	protected boolean useTerminalSpeedX, useTerminalSpeedY;
	protected float acceleration, velocity, velocityAngle, velocityXFactor, velocityYFactor, terminalVelocity;
	protected boolean useTerminalVelocity;
	protected float angularVelocity, angularAcceleration, terminalAngularVelocity;
	protected boolean useTerminalAngularVelocity;
	
	public DynamicObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * Called when the DynamicObject is added to a Layer
	 */
	protected void onAdd() {
		
	}
	
	/**
	 * Called when the DynamicObject is removed from a Layer
	 */
	protected void onRemove() {
		rotateToCallback = null;
		moveToCallback = null;
	}
	
	/**
	 * Stops all the dynamics for this object
	 */
	public void stop() {
		isMoveTo = false;
		accelerationX = 0;
		accelerationY = 0;
		acceleration = 0;
		speedX = 0;
		speedY = 0;
		velocity = 0;
		velocityXFactor = 0;
		velocityYFactor = 0;
		velocityAngle = 0;
		terminalSpeedX = 0;
		terminalSpeedY = 0;
		terminalVelocity = 0;
		angularVelocity = 0;
		angularAcceleration = 0;
		terminalAngularVelocity = 0;
	}
	
	protected void onUpdate() {
		if(isMoveTo) {
			onUpdateMoveTo();
		}
		if(isRotateTo) {
			onUpdateRotateTo();
		}
		if(accelerationX != 0) {
			speedX += accelerationX * Time.ticksFraction;
			if(useTerminalSpeedX && ((accelerationX > 0 && speedX > terminalSpeedX) || (accelerationX < 0 && speedY < terminalSpeedX))) {
				accelerationX = 0;
				speedX = terminalSpeedX;
				if(parentScene.useInvoke) attemptInvoke("onReachTerminalSpeedX");
			}
		}
		if(accelerationY != 0) {
			speedY += accelerationY * Time.ticksFraction;
			if(useTerminalSpeedY && ((accelerationY > 0 && speedY > terminalSpeedY) || (accelerationY < 0 && speedY < terminalSpeedY))) {
				accelerationY = 0;
				speedY = terminalSpeedY;
				if(parentScene.useInvoke) attemptInvoke("onReachTerminalSpeedY");
			}
		}
		if(speedX != 0) {
			x += speedX * Time.ticksFraction;
		}
		if(speedY != 0) {
			y += speedY * Time.ticksFraction;
		}
		if(acceleration != 0) {
			velocity += acceleration * Time.ticksFraction;
			if(useTerminalVelocity && ((acceleration > 0 && velocity > terminalVelocity) || (acceleration < 0 && velocity < terminalVelocity))) {
				acceleration = 0;
				velocity = terminalVelocity;
				if(parentScene.useInvoke) attemptInvoke("onReachTerminalVelocity");
			}
		}
		if(velocity != 0) {
			x += velocityXFactor * (velocity * Time.ticksFraction);
			y += velocityYFactor * (velocity * Time.ticksFraction);
		}
		if(angularAcceleration != 0) {
			angularVelocity += angularAcceleration * Time.ticksFraction;
			if(useTerminalAngularVelocity && ((angularAcceleration > 0 && angularVelocity > terminalAngularVelocity) || (angularAcceleration < 0 && angularVelocity < terminalAngularVelocity))) {
				angularAcceleration = 0;
				angularVelocity = terminalAngularVelocity;
				attemptInvoke("onReachTerminalAngularVelocity");
			}
		}
		if(angularVelocity != 0) {
			rotation += angularVelocity * Time.ticksFraction;
		}
	}
	
	/**
	 * Sets speed of the DynamicObject in the X direction
	 * 
	 * @param x positive or negative floating point
	 */
	public void setSpeedX(float x) {
		speedX = x;
	}
	
	/**
	 * Sets speed of the DynamicObject in the Y direction
	 * 
	 * @param y positive or negative floating point
	 */
	public void setSpeedY(float y) {
		speedY = y;
	}
	
	/**
	 * Sets the speed of the DynamicObject on both X and Y axis
	 * 
	 * @param x positive or negative floating point
	 * @param y positive or negative floating point
	 */
	public void setSpeed(int x, int y) {
		speedX = x;
		speedY = y;
	}
	
	/**
	 * Sets the velocity of the DynamicObject
	 * This is along the velocityAngle, and will be north if previously unset
	 * 
	 * @param velocity positive or negative floating point
	 */
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Sets the velocity of the DynamicObject
	 * 
	 * @param velocity positive or negative floating point
	 * @param angle relative to north, in radians
	 */
	public void setVelocity(float velocity, float angle) {
		this.velocity = velocity;
		this.velocityAngle = angle;
		this.velocityXFactor = (float)Math.sin(angle);
		this.velocityYFactor = (float)Math.cos(angle);
	}
	
	/**
	 * Accelerates along the X direction
	 * 
	 * @param accelerationX positive or negative floating point
	 */
	public void accelerateX(float accelerationX) {
		this.accelerationX = accelerationX;
	}
	
	public void accelerateX(float accelerationX, float terminalSpeedX) {
		this.accelerationX = accelerationX;
		this.terminalSpeedX = terminalSpeedX;
		useTerminalSpeedX = true;
	}
	
	/**
	 * Accelerates along the Y direction
	 * 
	 * @param accelerationY positive or negative floating point
	 */
	public void accelerateY(float accelerationY) {
		this.accelerationY = accelerationY;
	}
	
	/**
	 * Accelerates along the Y direction to a maximum speed
	 *
	 * @param accelerationY positive or negative floating point
	 * @param terminalSpeedY the maximum speed to achieve in Y direction
	 */
	public void accelerateY(float accelerationY, float terminalSpeedY) {
		this.accelerationY = accelerationY;
		this.terminalSpeedY = terminalSpeedY;
		useTerminalSpeedY = true;
	}
	
	/**
	 * Accelerates along a given angle
	 * 
	 * @param acceleration magnitude of acceleration
	 * @param angle relative to north, in radians
	 */
	public void accelerate(float acceleration, float angle) {
		this.acceleration = acceleration;
		this.velocityAngle = angle;
		this.velocityXFactor = (float)Math.sin(angle);
		this.velocityYFactor = (float)Math.cos(angle);
	}
	
	/**
	 * Accelerates along a given angle to a terminal velocity
	 * 
	 * @param acceleration magnitude of acceleration
	 * @param angle relative to north, in radians
	 * @param terminalVelocity maximum velocity to reach
	 */
	public void accelerate(float acceleration, float angle, float terminalVelocity) {
		accelerate(acceleration, angle);
		this.terminalVelocity = terminalVelocity;
		useTerminalVelocity = true;
	}
	
	/**
	 * Removes the limit on speed in the X direction
	 */
	public void stopUsingTerminalSpeedX() {
		useTerminalSpeedX = false;
	}
	
	/**
	 * Removes the limit on speed in the Y direction
	 */
	public void stopUsingTerminalSpeedY() {
		useTerminalSpeedY = false;
	}
	
	/**
	 * Removes the limit on speed in both X and Y directions
	 */
	public void stopUsingTerminalSpeed() {
		useTerminalSpeedX = false;
		useTerminalSpeedY = false;
	}
	
	/**
	 * @return TRUE if the DynamicObject is limited to a given speed in the X direction
	 */
	public boolean isUsingTerminalSpeedX() {
		return useTerminalSpeedX;
	}
	
	/**
	 * @return TRUE if the DynamicObject is limited to a given speed in the Y direction
	 */
	public boolean isUsingTerminalSpeedY() {
		return useTerminalSpeedY;
	}
	
	/**
	 * Removes the limit on velocity
	 */
	public void stopUsingTerminalVelocity() {
		useTerminalVelocity = false;
	}
	
	/**
	 * @return TRUE if the DynamicOject is limited to a given terminal velocity
	 */
	public boolean isUsingTerminalVelocity() {
		return useTerminalVelocity;
	}
	
	/**
	 * Removes the limit on angular velocity
	 */
	public void stopUsingTerminalAngularVelocity() {
		useTerminalAngularVelocity = false;
	}
	
	/**
	 * @return TRUE if the DynamicObject is limited to a given terminal angular velocity
	 */
	public boolean isUsingTerminalAngularVelocity() {
		return useTerminalAngularVelocity;
	}

	/**
	 * @return current acceleration to speed in X direction
	 */
	public float getAccelerationX() {
		return accelerationX;
	}
	
	/**
	 * @return current acceleration to speed in Y direction
	 */
	public float getAccelerationY() {
		return accelerationY;
	}

	/**
	 * @return current acceleration to velocity
	 */
	public float getAcceleration() {
		return acceleration;
	}
	
	/**
	 * @return angular acceleration
	 */
	public float getAngularAcceleration() {
		return angularAcceleration;
	}
	
	/**
	 * @return angular velocity
	 */
	public float getAngularVelocity() {
		return angularVelocity;
	}
	
	/**
	 * @return current angle at which the velocity is being applied
	 */
	public float getVelocityAngle() {
		return velocityAngle;
	}
	
	/**
	 * @return magnitude of the velocity
	 */
	public float getVelocity() {
		return velocity;
	}
	
	/**
	 * @return scalar speed in X direction
	 */
	public float getSpeedX() {
		return speedX;
	}
	
	/**
	 * @return scalar speed in Y direction
	 */
	public float getSpeedY() {
		return speedY;
	}
	
	/**
	 * @return terminal speed in X direction
	 */
	public float getTerminalSpeedX() {
		return terminalSpeedX;
	}
	
	/**
	 * @return terminal speed in Y direction
	 */
	public float getTerminalSpeedY() {
		return terminalSpeedY;
	}
	
	/**
	 * @return terminal velocity
	 */
	public float getTerminalVelocity() {
		return terminalVelocity;
	}
	
	/**
	 * @return terminal angular velocity
	 */
	public float getTerminalAngularVelocity() {
		return terminalAngularVelocity;
	}
	
	/**
	 * Sets the terminal speed in the X direction
	 * 
	 * @param terminalSpeedX terminal speed in X
	 */
	public void setTerminalSpeedX(float terminalSpeedX) {
		this.terminalSpeedX = terminalSpeedX;
		useTerminalSpeedX =true;
	}
	
	/**
	 * Sets the terminal speed in the Y direction
	 * 
	 * @param terminalSpeedY terminal speed in Y
	 */
	public void setTerminalSpeedY(float terminalSpeedY) {
		this.terminalSpeedY = terminalSpeedY;
		useTerminalSpeedY = true;
	}
	
	/**
	 * Sets the terminal speed in both basic directions
	 * 
	 * @param terminalSpeedX terminal speed in X
	 * @param terminalSpeedY terminal speed in Y
	 */
	public void setTerminalSpeed(float terminalSpeedX, float terminalSpeedY) {
		this.terminalSpeedX = terminalSpeedX;
		this.terminalSpeedY = terminalSpeedY;
		useTerminalSpeedX = true;
		useTerminalSpeedY = true;
	}
	
	/**
	 * Sets the terminal velocity
	 * 
	 * @param terminalVelocity terminal velocity
	 */
	public void setTerminalVelocity(float terminalVelocity) {
		this.terminalVelocity = terminalVelocity;
		useTerminalVelocity = true;
	}
	
	/**
	 * Sets the terminal angular velocity
	 * 
	 * @param terminalAngularVelocity terminal angular velocity
	 */
	public void setTerminalAngularVelocity(float terminalAngularVelocity) {
		this.terminalAngularVelocity = terminalAngularVelocity;
		useTerminalAngularVelocity = true;
	}
	
	/**
	 * Sets the angular acceleration
	 * 
	 * @param acceleration floating point integer, in radians 
	 */
	public void setAngularAcceleration(float acceleration) {
		this.angularAcceleration = acceleration;
	}
	
	protected boolean isRotateTo = false;
	protected float rotateToAngleStart, rotateToAngle;
	protected long rotateToStartTime;
	protected int rotateToTime, rotateToType, rotateToDirection;
	protected Callback rotateToCallback;
	
	public static final int ROTATE_TO_AUTOMATIC = 0, ROTATE_TO_CLOCKWISE = 1, ROTATE_TO_ANTI_CLOCKWISE = 2;
	
	public void rotateTo(float angle, int direction, int time, int type) {
		if(isRotateTo) {
			if(parentScene.useInvoke) attemptInvoke("onRotateToCancel");
		}

		angularVelocity = 0;
		angularAcceleration = 0;
		terminalAngularVelocity = 0;
		
		rotateToAngleStart = this.rotation;
		rotateToAngle = angle;
		rotateToDirection = direction;
		isRotateTo = true;
		rotateToType = type;
		rotateToStartTime = Time.ticks;
		rotateToTime = time;
		
		rotateToCallback = null;
		
		rotation = rotation % Movement.TWO_PI;

		
		//TODO Fix this rubbish, there has to be a better way
		if(rotateToDirection == ROTATE_TO_AUTOMATIC) {
			if(rotation > 180f) {
				if(angle > 180f) {
					if(angle > rotation) {
						rotateToDirection = ROTATE_TO_ANTI_CLOCKWISE;
					} else {
						rotateToDirection = ROTATE_TO_CLOCKWISE;
					}
				} else {
					if(angle > rotation - 180) {
						rotateToDirection = ROTATE_TO_ANTI_CLOCKWISE;
					} else {
						rotateToDirection = ROTATE_TO_CLOCKWISE;
					}
				}
			} else {
				if(angle > 180f) {
					if(angle > rotation + 180) {
						rotateToDirection = ROTATE_TO_ANTI_CLOCKWISE;
						rotateToAngleStart += 360;
					} else {
						rotateToDirection = ROTATE_TO_CLOCKWISE;
					}
				} else {
					if(angle > rotation) {
						rotateToDirection = ROTATE_TO_CLOCKWISE;
					} else {
						rotateToDirection = ROTATE_TO_ANTI_CLOCKWISE;
					}
				}
			}
		}
		Debug.print("Rotating from " + rotation + " to " + angle + " by "+ rotateToDirection);
	}

	
	/**
	 * Rotates to a given angle over a period of time
	 * 
	 * @param angle the final angle, in radians
	 * @param direction automatic, clockwise or anticlockwise - defined by ROTATE_TO_ constants
	 * @param time in milliseconds
	 * @param type movement type, through Movement constants
	 * @param callback Callback object for invoking
	 */
	public void rotateTo(float angle, int direction, int time, int type, Callback callback) {
		rotateTo(angle, direction, time, type);
		rotateToCallback = callback;
	}
	
	protected void onUpdateRotateTo() {
		float position = (float)(Time.ticks - rotateToStartTime) / (float)rotateToTime;
		float movementFactor = Movement.getPosition(position, rotateToType);
		if(position >= 1) {
			rotation = rotateToAngle;
			isRotateTo = false;
			if(parentScene.useInvoke) {
				attemptInvoke("onRotateToComplete");
			}
			if(rotateToCallback != null) {
				attemptInvoke(rotateToCallback);
			}
			angularVelocity = 0;
			angularAcceleration = 0;
			terminalAngularVelocity = 0;
			return;
		}
		
		if(rotateToDirection == ROTATE_TO_CLOCKWISE) {
			rotation = rotateToAngleStart + (rotateToAngle - rotateToAngleStart) * movementFactor;
		} else {
			rotation = rotateToAngleStart - (rotateToAngleStart - rotateToAngle) * movementFactor;
		}
	}
	
	protected boolean isMoveTo = false;
	protected float moveToStartX, moveToStartY, moveToFinalX, moveToFinalY;
	protected int moveToType;
	protected long moveToStartTime, moveToTime;
	protected Callback moveToCallback;
	
	public void moveTo(float x, float y, long time, int type, Callback callback) {
		moveTo(x, y, time, type);
		moveToCallback = callback;
	}

	/**
	 * Moves the DynamicObject to a given spot, in a given time using
	 * All previous motion is cancelled. It may be possible to apply your own velocity
	 * and acceleration changes while moveTo is running, though it should be avoided.
	 * 
	 * @param x final X coordinate
	 * @param y final Y coordinate
	 * @param time the time 
	 * @param type the movement type, from Movement constants
	 */
	public void moveTo(float x, float y, long time, int type) {
		if(isMoveTo) {
			if(parentScene.useInvoke) {
				attemptInvoke("onMoveToCancel");
			}
		}

		accelerationX = 0;
		accelerationY = 0;
		acceleration = 0;
		speedX = 0;
		speedY = 0;
		velocity = 0;
		velocityXFactor = 0;
		velocityYFactor = 0;
		velocityAngle = 0;
		terminalSpeedX = 0;
		terminalSpeedY = 0;
		terminalVelocity = 0;		
		
		moveToStartX = this.x;
		moveToStartY = this.y;
		moveToFinalX = x;
		moveToFinalY = y;
		isMoveTo = true;
		moveToType = type;
		moveToStartTime = Time.ticks;
		moveToTime = time;
		
		moveToCallback = null;
	}

	/**
	 * Linearly moves the DynamicObject to a given spot, in a given time using
	 * All previous motion is cancelled. It may be possible to apply your own velocity
	 * and acceleration changes while moveTo is running, though it should be avoided.
	 * If the object is already moving, the previous movements onCancel will be triggered if attached to a handler
	 * 
	 * @param x final X coordinate
	 * @param y final Y coordinate
	 * @param time the time 
	 */
	public void moveTo(float x, float y, long time) {
		moveTo(x, y, time, Movement.LINEAR);
	}
	
	protected void onUpdateMoveTo() {
		float position = (float)(Time.ticks - moveToStartTime) / (float)moveToTime;
		float movementFactor = Movement.getPosition(position, moveToType);
		if(position >= 1) {
			x = moveToFinalX;
			y = moveToFinalY;
			isMoveTo = false;
			if(moveToCallback != null) {
				attemptInvoke(moveToCallback);
			}
			if(parentScene.useInvoke) {
				attemptInvoke("onMoveToComplete");
			}
			accelerationX = 0;
			accelerationY = 0;
			acceleration = 0;
			speedX = 0;
			speedY = 0;
			velocity = 0;
			velocityXFactor = 0;
			velocityYFactor = 0;
			velocityAngle = 0;
			terminalSpeedX = 0;
			terminalSpeedY = 0;
			terminalVelocity = 0;
			return;
		}
		x = moveToStartX + ((moveToFinalX - moveToStartX) * movementFactor);
		y = moveToStartY + ((moveToFinalY - moveToStartY) * movementFactor);
	}

}
