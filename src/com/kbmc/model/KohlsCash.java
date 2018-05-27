package com.kbmc.model;

/**
 * KohlsCash Model Class
 * 
 * @author ZONE24X7
 */
public class KohlsCash {
	private long couponId;
	private String name;
	private String couponCode;

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

}
