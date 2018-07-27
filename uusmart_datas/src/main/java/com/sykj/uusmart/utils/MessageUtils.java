/**
 * @Title: MessageUtils.java
 * @Package com.yijucloud.common.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author lichao
 * @date 2015-7-29 下午2:09:20
 * @version V1.0
 */
package com.sykj.uusmart.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @ClassName: MessageUtils
 * @Description: 获取资源文件信息
 * @author liangcan
 * @date 2015-7-29 下午2:09:20
 * @version 1.0
 */
@Component
public class MessageUtils {
    /**
     * 语言
     */
    private static Locale language = Locale.CHINA;

    /**
     * 注入messageSource
     */
    @Autowired
    private MessageSource messageSource;

    /**
     *
     * @Title: getMessage
     * @Description: 获取资源信息
     * @param @param key
     * @param @param params
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public String getMessage(String key, Object[] params) {
        return messageSource.getMessage(key, params, language);
    }

    /**
     * @Title: getMessage
     * @Description: 获取资源信息
     * @param @param key
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public String getMessage(String key) {
        return getMessage(key, null);
    }

    /**
     * @param language
     *            the language to set
     */
    public static void setLanguage(Locale language) {
        MessageUtils.language = language;
    }
}
