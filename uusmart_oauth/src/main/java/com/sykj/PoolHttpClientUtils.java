package com.sykj;



import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author liangcan
 * @version 1.0
 * @ClassName: PoolHttpClientUtils
 * @Description: http帮助类
 * @date 2014-3-16 下午2:46:23
 */
public class PoolHttpClientUtils {
    /**
     * 设置请求超时
     */
    private static final int REQUEST_TIMEOUT = 10 * 1000;

    /**
     * 连接超时
     */
    private static final int SO_TIMEOUT = 60 * 1000;

    /**
     * 每个主机的最大并行链接数
     */
    private static final int MAX_PER_ROUTE = 1000;

    /**
     * 客户端总并行链接最大数
     */
    private static final int MAX_TOTAL = 15000;

    private static final int DEFAULTMAXCONNECTION = 300;

    private static HttpClientBuilder httpBulder = null;

    private static RequestConfig requestConfig = null;

    private static PoolingHttpClientConnectionManager pccm = null;

    /**
     * 初始化
     */
    static {
        try {
            SSLContext sslContext = SSLContexts.custom().useTLS().build();
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {

                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {

                }
            }}, null);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", new SSLConnectionSocketFactory(sslContext)).build();
            pccm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        } catch (Exception e) {
           System.out.println("连接线程发生异常 PoolHttpClientUtils:{}");
        }

        requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT).setConnectTimeout(REQUEST_TIMEOUT).setConnectionRequestTimeout(REQUEST_TIMEOUT).build();

        // 多连接的线程安全的管理器
        pccm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        pccm.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
        pccm.setMaxTotal(MAX_TOTAL);

        httpBulder = HttpClients.custom();
        httpBulder.setConnectionManager(pccm);
    }

    public static CloseableHttpClient getConnection() {
        return httpBulder.build();
    }

    /**
     * json格式请求发送数据
     */
    public static String post(String url, String params) {
        HttpUriRequest post = null;
        StringEntity jsonStrData = new StringEntity(params, "UTF-8");
        jsonStrData.setContentType("application/json");
        post = RequestBuilder.post().setUri(url).setEntity(jsonStrData).setConfig(requestConfig).build();
        return getRespData(post);
    }

    public static String getRespData(HttpUriRequest post)  {
        CloseableHttpResponse response = null;
        try {
            response = getConnection().execute(post);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != 200) {
                post.abort();
            }
            if (entity != null) {
                String str = EntityUtils.toString(entity, "UTF-8");
                return str;
            }
        } catch (ClientProtocolException e) {
            System.out.println("链接 http 异常" + e);
        } catch (IOException e) {
            System.out.println("http 发送 异常" + e);
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    System.out.println("关闭 http 异常" + e);
                }

                releaseConn();
            }
        }
        return null;
    }

    /**
     * 执行post请求
     */
    public static String post(String url, Map<String, String> params) throws Exception {
        HttpUriRequest post = null;
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            pairList.add(pair);
        }
        post = RequestBuilder.post().setUri(url).setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8"))).setConfig(requestConfig).build();
        return getRespData(post);
    }

    /**
     * @return void 返回类型
     * @Title: releaseConn
     * @Description: 释放连接
     */
    public static void releaseConn() {
        if (null != pccm) {
            pccm.closeExpiredConnections();
            pccm.closeIdleConnections(5, TimeUnit.SECONDS);
        }
    }
//
//    public static void main(String[] args) throws Exception {
//        PushAppMessageDTO pa = new PushAppMessageDTO();
//        pa.setKey("10001");
//        pa.setRequestTime(String.valueOf(System.currentTimeMillis()));
//        pa.setUserId(String.valueOf(10));
//        pa.setData("ABCDEFG");
//        System.out.println("result === " + post("http://127.0.0.1:8080/push/app/msg.do", GsonUtils.toJSON(pa)));
//    }

}
