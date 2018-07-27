/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2018-07-06 16:51:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RespBDDiscoveredDTO {

    private List<RespDBDiscoveredAppliances> discoveredAppliances;
    private List<RespBDDiscoveredGroups> discoveredGroups = new ArrayList<>();

    public void setDiscoveredAppliances(List<RespDBDiscoveredAppliances> discoveredAppliances) {
         this.discoveredAppliances = discoveredAppliances;
     }
     public List<RespDBDiscoveredAppliances> getDiscoveredAppliances() {
         return discoveredAppliances;
     }

    public void setDiscoveredGroups(List<RespBDDiscoveredGroups> discoveredGroups) {
         this.discoveredGroups = discoveredGroups;
     }
     public List<RespBDDiscoveredGroups> getDiscoveredGroups() {
         return discoveredGroups;
     }

}