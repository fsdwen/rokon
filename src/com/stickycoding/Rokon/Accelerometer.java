package com.stickycoding.Rokon;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.stickycoding.Rokon.Handlers.AccelerometerHandler;

/**
 * An interface to manage the Accelerometer sensor
 * Allows the game to extend AccelerometerHandler and 
 * manage events triggered by changes or shakes.
 * @author Richard
 */
public class Accelerometer {
	
	private static boolean _running = false;
	private static Sensor _sensor;
	private static AccelerometerHandler _accelerometerHandler;
	private static int _shakeThreshold = 3000;
	private static long _lastUpdate = 0;
	private static long _lastShake = 0;
	private static SensorManager sensorManager;
	private static int _minInterval = 1000;
	
	public static float x = 0;
	public static float y = 0;
	public static float z = 0;
	private static float _lastX = 0;
	private static float _lastY = 0;
	private static float _lastZ = 0;
	
	/**
	 * Sets the minimum interval between firing onShake events, default is 1000
	 * @param interval time in milliseconds
	 */
	public static void setShakeInterval(int interval) {
		_minInterval = interval;
	}
	
	/**
	 * Returns the minimum interval between firing onShake events
	 * @return int time in milliseconds
	 */
	public static int getShakeInterval() {
		return _minInterval;
	}
	
	/**
	 * Sets the minimum intensity of a shake to fire an onShake event, default is 3000
	 * @param threshold minimum threshold
	 */
	public static void setShakeThreshold(int threshold) {
		_shakeThreshold = threshold;
	}
	
	/**
	 * Returns the minimum intensity of a shake to fire an onShake event
	 * @return minimum threshold
	 */
	public static int getShakeThreshold() {
		return _shakeThreshold;
	}
	
	/**
	 * @return TRUE if an AccelerometerListener is active, FALSE if no listener is set
	 */
	public static boolean isListening() {
		return _running;
	}
	
	/**
	 * Removes the AccelerometerListener, it is good practice to do this when it is not needed to save CPU power.
	 */
	public static void stopListening() {
		_running = false;
		try {
			if(_sensorEventListener != null)
				sensorManager.unregisterListener(_sensorEventListener);
		} catch (Exception e) { }
	}
	
	/**
	 * Starts listening for accelerometer changes and shakes.
	 * @param accelerometerHandler Your instance of AccelerometerHandler
	 */
	public static void startListening(AccelerometerHandler accelerometerHandler) {
		sensorManager = (SensorManager)Rokon.getRokon().getActivity().getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensors.size() > 0)
			_sensor = sensors.get(0);
		sensorManager.registerListener(_sensorEventListener, _sensor, SensorManager.SENSOR_DELAY_GAME);
		_accelerometerHandler = accelerometerHandler;
	}
	
	private static SensorEventListener _sensorEventListener = new SensorEventListener() {

		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
		
		private long now, timeDiff;
		private float speed;
		public void onSensorChanged(SensorEvent event) {
			now = Rokon.getTime();
			
    		x = event.values[0];
    		y = event.values[1];
    		z = event.values[2];
    		
    		if(_lastUpdate == 0) {
    			_lastUpdate = now;
    			_lastShake = now;
    			_lastX = x;
    			_lastY = y;
    			_lastZ = z;
    		} else {
    			timeDiff = now - _lastUpdate;
    			if(timeDiff > 0) {
	    			speed = Math.abs(x + y + z - _lastX - _lastY - _lastZ) / timeDiff * 10000;
	    			if(speed > _shakeThreshold) {
	    				if(now - _lastShake >= _minInterval)
		    				_accelerometerHandler.onShake(speed);
	    				_lastShake = now;
	    			}
	        		_lastX = x;
	        		_lastY = y;
	        		_lastZ = z;
	        		_lastUpdate = now;
    			}
    		}
    		_accelerometerHandler.onChanged(x, y, z);
		}
		
	};

}
