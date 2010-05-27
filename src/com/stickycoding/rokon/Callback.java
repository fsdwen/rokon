package com.stickycoding.rokon;

/**
 * Callback.java
 * Provies a reference for a Callback for invoking Methods
 * 
 * @author Richard
 */
public class Callback {
	
	protected String methodName;
	protected Class<?>[] parameterTypes;
	protected Object[] parameters;
	
	public Callback(String methodName) {
		this.methodName = methodName;
	}
	
	public Callback(String methodName, Object parameter) {
		this.methodName = methodName;
		this.parameters = new Object[] { parameter };
	}
	
	public Callback(String methodName, Object[] parameters) {
		this.methodName = methodName;
		this.parameters = parameters;
	}
	
	public Callback(String methodName, Class<?> parameterType, Object parameter) {
		this.methodName = methodName;
		this.parameterTypes = new Class<?>[] { parameterType };
		this.parameters = new Object[] { parameter };
	}
	
	public Callback(String methodName, Class<?>[] parameterTypes, Object[] parameters) {
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public Class<?>[] getParameterTypes() {
		return parameterTypes;		
	}
	
	public Object[] getParameters() {
		return parameters;
	}

}
