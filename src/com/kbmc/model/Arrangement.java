package com.kbmc.model;

/**
 * Arrangement Model class
 * 
 * @author ZONE24X7
 */

public class Arrangement {

	private long arrangementId;
	private String name;
	private boolean active;
	private String locationKey;
	private long segmentId;

	public long getArrangementId() {
		return arrangementId;
	}

	public void setArrangementId(long arrangementId) {
		this.arrangementId = arrangementId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLocationKey() {
		return locationKey;
	}

	public void setLocationKey(String locationKey) {
		this.locationKey = locationKey;
	}

	public long getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(long segmentId) {
		this.segmentId = segmentId;
	}

}
