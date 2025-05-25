package com.afterofficetest.model.object;

public class ResponseListAddObjectData {
    private String year;
    private String price;
    private String cpu_model;
    private String hard_disk_size;
    private String color;
    private String capacity;
    private String screen_size;

    public ResponseListAddObjectData() {
    }

    public ResponseListAddObjectData(String year, String price, String cpu_model,
            String hard_disk_size, String color, String capacity, String screen_size) {
        this.year = year;
        this.price = price;
        this.cpu_model = cpu_model;
        this.hard_disk_size = hard_disk_size;
        this.color = color;
        this.capacity = capacity;
        this.screen_size = screen_size;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public String getCpu_model() {
        return cpu_model;
    }

    public String getHard_disk_size() {
        return hard_disk_size;
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

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCpu_model(String cpu_model) {
        this.cpu_model = cpu_model;
    }

    public void setHard_disk_size(String hard_disk_size) {
        this.hard_disk_size = hard_disk_size;
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
