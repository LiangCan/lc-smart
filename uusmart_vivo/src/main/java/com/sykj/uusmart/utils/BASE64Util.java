package com.sykj.uusmart.utils;

import org.apache.commons.codec.binary.Base64;

public class BASE64Util {

	/**
	 * BASE64 编码
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws Exception {
		Base64 base64Encoder = new Base64();
		byte[] resultbyte = base64Encoder.encode(bytes);
		return new String(resultbyte);
	}

	/**
	 * BASE64 解码
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(String data) throws Exception {
		Base64 decoder = new Base64();
		return decoder.decode(data.getBytes());
	}
}
