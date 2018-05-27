package com.kbmc.model;

import org.json.simple.JSONObject;

/**
 * Location Model class
 * 
 * @author ZONE24X7
 */
public class Location {

	private String name;
	private double latitude;
	private double longitude;
	private double radius;
	private String storeId;
	private String locationKey;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getLocationKey() {
		return locationKey;
	}

	public void setLocationKey(String locationKey) {
		this.locationKey = locationKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();

		obj.put("locationKey", locationKey);
		obj.put("storeId", storeId);
		obj.put("locationName", name);
		obj.put("latitude", latitude);
		obj.put("longitude", longitude);
		obj.put("radius", radius);

		return obj.toJSONString();
	}

}
