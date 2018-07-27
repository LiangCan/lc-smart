package com.sykj;

public class Router {
    private int encryption;
    private String ssid;
    private int rssi;
    private String mac;
    private int channel;

    public Router(int encryption,String ssid,int rssi,String mac,int channel){
        this.encryption = encryption;
        this.ssid = ssid;
        this.rssi = rssi;
        this.mac = mac;
        this.channel = channel;
    }

    public int getEncryption() {
        return encryption;
    }

    public void setEncryption(int encryption) {
        this.encryption = encryption;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}