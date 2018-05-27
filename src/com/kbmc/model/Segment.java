package com.kbmc.model;

import org.json.simple.JSONObject;

/**
 * Segment Model class
 * 
 * @author ZONE24X7
 */
public class Segment {

	private long id;
	private String name;
	private String description;
	private Object segmentMetadata;

	public Object getSegmentMetadata() {
		return segmentMetadata;
	}

	public void setSegmentMetadata(Object segmentMetadata) {
		this.segmentMetadata = segmentMetadata;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		obj.put("segmentId", id);
		obj.put("segmentName", name);
		obj.put("description", description);
		obj.put("segmentMetadata", segmentMetadata);
		return obj.toJSONString();
	}

}
