package com.kbmc.model;

public class GenericException extends RuntimeException {
	
	private String customMessage;

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}
	 
	public GenericException(String customMessage) {
		this.customMessage = customMessage;
	}

}
