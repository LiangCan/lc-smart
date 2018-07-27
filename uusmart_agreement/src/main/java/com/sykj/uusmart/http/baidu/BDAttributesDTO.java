/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;

/**
 * Auto-generated: 2018-07-06 16:51:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BDAttributesDTO {

    private String name;
    private String value;
    private String scale;
    private long timestampOfSample;
    private int uncertaintyInMilliseconds;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setValue(String value) {
         this.value = value;
     }
     public String getValue() {
         return value;
     }

    public void setScale(String scale) {
         this.scale = scale;
     }
     public String getScale() {
         return scale;
     }

    public void setTimestampOfSample(long timestampOfSample) {
         this.timestampOfSample = timestampOfSample;
     }
     public long getTimestampOfSample() {
         return timestampOfSample;
     }

    public void setUncertaintyInMilliseconds(int uncertaintyInMilliseconds) {
         this.uncertaintyInMilliseconds = uncertaintyInMilliseconds;
     }
     public int getUncertaintyInMilliseconds() {
         return uncertaintyInMilliseconds;
     }

}