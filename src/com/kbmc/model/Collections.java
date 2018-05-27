package com.kbmc.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Collections Model class. @author ZONE24X7
 */

public class Collections {

	private long collectionId;
	private String name;
	private String description;
	private String collectionType;
	private Date startDate;
	private Date endDate;
	private Boolean active;
	private ArrayList<Long> arrangementId;
	private long locationKey;
	private ArrayList<String> channelName;

	public ArrayList<String> getChannelName() {
		return channelName;
	}

	public void setChannelName(ArrayList<String> channelName) {
		this.channelName = channelName;
	}

	public long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public ArrayList<Long> getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(ArrayList<Long> arrangementId) {
		this.arrangementId = arrangementId;
	}

	public long getLocationKey() {
		return locationKey;
	}

	public void setLocationKey(long locationKey) {
		this.locationKey = locationKey;
	}

}
