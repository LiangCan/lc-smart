package com.sykj.uusmart.utils;

import java.security.MessageDigest;

/**  */
public class MD5Utils {

    /**
     * 32位MD5加密
     * @param plainText
     * @return
     */
    public static String toMD5(String plainText) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return   buf.toString();// 32位的加密
//            System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public static void main(String agrs[]) {
//        System.out.println(System.currentTimeMillis());
//
//        for(int i=0;i<1000;i++){
//            String ss =  toMD5("123456");
//            System.out.println( ss + "  = " + ss.length());
//        }
//        System.out.println(System.currentTimeMillis());


//        String jsonStr = "{\"cmd\":22,\"src\":30,\"dest\":10,\"dir\":0,\"deviceID\":10000,\"responseTime\":4294967295.0\n,\"code\":0,\"msg\":{\"newVersion\":\"VXXX.XXX \",\"isUpdated\":0,\"downloadUrl\":\"fw.isesmart.com/smart_ac_usb\"}}";
//        Test test = GsonUtils.toObj(jsonStr, Test.class);
//        LogUtils.info(test.getResponseTime() + "");

//    }

}
