package com.sykj.uusmart.utils;

import java.io.UnsupportedEncodingException;

public class DingDongAesUtil {

	/**
	 * 加密方法
	 * 
	 * @param key
	 *            秘钥
	 * @param ming
	 *            明文数据
	 * @return 加密后BASE64编码的字符串
	 * @throws Exception
	 */
	public static String encryptForNonJava(String key, String data) throws Exception {
		return BASE64Util.encode(AESUtils.encryptForNodeJs(data, key));
	}

	/**
	 * 解密方法
	 * 
	 * @param key
	 *            秘钥
	 * @param mi
	 *            密文数据，BASE64编码后的字符串
	 * @return 解密后的数据
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static String decryptForNonJava(String key, String mi) throws UnsupportedEncodingException, Exception {
		return new String(AESUtils.decryptForNodeJs(BASE64Util.decode(mi), key));
	}

	/**
	 * 加密方法
	 * 
	 * @param key
	 *            秘钥
	 * @param ming
	 *            明文数据
	 * @return 加密后BASE64编码的字符串
	 * @throws Exception
	 */
	public static String encrypt(String key, String data) throws Exception {
		return BASE64Util.encode(AESUtils.encrypt(data, key));
	}

	/**
	 * 解密方法
	 * 
	 * @param key
	 *            秘钥
	 * @param mi
	 *            密文数据，BASE64编码后的字符串
	 * @return 解密后的数据
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static String decrypt(String key, String mi)  {
		try {
			return new String(AESUtils.decrypt(BASE64Util.decode(mi), key));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
