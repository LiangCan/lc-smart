package com.sykj.uusmart.utils;

import com.sykj.uusmart.pojo.NexusUserDevice;

import java.util.List;

public class BoyerMoore {
    /**
     * 计算滑动距离
     * @param c 主串（源串）中的字符
     * @param T 模式串（目标串）字符数组
     * @param noMatchPos 上次不匹配的位置
     * @return 滑动距离
     */
    private static int dist(char c, char T[], int noMatchPos) {
        int n = T.length;

        for (int i = noMatchPos; i >= 1; i--) {
            if (T[i - 1] == c) {
                return n - i;
            }
        }

        // c不出现在模式中时
        return n;
    }

    /**
     * 找出指定字符串在目标字符串中的位置
     * @param source 目标字符串
     * @param pattern 指定字符串
     * @return 指定字符串在目标字符串中的位置
     */
    public static double match(String source, String pattern) {
        //"分类啊"  "风类啊"
        char[] str1 = source.toCharArray();
        char[] str2 = pattern.toCharArray();
        double result = 0;
        int i = 0;
        for(int j = 0; j< str1.length; j ++){

            if(i >= str2.length ){
                return result/(double)str1.length;
            }

            if(CharactorTool.getFullSpell(String.valueOf(str1[j])).equals(CharactorTool.getFullSpell(String.valueOf(str2[i])))){
                result++;
                i++;
            }else{
                if(i > 0){
                    return result/(double)str1.length;
                }
                i = 0;
            }

            if(i < str2.length && j ==(str1.length -1)){
                j = 0; i ++;
            }
        }
        return result/(double)str1.length;
    }


    public static NexusUserDevice matchingDingDongNexusUserDevice(List<NexusUserDevice> uex, String aleaxName){
        double max = 0.0;
        NexusUserDevice nexusUserDevice = new NexusUserDevice();
        double ing = 0.0;
        for(NexusUserDevice nudIng : uex){
            double one = match(nudIng.getRemarks(), aleaxName);
            double two = match(aleaxName, nudIng.getRemarks());
            ing =  one > two ? one : two;
            if(ing > max){
                max = ing;
                nexusUserDevice = nudIng;
            }
            if(ing == 1.0){
                return nexusUserDevice;
            }
        }
        if(max < 0.5){
            nexusUserDevice = null;
        }
        return nexusUserDevice;
    }



}