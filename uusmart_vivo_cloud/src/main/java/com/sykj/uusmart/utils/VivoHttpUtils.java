package com.sykj.uusmart.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 *	此方法只针对 vivo http 请求 封装的 http 工具类；
 *	请求头参数修改过；
 *	lgf
 *	2018年8月17日16:35:12
 */
public class VivoHttpUtils {
//	private static final String clientSecret = "e8e07d98054d47b5bd96b3f6b8751d80";
	/**
	 * http post
	 * @param jsonParam	请求参数 json 格式
	 * @param urls			请求地址
	 * @param headMap		请求头map，可以为null
	 * @return				json格式的String 字符串
	 */
	public static String httpPost(JSONObject jsonParam, String urls , Map<String ,String > headMap , String appKey) {
		StringBuffer sb=new StringBuffer();
		try {
			;
			// 创建url资源
			URL url = new URL(urls);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);
			// 设置允许输入
			conn.setDoInput(true);
			// 设置不用缓存
			conn.setUseCaches(false);
			// 设置传递方式
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置文件字符集:
			conn.setRequestProperty("Charset", "UTF-8");

			if(headMap != null){
				String signature = "";
				Iterator<Map.Entry<String ,String >> entries = headMap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<String ,String > entry = entries.next();
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
					conn.setRequestProperty( entry.getKey() , entry.getValue());
					signature+=entry.getKey()+"="+entry.getValue()+"&";
				}
				signature = signature.substring(0,signature.lastIndexOf("&"));
				conn.setRequestProperty("signature",   VivoSHA256Util.encrytSHA256(signature+jsonParam.toJSONString(),appKey));
				conn.setRequestProperty("hosts","iot-api-test.vivo.com.cn");

				System.out.println("待加密参数为："+signature+jsonParam.toJSONString()+"；秘钥为：" +appKey);
				System.out.println("加密后："+VivoSHA256Util.encrytSHA256(signature+jsonParam.toJSONString(),appKey));
			}

			// 转换为字节数组
			byte[] data = (jsonParam.toString()).getBytes();
			// 设置文件长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));
			// 设置文件类型:
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			// 开始连接请求
			conn.connect();
			OutputStream out = new DataOutputStream(conn.getOutputStream()) ;
			// 写入请求的字符串
			out.write((jsonParam.toString()).getBytes());
			out.flush();
			out.close();
 
			System.out.println(conn.getResponseCode());
			
			// 请求返回的状态
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()){
//				System.out.println("连接成功");
				// 请求返回的数据
				InputStream in1 = conn.getInputStream();
				try {
				      String readLine=new String();
				      BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
				      while((readLine=responseReader.readLine())!=null){
				        sb.append(readLine).append("\n");
				      }
				      responseReader.close();
				      System.out.println(sb.toString());
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				System.out.println("error++");
				
			}
 
		} catch (Exception e) {
 
		}
		
		return sb.toString();
 
	}

	public static String sendPostRequest(String url,String param){
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; //写
		InputStream in = null;   //读
		int responseCode = 0;    //远程主机响应的HTTP状态码
		String result="";
		try{
			URL sendUrl = new URL(url);
			httpURLConnection = (HttpURLConnection)sendUrl.openConnection();
			//post方式请求
			httpURLConnection.setRequestMethod("POST");
			//设置头部信息
			httpURLConnection.setRequestProperty("headerdata", "ceshiyongde");
			//一定要设置 Content-Type 要不然服务端接收不到参数
			httpURLConnection.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
			//指示应用程序要将数据写入URL连接,其值默认为false（是否传参）
			httpURLConnection.setDoOutput(true);
			//httpURLConnection.setDoInput(true);

			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); //30秒连接超时
			httpURLConnection.setReadTimeout(30000);    //30秒读取超时
			//获取输出流
			out = httpURLConnection.getOutputStream();
			//输出流里写入POST参数
			out.write(param.getBytes());
			out.flush();
			out.close();
			responseCode = httpURLConnection.getResponseCode();
			BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
			result =br.readLine();
		}catch(Exception e) {
			e.printStackTrace();

		}
		return result;

	}

}