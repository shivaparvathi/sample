package com.kbmc.model;
/**
 * @author ZONE24X7
 */
public enum Status {
	SUCCESS,FAILURE,RECORD_ALREADY_EXISTS,CONSTRAINT_VIOLATION,NO_DATA,INVALID_PARAMETERS;
	
	public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumeration, String name) {
		for(T enumValue : enumeration.getEnumConstants()) {
	        if(enumValue.name().equalsIgnoreCase(name)) {
	            return enumValue;
	        }
	    }
	    throw new IllegalArgumentException("There is no value with name '" + name + "' in Enum " + enumeration.getClass().getName());        
	}
};
