package com.beyang.cn.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class DigestUtil {
	private static final String MD5_ALGORITHM_NAME = "MD5";
	private static final String ENCODING = "UTF-8";

	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
		}
	}

	private static byte[] digest(String algorithm, byte[] bytes) {
		return getDigest(algorithm).digest(bytes);
	}
	
	private static byte[] md5(byte[] bytes){
		return digest(MD5_ALGORITHM_NAME, bytes);
	}
	
	public static String md5(String string){
		try {
			return new String(md5(string.getBytes(ENCODING)), ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
