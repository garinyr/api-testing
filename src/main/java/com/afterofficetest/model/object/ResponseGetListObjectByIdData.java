package com.afterofficetest.model.object;

public class ResponseGetListObjectByIdData {
   private String year;
   private String price;
   private String cpu_model;
   private String hard_disk_size;
   private String color;
   private int capacity;
   private int screen_size;

   public ResponseGetListObjectByIdData() {
   }

   public ResponseGetListObjectByIdData(String year, String price, String cpu_model,
         String hard_disk_size, String color, int capacity, int screen_size) {
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

   public int getCapacity() {
      return capacity;
   }

   public int getScreen_size() {
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

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public void setScreen_size(int screen_size) {
      this.screen_size = screen_size;
   }
}