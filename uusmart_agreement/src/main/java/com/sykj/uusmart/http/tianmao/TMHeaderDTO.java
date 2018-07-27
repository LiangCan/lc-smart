package com.sykj.uusmart.http.tianmao;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
public class TMHeaderDTO {

    String namespace;
    String name;
    String messageId;
    String payLoadVersion;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPayLoadVersion() {
        return payLoadVersion;
    }

    public void setPayLoadVersion(String payLoadVersion) {
        this.payLoadVersion = payLoadVersion;
    }

    @Override
    public String toString() {
        return "TMHeaderDTO{" +
                "namespace='" + namespace + '\'' +
                ", name='" + name + '\'' +
                ", messageId='" + messageId + '\'' +
                ", payLoadVersion='" + payLoadVersion + '\'' +
                '}';
    }
}
