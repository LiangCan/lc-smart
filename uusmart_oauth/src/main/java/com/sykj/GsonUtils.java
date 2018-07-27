/**
 * @Title: GsonUtils.java
 * @Package com.yijucloud.iotlife.util
 * @Description: json转换帮助类
 * @author Henry
 * @date 2015年7月28日 下午3:28:44
 * @version V1.0
 */
package com.sykj;

import com.google.gson.Gson;

/**
 * @ClassName: GsonUtils
 * @Description: json转换帮助类
 * @date 2015年7月28日 下午3:28:44
 * @version 1.0
 */
public class GsonUtils {
    /**
     * gson对象
     */
    private static Gson gs = new Gson();

    /**
     * @Title: toJSON
     * @Description: 对象转JSON字符串
     * @param @param obj
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String toJSON(Object obj) {
        return gs.toJson(obj);
    }

    /**
     * @Title: toObj
     * @Description: JSON字符串转对象
     * @param @param obj
     * @param @param t
     * @param @return 设定文件
     * @return T 返回类型
     * @throws
     */
    public static <T> T toObj(String obj, Class<T> t) {
        try {
            return gs.fromJson(obj, t);
        }catch (Exception e){
            return null;
        }

    }
}
