/**
  * Copyright 2018 bejson.com 
  */
package com.sykj.uusmart.http.baidu;


public class BDHeaderDTO {

    private String namespace;
    private String name;
    private String messageId;
    private String payloadVersion;
    public void setNamespace(String namespace) {
         this.namespace = namespace;
     }
     public String getNamespace() {
         return namespace;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setMessageId(String messageId) {
         this.messageId = messageId;
     }
     public String getMessageId() {
         return messageId;
     }

    public void setPayloadVersion(String payloadVersion) {
         this.payloadVersion = payloadVersion;
     }
     public String getPayloadVersion() {
         return payloadVersion;
     }

}