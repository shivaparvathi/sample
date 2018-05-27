package com.kbmc.model;

import java.util.Date;

/**
 * Offer Model class
 * 
 * @author ZONE24X7
 */
public class Offer {
	private long offerId;
	private String couponCode;
	private String description;
	private String iconURL;
	private Date startDate;
	private Date expiryDate;
	private Double discountPercentage;
	private String passbookURL;
	private String title;
	private String feedImageURL;

	public String getFeedImageURL() {
		return feedImageURL;
	}

	public void setFeedImageURL(String feedImageURL) {
		this.feedImageURL = feedImageURL;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getPassbookURL() {
		return passbookURL;
	}

	public void setPassbookURL(String passbookURL) {
		this.passbookURL = passbookURL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
