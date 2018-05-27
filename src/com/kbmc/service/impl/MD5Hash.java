package com.kbmc.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

import com.kbmc.model.GenericException;
/**
 * This class can handle the MD5 hashing mechanism
 * 
 * @author ZONE24X7
 */
public class MD5Hash {
	
	private static final Logger log = Logger
			.getLogger(MD5Hash.class);
	
	public String MD5Encrypt(String password){
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			password = sb.toString();
		} catch (NoSuchAlgorithmException exception) {
			log.error("MD5Encrypt "+exception);
			throw new GenericException(exception.getMessage());
		}
		return password;
	}
}
