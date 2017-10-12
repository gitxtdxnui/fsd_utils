package com.feishidai.common.utils;

import java.io.IOException;
import java.security.MessageDigest;

public class MD5Utils {

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// System.out.println((int)b);
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String BASE64Encoder(String bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr.getBytes());
	}

	public static String BASE64Decoder(String str) {
		String result = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] bt = decoder.decodeBuffer(str);
			result = new String(bt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String a = "YXNkZjEyMw==";
		System.out.println(MD5Utils.BASE64Decoder(a));
	}
}
