package com.chaowei.mobileguard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5PWDUtils {

	public static String encode(String password){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("md5");
			byte[] result = messageDigest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff - 2;
				String str = Integer.toHexString(number);
				if (str.length() == 1)
					sb.append("0");
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
