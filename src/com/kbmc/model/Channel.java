package com.kbmc.model;

import java.util.ArrayList;

/**
 * Channel Model Class
 * 
 * @author ZONE24X7
 */
public class Channel {

	private Long channelId;
	private String name;
	private String status;
	private String image;
	private String thumbnailImage;
	private String description;
	private String channelType;
	private ArrayList<Integer> collectionId;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public ArrayList<Integer> getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(ArrayList<Integer> collectionId) {
		this.collectionId = collectionId;
	}

}
