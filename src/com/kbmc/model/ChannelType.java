package com.kbmc.model;

/**
 * ChannelType Model class.
 * 
 * @author ZONE24X7
 * 
 */

public class ChannelType {

	private long id;
	private String name;
	private String cronExpression;
	private String parameters;
	private String status;
	private long backlog;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getBacklog() {
		return backlog;
	}
	public void setBacklog(long backlog) {
		this.backlog = backlog;
	}
	
	
	
}
