package com.example.processscannerstockmarket;

public class ScannerData {

    private String scannerType;

    private String processValue;

    private String channel;
    public ScannerData(String scannerType, String processValue,String channel) {
        this.scannerType = scannerType;
        this.processValue = processValue;
        this.channel = channel;
    }

    public String getScannerType() {
        return scannerType;
    }

    public void setScannerType(String scannerType) {
        this.scannerType = scannerType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getProcessValue() {
        return processValue;
    }

    public void setProcessValue(String processValue) {
        this.processValue = processValue;
    }
}
