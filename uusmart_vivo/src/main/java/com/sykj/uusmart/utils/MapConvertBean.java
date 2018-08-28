package com.sykj.uusmart.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * map转换为java bean 
 * 或bean 转换为map 工具
 * @author Administrator
 *
 */
public class MapConvertBean {  
  

    /**
     * List<Map<String , Object >> 转换为 List<T>
     * @param c
     * @param dataMap
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> List<T> transMap2Bean2( Class<T> c,  List<Map<String , Object >> dataMap) throws IllegalAccessException, InvocationTargetException, InstantiationException {  
    	if(dataMap .size() == 0 ) return null ;
    	List<T> resultList = new ArrayList<T>();
		//调用泛型方法
    	for (Map<String , Object > map : dataMap) {
    		T obj = c.newInstance();
    		BeanUtils.populate(obj, map);  
    		if (obj != null ) resultList.add(  obj);
    	}
    	return resultList;
    }

    /**
     * List<Map<String , Object >> 转换为 List<T>
     * @param javaBeanList
     */
    public static  <T>   List<Map<String , Object>> transBean2Map(    List< T > javaBeanList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Map<String , Object>> resultList = new ArrayList<Map<String, Object>>( );
       if(javaBeanList == null )
           return  resultList;
        for ( int i = 0; i <javaBeanList.size() ; i ++) {
            resultList.add(BeanUtils.describe( javaBeanList.get(i)));
        }
        return resultList;
    }

    public static <T>T Map2Bean2(Class<T> c, Map<String, Object> map ) {

        T obj = null;
        try {
            obj = c.newInstance();
            BeanUtils.populate(obj, map);
            return obj;
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
        return obj;
    }


    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean  
    public static void transMap2Bean2(Map<String, Object> map, Object obj) throws IllegalAccessException, InvocationTargetException {  
        if (map == null || obj == null) {  
            return;  
        }  
        BeanUtils.populate(obj, map);  
    }  
  
    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
  
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    public static Map<String, Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
}  