/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;
import java.util.Date;

/**
 * Auto-generated: 2018-07-06 16:51:3
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RespBDDiscoveredGroups {

    private String groupName;
    private Date applianceIds;
    private String groupNotes;
    private String[] additionalGroupDetails ={};
    public void setGroupName(String groupName) {
         this.groupName = groupName;
     }
     public String getGroupName() {
         return groupName;
     }

    public void setApplianceIds(Date applianceIds) {
         this.applianceIds = applianceIds;
     }
     public Date getApplianceIds() {
         return applianceIds;
     }

    public void setGroupNotes(String groupNotes) {
         this.groupNotes = groupNotes;
     }
     public String getGroupNotes() {
         return groupNotes;
     }

    public String[] getAdditionalGroupDetails() {
        return additionalGroupDetails;
    }

    public void setAdditionalGroupDetails(String[] additionalGroupDetails) {
        this.additionalGroupDetails = additionalGroupDetails;
    }
}