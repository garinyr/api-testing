package com.afterofficetest.model.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseListUpdateObjectData {
    private String year;
    private int price;
    private String color;
    private String capacity;
    private String screen_size;

    @JsonProperty("CPU model")
    private String cpuModel;

    @JsonProperty("Hard disk size")
    private String hardDiskSize;

    public ResponseListUpdateObjectData() {
    }

    public ResponseListUpdateObjectData(String year, int price, String cpuModel,
            String hardDiskSize, String color, String capacity, String screen_size) {
        this.year = year;
        this.price = price;
        this.cpuModel = cpuModel;
        this.hardDiskSize = hardDiskSize;
        this.color = color;
        this.capacity = capacity;
        this.screen_size = screen_size;
    }

    public String getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public String getcpuModel() {
        return cpuModel;
    }

    public String gethardDiskSize() {
        return hardDiskSize;
    }

    public String getColor() {
        return color;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getScreen_size() {
        return screen_size;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setcpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public void sethardDiskSize(String hardDiskSize) {
        this.hardDiskSize = hardDiskSize;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setScreen_size(String screen_size) {
        this.screen_size = screen_size;
    }
}
