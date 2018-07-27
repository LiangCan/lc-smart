package com.sykj.uusmart.utils;

import java.util.Calendar;

/**
 * 时间工具类
 * lgf
 * 2018年7月18日09:34:56
 */
public class TimeUtils {

    /**
     * 获取距离第二天的凌晨 12点的秒数；
     * @return 秒数 long
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Long seconds = (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        return seconds.longValue();
    }
}
