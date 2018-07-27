package com.sykj.uusmart.utils;

/**
 * Created by Administrator on 2016/11/16.
 */
public class RegularExpressionUtils {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证 1，2，3，
     */
    public static final String NUMBER_DOUHAO = "[[0-9]*+,+[0-9]]*";

    /**
     * 正则表达式：验证 字母1位/数字字母，
     */
    public static final String NUMBER_XIEGAN = "[a-z]{1}+/+[0-9a-zA-Z]*";

    /**
     * 正则表达式：验证16进制字符串
     */
    public static final String NUMBER_SIXTEEN = "^[A-Fa-f0-9]+$";

    /**
     * 正则表达式：验证 1.2.3
     */
    public static final String NUMBER_DIAN = "[[0-9]*+.+[0-9]]*";

    /** 校验数字 */
    public static final String REGEX_NUMBER =  "^[0-9]*$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\";

    /**
     * 正则表达式：验证是否全英文
     */
    public static final String REGEX_ENGLISH ="^([a-zA-Z ]+|[\u4e00-\u9fa5]+)$";


    /**
     * 正则表达式：验证是否带有特殊字符(不包含下划线, 空格，等其他)
     */
    public static final String PEGEX_EX_STR = "[^`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、]*";

    public static void main(String args []){
        String str = "s/deviceSer";
        System.out.println("result :" + str.matches(NUMBER_XIEGAN));
    }
}
